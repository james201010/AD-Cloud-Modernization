/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.security;

/**
 * @author James Schneider
 *
 */
public interface CloudSecurityManager {

	
	public abstract CloudSecurityGroup createSecurityGroup(String securityGroupName) throws CloudSecurityException;
	
	public abstract void deleteSecurityGroup(String securityGroupId) throws CloudSecurityException;
	
	public abstract CloudSecurityGroup getSecurityGroup(String securityGroupName) throws CloudSecurityException;
}
