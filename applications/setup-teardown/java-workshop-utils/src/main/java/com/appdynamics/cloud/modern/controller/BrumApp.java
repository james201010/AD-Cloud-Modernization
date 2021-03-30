/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class BrumApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5196794445377293563L;
	public String appName;
	public int appId = -1;
	public String eumKey = null;
	public boolean updateConfig = false;
	public String slowThresholdType;
	public String slowThresholdValue;
	public String verySlowThresholdType;
	public String verySlowThresholdValue;
	
	
}
