/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.storage;

import java.util.Iterator;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * @author James Schneider
 *
 */
public class AwsCloudStorageInstance implements CloudStorageInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3054570032888896144L;
	private transient AmazonS3 s3;
	public String instanceName;
	
	/**
	 * 
	 */
	public AwsCloudStorageInstance(AmazonS3 s3, String instanceName) {
		this.s3 = s3;
		this.instanceName = instanceName;
	}

	@Override
	public String getInstanceName() {
		return this.instanceName;
	}

	@Override
	public void addRecord(String key, String contents) throws CloudStorageException {
		
		try {
			
			s3.putObject(this.instanceName, key, contents);
			
			Thread.currentThread().sleep(600);
			
			s3.deleteObject(this.instanceName, key);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new CloudStorageException(ex);
		}
		
	}

	@Override
	public void deleteAllRecords()  throws CloudStorageException {
		
		try {
			ObjectListing object_listing = s3.listObjects(this.instanceName);
		    while (true) {
		        for (Iterator<?> iterator =
		                object_listing.getObjectSummaries().iterator();
		                iterator.hasNext();) {
		            S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
		            s3.deleteObject(this.instanceName, summary.getKey());
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
		
	}
	
	
	
	
}
