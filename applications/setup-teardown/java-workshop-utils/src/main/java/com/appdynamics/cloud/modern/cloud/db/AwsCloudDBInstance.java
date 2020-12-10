/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.db;

/**
 * @author James Schneider
 *
 */
public class AwsCloudDBInstance implements CloudDBInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279425839384078994L;
	private String instanceIdentifier;
	private String instanceName;
	private String instanceEndpoint;
	private String instanceType;
	private int instancePort;
	private String instanceUser;
	private String instancePassword;
	
	/**
	 * 
	 */
	public AwsCloudDBInstance(String instanceIdentifier, String instanceName, String instanceEndpoint, String instanceType, int instancePort, String instanceUser, String instancePassword) {
		this.instanceIdentifier = instanceIdentifier;
		this.instanceName = instanceName;
		this.instanceEndpoint = instanceEndpoint;
		this.instanceType = instanceType;
		this.instancePort = instancePort;
		this.instanceUser = instanceUser;
		this.instancePassword = instancePassword;
		
		// this.instanceEndpoint = "ad-fin-mod-post-mysql.cluster-czgm2c0uvn.us-west-2.rds.amazonaws.com";
		
	}

	public String getInstanceIdentifier() {
		return instanceIdentifier;
	}

	@Override
	public String getInstanceName() {
		return this.instanceName;
	}

	@Override
	public String getInstanceEndpoint() {
		return this.instanceEndpoint;
	}

	@Override
	public String getInstanceType() {
		return instanceType;
	}

	@Override
	public int getInstancePort() {
		return instancePort;
	}

	@Override
	public String getInstanceUser() {
		return instanceUser;
	}

	@Override
	public String getInstancePassword() {
		return instancePassword;
	}
	
	
}
