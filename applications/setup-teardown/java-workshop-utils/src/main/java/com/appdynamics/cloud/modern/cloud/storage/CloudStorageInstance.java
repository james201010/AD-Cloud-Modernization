/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.storage;

import java.io.Serializable;

/**
 * Interface to represent either an AWS S3 bucket ....
 * 
 * @author James Schneider
 *
 */
public interface CloudStorageInstance extends Serializable {

	public abstract String getInstanceName();
	
	public abstract void addRecord(String key, String contents) throws CloudStorageException;
	
	public abstract void deleteAllRecords()  throws CloudStorageException;
	
}
