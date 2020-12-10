/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.io.Serializable;
import java.util.List;

import com.appdynamics.cloud.modern.utils.ApplicationConstants;

/**
 * @author James Schneider
 *
 */
public class Task implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3607674744894336834L;
	private String taskName = "";
	private String taskType;
	private List<Tag> inputTags;
	private List<TemplateTarget> templateTargets;
	private List<TaskTarget> taskTargets;
	
	/**
	 * 
	 */
	public Task() {
		
	}

	public boolean shouldExecute(String lastSetupStepCompleted) {
		if (this.inputTags != null && !this.inputTags.isEmpty()) {
			for (Tag inTag : this.inputTags) {
				if (inTag.getTagKey().equals(ApplicationConstants.VADWRKSHP_LAST_SETUP_STEP_TRIGGER)) {
					if (inTag.getTagValue().equals(lastSetupStepCompleted)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public String getNextSetupStepTrigger() {
		if (this.inputTags != null && !this.inputTags.isEmpty()) {
			for (Tag inTag : this.inputTags) {
				if (inTag.getTagKey().equals(ApplicationConstants.VADWRKSHP_NEXT_SETUP_STEP_TRIGGER)) {
					
					return inTag.getTagValue();
					
				}
			}
		}
		
		return null;
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
	
	public List<Tag> getInputTags() {
		return inputTags;
	}
	public void setInputTags(List<Tag> inputTags) {
		this.inputTags = inputTags;
	}

	public List<TemplateTarget> getTemplateTargets() {
		return templateTargets;
	}
	public void setTemplateTargets(List<TemplateTarget> templateTargets) {
		this.templateTargets = templateTargets;
	}

	public List<TaskTarget> getTaskTargets() {
		return taskTargets;
	}
	public void setTaskTargets(List<TaskTarget> taskTargets) {
		this.taskTargets = taskTargets;
	}

	
	
}
