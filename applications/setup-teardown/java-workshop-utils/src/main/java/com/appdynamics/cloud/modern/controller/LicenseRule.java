/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class LicenseRule implements Serializable {

	public String requestId; 
	public String ruleName; 
	public String licenseKey; 
	public int numberOfApm; 
	public int numberOfMA; 
	public int numberOfSim; 
	public int numberOfNet;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3739957318596731698L;

	/**
	 * 
	 */
	public LicenseRule() {
		
	}

}
