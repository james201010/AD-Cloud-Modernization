/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.db;

import com.appdynamics.cloud.modern.cloud.security.CloudSecurityGroup;

/**
 * @author James Schneider
 *
 */
public interface CloudDBManager {
	
	public abstract CloudDBInstance createDBInstance(String instanceName, String instanceSize, String instanceType, int instancePort, String instanceUser, String instancePassword, CloudSecurityGroup securityGroup) throws CloudDBException;
	
	public abstract void deleteDBInstance(String instanceIdentifier) throws CloudDBException;
	
}
