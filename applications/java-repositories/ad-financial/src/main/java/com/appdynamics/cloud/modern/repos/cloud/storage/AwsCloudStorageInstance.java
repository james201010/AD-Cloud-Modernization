/**
 * 
 */
package com.appdynamics.cloud.modern.repos.cloud.storage;

import com.amazonaws.services.s3.AmazonS3;

/**
 * @author James Schneider
 *
 */
public class AwsCloudStorageInstance implements CloudStorageInstance {

	private AmazonS3 s3;
	private String instanceName;
	
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

}
