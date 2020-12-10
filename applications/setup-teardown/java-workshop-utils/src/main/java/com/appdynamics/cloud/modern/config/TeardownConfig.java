/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James Schneider
 *
 */
public class TeardownConfig {

	private VaultInfo vaultInfo;
	
	private String teardownScript;
	
	private Integer userId;
	private Integer roleId;
	
	private List<Integer> apmApps = new ArrayList<Integer>();
	private List<Integer> brumApps = new ArrayList<Integer>();
	private List<Integer> dbCollectors = new ArrayList<Integer>();
	
	private String cloudProvider;
	private String clusterAgentName;
	
	private List<String> securityGroups = new ArrayList<String>();
	private List<String> storageInstances = new ArrayList<String>();
	private List<String> dbInstances = new ArrayList<String>();
	
	
	/**
	 * 
	 */

	public TeardownConfig() {
		
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getApmApps() {
		return apmApps;
	}

	public void setApmApps(List<Integer> apmApps) {
		this.apmApps = apmApps;
	}


	public List<Integer> getBrumApps() {
		return brumApps;
	}

	public void setBrumApps(List<Integer> brumApps) {
		this.brumApps = brumApps;
	}

	public List<Integer> getDbCollectors() {
		return dbCollectors;
	}

	public void setDbCollectors(List<Integer> dbCollectors) {
		this.dbCollectors = dbCollectors;
	}

	public String getCloudProvider() {
		return cloudProvider;
	}

	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	public List<String> getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(List<String> securityGroups) {
		this.securityGroups = securityGroups;
	}

	public List<String> getStorageInstances() {
		return storageInstances;
	}

	public void setStorageInstances(List<String> storageInstances) {
		this.storageInstances = storageInstances;
	}

	public List<String> getDbInstances() {
		return dbInstances;
	}

	public void setDbInstances(List<String> dbInstances) {
		this.dbInstances = dbInstances;
	}

	public VaultInfo getVaultInfo() {
		return vaultInfo;
	}

	public void setVaultInfo(VaultInfo vaultInfo) {
		this.vaultInfo = vaultInfo;
	}

	public String getClusterAgentName() {
		return clusterAgentName;
	}

	public void setClusterAgentName(String clusterAgentName) {
		this.clusterAgentName = clusterAgentName;
	}

	public String getTeardownScript() {
		return teardownScript;
	}

	public void setTeardownScript(String teardownScript) {
		this.teardownScript = teardownScript;
	}


	
}
