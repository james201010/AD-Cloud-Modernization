/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class DBCollector implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6766559667200862766L;
	public int collectorId;
	public String collectorName;
	public String collectorType;
	public String dbAgentName;
	public String dbHost;
	public String dbPort;
	public String dbUsername;
	public String dbPassword;
	
	/**
	 * 
	 */
	public DBCollector() {
		
	}

}
