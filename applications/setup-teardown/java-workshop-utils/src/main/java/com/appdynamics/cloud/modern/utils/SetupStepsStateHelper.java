/**
 * 
 */
package com.appdynamics.cloud.modern.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.appdynamics.cloud.modern.ADWorkshopUtils;
import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.cloud.CloudTaskResults;
import com.appdynamics.cloud.modern.cloud.db.CloudDBInstance;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityGroup;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageInstance;
import com.appdynamics.cloud.modern.config.SetupConfig;
import com.appdynamics.cloud.modern.config.SetupStepsState;
import com.appdynamics.cloud.modern.config.Tag;
import com.appdynamics.cloud.modern.config.Task;
import com.appdynamics.cloud.modern.config.TaskExecution;
import com.appdynamics.cloud.modern.config.TeardownConfig;
import com.appdynamics.cloud.modern.controller.ApmApp;
import com.appdynamics.cloud.modern.controller.BrumApp;
import com.appdynamics.cloud.modern.controller.ControllerTaskResults;
import com.appdynamics.cloud.modern.controller.DBCollector;
import com.appdynamics.cloud.modern.shell.ShellTaskResults;

/**
 * @author James Schneider
 *
 */
public class SetupStepsStateHelper implements ApplicationConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3491143778028308230L;
	public static Logger logr = new Logger(SetupStepsStateHelper.class.getSimpleName());
	/**
	 * 
	 */
	public SetupStepsStateHelper() {
		
	}

	public static void setStepSequenceOnTasks(SetupConfig setupConfig) throws Throwable {
		
		List<TaskExecution> stepTasks = setupConfig.getTaskExecutionOrder();
		
		int lastStepTrigger = 100;
		int nextStepTrigger = 110;
		
		
		for (TaskExecution te : stepTasks) {
			
			switch (te.getTaskType()) {
			
			case VADWRKSHP_TASK_EXEC_TYPE_SHELL:
				
				List<Task> shellTasks = setupConfig.getShellTasks();
				if (shellTasks != null && !shellTasks.isEmpty()) {
					for (Task task : shellTasks) {
						if (task.getTaskName().equals(te.getTaskName())) {
							
							SetupStepsStateHelper.addStepTriggerTags(task, lastStepTrigger, nextStepTrigger);
							logr.debug(" - Task Step : Task : " + task.getTaskName() + " : " + lastStepTrigger + "/" + nextStepTrigger);
							
							lastStepTrigger = lastStepTrigger + 10;
							nextStepTrigger = nextStepTrigger + 10;
						}
					}
				}	
				break;

			case VADWRKSHP_TASKS_EXEC_TYPE_CLOUD:
				
				List<Task> cloudTasks = setupConfig.getCloudTasks();
				if (cloudTasks != null && !cloudTasks.isEmpty()) {
					for (Task task : cloudTasks) {
						
						SetupStepsStateHelper.addStepTriggerTags(task, lastStepTrigger, nextStepTrigger);
						logr.debug(" - Task Step : Task : " + task.getTaskName() + " : " + lastStepTrigger + "/" + nextStepTrigger);
						
						lastStepTrigger = lastStepTrigger + 10;
						nextStepTrigger = nextStepTrigger + 10;						
					}
				}				
				break;

			case VADWRKSHP_TASKS_EXEC_TYPE_CONTROLLER_INIT:
				
				List<Task> coninitTasks = setupConfig.getControllerInitTasks();
				if (coninitTasks != null && !coninitTasks.isEmpty()) {
					for (Task task : coninitTasks) {
						
						SetupStepsStateHelper.addStepTriggerTags(task, lastStepTrigger, nextStepTrigger);
						logr.debug(" - Task Step : Task : " + task.getTaskName() + " : " + lastStepTrigger + "/" + nextStepTrigger);
						
						lastStepTrigger = lastStepTrigger + 10;
						nextStepTrigger = nextStepTrigger + 10;						
					}
				}
				break;
				
			case VADWRKSHP_TASKS_EXEC_TYPE_CONTROLLER:

				List<Task> connTasks = setupConfig.getControllerTasks();
				if (connTasks != null && !connTasks.isEmpty()) {
					for (Task task : connTasks) {
						
						SetupStepsStateHelper.addStepTriggerTags(task, lastStepTrigger, nextStepTrigger);
						logr.debug(" - Task Step : Task : " + task.getTaskName() + " : " + lastStepTrigger + "/" + nextStepTrigger);
						
						lastStepTrigger = lastStepTrigger + 10;
						nextStepTrigger = nextStepTrigger + 10;						
					}
				}
				break;
				
				
			default:
				break;
			}
			
		}
		
	}
	
	private static void addStepTriggerTags(Task task, int lastStepTrigger, int nextStepTrigger) throws Throwable {
		
		List<Tag> inTags = task.getInputTags();
		if (inTags == null) {
			inTags = new ArrayList<Tag>();
			task.setInputTags(inTags);
		} else {
			
			Tag lTag = null;
			for(Tag iTag : inTags) {
				if (iTag.getTagKey().equals(VADWRKSHP_LAST_SETUP_STEP_TRIGGER)) {
					lTag = iTag;
				}
			}
			if (lTag != null) {
				inTags.remove(lTag);
			}
			
			Tag nTag = null;
			for(Tag iTag : inTags) {
				if (iTag.getTagKey().equals(VADWRKSHP_NEXT_SETUP_STEP_TRIGGER)) {
					nTag = iTag;
				}
			}
			if (nTag != null) {
				inTags.remove(nTag);
			}
			

		}
		
		
		Tag lastTag = new Tag();
		lastTag.setTagKey(VADWRKSHP_LAST_SETUP_STEP_TRIGGER);
		lastTag.setTagValue("" + lastStepTrigger);
		
		inTags.add(lastTag);
		
		Tag nextTag = new Tag();
		nextTag.setTagKey(VADWRKSHP_NEXT_SETUP_STEP_TRIGGER);
		nextTag.setTagValue("" + nextStepTrigger);
		
		inTags.add(nextTag);
		
	}
	
	public static void saveSuccessfulTaskCompletion(Task task, SetupConfig setupConfig, ControllerTaskResults connResults, CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		
		SetupStepsStateHelper.saveSetupStepsState(setupConfig, connResults, cloudResults, shellResults);
		SetupStepsStateHelper.createTeardownFile(setupConfig, cloudResults, connResults, shellResults);
		StringUtils.saveStringAsFile(setupConfig.getSetupProgressDirectory() + "/" + setupConfig.getSetupStepsFileName(), task.getNextSetupStepTrigger());
		ADWorkshopUtils.LAST_SETUP_STEP_COMPLETED = task.getNextSetupStepTrigger();
		
	}
	
	public static SetupStepsState getSavedSetupStepsState(SetupConfig setupConfig) throws Throwable {
		SetupStepsState state;
		String setupStepsFilePath = setupConfig.getSetupProgressDirectory() + "/" + ApplicationConstants.ADWRKSHP_TASK_RESULTS_STATE_FILE;
		
	    FileInputStream fileInputStream = new FileInputStream(setupStepsFilePath);
	    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	    state = (SetupStepsState) objectInputStream.readObject();
	    objectInputStream.close(); 
	    
	    if (state.cloudTaskResults == null) {
	    	state.cloudTaskResults = new CloudTaskResults();
	    }
	    if (state.controllerTaskResults == null) {
	    	state.controllerTaskResults = new ControllerTaskResults();
	    }
	    if (state.shellTaskResults == null) {
	    	state.shellTaskResults = new ShellTaskResults();
	    }
	    
	    return state;

	}
	
	private static void saveSetupStepsState(SetupConfig setupConfig, ControllerTaskResults connResults, CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		
		String setupStepsFilePath = setupConfig.getSetupProgressDirectory() + "/" + ApplicationConstants.ADWRKSHP_TASK_RESULTS_STATE_FILE;
		
		SetupStepsState state = new SetupStepsState();
		state.setupConfig = setupConfig;
		state.cloudTaskResults = cloudResults;
		state.controllerTaskResults = connResults;
		state.shellTaskResults = shellResults;
		
		FileOutputStream fileOutputStream = new FileOutputStream(setupStepsFilePath);
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
	    objectOutputStream.writeObject(state);
	    objectOutputStream.flush();
	    objectOutputStream.close();

	}
	
	public static void createTeardownFile(SetupConfig setupConfig, CloudTaskResults cloudResults, 
			ControllerTaskResults conResults, ShellTaskResults shellResults) throws Throwable {
		
		String filePath = setupConfig.getTeardownFilePath();
		TeardownConfig tdConf = new TeardownConfig();
		
		if (setupConfig.getTeardownScript() != null && !setupConfig.getTeardownScript().equals("")) {
			tdConf.setTeardownScript(setupConfig.getTeardownScript());
		}

		tdConf.setVaultInfo(setupConfig.getVaultInfo());

		if (conResults != null) {
			
			if (conResults.licenseRule != null) {
				tdConf.setLicenseRequestId(conResults.licenseRule.requestId);
			}
			if (conResults.rbacUser != null) {
				tdConf.setUserId(conResults.rbacUser.userId);
			}
			if (conResults.rbacRole != null) {
				tdConf.setRoleId(conResults.rbacRole.roleId);
			}
			
			
			if (conResults.apmApps != null && !conResults.apmApps.isEmpty()) {
				List<Integer> apmApps = new ArrayList<Integer>();
				
				for (ApmApp apmApp : conResults.apmApps) {
					
					if (apmApp.appType != null && apmApp.appType.equals(VADWRKSHP_APM_APP_TYPE_K8S)) {
						tdConf.setClusterAgentName(apmApp.appName);
					}
					
					apmApps.add(apmApp.appId);
					
				}
				tdConf.setApmApps(apmApps);
			}
			

			if (conResults.brumApps != null && !conResults.brumApps.isEmpty()) {
				List<Integer> brumApps = new ArrayList<Integer>();
				
				for (BrumApp brumApp : conResults.brumApps) {
					brumApps.add(brumApp.appId);
				}
				tdConf.setBrumApps(brumApps);
			}
			
			if (conResults.dbCollectors != null && !conResults.dbCollectors.isEmpty()) {
				List<Integer> dbCollectors = new ArrayList<Integer>();
				
				for (DBCollector dbCol : conResults.dbCollectors) {
					dbCollectors.add(dbCol.collectorId);
				}
				tdConf.setDbCollectors(dbCollectors);

			}
			
		}
		

		
		// add cloud teardown data
		tdConf.setCloudProvider(setupConfig.getCloudProvider());
		
		if (cloudResults != null) {
			if (cloudResults.dbInstances != null && !cloudResults.dbInstances.isEmpty()) {
				List<String> dbInstances = new ArrayList<String>();
				
				for (CloudDBInstance inst : cloudResults.dbInstances) {
					dbInstances.add(inst.getInstanceIdentifier());
				}
				tdConf.setDbInstances(dbInstances);
			}
			
			if (cloudResults.storageInstances != null && !cloudResults.storageInstances.isEmpty()) {
				List<String> storeInstances = new ArrayList<String>();
				
				for (CloudStorageInstance inst : cloudResults.storageInstances) {
					storeInstances.add(inst.getInstanceName());
				}
				tdConf.setStorageInstances(storeInstances);
			}
			
			if (cloudResults.secGroupInstances != null && !cloudResults.secGroupInstances.isEmpty()) {
				List<String> secgrpInstances = new ArrayList<String>();
				
				for (CloudSecurityGroup inst : cloudResults.secGroupInstances) {
					secgrpInstances.add(inst.getGroupId());
				}
				tdConf.setSecurityGroups(secgrpInstances);
			}
			
		}
		

		
		Yaml yml = new Yaml(new Constructor(TeardownConfig.class));
		Writer writer = new StringWriter();
		
		yml.dump(tdConf, writer);
		StringUtils.saveStringAsFile(filePath, writer.toString());
		
		logr.debug("Writing Teardown File");
		logr.debug(writer.toString());
		
	}

}
