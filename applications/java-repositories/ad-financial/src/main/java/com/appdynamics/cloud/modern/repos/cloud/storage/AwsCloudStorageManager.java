/**
 * 
 */
package com.appdynamics.cloud.modern.repos.cloud.storage;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.appdynamics.cloud.modern.repos.utils.ApplicationConstants;

/**
 * @author James Schneider
 *
 */
public class AwsCloudStorageManager implements CloudStorageManager {

	private AmazonS3 s3;
	private Map<String, CloudStorageInstance> instances = new HashMap<String, CloudStorageInstance>();
	
	/**
	 * 
	 */
	public AwsCloudStorageManager() throws CloudStorageException {
		
		try {
			// this.s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
			String CLOUD_REGION = System.getenv(ApplicationConstants.ADWRKSHP_CLOUD_REGION);
			
			this.s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(CLOUD_REGION)).withCredentials( 
					new AWSCredentialsProviderChain(WebIdentityTokenCredentialsProvider.create(), 
					new EnvironmentVariableCredentialsProvider(), new ProfileCredentialsProvider())).build();
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);
		}
		
	}

	@Override
	public CloudStorageInstance createStorageInstance(String instanceName) throws CloudStorageException {
		
		try {
			if (this.instances.containsKey(instanceName)) {
				
				return this.instances.get(instanceName);
				
			} else {
				
				if (!this.s3.doesBucketExistV2(instanceName)) {
					//this.s3.createBucket(instanceName);
				}
				
				CloudStorageInstance csi = new AwsCloudStorageInstance(this.s3, instanceName);
				this.instances.put(instanceName, csi);
				return csi;
			}
			

			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);			
		}
		
	}

	@Override
	public void deleteStorageInstance(CloudStorageInstance instance) throws CloudStorageException {
		
		try {
			
			this.s3.deleteBucket(instance.getInstanceName());
			this.instances.remove(instance.getInstanceName());
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);			
		}		
		

	}

}
