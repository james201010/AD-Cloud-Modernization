/**
 * 
 */
package com.appdynamics.cloud.modern.repos.cloud.storage;

/**
 * Interface to represent either an AWS S3 bucket ....
 * 
 * @author James Schneider
 *
 */
public interface CloudStorageInstance {

	public abstract String getInstanceName();
	
	public abstract void addRecord(String key, String contents) throws CloudStorageException;
	
	
}
