/**
 * 
 */
package com.appdynamics.cloud.modern.repos.cloud.storage;

/**
 * @author James Schneider
 *
 */
public interface CloudStorageManager {

	public abstract CloudStorageInstance createStorageInstance(String instanceName) throws CloudStorageException;
	
	public abstract void deleteStorageInstance(CloudStorageInstance instance) throws CloudStorageException;

	
}
