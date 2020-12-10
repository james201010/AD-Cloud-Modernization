/**
 * 
 */
package com.appdynamics.cloud.modern.cloud;

import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.cloud.db.CloudDBException;
import com.appdynamics.cloud.modern.cloud.db.CloudDBInstance;
import com.appdynamics.cloud.modern.cloud.db.CloudDBManager;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityException;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityGroup;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageException;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageInstance;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageManager;

/**
 * @author James Schneider
 *
 */
public class CloudHelper {

	public static Logger logr = new Logger(CloudHelper.class.getSimpleName());
	private static String CLOUD_PROVIDER = null;
	
	// ad-fin-mod-post-mysql.cluster-czgm2c0uvnhv.us-west-2.rds.amazonaws.com
	
	public static void initSetup(String cloudProvider) throws Throwable {
		
		if (CLOUD_PROVIDER == null) {
			CLOUD_PROVIDER = cloudProvider;
		}
	}
	
	public static String getCloudProvider() throws Throwable {
		return CLOUD_PROVIDER;
	}
	
	public static String getCurrentCloudRegion() throws Throwable {
		
		return CloudProviderManager.getCurrentCloudRegion(getCloudProvider());
	}
	
	public static CloudStorageInstance createStorageInstance(String instanceName) throws CloudStorageException {
		//logr.info("Creating Cloud Storage Instance : " + instanceName);
		CloudStorageManager storageMgr = CloudProviderManager.getCloudStorageManager(CLOUD_PROVIDER);
		CloudStorageInstance instance = storageMgr.createStorageInstance(instanceName);
		//logr.info("Finished Creating Cloud Storage Instance : " + instanceName);
		return instance;
	}

	public static CloudDBInstance createDBInstance(String instanceName, String instanceType, int instancePort, String instanceUser, String instancePassword, CloudSecurityGroup securityGroup) throws CloudDBException {
		//logr.info("Creating Cloud Database Instance : " + instanceName);
		CloudDBManager dbMgr = CloudProviderManager.getCloudDBManager(CLOUD_PROVIDER);
		CloudDBInstance instance = dbMgr.createDBInstance(instanceName, instanceType, instancePort, instanceUser, instancePassword, securityGroup);
		//logr.info("Finished Creating Cloud Database Instance : " + instanceName);
		return instance;
		
	}

	public static CloudSecurityGroup createSecurityGroup(String instanceName) throws CloudSecurityException {
		//logr.info("Creating Cloud Security Group : " + instanceName);
		CloudSecurityGroup csg = CloudProviderManager.getCloudSecurityManager(CLOUD_PROVIDER).createSecurityGroup(instanceName);
		//logr.info("Finished Creating Cloud Security Group : " + instanceName);
		return csg;
		
	}
	
	public static CloudSecurityGroup getSecurityGroup(String instanceName) throws CloudSecurityException {
		
		return CloudProviderManager.getCloudSecurityManager(CLOUD_PROVIDER).getSecurityGroup(instanceName);
	}

	public static void deleteDBInstance(String instanceIdentifier) throws CloudDBException {
		logr.info("    - Deleting Cloud Database Instance : " + instanceIdentifier);
		CloudDBManager dbMgr = CloudProviderManager.getCloudDBManager(CLOUD_PROVIDER);
		dbMgr.deleteDBInstance(instanceIdentifier);;
		logr.info("    - Finished Deleting Cloud Database Instance : " + instanceIdentifier);
		
	}
	
	public static void delteStorageInstance(String instanceName) throws CloudStorageException {
		logr.info("    - Deleting Cloud Storage Instance : " + instanceName);
		CloudStorageManager storageMgr = CloudProviderManager.getCloudStorageManager(CLOUD_PROVIDER);
		storageMgr.deleteStorageInstance(instanceName);
		logr.info("    - Finished Deleting Cloud Storage Instance : " + instanceName);

	}
	
	public static void deleteSecurityGroup(String securityGroupId) throws CloudSecurityException {
		logr.info("    - Deleting Cloud Security Group : " + securityGroupId);
		CloudProviderManager.getCloudSecurityManager(CLOUD_PROVIDER).deleteSecurityGroup(securityGroupId);;
		logr.info("    - Finished Deleting Cloud Security Group : " + securityGroupId);
		
	}
	
}
