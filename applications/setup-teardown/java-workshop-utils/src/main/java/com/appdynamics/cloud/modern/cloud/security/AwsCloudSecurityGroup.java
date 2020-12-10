/**
 * 
 */
package com.appdynamics.cloud.modern.cloud.security;

/**
 * @author James Schneider
 *
 */
public class AwsCloudSecurityGroup implements CloudSecurityGroup {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9067323617623396167L;
	private String groupName;
	private String groupId;
	
	/**
	 * 
	 */
	public AwsCloudSecurityGroup(String groupName, String groupId) {
		this.groupName = groupName;
		this.groupId = groupId;
	}

	@Override
	public String getGroupName() {
		return this.groupName;
	}

	@Override
	public String getGroupId() {
		return this.groupId;
	}

}
