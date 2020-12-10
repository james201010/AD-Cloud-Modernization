/**
 * 
 */
package com.appdynamics.cloud.modern.cloud;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.appdynamics.cloud.modern.cloud.db.AwsCloudDBManager;
import com.appdynamics.cloud.modern.cloud.db.CloudDBException;
import com.appdynamics.cloud.modern.cloud.db.CloudDBManager;
import com.appdynamics.cloud.modern.cloud.security.AwsCloudSecurityManager;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityException;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityManager;
import com.appdynamics.cloud.modern.cloud.storage.AwsCloudStorageManager;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageException;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageManager;
import com.appdynamics.cloud.modern.utils.ApplicationConstants;

/**
 * @author James Schneider
 *
 */
public class CloudProviderManager {

	//public static final String STORAGE_CASHACCT_KEY = "cashaccts";
	//public static final String STORAGE_BIZLOANS_KEY = "bizloans";
	//public static final String STORAGE_PERLOANS_KEY = "perloans";
	
	private static CloudStorageManager CLOUD_STORAGE_MANAGER = null;
	private static CloudDBManager CLOUD_DB_MANAGER = null;
	private static CloudSecurityManager CLOUD_SECURITY_MANAGER = null;
	
	/**
	 * 
	 */
	public CloudProviderManager() {
		
	}

	public static String getCurrentCloudRegion(String cloudProvider) throws Throwable {
		
		String regionName = "";
			
		switch (cloudProvider) {
		case ApplicationConstants.ADWRKSHP_CLOUD_PROVIDER_AWS:
			Region region = Regions.getCurrentRegion();
			regionName = region.getName();
			
			break;

		default:
			break;
		}				
		
		
		return regionName; 
	}
	
	public static CloudSecurityManager getCloudSecurityManager(String cloudProvider) throws CloudSecurityException {
		
		try {
			if (CLOUD_SECURITY_MANAGER == null) {
					
				switch (cloudProvider) {
				case ApplicationConstants.ADWRKSHP_CLOUD_PROVIDER_AWS:
					CLOUD_SECURITY_MANAGER = new AwsCloudSecurityManager();
					break;

				default:
					break;
				}				
			}
			
			return CLOUD_SECURITY_MANAGER;	
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudSecurityException(ex);			
		}

	}
	
	public static CloudStorageManager getCloudStorageManager(String cloudProvider) throws CloudStorageException {
		
		try {
			if (CLOUD_STORAGE_MANAGER == null) {
					
				switch (cloudProvider) {
				case ApplicationConstants.ADWRKSHP_CLOUD_PROVIDER_AWS:
					CLOUD_STORAGE_MANAGER = new AwsCloudStorageManager();
					break;

				default:
					break;
				}				
			}
			
			return CLOUD_STORAGE_MANAGER;	
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);			
		}

	}

	public static CloudDBManager getCloudDBManager(String cloudProvider) throws CloudDBException {
		
		try {
			if (CLOUD_DB_MANAGER == null) {
					
				switch (cloudProvider) {
				case ApplicationConstants.ADWRKSHP_CLOUD_PROVIDER_AWS:
					CLOUD_DB_MANAGER = new AwsCloudDBManager();
					break;

				default:
					break;
				}				
			}
			
			return CLOUD_DB_MANAGER;	
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudDBException(ex);			
		}

	}	
}
