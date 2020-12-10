/**
 * 
 */
package com.appdynamics.cloud.modern.repos.cloud;

import com.appdynamics.cloud.modern.repos.cloud.storage.AwsCloudStorageManager;
import com.appdynamics.cloud.modern.repos.cloud.storage.CloudStorageException;
import com.appdynamics.cloud.modern.repos.cloud.storage.CloudStorageManager;
import com.appdynamics.cloud.modern.repos.utils.ApplicationConstants;

/**
 * @author James Schneider
 *
 */
public class CloudServiceFactory {

//	public static final String STORAGE_QUOTES_KEY = "stocks";
//	public static final String STORAGE_ORDERS_KEY = "orders";
//	public static final String STORAGE_POLICIES_KEY = "policies";

	
	private static CloudStorageManager MANAGER = null;
	
	/**
	 * 
	 */
	public CloudServiceFactory() {
		
	}

	public static CloudStorageManager getCloudStorageManager() throws CloudStorageException {
		
		try {
			if (MANAGER == null) {
				
				String APP_CLOUD_MODE = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_MODE).toLowerCase();
				switch (APP_CLOUD_MODE) {
				case "aws":
					MANAGER = new AwsCloudStorageManager();
					break;

				default:
					break;
				}				
			}
			
			return MANAGER;	
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);			
		}

	}
	
}
