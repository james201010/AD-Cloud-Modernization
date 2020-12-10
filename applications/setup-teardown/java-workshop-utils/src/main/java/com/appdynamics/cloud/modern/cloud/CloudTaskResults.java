/**
 * 
 */
package com.appdynamics.cloud.modern.cloud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.appdynamics.cloud.modern.cloud.db.CloudDBInstance;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityGroup;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageInstance;

/**
 * @author James Schneider
 *
 */
public class CloudTaskResults implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7900748611504554987L;
	public List<CloudDBInstance> dbInstances = new ArrayList<CloudDBInstance>();
	public List<CloudStorageInstance> storageInstances = new ArrayList<CloudStorageInstance>();
	public List<CloudSecurityGroup> secGroupInstances = new ArrayList<CloudSecurityGroup>();
	
	// 
	/**
	 * 
	 */
	public CloudTaskResults() {
		
	}

}
