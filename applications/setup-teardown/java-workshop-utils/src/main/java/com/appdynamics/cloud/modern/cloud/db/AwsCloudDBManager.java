/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.CreateDBInstanceRequest;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DeleteDBInstanceRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.amazonaws.services.rds.model.Endpoint;
import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.cloud.CloudHelper;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityGroup;



/**
 * @author James Schneider
 *
 */
public class AwsCloudDBManager implements CloudDBManager {

	public static Logger logr = new Logger(AwsCloudDBManager.class.getSimpleName());
	private AmazonRDS rds;
	private Map<String, CloudDBInstance> instances = new HashMap<String, CloudDBInstance>();
	
	/**
	 * 
	 */
	public AwsCloudDBManager() throws CloudDBException {
		try {
			
			this.rds = AmazonRDSClientBuilder.standard().withRegion(Regions.fromName(CloudHelper.getCurrentCloudRegion())).build();
			

//			AWSCredentialsProvider credentials = new
//	                AWSStaticCredentialsProvider(new
//	                        BasicAWSCredentials("<ACCESS_KEY>","<SECRET_KEY>"));			
			

			//rds = AmazonRDSClientBuilder.standard().withCredentials(credentials).withRegion(Regions.US_WEST_1).build();
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudDBException(ex);
		}		
	}
	
	
	@Override
	public CloudDBInstance createDBInstance(String instanceName, String instanceType, int instancePort, String instanceUser, String instancePassword, CloudSecurityGroup securityGroup) throws CloudDBException {
		instanceName = instanceName.toLowerCase();
		try {
			if (this.instances.containsKey(instanceName)) {
				
				return this.instances.get(instanceName);
				
			} else {
				
				logr.info(" - Creating Cloud Database Instance : " + instanceName);
				
				List<String> secureGroups = new ArrayList<String>();
				secureGroups.add(securityGroup.getGroupId());
				
		        //String identifier = "";
		        CreateDBInstanceRequest req = new CreateDBInstanceRequest();
		        
		        // RDS instance name
		        req.setDBInstanceIdentifier(instanceName);
		        
		        // req.setEngine("mysql");
		        req.setEngine(instanceType);
		        
		        // req.setPort(3306);
		        req.setPort(instancePort);
		        
		        req.setDBInstanceClass("db.r4.large");
		        req.setMultiAZ(false);
		        
		        // req.setMasterUsername("root");
		        req.setMasterUsername(instanceUser);
		        
		        // req.setMasterUserPassword("Welcome1!");
		        req.setMasterUserPassword(instancePassword);
		        
		        
		        //req.setDBName(db_database);
		        req.setStorageType("gp2");
		        //req.setIops(100);
		        req.setAllocatedStorage(10);
		        req.setBackupRetentionPeriod(0);
		        req.setPubliclyAccessible(true);
		        
		        req.setVpcSecurityGroupIds(secureGroups);
				//req.setDBSecurityGroups(secureGroups);
		        
		        
		        DBInstance instance = this.rds.createDBInstance(req);
		       		        
		        // Information about the new RDS instance
		        //identifier = instance.getDBInstanceIdentifier();
		        String status = instance.getDBInstanceStatus();
		        Endpoint endpoint = instance.getEndpoint();
		        String instanceEndpoint = "Endpoint URL not available yet.";
		        

				String waitMsg = "   - Waiting for Endpoint Creation for Database ";
				waitMsg = waitMsg + ".........................................................................";
				
				                                                                             //   ............................
		        while (endpoint == null) {
		        	// takes about 3.5 minutes to create db and get endpoint back
		        	
		        	waitMsg = waitMsg.substring(0, waitMsg.length()-1);
					logr.info(waitMsg);
	        	
		        	Thread.currentThread().sleep(3000);
		        	endpoint = getInstanceEndpoint(instanceName);
				}
		        
		        if (endpoint != null) {
		        	instanceEndpoint = endpoint.getAddress();
		        }
		        
		        logr.info(" - Finished Creating Cloud Database Instance : " + instanceName);
		        logr.info(" - Database endpoint : " + instanceEndpoint);
		        
		        //System.out.println(identifier + "\t" + status);
		        //System.out.println(endpoint_url);		        
		        
				CloudDBInstance cdbi = new AwsCloudDBInstance(instance.getDBInstanceIdentifier(), instanceName, instanceEndpoint, instanceType, instancePort, instanceUser, instancePassword);
				this.instances.put(instanceName, cdbi);
				
				
				return cdbi;
			}
			

			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudDBException(ex);			
		}
	}

	

    public Endpoint getInstanceEndpoint(String instanceName) {
        DescribeDBInstancesResult result = rds.describeDBInstances();
        List<DBInstance> instances = result.getDBInstances();
        for (DBInstance instance : instances) {
            // Information about each RDS instance
            if (instance.getDBInstanceIdentifier().equals(instanceName)) {
            	return instance.getEndpoint();
            }
        }
        return null;
    }	
    
    
    // Describe DB instances
    public void listInstances() {
        DescribeDBInstancesResult result = rds.describeDBInstances();
        List<DBInstance> instances = result.getDBInstances();
        for (DBInstance instance : instances) {
            // Information about each RDS instance
            String identifier = instance.getDBInstanceIdentifier();
            String engine = instance.getEngine();
            String status = instance.getDBInstanceStatus();
            Endpoint endpoint = instance.getEndpoint();
            String endpoint_url = "Endpoint URL not available yet.";
            if (endpoint != null) {
                endpoint_url = endpoint.toString();
            }
            
            //System.out.println(identifier + "\t" + engine + "\t" + status);
            //System.out.println("\t" + endpoint_url);
        }

    }
	
	@Override
	public void deleteDBInstance(String instanceIdentifier) throws CloudDBException {

		try {
	        DeleteDBInstanceRequest ddbiReq = new DeleteDBInstanceRequest();
	        ddbiReq.setSkipFinalSnapshot(true);
	        
	        ddbiReq.setDBInstanceIdentifier(instanceIdentifier);
			
			this.rds.deleteDBInstance(ddbiReq);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudDBException(ex);			
		}		
		

	}

}
