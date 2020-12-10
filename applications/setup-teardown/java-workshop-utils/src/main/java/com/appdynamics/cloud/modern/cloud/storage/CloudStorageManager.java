/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.storage;

/**
 * @author James Schneider
 *
 */
public interface CloudStorageManager {

	public abstract CloudStorageInstance createStorageInstance(String instanceName) throws CloudStorageException;
	
	public abstract void deleteStorageInstance(String instanceName) throws CloudStorageException;

	
}
