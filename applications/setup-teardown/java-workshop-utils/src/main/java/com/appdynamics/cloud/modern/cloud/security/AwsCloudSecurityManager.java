/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.DeleteSecurityGroupRequest;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;
import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.cloud.CloudHelper;

/**
 * @author James Schneider
 *
 */
public class AwsCloudSecurityManager implements CloudSecurityManager {
	
	public static Logger logr = new Logger(AwsCloudSecurityManager.class.getSimpleName());
	private AmazonEC2 ec2;
	private Map<String, CloudSecurityGroup> securityGroups = new HashMap<String, CloudSecurityGroup>();
	
	
	/**
	 * 
	 */
	public AwsCloudSecurityManager() throws CloudSecurityException {
		
		try {
			
			this.ec2 = AmazonEC2ClientBuilder.standard().withRegion(Regions.fromName(CloudHelper.getCurrentCloudRegion())).build();
			
			
//			AWSCredentialsProvider credentials = new
//            AWSStaticCredentialsProvider(new
//                    BasicAWSCredentials("<ACCESS_KEY>","<SECRET_KEY>"));						

			
			//ec2 = AmazonEC2ClientBuilder.standard().withCredentials(credentials).withRegion(Regions.US_WEST_1).build();		
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudSecurityException(ex);
		}		
	}

	@Override
	public CloudSecurityGroup createSecurityGroup(String groupName) throws CloudSecurityException {
		
		logr.info(" - Creating Cloud Security Group : " + groupName);
		try {
			if (this.securityGroups.containsKey(groupName)) {
				
				return this.securityGroups.get(groupName);
				
			} else {
				
		        String groupId = "";
		        
		        CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
		        csgr.withGroupName(groupName).withDescription(groupName);
		        
		        CreateSecurityGroupResult createSecurityGroupResult = ec2.createSecurityGroup(csgr);
		        
		        IpPermission ipPermission;
		        IpRange ipRange1;
		        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest;
		        
		        ipPermission = new IpPermission();
		        ipRange1 = new IpRange().withCidrIp("0.0.0.0/0");
		        
		        
		        ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1}))
	            .withIpProtocol("tcp")
	            .withFromPort(3306)
	            .withToPort(3306);
		        authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest();
		        authorizeSecurityGroupIngressRequest.withGroupName(groupName).withIpPermissions(ipPermission);		        
		        ec2.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
		        
		        ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] {ipRange1}))
	            .withIpProtocol("tcp")
	            .withFromPort(8080)
	            .withToPort(8080);
		        authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest();
		        authorizeSecurityGroupIngressRequest.withGroupName(groupName).withIpPermissions(ipPermission);		        
		        ec2.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
		        
		        
		        groupId = createSecurityGroupResult.getGroupId();
		        
		        
				CloudSecurityGroup group = new AwsCloudSecurityGroup(groupName, groupId);
				this.securityGroups.put(groupName, group);
				
				logr.info(" - Finished Creating Cloud Security Group : " + groupName);
				return group;
			}
			

			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudSecurityException(ex);			
		}
	}

	@Override
	public void deleteSecurityGroup(String securityGroupId) throws CloudSecurityException {
		try {
			
	        DeleteSecurityGroupRequest dsgReq = new DeleteSecurityGroupRequest().withGroupId(securityGroupId);			
			this.ec2.deleteSecurityGroup(dsgReq);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudSecurityException(ex);			
		}		

	}

	@Override
	public CloudSecurityGroup getSecurityGroup(String securityGroupName) throws CloudSecurityException {
		return this.securityGroups.get(securityGroupName);
	}

		
	
}
