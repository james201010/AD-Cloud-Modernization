/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class TaskExecution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9182166402775619314L;
	private String taskName = "";
	private String taskType;

	/**
	 * 
	 */
	public TaskExecution() {
		
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	
}
