package com.appdynamics.cloud.modern;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.appdynamics.cloud.modern.cloud.CloudHelper;
import com.appdynamics.cloud.modern.cloud.CloudTaskResults;
import com.appdynamics.cloud.modern.cloud.db.CloudDBInstance;
import com.appdynamics.cloud.modern.cloud.security.CloudSecurityGroup;
import com.appdynamics.cloud.modern.cloud.storage.CloudStorageInstance;
import com.appdynamics.cloud.modern.config.ControllerConfig;
import com.appdynamics.cloud.modern.config.ControllerLoginConfig;

//import java.util.logging.Logger;

import com.appdynamics.cloud.modern.config.SetupConfig;
import com.appdynamics.cloud.modern.config.SetupStepsState;
import com.appdynamics.cloud.modern.config.Tag;
import com.appdynamics.cloud.modern.config.Task;
import com.appdynamics.cloud.modern.config.TaskExecution;
import com.appdynamics.cloud.modern.config.TaskTarget;
import com.appdynamics.cloud.modern.config.TeardownConfig;
import com.appdynamics.cloud.modern.config.Template;
import com.appdynamics.cloud.modern.config.TemplateTarget;
import com.appdynamics.cloud.modern.controller.AppdControllerHelper;
import com.appdynamics.cloud.modern.controller.BrumApp;
import com.appdynamics.cloud.modern.controller.ControllerTaskResults;
import com.appdynamics.cloud.modern.controller.DBCollector;
import com.appdynamics.cloud.modern.controller.Dashboard;
import com.appdynamics.cloud.modern.controller.RbacUser;
import com.appdynamics.cloud.modern.controller.json.MachineAgentListResponse;
import com.appdynamics.cloud.modern.shell.ShellHelper;
import com.appdynamics.cloud.modern.shell.ShellTaskResults;
import com.appdynamics.cloud.modern.utils.ApplicationConstants;
import com.appdynamics.cloud.modern.utils.SetupStepsStateHelper;
import com.appdynamics.cloud.modern.utils.StringUtils;
import com.appdynamics.cloud.modern.vault.WorkshopVault;
import com.google.gson.Gson;

