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
public class SetupConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1600322929077888136L;
	private String description;
	private String teardownFilePath;
	private String setupProgressDirectory;
	private String setupStepsFileName;
	private String teardownScript;
	private boolean debugLogging = false;
	private boolean usingCloudTasks = false;
	private String cloudProvider;
	private List<Template> templates;
	private List<Task> shellTasks;
	private List<Task> controllerTasks;
	private List<Task> cloudTasks;
	private VaultInfo vaultInfo;
	private List<TaskExecution> taskExecutionOrder;
	
	
	/**
	 * 
	 */
	public SetupConfig() {
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public String getTeardownFilePath() {
		return teardownFilePath;
	}

	public void setTeardownFilePath(String teardownFilePath) {
		this.teardownFilePath = teardownFilePath;
	}

	public List<Task> getControllerTasks() {
		return controllerTasks;
	}

	public void setControllerTasks(List<Task> controllerTasks) {
		this.controllerTasks = controllerTasks;
	}

	public List<Task> getCloudTasks() {
		return cloudTasks;
	}

	public void setCloudTasks(List<Task> cloudTasks) {
		this.cloudTasks = cloudTasks;
	}

	public boolean isDebugLogging() {
		return debugLogging;
	}

	public void setDebugLogging(boolean debugLogging) {
		this.debugLogging = debugLogging;
	}

	public boolean isUsingCloudTasks() {
		return usingCloudTasks;
	}

	public void setUsingCloudTasks(boolean usingCloudTasks) {
		this.usingCloudTasks = usingCloudTasks;
	}

	public String getCloudProvider() {
		return cloudProvider;
	}

	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}

	public VaultInfo getVaultInfo() {
        return vaultInfo;
    }

    public void setVaultInfo(VaultInfo vaultInfo) {
        this.vaultInfo = vaultInfo;
    }

	public List<Task> getShellTasks() {
		return shellTasks;
	}

	public void setShellTasks(List<Task> shellTasks) {
		this.shellTasks = shellTasks;
	}

	public List<TaskExecution> getTaskExecutionOrder() {
		return taskExecutionOrder;
	}

	public void setTaskExecutionOrder(List<TaskExecution> taskExecutionOrder) {
		this.taskExecutionOrder = taskExecutionOrder;
	}

	public String getTeardownScript() {
		return teardownScript;
	}

	public void setTeardownScript(String teardownScript) {
		this.teardownScript = teardownScript;
	}

	public String getSetupProgressDirectory() {
		return setupProgressDirectory;
	}

	public void setSetupProgressDirectory(String setupProgressDirectory) {
		this.setupProgressDirectory = setupProgressDirectory;
	}

	public String getSetupStepsFileName() {
		return setupStepsFileName;
	}

	public void setSetupStepsFileName(String setupStepsFileName) {
		this.setupStepsFileName = setupStepsFileName;
	}

	
	
}
