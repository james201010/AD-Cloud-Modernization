/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.io.Serializable;

import com.appdynamics.cloud.modern.cloud.CloudTaskResults;
import com.appdynamics.cloud.modern.controller.ControllerTaskResults;
import com.appdynamics.cloud.modern.shell.ShellTaskResults;

/**
 * @author James Schneider
 *
 */
public class SetupStepsState implements Serializable {

	public SetupConfig setupConfig;
	public ShellTaskResults shellTaskResults;
	public CloudTaskResults cloudTaskResults;
	public ControllerTaskResults controllerTaskResults;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5326146934884930906L;

	/**
	 * 
	 */
	public SetupStepsState() {
		
	}

}
