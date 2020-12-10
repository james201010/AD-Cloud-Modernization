/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class Dashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4344142462385016159L;
	public int dashboardId;
	public boolean allowViewing = false;
	public boolean allowEditing = false;
	public boolean allowDeletion = false;
	public boolean allowSharing = false;
	
	
	/**
	 * 
	 */
	public Dashboard() {
		
	}

}
