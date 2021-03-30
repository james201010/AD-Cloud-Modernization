/**
 * 
 */
package com.appdynamics.cloud.modern.utils;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public interface ApplicationConstants extends Serializable {

	// ####### -D Properties #######
	// The absolute path of the setup.yaml file 
	public static final String ADWRKSHP_UTILS_CONF_KEY = "workshopUtilsConf";
	
	// The 5 character lab user id used to generate the final lab user key
	public static final String ADWRKSHP_LABUSER_KEY_PREFIX = "workshopLabUserPrefix";
	
	public static final String ADWRKSHP_LAST_SETUP_STEP_KEY = "lastSetupStepDone";
	public static final String ADWRKSHP_FIRST_SETUP_STEP_TRIGGER = "100";
	public static final String ADWRKSHP_TASK_RESULTS_STATE_FILE = "SetupStepsState.fvq";
	
	
	// Action parameter ('setup' or 'teardown' or 'test')
	public static final String ADWRKSHP_UTILS_ACTION_KEY = "workshopAction";
	public static final String ADWRKSHP_UTILS_ACTION_SETUP = "setup";
	public static final String ADWRKSHP_UTILS_ACTION_TEARDOWN = "teardown";
	public static final String ADWRKSHP_UTILS_ACTION_TEST = "test";
	
	// print logo banner at start
	public static final String ADWRKSHP_UTILS_SHOW_BANNER_KEY = "showWorkshopBanner";
	
	// Cloud provider values
	public static final String ADWRKSHP_CLOUD_PROVIDER_AWS = "aws";
	public static final String ADWRKSHP_CLOUD_PROVIDER_AZURE = "azure";
	public static final String ADWRKSHP_CLOUD_PROVIDER_GCP = "gcp";
	
	
	// Task Execution Order Types
	public static final String VADWRKSHP_TASK_EXEC_TYPE_SHELL = "SHELL_TASK";
	public static final String VADWRKSHP_TASKS_EXEC_TYPE_CLOUD = "CLOUD_TASKS";
	public static final String VADWRKSHP_TASKS_EXEC_TYPE_CONTROLLER_INIT = "CONTROLLER_INIT_TASKS";
	public static final String VADWRKSHP_TASKS_EXEC_TYPE_CONTROLLER = "CONTROLLER_TASKS";
	
	
	// Tag Value Keys
	public static final String VADWRKSHP_RANDOM_UUID = "@[ADWRKSHP_RANDOM_UUID]";
	public static final String VADWRKSHP_UTILS_TASK_RESULT = "@[ADWRKSHP_UTILS_TASK_RESULT]";
	public static final String VADWRKSHP_LABUSER_KEY = "@[ADWRKSHP_LABUSER_KEY]";
	public static final String VADWRKSHP_LAST_SETUP_STEP_TRIGGER = "@[ADWRKSHP_LAST_SETUP_STEP_TRIGGER]";
	public static final String VADWRKSHP_NEXT_SETUP_STEP_TRIGGER = "@[ADWRKSHP_NEXT_SETUP_STEP_TRIGGER]";
	
	public static final String VADWRKSHP_CONTROLLER_URL_FULL = "@[ADWRKSHP_CONTROLLER_URL_FULL]";
	public static final String VADWRKSHP_CONTROLLER_HOST = "@[ADWRKSHP_CONTROLLER_HOST]";
	public static final String VADWRKSHP_CONTROLLER_ACCT_NAME = "@[ADWRKSHP_CONTROLLER_ACCT_NAME]";
	public static final String VADWRKSHP_CONTROLLER_PORT = "@[ADWRKSHP_CONTROLLER_PORT]";
	public static final String VADWRKSHP_CONTROLLER_SSL_ENABLED = "@[ADWRKSHP_CONTROLLER_SSL_ENABLED]";
	public static final String VADWRKSHP_ACCT_ACCESS_KEY = "@[ADWRKSHP_ACCT_ACCESS_KEY]";
	public static final String VADWRKSHP_LABUSER_PWD = "@[ADWRKSHP_LABUSER_PWD]";
	
	public static final String VADWRKSHP_LICENSE_RULE_NAME = "@[ADWRKSHP_LICENSE_RULE_NAME]";
	public static final String VADWRKSHP_LICENSE_RULE_NBR_APM = "@[ADWRKSHP_LICENSE_RULE_NBR_APM]";
	public static final String VADWRKSHP_LICENSE_RULE_NBR_MA = "@[ADWRKSHP_LICENSE_RULE_NBR_MA]";
	public static final String VADWRKSHP_LICENSE_RULE_NBR_SIM = "@[ADWRKSHP_LICENSE_RULE_NBR_SIM]";
	public static final String VADWRKSHP_LICENSE_RULE_NBR_NET = "@[ADWRKSHP_LICENSE_RULE_NBR_NET]";
	
	
	public static final String VADWRKSHP_EUM_APP_KEY = "@[ADWRKSHP_EUM_APP_KEY]";
	public static final String VADWRKSHP_EUM_APP_KEY_PRE = "@[ADWRKSHP_EUM_APP_KEY_PRE]";
	public static final String VADWRKSHP_EUM_APP_KEY_POST = "@[ADWRKSHP_EUM_APP_KEY_POST]";
	public static final String VADWRKSHP_EUM_APP_NAME_PFX = "@[ADWRKSHP_EUM_APP_NAME";
	
	public static final String VADWRKSHP_EUM_APP_CONF_UPDATE = "@[ADWRKSHP_EUM_APP_CONF_UPDATE]";
	public static final String VADWRKSHP_EUM_APP_CONF_SLOWT_TYPE = "@[ADWRKSHP_EUM_APP_CONF_SLOWT_TYPE]";
	public static final String VADWRKSHP_EUM_APP_CONF_SLOWT_VAL = "@[ADWRKSHP_EUM_APP_CONF_SLOWT_VAL]";
	public static final String VADWRKSHP_EUM_APP_CONF_VSLOWT_TYPE = "@[ADWRKSHP_EUM_APP_CONF_VSLOWT_TYPE]";
	public static final String VADWRKSHP_EUM_APP_CONF_VSLOWT_VAL = "@[ADWRKSHP_EUM_APP_CONF_VSLOWT_VAL]";
	
	
	public static final String VADWRKSHP_CLOUD_REGION = "@[ADWRKSHP_CLOUD_REGION]";
	public static final String VADWRKSHP_CLOUD_MODE = "@[ADWRKSHP_CLOUD_MODE]";
	
	public static final String VADWRKSHP_APM_APP_NAME = "@[ADWRKSHP_APM_APP_NAME]";
	public static final String VADWRKSHP_APM_APP_NAME_POST = "@[ADWRKSHP_APM_APP_NAME_POST]";
	public static final String VADWRKSHP_APM_APP_NAME_PRE = "@[ADWRKSHP_APM_APP_NAME_PRE]";
	public static final String VADWRKSHP_APM_APP_TYPE = "@[ADWRKSHP_APM_APP_TYPE]";
	public static final String VADWRKSHP_APM_APP_TYPE_K8S = "@[ADWRKSHP_APM_APP_TYPE_K8S]";
	
	public static final String VADWRKSHP_CLOUD_STORG_INST_NAME_PFX = "@[ADWRKSHP_CLOUD_STORG_INST_NAME";
	
	public static final String VADWRKSHP_CLOUD_DB_INST_ENDPOINT = "@[ADWRKSHP_CLOUD_DB_INST_ENDPOINT]";
	public static final String VADWRKSHP_CLOUD_DB_INST_NAME = "@[ADWRKSHP_CLOUD_DB_INST_NAME]";

	public static final String VADWRKSHP_CLOUD_DB_INST_TYPE = "@[ADWRKSHP_CLOUD_DB_INST_TYPE]";
	public static final String VADWRKSHP_CLOUD_DB_INST_SIZE = "@[ADWRKSHP_CLOUD_DB_INST_SIZE]";
	public static final String VADWRKSHP_CLOUD_DB_INST_PORT = "@[ADWRKSHP_CLOUD_DB_INST_PORT]";
	public static final String VADWRKSHP_CLOUD_DB_INST_USER = "@[ADWRKSHP_CLOUD_DB_INST_USER]";
	public static final String VADWRKSHP_CLOUD_DB_INST_PWD = "@[ADWRKSHP_CLOUD_DB_INST_PWD]";
	public static final String VADWRKSHP_CLOUD_SECURITY_GROUP_ID = "@[ADWRKSHP_CLOUD_SECURITY_GROUP_ID]";
	
	public static final String VADWRKSHP_DB_AGENT_HOME_DIR = "@[ADWRKSHP_DB_AGENT_HOME_DIR]";
	public static final String VADWRKSHP_DB_AGENT_NAME = "@[ADWRKSHP_DB_AGENT_NAME]";
	
	public static final String VADWRKSHP_DASHBOARD_ID = "@[ADWRKSHP_DASHBOARD_ID]";
	public static final String VADWRKSHP_DASHBOARD_ACTION_VIEW = "@[ADWRKSHP_DASHBOARD_ACTION_VIEW]";
	public static final String VADWRKSHP_DASHBOARD_ACTION_EDIT = "@[ADWRKSHP_DASHBOARD_ACTION_EDIT]";
	public static final String VADWRKSHP_DASHBOARD_ACTION_DELETE = "@[ADWRKSHP_DASHBOARD_ACTION_DELETE]";
	public static final String VADWRKSHP_DASHBOARD_ACTION_SHARE = "@[ADWRKSHP_DASHBOARD_ACTION_SHARE]";
	
	public static final String VADWRKSHP_DISK_VOLUME_SIZE_GB = "@[ADWRKSHP_DISK_VOLUME_SIZE_GB]";
	public static final String VADWRKSHP_SHELL_SCRIPT_FILEPATH = "@[ADWRKSHP_SHELL_SCRIPT_FILEPATH]";
	public static final String VADWRKSHP_SHELL_SCRIPT_ARGUMENTS = "@[ADWRKSHP_SHELL_SCRIPT_ARGUMENTS]";
	public static final String VADWRKSHP_SHELL_SCRIPT_BEGIN_MSG = "@[ADWRKSHP_SHELL_SCRIPT_BEGIN_MSG]";
	public static final String VADWRKSHP_SHELL_SCRIPT_END_MSG = "@[ADWRKSHP_SHELL_SCRIPT_END_MSG]";
	public static final String VADWRKSHP_SHELL_SCRIPT_INHERIT_IO = "@[ADWRKSHP_SHELL_SCRIPT_INHERIT_IO]";
	
	
	// Shell Task Types
	public static final String SHELL_TASK_EXECUTE_SHELL_SCRIPT = "SHELL_TASK_EXECUTE_SHELL_SCRIPT";
	public static final String SHELL_TASK_DEPLOY_DB_AGENT = "SHELL_TASK_DEPLOY_DB_AGENT";
	public static final String SHELL_TASK_EXPAND_DISK_VOLUME = "SHELL_TASK_EXPAND_DISK_VOLUME";
	
	
	// Controller Task Types
	public static final String CONTROLLER_TASK_CREATE_LICENSE_RULE = "CONTROLLER_TASK_CREATE_LICENSE_RULE";
	public static final String CONTROLLER_TASK_CREATE_APM_APP = "CONTROLLER_TASK_CREATE_APM_APP";
	public static final String CONTROLLER_TASK_CREATE_BRUM_APP = "CONTROLLER_TASK_CREATE_BRUM_APP";
	public static final String CONTROLLER_TASK_CREATE_RBAC_USER = "CONTROLLER_TASK_CREATE_RBAC_USER";
	public static final String CONTROLLER_TASK_CREATE_DB_COLLECTOR = "CONTROLLER_TASK_CREATE_DB_COLLECTOR";
	public static final String CONTROLLER_TASK_SET_DASHBOARD_PERMISSIONS = "CONTROLLER_TASK_SET_DASHBOARD_PERMISSIONS";
	
	// Cloud Task Types
	public static final String CLOUD_TASK_CREATE_STORAGE_AWS_S3_BUCKET = "CLOUD_TASK_CREATE_STORAGE_AWS_S3_BUCKET";
	public static final String CLOUD_TASK_CREATE_DB_AWS_RDS_MYSQL = "CLOUD_TASK_CREATE_DB_AWS_RDS_MYSQL";
	
	
	
}
