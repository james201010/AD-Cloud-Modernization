/**
 * 
 */
package com.appdynamics.cloud.modern.shell;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class ShellTaskResults implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3928993758266785375L;
	public String dbAgentName;
	public String dbAgentHomeDir;
	public String dbAgentStartCommand;
		
	/**
	 * 
	 */
	public ShellTaskResults() {
		
	}

}