public class ADWorkshopUtils implements ApplicationConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8382650430577938576L;

	//public final static Logger logr = Logger.getLogger(ADWorkshopUtils.class.getName());
	public static Logger logr = new Logger(ADWorkshopUtils.class.getSimpleName());
	
	protected static SetupConfig SETUP_CONF;
	
	public static String LAST_SETUP_STEP_COMPLETED = "";
	
	protected static String BEGIN_TASK_ERROR_MSG = "########################          There was an error processing task :          ";
	
	protected static String END_TASK_ERROR_MSG = "          : You can attempt to resolve the issue and then re-run the setup script          ########################";
	
	
	public ADWorkshopUtils(String[] args) {
		
	}

	public static void main(String[] args) {
		
		
		try {
			
			long startTime = Calendar.getInstance().getTimeInMillis();
			
			Logger l = new Logger("");
			
			String printBanner = System.getProperty(ADWRKSHP_UTILS_SHOW_BANNER_KEY);
			
			if (printBanner != null && printBanner.equals("true")) {
				l.printBanner(true);
			} else {
				l.printBanner(false);
			}
			
			
			
			l.log("##########################################################################################    STARTING APPDYNAMICS CLOUD WORKSHOP UTILITIES    ################################################################################");
			l.carriageReturn();
			
			String action = System.getProperty(ADWRKSHP_UTILS_ACTION_KEY);
			
			if (action == null) {
				logr.error("Missing startup property -D" + ADWRKSHP_UTILS_ACTION_KEY);
				logr.error("Please set this property -D" + ADWRKSHP_UTILS_ACTION_KEY + "=" + ADWRKSHP_UTILS_ACTION_SETUP 
						+ "  OR -D" + ADWRKSHP_UTILS_ACTION_KEY + "=" + ADWRKSHP_UTILS_ACTION_TEARDOWN);
				l.carriageReturn();
				l.log("##########################################################################################    FINISHED APPDYNAMICS CLOUD WORKSHOP UTILITIES    ################################################################################");

				System.exit(1);
			}
			
			switch (action) {
			
			case ADWRKSHP_UTILS_ACTION_SETUP:
				
				if (System.getProperty(ADWRKSHP_LABUSER_KEY_PREFIX) == null) {
					logr.error("Missing startup property -D" + ADWRKSHP_LABUSER_KEY_PREFIX);
					l.carriageReturn();
					l.log("##########################################################################################    FINISHED APPDYNAMICS CLOUD WORKSHOP UTILITIES    ################################################################################");

					System.exit(1);
				}
				if (System.getProperty(ADWRKSHP_LAST_SETUP_STEP_KEY) == null) {
					logr.error("Missing startup property -D" + ADWRKSHP_LAST_SETUP_STEP_KEY);
					l.carriageReturn();
					l.log("##########################################################################################    FINISHED APPDYNAMICS CLOUD WORKSHOP UTILITIES    ################################################################################");

					System.exit(1);
				}
												
				setupWorkshop();
				
				break;

			case ADWRKSHP_UTILS_ACTION_TEARDOWN:
				
				teardownWorkshop();
				
				break;

			case ADWRKSHP_UTILS_ACTION_TEST:

				String confPath = System.getProperty(ADWRKSHP_UTILS_CONF_KEY);
				Yaml yaml = new Yaml(new Constructor(SetupConfig.class));
				InputStream inputStream = StringUtils.getFileAsStream(confPath);
				
				//TeardownConfig conf = yaml.load(inputStream);
				SetupConfig conf = yaml.load(inputStream);
				
				WorkshopVault vault = new WorkshopVault(conf.getVaultInfo());
				Gson gson = new Gson();
						
		        ControllerConfig controllerConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-info")), ControllerConfig.class);
		        ControllerLoginConfig loginConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-login-info")), ControllerLoginConfig.class);	

		        logr.info("" + controllerConfig.getControllerSslEnabled());
		        
				String labUserKey = System.getProperty(ADWRKSHP_LABUSER_KEY_PREFIX);
				
				ControllerTaskResults controllerResults = new ControllerTaskResults();
				
				RbacUser labUser = generateLabUser(labUserKey, controllerConfig, controllerResults);
				
				String labUserId = labUser.userName;
				
				CloudTaskResults cloudResults = new CloudTaskResults();
				ShellTaskResults shellResults = new ShellTaskResults();
				
				
				List<TaskExecution> execTasks = SETUP_CONF.getTaskExecutionOrder();
				
				for (TaskExecution exec : execTasks) {
					
					switch (exec.getTaskType()) {
					case VADWRKSHP_TASK_EXEC_TYPE_SHELL:
						
						// PROCESS SHELL TASK
						//processShellTask(SETUP_CONF, exec.getTaskName(), controllerConfig, controllerResults, cloudResults, shellResults);
						break;

						
					//case VADWRKSHP_TASKS_EXEC_TYPE_CLOUD:
						// PROCESS CLOUD TASKS
						//processCloudTasks(SETUP_CONF, controllerConfig, loginConfig, controllerResults, cloudResults, shellResults);
						
						//break;

					//case VADWRKSHP_TASKS_EXEC_TYPE_CONTROLLER:
						// PROCESS CONTROLLER TASKS
						//processControllerTasks(SETUP_CONF, controllerConfig, loginConfig, controllerResults, cloudResults, shellResults);
						
						// PROCESS ANY REMAINING TAGS
						//processRemainingTags(SETUP_CONF, labUserId);
						
						// FINAL TEMPLATE PROCESSING AND FILE OUTPUT
						//processTemplateOutputFiles(SETUP_CONF);
						
						//break;
						
					default:
						break;
					}
					
				}
		    
		        
		        
		        
		        
		        //logr.info("" + loginConfig.);
		        
				//AppdControllerHelper.initTeardown(conf, controllerConfig, loginConfig);

				//MachineAgentListResponse maList = AppdControllerHelper.getMachineAgentListForApmApps(conf.getApmApps());
				//AppdControllerHelper.deleteMachineAgents(maList);
				
				//if (conf.getClusterAgentName() != null) {
					//AppdControllerHelper.deleteClusterAgent(conf.getClusterAgentName());
				//}
				
				//AppdControllerHelper.closeClient();

				
				break;
				
			default:
				logr.error("Invalid startup property -D" + ADWRKSHP_UTILS_ACTION_KEY + "=" + action);
				logr.error("Please set this property -D" + ADWRKSHP_UTILS_ACTION_KEY + "=" + ADWRKSHP_UTILS_ACTION_SETUP 
						+ "  OR -D" + ADWRKSHP_UTILS_ACTION_KEY + "=" + ADWRKSHP_UTILS_ACTION_TEARDOWN);
				
				l.carriageReturn();
				l.log("##########################################################################################    FINISHED APPDYNAMICS CLOUD WORKSHOP UTILITIES    ################################################################################");

				System.exit(1);
				
				break;
			}
			
			long endTime = Calendar.getInstance().getTimeInMillis();
			long totalTimeSecs = (endTime - startTime) / 1000;
			long totalTimeMins = totalTimeSecs / 60;			
			long minsInSecs = totalTimeMins * 60;
			long remainingSecs = totalTimeSecs - minsInSecs;
			
			l.carriageReturn();
			l.carriageReturn();
			logr.info(" - Total Elapsed Time = " + totalTimeMins + " minutes : " + remainingSecs + " seconds");
			l.carriageReturn();
			
			l.carriageReturn();
			l.log("##########################################################################################    FINISHED APPDYNAMICS CLOUD WORKSHOP UTILITIES    ################################################################################");
			l.carriageReturn();
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		
	}

	private static void teardownWorkshop() throws Throwable {
		
		logr.info(" - Starting Workshop Teardown");
		
		String confPath = System.getProperty(ADWRKSHP_UTILS_CONF_KEY);
		Yaml yaml = new Yaml(new Constructor(TeardownConfig.class));
		InputStream inputStream = StringUtils.getFileAsStream(confPath);
		
		TeardownConfig conf = yaml.load(inputStream);	
		WorkshopVault vault = new WorkshopVault(conf.getVaultInfo());
		Gson gson = new Gson();
				
        ControllerConfig controllerConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-info")), ControllerConfig.class);
        ControllerLoginConfig loginConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-login-info")), ControllerLoginConfig.class);	

        
        // first find any related machine agents to the applications
        // !!!! before the applications are shutdown !!!!
		AppdControllerHelper.initTeardown(conf, controllerConfig, loginConfig);
		MachineAgentListResponse maList = AppdControllerHelper.getMachineAgentListForApmApps(conf.getApmApps());
		AppdControllerHelper.closeClient();
        
		
		// execute teardown script if there is one
		// this is typically used to stop the applications
		// and or delete a k8s cluster
		if (conf.getTeardownScript() != null && !conf.getTeardownScript().equals("")) {
			ShellHelper.executeTeardownScript(conf.getTeardownScript(), true);
		}
		
        
		AppdControllerHelper.initTeardown(conf, controllerConfig, loginConfig);
				
		
		logr.info("   - Deleting APM Applications");
		List<Integer> apmApps = conf.getApmApps();
		if (apmApps != null && !apmApps.isEmpty()) {
			for (Integer id : apmApps) {
				AppdControllerHelper.deleteApmApplication(id);
			}
			
		}
		
		logr.info("   - Deleting BRUM Applications");
		List<Integer> brumApps = conf.getBrumApps();
		if (brumApps != null && !brumApps.isEmpty()) {
			for (Integer id : brumApps) {
				AppdControllerHelper.deleteBrumApplication(id);
			}			
		}

		
		logr.info("   - Deleting DB Collectors");
		List<Integer> dbCols = conf.getDbCollectors();
		if (dbCols != null && !dbCols.isEmpty()) {
			for (Integer id : dbCols) {
				AppdControllerHelper.deleteDBCollector(id);
			}			
		}
				
		AppdControllerHelper.closeClient();
		
		
		
		// Teardown Cloud Assets
		if (conf.getCloudProvider() != null && !conf.getCloudProvider().equals("")) {
			CloudHelper.initSetup(conf.getCloudProvider());
			
			List<String> cloudIds = null;
			
			
			logr.info("   - Deleting Cloud Database Instances");
			cloudIds = conf.getDbInstances();
			if (cloudIds != null && !cloudIds.isEmpty()) {
				for (String id : cloudIds) {
					CloudHelper.deleteDBInstance(id);
				}
			}
			cloudIds = null;
			
			logr.info("   - Deleting Cloud Storage Instances");
			cloudIds = conf.getStorageInstances();
			if (cloudIds != null && !cloudIds.isEmpty()) {
				for (String id : cloudIds) {
					CloudHelper.delteStorageInstance(id);
				}
			}
			cloudIds = null;

			
			
			logr.info("   - Deleting Cloud Security Groups");
			cloudIds = conf.getSecurityGroups();
			if (cloudIds != null && !cloudIds.isEmpty()) {
				// sleeping here to ensure security groups get detached
				String msg = "     - Waiting for Security Groups to be released ...............................................................";
				                                                                                                                       //   ..........................
				logr.info(msg);
				for (int i = 0; i < 60; i++) {
					
					msg = msg.substring(0, msg.length() -1);
					Thread.currentThread().sleep(4000);
					logr.info(msg);
				}

				for (String id : cloudIds) {
					CloudHelper.deleteSecurityGroup(id);
				}
			}
			cloudIds = null;
			
			
		}

		
		AppdControllerHelper.initTeardown(conf, controllerConfig, loginConfig);

		// Delete Cluster Agent
		if (conf.getClusterAgentName() != null) {
			AppdControllerHelper.deleteClusterAgent(conf.getClusterAgentName());
		}

		
		logr.info("   - Deleting RBAC User");
		if (conf.getUserId() != null) {
			AppdControllerHelper.deleteRbacUser(conf.getUserId());
		}
		
		logr.info("   - Deleting RBAC Role");
		if (conf.getRoleId() != null) {
			AppdControllerHelper.deleteRbacRole(conf.getRoleId());
		}

		// delete the machine agents
		AppdControllerHelper.deleteMachineAgents(maList);
		
		AppdControllerHelper.closeClient();

		
		logr.info(" - Finished Workshop Teardown");
	}
	
		
	private static void setupWorkshop() throws Throwable {
		
		// LOAD CONFIG
		String confPath = System.getProperty(ADWRKSHP_UTILS_CONF_KEY);
		Yaml yaml = new Yaml(new Constructor(SetupConfig.class));
		InputStream inputStream = StringUtils.getFileAsStream(confPath);
		
		SETUP_CONF = yaml.load(inputStream);			
		logr = new Logger(ADWorkshopUtils.class.getSimpleName(), SETUP_CONF.isDebugLogging());
		//logSetupConfig();
		
		WorkshopVault vault = new WorkshopVault(SETUP_CONF.getVaultInfo());
		Gson gson = new Gson();
				
        ControllerConfig controllerConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-info")), ControllerConfig.class);
        ControllerLoginConfig loginConfig = gson.fromJson(gson.toJson(vault.getVaultSecret(null, "controller-login-info")), ControllerLoginConfig.class);	
        
        LAST_SETUP_STEP_COMPLETED =  System.getProperty(ADWRKSHP_LAST_SETUP_STEP_KEY);
		
		String labUserKey = System.getProperty(ADWRKSHP_LABUSER_KEY_PREFIX);        
        
		ControllerTaskResults controllerResults = null;
		CloudTaskResults cloudResults = null;
		ShellTaskResults shellResults = null;
		RbacUser labUser = null;
		
		if (LAST_SETUP_STEP_COMPLETED.equals(ADWRKSHP_FIRST_SETUP_STEP_TRIGGER)) {
			controllerResults = new ControllerTaskResults();
			cloudResults = new CloudTaskResults();
			shellResults = new ShellTaskResults();
			
			// Generate Lab User as RbacUser as part of ControllerTaskResults
			labUser = generateLabUser(labUserKey, controllerConfig, controllerResults);
			
		} else {
			// get serialized result objects that were saved during the last successful task
			SetupStepsState setupStepsState = SetupStepsStateHelper.getSavedSetupStepsState(SETUP_CONF);
			SETUP_CONF = setupStepsState.setupConfig;
			controllerResults = setupStepsState.controllerTaskResults;
			cloudResults = setupStepsState.cloudTaskResults;
			shellResults = setupStepsState.shellTaskResults;
			labUser = controllerResults.rbacUser;
		}
		
		
		String labUserId = labUser.userName;
		
		List<TaskExecution> execTasks = SETUP_CONF.getTaskExecutionOrder();
		
		for (TaskExecution exec : execTasks) {
			
			switch (exec.getTaskType()) {
			case VADWRKSHP_TASK_EXEC_TYPE_SHELL:
				
				// PROCESS SHELL TASK
				processShellTask(SETUP_CONF, exec.getTaskName(), controllerConfig, controllerResults, cloudResults, shellResults);
				break;

				
			case VADWRKSHP_TASKS_EXEC_TYPE_CLOUD:
				// PROCESS CLOUD TASKS
				processCloudTasks(SETUP_CONF, controllerConfig, loginConfig, controllerResults, cloudResults, shellResults);
				
				break;

			case VADWRKSHP_TASKS_EXEC_TYPE_CONTROLLER:
				// PROCESS CONTROLLER TASKS
				processControllerTasks(SETUP_CONF, controllerConfig, loginConfig, controllerResults, cloudResults, shellResults);
				
				// PROCESS ANY REMAINING TAGS
				processRemainingTags(SETUP_CONF, labUserId);
				
				// FINAL TEMPLATE PROCESSING AND FILE OUTPUT
				processTemplateOutputFiles(SETUP_CONF);
				
				break;
				
			default:
				break;
			}
			
		}
		
		
		
		// CREATE TEARDOWN FILE
		logr.info(" - Creating Teardown File");
		SetupStepsStateHelper.createTeardownFile(SETUP_CONF, cloudResults, controllerResults, shellResults);
		logr.info(" - Finished Creating Teardown File");
		
	}

	private static void processShellTask(SetupConfig setupConfig, String taskName, ControllerConfig controllerConfig, 
			ControllerTaskResults connResults, CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		boolean success = false;
		
		List<Task> tasks = setupConfig.getShellTasks();
		if (tasks != null && !tasks.isEmpty()) {
			for (Task task : tasks) {
				if (task.getTaskName().equals(taskName)) {
					
					
					
					if (task.shouldExecute(LAST_SETUP_STEP_COMPLETED)) {
						success = processShellTask(setupConfig, task, controllerConfig, 
								connResults, cloudResults, shellResults);
						
						if (success) {
							SetupStepsStateHelper.saveSuccessfulTaskCompletion(task, setupConfig, connResults, cloudResults, shellResults);
						} else {
							logr.error(BEGIN_TASK_ERROR_MSG + task.getTaskName() + END_TASK_ERROR_MSG);
							System.exit(1);
						}
						
					}
					
					
					
				}
			}
		}
		
	}
	
	private static boolean processShellTask(SetupConfig setupConfig, Task task, ControllerConfig controllerConfig, 
			ControllerTaskResults connResults, CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		
		// TODO add support for TaskType of SHELL_TASK_FIND_REPLACE_IN_FILES
		
		boolean success = false;
		
		switch (task.getTaskType()) {

		case SHELL_TASK_EXECUTE_SHELL_SCRIPT:
		
			String shellFile = "";
			String shellStartMsg = " - Start shell script execution";
			String shellEndMsg = " - Finished shell script execution";
			String[] shellArgs = null;
			boolean inheritIO = false;
			
			List<Tag> inputTags0 = task.getInputTags();
			for (Tag inputTag : inputTags0) {
				
				if (inputTag.getTagKey().equals(VADWRKSHP_SHELL_SCRIPT_FILEPATH)) {
					
					shellFile = inputTag.getTagValue();	
				
				} else if (inputTag.getTagKey().equals(VADWRKSHP_SHELL_SCRIPT_ARGUMENTS)) {
					
					if (inputTag.getTagValue() != null && !inputTag.getTagValue().equals("")) {
						shellArgs = StringUtils.split(inputTag.getTagValue(), ",");
						String[] processedArgs = new String[shellArgs.length];
						
						for (int i = 0; i < shellArgs.length; i++) {
							
							if (tagValueHasPlaceholderKey(shellArgs[i])) {
								
								switch (shellArgs[i]) {
								case VADWRKSHP_ACCT_ACCESS_KEY:
									
									processedArgs[i] = controllerConfig.getControllerAccessKey();
									
									break;

								default:
									break;
								}
								
							} else {
								processedArgs[i] = shellArgs[i];
								
							}
							
						}
						
						shellArgs = processedArgs;
						
					}
					
					
					
				} else if (inputTag.getTagKey().equals(VADWRKSHP_SHELL_SCRIPT_BEGIN_MSG)) {
					
					shellStartMsg = inputTag.getTagValue();
					
				} else if (inputTag.getTagKey().equals(VADWRKSHP_SHELL_SCRIPT_END_MSG)) {
					
					shellEndMsg = inputTag.getTagValue();

				} else if (inputTag.getTagKey().equals(VADWRKSHP_SHELL_SCRIPT_INHERIT_IO)) {
					
					if (inputTag.getTagValue() != null && inputTag.getTagValue().equalsIgnoreCase("true")) {
						
						inheritIO = true;
						
					}					
					
				}
				
			}
			
			logr.carriageReturn();
			logr.info(shellStartMsg);
			logr.carriageReturn();
			
			
			
			success = ShellHelper.executeShellScript(setupConfig, shellFile, shellArgs, shellResults, inheritIO);
			
			if (success) {
				logr.carriageReturn();
				logr.info(shellEndMsg);
				logr.carriageReturn();				
			}
			
			break;
			

		case SHELL_TASK_EXPAND_DISK_VOLUME:
			
			int sizeGb = 80;
			
			List<Tag> inputTags1 = task.getInputTags();
			for (Tag inputTag : inputTags1) {
				
				if (inputTag.getTagKey().equals(VADWRKSHP_DISK_VOLUME_SIZE_GB)) {
					sizeGb = Integer.parseInt(inputTag.getTagValue());	
				}
				
			}
			
			if (setupConfig.getCloudProvider().equals(ADWRKSHP_CLOUD_PROVIDER_AWS)) {
				success = ShellHelper.expandEC2InstanceVolume(setupConfig, sizeGb, shellResults);
			}
						
			break;
			
			
			
		case SHELL_TASK_DEPLOY_DB_AGENT:
			
			String dbAgentName = "";
			String dbAgentHostname = "";
			String dbAgentHomeDir = "";
			String dbStartMsg = " - Start shell script execution";
			String dbEndMsg = " - Finished shell script execution";
			
			List<Tag> inputTags2 = task.getInputTags();
			for (Tag inputTag : inputTags2) {
				
				if (inputTag.getTagKey().equals(VADWRKSHP_DB_AGENT_NAME)) {
					
					dbAgentName = getInputTagValue(inputTag, connResults.rbacUser.userName);
					dbAgentHostname = dbAgentName + "Host";
					
				} else if (inputTag.getTagKey().equals(VADWRKSHP_DB_AGENT_HOME_DIR)) {
					
					dbAgentHomeDir = inputTag.getTagValue();
					inputTag.setTagValueResult(dbAgentHomeDir);
					
				} else if (inputTag.getTagKey().equals(VADWRKSHP_SHELL_SCRIPT_BEGIN_MSG)) {
					
					dbStartMsg = inputTag.getTagValue();
					
				} else if (inputTag.getTagKey().equals(VADWRKSHP_SHELL_SCRIPT_END_MSG)) {
					
					dbEndMsg = inputTag.getTagValue();					
				}
				
			}
			
			logr.carriageReturn();
			logr.info(dbStartMsg);
			//logr.carriageReturn();
			
			success = ShellHelper.downloadDBAgent(setupConfig, dbAgentHomeDir);
			success = ShellHelper.launchDBAgent(dbAgentHostname, controllerConfig, dbAgentHomeDir, dbAgentName, shellResults);
			
			//logr.carriageReturn();
			
			if (success) {
				logr.info(dbEndMsg);
				logr.carriageReturn();				
			}			
			
			break;
		
			
			
		default:
			
			success = true;
			
			break;
		}
		
		return success;
	}
	
	private static void processCloudTasks(SetupConfig setupConfig, ControllerConfig controllerConfig, ControllerLoginConfig loginConfig, 
			ControllerTaskResults connResults, CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		
		CloudHelper.initSetup(setupConfig.getCloudProvider());
		List<Task> tasks = setupConfig.getCloudTasks();		
		
		for (Task task : tasks) {
			
			boolean success = false;
			
			if (task.shouldExecute(LAST_SETUP_STEP_COMPLETED)) {
				
				success = processCloudTask(setupConfig, task, connResults, cloudResults, shellResults);
				
				if (success) {
					SetupStepsStateHelper.saveSuccessfulTaskCompletion(task, setupConfig, connResults, cloudResults, shellResults);
				} else {
					logr.error(BEGIN_TASK_ERROR_MSG + task.getTaskName() + END_TASK_ERROR_MSG);
					System.exit(1);
				}
				
			}
			
			
		}	
		
	}
	
	private static boolean processCloudTask(SetupConfig setupConfig, Task task, ControllerTaskResults connResults, 
			CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		
		boolean success = false;
		
		List<Template> templates = setupConfig.getTemplates();
		switch (task.getTaskType()) {
		
		case CLOUD_TASK_CREATE_STORAGE_AWS_S3_BUCKET:
			
			try {
				Tag storageTag = null;
				List<Tag> inputTags0 = task.getInputTags();
				for (Tag tag : inputTags0) {
					
					if (tag.getTagKey().startsWith(VADWRKSHP_CLOUD_STORG_INST_NAME_PFX)) {
						storageTag = tag;
					}
				}
				
				String preTagVal = connResults.rbacUser.userName.toLowerCase();
				String storageTagVal = getInputTagValue(storageTag, preTagVal);
				
				CloudStorageInstance csInst = CloudHelper.createStorageInstance(storageTagVal);	
				cloudResults.storageInstances.add(csInst);

				processTemplateTargets(task.getTemplateTargets(), templates, storageTag.getTagKey(), csInst.getInstanceName());
				
				success = true;
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
			break;
		
		case CLOUD_TASK_CREATE_DB_AWS_RDS_MYSQL:

			try {
				String instanceName = "";
				String instanceType = "";
				int instancePort = 3306;
				String instanceUser = "";
				String instancePassword = "";
				String securityGroupId = "";
				CloudSecurityGroup group = null;
				Tag dbInputTag = null;
				
				List<Tag> inputTags = task.getInputTags();
				
				for (Tag tag : inputTags) {
					
					switch (tag.getTagKey()) {
					
					case VADWRKSHP_CLOUD_DB_INST_NAME:
						instanceName = getInputTagValue(tag, connResults.rbacUser.userName);
						instanceName = instanceName.toLowerCase();

						setInputTagValueResult(tag, instanceName);
						break;

					case VADWRKSHP_CLOUD_DB_INST_TYPE:
						instanceType = tag.getTagValue();
						break;

					case VADWRKSHP_CLOUD_DB_INST_PORT:
						instancePort = Integer.parseInt(tag.getTagValue());
						setInputTagValueResult(tag, "" + instancePort);
						break;
						
					case VADWRKSHP_CLOUD_DB_INST_USER:
						instanceUser = tag.getTagValue();
						setInputTagValueResult(tag, instanceUser);
						break;
						
					case VADWRKSHP_CLOUD_DB_INST_PWD:
						instancePassword = tag.getTagValue();
						setInputTagValueResult(tag, instancePassword);
						break;
						
					case VADWRKSHP_CLOUD_SECURITY_GROUP_ID:
						securityGroupId = getInputTagValue(tag, connResults.rbacUser.userName);
						
						group = CloudHelper.createSecurityGroup(securityGroupId);
						//group = CloudHelper.getSecurityGroup(securityGroupId);
						cloudResults.secGroupInstances.add(group);
						break;

					case VADWRKSHP_CLOUD_DB_INST_ENDPOINT:
						dbInputTag = tag;
						break;

					default:
						break;
					}
					
				}
				
				CloudDBInstance cdbInst = CloudHelper.createDBInstance(instanceName, instanceType, instancePort, instanceUser, instancePassword, group);	
				cloudResults.dbInstances.add(cdbInst);
				
				setInputTagValueResult(dbInputTag, cdbInst.getInstanceEndpoint());
							
				task.getTaskTargets();
				setupConfig.getControllerTasks();
				
				processCloudTaskTargets(task.getTaskTargets(), task.getInputTags(), setupConfig.getControllerTasks());
				
				success = true;
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
			break;

			
		default:
			
			success = true;
			break;
			
		}
		
		return success;
	}

	private static void setInputTagValueResult(Tag tag, String value) {
		tag.setTagValueResult(value);
	}	
	
	
	private static void processCloudTaskTargets(List<TaskTarget> cloudTaskTargets, List<Tag> cloudInputTags, List<Task> connTasks) {
		
		for (TaskTarget cloudTaskTarget : cloudTaskTargets) {
			
			for (Task connTask : connTasks) {
				
				if (cloudTaskTarget.getTaskName().equals(connTask.getTaskName()) && cloudTaskTarget.getTaskType().equals(connTask.getTaskType())) {
					
					// now we have matched a cloud taskTarget to a controllerTask
					// get the target tags for the cloud task
					List<Tag> cloudTargetTags = cloudTaskTarget.getTargetTags();
					
					
					for (Tag cloudTargetTag : cloudTargetTags) {
						
						// set the tag results on the cloud target tags first
						for (Tag cloudTaskInputTag : cloudInputTags) {
							
							if (cloudTargetTag.getTagKey().equals(cloudTaskInputTag.getTagKey())) {
								
								// now we have matched a cloud target tag to cloud task input tag
								if (cloudTaskInputTag.getTagValueResult() != null) {
									
									cloudTargetTag.setTagValueResult(cloudTaskInputTag.getTagValueResult());
								}
								
							}
						}
						
						// now match the target tag to the controller task input tag and set the value for the input tag
						
						List<Tag> connTaskInputTags = connTask.getInputTags();
						for (Tag connTaskInputTag : connTaskInputTags) {
							
							if (connTaskInputTag.getTagKey().equals(cloudTargetTag.getTagKey())) {
								
								if (cloudTargetTag.getTagValueResult() != null) {
									connTaskInputTag.setTagValue(cloudTargetTag.getTagValueResult());
									connTaskInputTag.setTagValueResult(cloudTargetTag.getTagValueResult());
								}
								
							}
						}
						
						
					} // end of cloud target tag loop
					
					
					
				} // end if on matching a cloud taskTarget to a controllerTask
				
			} // end of connTasks loop
			
		}
		
		
	}

	
	private static void processRemainingTags(SetupConfig setupConfig, String labUserId) throws Throwable {
		
		List<Template> templates = setupConfig.getTemplates();
		for (Template temp : templates) {
			List<Tag> tags = temp.getTags();
			
			for (Tag tag : tags) {
				
				if (tag.getTagValueResult() == null) {
					
					// populate tags that need the cloud region
					if (tag.getTagValue().equals(VADWRKSHP_CLOUD_REGION)) {
						//if (setupConfig.isUsingCloudTasks()) {
							tag.setTagValueResult(CloudHelper.getCurrentCloudRegion());
						//}
					
					// populate tags that need the cloud provider / cloud mode
					} else if (tag.getTagValue().equals(VADWRKSHP_CLOUD_MODE)) { 
						//if (setupConfig.isUsingCloudTasks()) {
							tag.setTagValueResult(CloudHelper.getCloudProvider());
						//}
						
						
					// populate any remaining tags that need labUserId substitution	
					} else {
						String tagValue = tag.getTagValue();
						String result = StringUtils.replaceFirst(tagValue, VADWRKSHP_LABUSER_KEY, labUserId);
						tag.setTagValueResult(result);
						
					}
					
					
				}
				
			}

		
		}		
	}
	
	private static void processTemplateOutputFiles(SetupConfig setupConfig) throws Throwable {
		
		List<Template> templates = setupConfig.getTemplates();
		for (Template temp : templates) {
			
			String tempStr = StringUtils.getFileAsString(temp.getSourceFile());
			List<Tag> tags = temp.getTags();
			
			for (Tag tag : tags) {
				if (tag.getTagValueResult() != null) {
					tempStr = StringUtils.replaceAll(tempStr, tag.getTagKey(), tag.getTagValueResult());
				}
			}
			
			StringUtils.saveStringAsFile(temp.getDestinationFile(), tempStr);
		}
		
		
		
		
	}
	

	private static boolean processControllerTask(SetupConfig setupConfig, Task task, ControllerConfig controllerConfig, 
			ControllerTaskResults connResults, CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		
		boolean success = false;
		List<Template> templates = setupConfig.getTemplates();
		
		switch (task.getTaskType()) {
		
		case CONTROLLER_TASK_CREATE_APM_APP:
			
			try {
				List<Tag> apmInputTags = task.getInputTags();
				
				String apmAppNameKey = "";
				String apmAppName = "";
				String apmAppType = "other";
				
				for (Tag tag : apmInputTags) {
					
					switch (tag.getTagKey()) {
					case VADWRKSHP_APM_APP_NAME_POST:

						apmAppNameKey = VADWRKSHP_APM_APP_NAME_POST;
						apmAppName = getInputTagValue(tag, connResults.rbacUser.userName);
						
						break;

					case VADWRKSHP_APM_APP_NAME_PRE:
						
						apmAppNameKey = VADWRKSHP_APM_APP_NAME_PRE;
						apmAppName = getInputTagValue(tag, connResults.rbacUser.userName);
						
						break;

					case VADWRKSHP_APM_APP_TYPE:

						apmAppType = tag.getTagValue();
						tag.setTagValueResult(apmAppType);
						
						break;

						
					default:
						
						apmAppNameKey = tag.getTagKey();
						apmAppName = getInputTagValue(tag, connResults.rbacUser.userName);
						
						break;
					}
					
				}
				
							
				AppdControllerHelper.createApmApp(connResults, apmAppName, apmAppType);
				
				processTemplateTargets(task.getTemplateTargets(), templates, apmAppNameKey, apmAppName);
				
				success = true;
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
			break;
		
		case CONTROLLER_TASK_CREATE_BRUM_APP:

			try {
				Tag brumTag = null;
				
				List<Tag> inTags = task.getInputTags();
				for (Tag tag : inTags) {
					if (tag.getTagKey().startsWith(VADWRKSHP_EUM_APP_NAME_PFX)) {
						brumTag = tag;
					}
					
				}
			
				String brumTagVal = getInputTagValue(brumTag, connResults.rbacUser.userName);
				AppdControllerHelper.createBrumApp(connResults, brumTagVal);
				
				processTemplateTargets(task.getTemplateTargets(), templates, brumTag.getTagKey(), brumTagVal);
				
				for (BrumApp brumApp : connResults.brumApps) {
					if (brumApp.appName.equals(brumTagVal)) {
						
						processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_EUM_APP_KEY_PRE, brumApp.eumKey);
						processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_EUM_APP_KEY_POST, brumApp.eumKey);
					
					}
					
				}
				
				success = true;
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
			
			break;

		case CONTROLLER_TASK_CREATE_RBAC_USER:
			
			try {
				// TODO change to loop thru input tags
				// TODO fix hacks here - add input tags for all role_ids and cloudNativeAppId
				
				// Hack to add Cloud Native APM App to RBAC Role
				int cloudNativeAppId = 557395; 
				
				AppdControllerHelper.createRbacUser(connResults.rbacUser, connResults);
				AppdControllerHelper.createRbacRole(connResults.rbacUser.userName + "-Role", connResults, cloudNativeAppId);
				
				
				Thread.currentThread().sleep(2000);
				
				// aws-sandbox controller
				// Hack to add user to 'Server Monitoring User (Default)' Role (id = 58)
				// Hack to add user to 'DB Monitoring User (Default)' Role (id = 55)

				// kickstarter controller
				// Hack to add user to 'Server Monitoring User (Default)' Role (id = 9321)
				// Hack to add user to 'DB Monitoring User (Default)' Role (id = 9318)
				
				// channel controller
				// Hack to add user to 'Server Monitoring User (Default)' Role (id = 286308)
				// Hack to add user to 'DB Monitoring User (Default)' Role (id = 286305)
				// Hack to add user to 'viewinfraentity' Role (id = 286387)
	            // Hack to add user to 'Analytics User' Role (id = 286459)
				
				int[] defRoleIds = new int[4];
				defRoleIds[0] = 286308;
				defRoleIds[1] = 286305;
				defRoleIds[2] = 286387;
				defRoleIds[3] = 286459;
				
				
				AppdControllerHelper.addDefaultRolesToUser(connResults.rbacUser, defRoleIds);
				
				Thread.currentThread().sleep(2000);
				
				 
				AppdControllerHelper.addUserToRole(connResults.rbacUser, connResults.rbacRole);
				
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_CONTROLLER_SSL_ENABLED, "" + controllerConfig.getControllerSslEnabled());
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_CONTROLLER_PORT, "" + controllerConfig.getControllerPort());
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_CONTROLLER_HOST, controllerConfig.getControllerHostName());
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_CONTROLLER_URL_FULL, controllerConfig.getControllerUrlFull());
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_CONTROLLER_ACCT_NAME, controllerConfig.getControllerAccount());
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_ACCT_ACCESS_KEY, controllerConfig.getControllerAccessKey());
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_LABUSER_KEY, connResults.rbacUser.userName);
				
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_LABUSER_PWD, connResults.rbacUser.userPwd);
				
				String uuid = UUID.randomUUID().toString();
				//String randomUuid = uuid.substring(24, uuid.length());
				processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_RANDOM_UUID, uuid);
				
				success = true;
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
			break;
			
		case CONTROLLER_TASK_CREATE_DB_COLLECTOR:
			
			try {
				DBCollector dbColl = new DBCollector();
				
				List<Tag> tags = task.getInputTags();
							
				//logr.carriageReturn();
				//logr.info("DB Collector Task Name: " + task.getTaskName());
				//logr.info("DB Collector Input Tags");
				
				for (Tag tag : tags) {
					
					switch (tag.getTagKey()) {
					case VADWRKSHP_CLOUD_DB_INST_NAME:
						String instNameTagVal = getInputTagValue(tag, connResults.rbacUser.userName);
						dbColl.collectorName = instNameTagVal;
						break;

					case VADWRKSHP_CLOUD_DB_INST_TYPE:
						tag.setTagValueResult(tag.getTagValue());
						dbColl.collectorType = tag.getTagValueResult();
						break;

					case VADWRKSHP_DB_AGENT_NAME:
						if (tagValueHasPlaceholderKey(tag.getTagValue())) {
							tag.setTagValue(shellResults.dbAgentName);
						} 
						tag.setTagValueResult(tag.getTagValue());
						dbColl.dbAgentName = tag.getTagValueResult();
						break;
						
					case VADWRKSHP_CLOUD_DB_INST_ENDPOINT:
						tag.setTagValueResult(tag.getTagValue());
						dbColl.dbHost = tag.getTagValue();
						break;

					case VADWRKSHP_CLOUD_DB_INST_PORT:
						tag.setTagValueResult(tag.getTagValue());
						dbColl.dbPort = tag.getTagValue();
						break;

					case VADWRKSHP_CLOUD_DB_INST_USER:
						tag.setTagValueResult(tag.getTagValue());
						dbColl.dbUsername = tag.getTagValue();
						break;

					case VADWRKSHP_CLOUD_DB_INST_PWD:
						tag.setTagValueResult(tag.getTagValue());
						dbColl.dbPassword = tag.getTagValue();
						break;
						
					default:
						break;
					}
					
					
					//logr.info("TagKey: " + tag.getTagKey());
					//logr.info("TagValue: " + tag.getTagValue());
					//logr.info("TagValueResult: " + tag.getTagValueResult());
					
					//logr.carriageReturn();
					
				} // end of for loop

				connResults.dbCollectors.add(dbColl);
				
				// create the collector in the controller
				AppdControllerHelper.createDBCollector(dbColl.collectorName, connResults);
				
				// last thing to do here
				if (task.getTemplateTargets() != null && !task.getTemplateTargets().isEmpty()) {
					processTemplateTargets(task.getTemplateTargets(), templates, VADWRKSHP_CLOUD_DB_INST_ENDPOINT, dbColl.dbHost);
				}
				
				success = true;
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
			break;

		
		case CONTROLLER_TASK_SET_DASHBOARD_PERMISSIONS:
			
			try {
				Dashboard dash = new Dashboard();
				List<Tag> dashTags = task.getInputTags();
				
				for (Tag tag : dashTags) {
					
					switch (tag.getTagKey()) {
					case VADWRKSHP_DASHBOARD_ID:
						tag.setTagValueResult(tag.getTagValue());
						dash.dashboardId = Integer.parseInt(tag.getTagValueResult());
						break;

					case VADWRKSHP_DASHBOARD_ACTION_VIEW:
						tag.setTagValueResult(tag.getTagValue());
						dash.allowViewing = Boolean.parseBoolean(tag.getTagValueResult());
						break;

					case VADWRKSHP_DASHBOARD_ACTION_EDIT:
						tag.setTagValueResult(tag.getTagValue());
						dash.allowEditing = Boolean.parseBoolean(tag.getTagValueResult());
						break;
						
					case VADWRKSHP_DASHBOARD_ACTION_DELETE:
						tag.setTagValueResult(tag.getTagValue());
						dash.allowDeletion = Boolean.parseBoolean(tag.getTagValueResult());
						break;

					case VADWRKSHP_DASHBOARD_ACTION_SHARE:
						tag.setTagValueResult(tag.getTagValue());
						dash.allowSharing = Boolean.parseBoolean(tag.getTagValueResult());
						break;
						
					default:
						break;
					}
				
				}
				
				
				connResults.dashboards.add(dash);
				
				success = true;
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
			
			
		default:
			success = true;
			break;
		}
		
		return success;
	}
	
	private static boolean tagValueHasPlaceholderKey(String tagValue) {
		if (tagValue == null) return false;
		CharSequence charr = "@[";
		if (tagValue.contains(charr)) {
			return true;
		}
		return false;
	}
	
	
	private static void processTemplateTargets(List<TemplateTarget> targets, List<Template> templates, String tagValueKey, String tagValue) {
		
		for (TemplateTarget target : targets) {
			
			for (Template template : templates) {
				
				if (template.getTemplateName().equals(target.getTemplateName())) {
					
					List<Tag> tags = template.getTags();
					for (Tag tag : tags) {
						
						if (tag.getTagValue().equals(tagValueKey)) {
							if (tag.getTagValueResult() == null) {
								tag.setTagValueResult(tagValue);
							}
							
						}
						
					}
				}
				
			}
			
		}
		
	}
	
	private static String getInputTagValue(Tag tag, String labUserId) {
		String tagValue = tag.getTagValue();
		String result = StringUtils.replaceFirst(tagValue, VADWRKSHP_LABUSER_KEY, labUserId);
		tag.setTagValueResult(result);
		return result;		
		
	}
	
	
	private static void processControllerTasks(SetupConfig setupConfig, ControllerConfig controllerConfig, ControllerLoginConfig loginConfig, 
			ControllerTaskResults connResults, CloudTaskResults cloudResults, ShellTaskResults shellResults) throws Throwable {
		
		AppdControllerHelper.initSetup(setupConfig, controllerConfig, loginConfig);
		
		List<Task> tasks = setupConfig.getControllerTasks();
		
		for (Task task : tasks) {
			
		
			boolean success = false;
			
			if (task.shouldExecute(LAST_SETUP_STEP_COMPLETED)) {
				
				success = processControllerTask(setupConfig, task, controllerConfig, connResults, cloudResults, shellResults);
				
				if (success) {
					SetupStepsStateHelper.saveSuccessfulTaskCompletion(task, setupConfig, connResults, cloudResults, shellResults);
				} else {
					logr.error(BEGIN_TASK_ERROR_MSG + task.getTaskName() + END_TASK_ERROR_MSG);
					System.exit(1);
				}
				
			}
			
		
		}
	
		AppdControllerHelper.closeClient();
	}
	
	
	private static RbacUser generateLabUser(String labUserKey, ControllerConfig controllerConfig, ControllerTaskResults controllerResults) {
		
		RbacUser user = new RbacUser();
		
		String key = UUID.randomUUID().toString();
		
		String labUserPwdSuffix = key.substring(0, 7);
		
		while (!isValidPassword(labUserKey + labUserPwdSuffix)) {
			
			key = UUID.randomUUID().toString();
			labUserPwdSuffix = key.substring(0, 7);
			labUserPwdSuffix = convertToTitleCaseIteratingChars(labUserPwdSuffix);
		}
		
		user.userName = labUserKey + labUserPwdSuffix.substring(0, 4);
		user.userPwd = user.userName + labUserPwdSuffix.substring(4, 7);
		user.email = user.userName + "@email.com";
		user.accessKey = controllerConfig.getControllerAccessKey();
		
		controllerResults.rbacUser = user;
		
		return user;
		
	}
	
	
	private static boolean isValidPassword(String pwd) {
		String allUppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String allLowers = "abcdefghijklmnopqrstuvwxyz";
		String allNums = "0123456789";
		
		boolean hasUpper = false;
		boolean hasLower = false;
		boolean hasNum = false;
		
		String[] pwdChars = pwd.split("");
	
		CharSequence charr; 
		for (int i = 0; i < pwdChars.length; i++) {
			charr = pwdChars[i];
			if (allUppers.contains(charr)) {
				hasUpper = true;
			}
			if (allLowers.contains(charr)) {
				hasLower = true;
			}
			if (allNums.contains(charr)) {
				hasNum = true;
			}
		}
		
		if (hasUpper && hasLower && hasNum) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String convertToTitleCaseIteratingChars(String text) {
	    if (text == null || text.isEmpty()) {
	        return text;
	    }
	 
	    StringBuilder converted = new StringBuilder();
	 
	    boolean convertNext = true;
	    for (char ch : text.toCharArray()) {
	        if (Character.isSpaceChar(ch)) {
	            convertNext = true;
	        } else if (convertNext) {
	            ch = Character.toTitleCase(ch);
	            convertNext = false;
	        } else {
	            ch = Character.toLowerCase(ch);
	        }
	        converted.append(ch);
	    }
	 
	    return converted.toString();
	}	
	
	private static void logSetupConfig() {
		
		logr.info("Description = " + SETUP_CONF.getDescription());
		logr.info("TeardownFilePath = " + SETUP_CONF.getTeardownFilePath());
		logr.info("UsingCloudTasks = " + SETUP_CONF.isUsingCloudTasks());
		logr.info("CloudProvider = " + SETUP_CONF.getCloudProvider());
		logr.info("DebugLogging = " + SETUP_CONF.isDebugLogging());
		logr.carriageReturn();;
		
		List<Template> temps = SETUP_CONF.getTemplates();
		
		for (Template temp : temps) {
			logr.info("TemplateName = " + temp.getTemplateName());
			logr.info("SourceFile = " + temp.getSourceFile());
			logr.info("DestinationFile = " + temp.getDestinationFile());
			
			List<Tag> tags = temp.getTags();
			for (Tag tag : tags) {
				logr.info("     TagKey = " + tag.getTagKey());
				logr.info("     TagValue = " + tag.getTagValue());
			}
			
			logr.carriageReturn();
		}
		
		logr.carriageReturn();
		
		List<Task> contTasks = SETUP_CONF.getControllerTasks();
		
		for (Task task : contTasks) {
			logr.info("TaskType = " + task.getTaskType());
			
			List<Tag> tags = task.getInputTags();
			if (tags != null) {
				for (Tag tag : tags) {
					logr.info("          InputTagKey = " + tag.getTagKey());
					logr.info("          InputTagValue = " + tag.getTagValue());
				}
			}

			logr.carriageReturn();
			
			List<TemplateTarget> targs = task.getTemplateTargets();
			for (TemplateTarget targ : targs) {
				logr.info("     TemplateName = " + targ.getTemplateName());
				
				List<Tag> ttags = targ.getTargetTags();
				for (Tag tag : ttags) {
					logr.info("          TagKey = " + tag.getTagKey());
					logr.info("          TagValue = " + tag.getTagValue());
				}
			}
			
			logr.carriageReturn();
		}


		logr.carriageReturn();
		
		
		
		List<Task> cloudTasks = SETUP_CONF.getCloudTasks();
		
		for (Task task : cloudTasks) {
			logr.info("TaskType = " + task.getTaskType());
			
			List<TemplateTarget> targs = task.getTemplateTargets();
			for (TemplateTarget targ : targs) {
				logr.info("     TemplateName = " + targ.getTemplateName());
				
				List<Tag> tags = targ.getTargetTags();
				for (Tag tag : tags) {
					logr.info("          TagKey = " + tag.getTagKey());
					logr.info("          TagValue = " + tag.getTagValue());
				}
			}
			
			logr.carriageReturn();
		}
		
	}	
	
	
	//ADWorkshopUtils utils = new ADWorkshopUtils(args);
	//utils.appStarted();
	
	//AppdController cnr = new AppdController();
	
	//cnr.initClient();
	
	//cnr.getRole(50);
	//String app = cnr.createApmApplication("My-New-App-3");
	
	//cnr.deleteApmApplication(1121);
	
	//cnr.closeClient();
	
//	logr.info(Regions.DEFAULT_REGION.getName());
//	logr.info(Regions.US_WEST_1.getName());
//	logr.info(Regions.US_WEST_2.getName());
//	logr.info(Regions.US_EAST_1.getName());
//	logr.info(Regions.US_EAST_2.getName());
//	
//	Regions reg = Regions.fromName("us-west-2");
//	logr.info(reg.getName());
	
	//Region region = Regions.getCurrentRegion();
	//logr.info(region.getName());	
	
}
