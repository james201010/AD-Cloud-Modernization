/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author James Schneider
 *
 */
public class TaskTarget implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8779926417797919498L;
	private String taskName;
	private String taskType;
	private List<Tag> targetTags;
	
	/**
	 * 
	 */
	public TaskTarget() {
		
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

	public List<Tag> getTargetTags() {
		return targetTags;
	}

	public void setTargetTags(List<Tag> targetTags) {
		this.targetTags = targetTags;
	}	
}
