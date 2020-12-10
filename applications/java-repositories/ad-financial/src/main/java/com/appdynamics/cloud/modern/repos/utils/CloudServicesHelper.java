/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.appdynamics.cloud.modern.repos.cloud.CloudServiceFactory;
import com.appdynamics.cloud.modern.repos.cloud.storage.CloudStorageException;
import com.appdynamics.cloud.modern.repos.cloud.storage.CloudStorageInstance;
import com.appdynamics.cloud.modern.repos.cloud.storage.CloudStorageManager;

/**
 * @author James Schneider
 *
 */
public class CloudServicesHelper {
	
	private static Map<String, CloudStorageInstance> STORAGE_INSTANCES = new HashMap<String, CloudStorageInstance>();
	private static final String STORAGE_DATA = "w345ijl345h2345ih2345oi2345oij435ij435oi345oij2345oij345ioj2345oij2345ioj4ioj2345";
	
	/**
	 * 
	 */
	public CloudServicesHelper() {
		
	}
	
	public static void createCloudStorageInstances() throws CloudStorageException {
		
		CloudStorageManager mgr = CloudServiceFactory.getCloudStorageManager();
		
		String tierName = System.getenv("APPDYNAMICS_AGENT_TIER_NAME");
		String instanceName = "";
		
		
		if (tierName.startsWith("Balance")) {
			instanceName = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_STORG_INST_1);
			STORAGE_INSTANCES.put(ApplicationConstants.STORAGE_CASHACCT_KEY, 
					mgr.createStorageInstance(instanceName));
			
		} else if (tierName.startsWith("BizLoan")) {
			instanceName = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_STORG_INST_2);
			STORAGE_INSTANCES.put(ApplicationConstants.STORAGE_BIZLOANS_KEY, 
					mgr.createStorageInstance(instanceName));
			
		} else if (tierName.startsWith("PerLoan")) {
			instanceName = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_STORG_INST_3);
			STORAGE_INSTANCES.put(ApplicationConstants.STORAGE_PERLOANS_KEY, 
					mgr.createStorageInstance(instanceName));
			
		}
						
				
	}
	
	public static void deleteCloudStorageInstances() throws CloudStorageException {
		
		CloudStorageManager mgr = CloudServiceFactory.getCloudStorageManager();	
		Set<String> keys = STORAGE_INSTANCES.keySet();
		for (String key : keys) {
			mgr.deleteStorageInstance(STORAGE_INSTANCES.get(key));
		}
	}

	public static void addCloudStorageRecordBizLoans() throws CloudStorageException {
		String key = UUID.randomUUID().toString();
		STORAGE_INSTANCES.get(ApplicationConstants.STORAGE_BIZLOANS_KEY).addRecord(key, STORAGE_DATA);
	}

	public static void addCloudStorageRecordPerLoans() throws CloudStorageException {
		String key = UUID.randomUUID().toString();
		STORAGE_INSTANCES.get(ApplicationConstants.STORAGE_PERLOANS_KEY).addRecord(key, STORAGE_DATA);
	}

	public static void addCloudStorageRecordCashAccts() throws CloudStorageException {
		String key = UUID.randomUUID().toString();
		STORAGE_INSTANCES.get(ApplicationConstants.STORAGE_CASHACCT_KEY).addRecord(key, STORAGE_DATA);
	}
	
	
	
}
