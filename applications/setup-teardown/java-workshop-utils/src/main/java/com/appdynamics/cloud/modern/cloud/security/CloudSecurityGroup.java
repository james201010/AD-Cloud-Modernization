/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.security;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public interface CloudSecurityGroup extends Serializable {
	
	public abstract String getGroupName();
	public abstract String getGroupId();
	
}
