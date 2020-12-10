/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.cloud.CloudHelper;

/**
 * @author James Schneider
 *
 */
public class AwsCloudStorageManager implements CloudStorageManager {

	public static Logger logr = new Logger(AwsCloudStorageManager.class.getSimpleName());
	private AmazonS3 s3;
	private Map<String, CloudStorageInstance> instances = new HashMap<String, CloudStorageInstance>();
	
	/**
	 * 
	 */
	public AwsCloudStorageManager() throws CloudStorageException {
		
		try {
			
			
			// USE HERE !!!! this builder should work with EC2 Role
			this.s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(CloudHelper.getCurrentCloudRegion())).build();
			
			
			// this builder works for EKS cluster role
			// this.s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(regionName)).withCredentials( 
					// new AWSCredentialsProviderChain(WebIdentityTokenCredentialsProvider.create(), 
					// new EnvironmentVariableCredentialsProvider(), new ProfileCredentialsProvider())).build();

			

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
				
				logr.info(" - Creating Cloud Storage Instance : " + instanceName);
				if (!this.s3.doesBucketExistV2(instanceName)) {
					this.s3.createBucket(instanceName);
				}
				
				CloudStorageInstance csi = new AwsCloudStorageInstance(this.s3, instanceName);
				this.instances.put(instanceName, csi);
				
				logr.info(" - Finished Creating Cloud Storage Instance : " + instanceName);
				return csi;
			}
			

				
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);			
		}
		
	}

	@Override
	public void deleteStorageInstance(String instanceName) throws CloudStorageException {
		
		try {
			ObjectListing object_listing = s3.listObjects(instanceName);
		    while (true) {
		        for (Iterator<?> iterator =
		                object_listing.getObjectSummaries().iterator();
		                iterator.hasNext();) {
		            S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
		            s3.deleteObject(instanceName, summary.getKey());
		        }

		        // more object_listing to retrieve?
		        if (object_listing.isTruncated()) {
		            object_listing = s3.listNextBatchOfObjects(object_listing);
		        } else {
		            break;
		        }
		    };
		} catch (Throwable ex) {
			ex.printStackTrace();
		}	
		
		
		try {
			
			this.s3.deleteBucket(instanceName);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);			
		}		
		

	}

}
