/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class MachineAgentListResponse {

	public MachineKey[] machineKeys;
	public boolean simEnabledMachineExists;

	/**
	 * 
	 */
	public MachineAgentListResponse() {
		
	}
	

	public boolean getSimEnabledMachineExists() {
	    return simEnabledMachineExists;
	}
	
	public void setSimEnabledMachineExists( boolean simEnabledMachineExists ) {
	    this.simEnabledMachineExists = simEnabledMachineExists;
	}	
	  
	public MachineKey[] getMachineKeys() {
		return machineKeys;
	}
	public void setMachineKeys(MachineKey[] machineKeys) {
		this.machineKeys = machineKeys;
	}


		public class MachineKey {
			
			public int machineId;
			public String serverName;
			  
			public int getMachineId() {
				return machineId;
			}
			public void setMachineId(int machineId) {
				this.machineId = machineId;
			}
			public String getServerName() {
				return serverName;
			}
			public void setServerName(String serverName) {
				this.serverName = serverName;
			}


			  
		}
	  
}
