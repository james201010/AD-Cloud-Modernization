/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.db;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public interface CloudDBInstance extends Serializable {

	public abstract String getInstanceIdentifier();
	public abstract String getInstanceName();
	public abstract String getInstanceEndpoint();
	public abstract String getInstanceType();
	public abstract int getInstancePort();
	public abstract String getInstanceUser();
	public abstract String getInstancePassword();
	
}
