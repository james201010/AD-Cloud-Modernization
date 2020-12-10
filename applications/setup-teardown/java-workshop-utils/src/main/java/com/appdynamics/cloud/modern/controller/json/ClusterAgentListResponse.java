/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class ClusterAgentListResponse {

	  public Data[] data;
	  public int totalCount;
	
	/**
	 * 
	 */
	public ClusterAgentListResponse() {
		
	}

	public Data[] getData() {
		return data;
	}
	public void setData(Data[] data) {
		this.data = data;
	}

	public int getTotalCount() {
	    return totalCount;
	}
	public void setTotalCount( int totalCount ) {
	    this.totalCount = totalCount;
	}
	  
	    public class Data {
	    	
		    private String hostName;
		    private String agentVersion;
		    private String kubernetesVersion;
		    private String status;
		    private int machineId;
		    private boolean agentEnabled;
		    private String pendingChanges;
		    
			public String getHostName() {
				return hostName;
			}
			public void setHostName(String hostName) {
				this.hostName = hostName;
			}
			public String getAgentVersion() {
				return agentVersion;
			}
			public void setAgentVersion(String agentVersion) {
				this.agentVersion = agentVersion;
			}
			public String getKubernetesVersion() {
				return kubernetesVersion;
			}
			public void setKubernetesVersion(String kubernetesVersion) {
				this.kubernetesVersion = kubernetesVersion;
			}
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public int getMachineId() {
				return machineId;
			}
			public void setMachineId(int machineId) {
				this.machineId = machineId;
			}
			public boolean isAgentEnabled() {
				return agentEnabled;
			}
			public void setAgentEnabled(boolean agentEnabled) {
				this.agentEnabled = agentEnabled;
			}
			public String getPendingChanges() {
				return pendingChanges;
			}
			public void setPendingChanges(String pendingChanges) {
				this.pendingChanges = pendingChanges;
			}
	
	    }
	  
	
}
