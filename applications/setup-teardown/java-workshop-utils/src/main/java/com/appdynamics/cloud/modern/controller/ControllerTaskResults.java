/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author James Schneider
 *
 */
public class ControllerTaskResults implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2714734535252736497L;
	
	public LicenseRule licenseRule;
	public RbacUser rbacUser;
	public RbacRole rbacRole;
	
	public List<ApmApp> apmApps = new ArrayList<ApmApp>();
	public List<BrumApp> brumApps = new ArrayList<BrumApp>();
	public List<DBCollector> dbCollectors = new ArrayList<DBCollector>();
	public List<Dashboard> dashboards = new ArrayList<Dashboard>(); 
}
