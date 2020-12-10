/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.util.List;

import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.config.ControllerConfig;
import com.appdynamics.cloud.modern.config.ControllerLoginConfig;
import com.appdynamics.cloud.modern.config.SetupConfig;
import com.appdynamics.cloud.modern.config.TeardownConfig;
import com.appdynamics.cloud.modern.controller.json.MachineAgentListResponse;

/**
 * @author James Schneider
 *
 */
public class AppdControllerHelper {

	public static Logger logr;
	private static AppdController CNTRLR = null;
	

	public static void initSetup(SetupConfig setupConfig, ControllerConfig controllerConfig, ControllerLoginConfig loginConfig) throws Throwable {
		logr = new Logger(AppdControllerHelper.class.getSimpleName(), setupConfig.isDebugLogging());
		logr.info(" - Initilizing Controller for Setup");
		if (CNTRLR == null) {
			CNTRLR = new AppdController(controllerConfig, loginConfig);
			CNTRLR.initClientForSetup(setupConfig);
		}
	}
	
	public static void initTeardown(TeardownConfig config, ControllerConfig controllerConfig, ControllerLoginConfig loginConfig) throws Throwable {
		
		logr = new Logger(AppdControllerHelper.class.getSimpleName());
		logr.info(" - Initilizing Controller for Teardown");
		if (CNTRLR == null) {
			CNTRLR = new AppdController(controllerConfig, loginConfig);
			CNTRLR.initClientForTeardown(config);
		} else {
			CNTRLR.initClientForTeardown(config);
		}
	}
	
	public static void closeClient() throws Throwable {
		logr.info(" - Closing Controller Client");
		CNTRLR.closeClient();	
	}
	
	public static ControllerTaskResults createApmApp(ControllerTaskResults results, String appName, String appType) throws Throwable  {
		logr.info(" - Creating APM Application : " + appName);
		CNTRLR.createApmApplication(appName, appType, results);		
		
		return results;
	}

	
	public static ControllerTaskResults createBrumApp(ControllerTaskResults taskResults, String appName) throws Throwable  {
		logr.info(" - Creating BRUM Application : " + appName);
		CNTRLR.createBrumApplication(appName, taskResults);
		return taskResults;
	}	
	
	public static ControllerTaskResults createRbacUser(RbacUser user, ControllerTaskResults taskResults) throws Throwable  {
		logr.info(" - Creating RBAC User : " + user.userName);
		CNTRLR.createRbacUser(user, taskResults);
		return taskResults;
	}	
	
	public static ControllerTaskResults createRbacRole(String roleName, ControllerTaskResults taskResults, int CloudNativeAppId) throws Throwable {
		logr.info(" - Creating RBAC Role : " + roleName);
		CNTRLR.createRbacRole(roleName, taskResults, CloudNativeAppId);
		return taskResults;
	}

	public static ControllerTaskResults createDBCollector(String collectorName, ControllerTaskResults taskResults) throws Throwable {
		logr.info(" - Creating DB Collector : " + collectorName);
		CNTRLR.createDBCollector(collectorName, taskResults);
		return taskResults;
	}
	
	public static void addUserToRole(RbacUser rbacUser, RbacRole rbacRole) throws Throwable {
		logr.info(" - Adding RBAC User : " + rbacUser.userName + " to RBAC Role : " + rbacRole.roleName);
		CNTRLR.addUserToRole(rbacUser, rbacRole);
	}
	
	public static void addDefaultRolesToUser(RbacUser rbacUser, int[] roleIds) throws Throwable {
		logr.info(" - Adding RBAC User : " + rbacUser.userName + " to Default RBAC Roles");
		CNTRLR.addDefaultRolesToUser(rbacUser, roleIds);
	}	
	
	public static void deleteApmApplication(int appId) throws Throwable {
		logr.info("     - Deleting APM Application : " + appId);
		CNTRLR.deleteApmApplication(appId);
	}
	
	public static void deleteBrumApplication(int appId) throws Throwable {
		logr.info("     - Deleting BRUM Application : " + appId);
		CNTRLR.deleteBrumApplication(appId);
	}
	
	public static void deleteRbacUser(int userId) throws Throwable {
		logr.info("     - Deleting RBAC User : " + userId);
		CNTRLR.deleteRbacUser(userId);
	}
	
	public static void deleteRbacRole(int roleId) throws Throwable {
		logr.info("     - Deleting RBAC Role : " + roleId);
		CNTRLR.deleteRbacRole(roleId);
	}
	
	public static void deleteDBCollector(int collectorId) throws Throwable {
		logr.info("     - Deleting DB Collector : " + collectorId);
		CNTRLR.deleteDBCollector(collectorId);
	}
	
	public static void deleteMachineAgents(MachineAgentListResponse malr) throws Throwable {
		logr.info("   - Deleting Machine Agents");
		CNTRLR.deleteMachineAgents(malr);
	}
	
	public static MachineAgentListResponse getMachineAgentListForApmApps(List<Integer> apmAppIds) throws Throwable {
		logr.info("   - Finding Machine Agents");
		return CNTRLR.getMachineAgentListForApmApps(apmAppIds);
	}

	public static void deleteClusterAgent(String clusterAgentName) throws Throwable {
		logr.info("   - Deleting Cluster Agent");
		CNTRLR.deleteClusterAgent(clusterAgentName);
	}

	
}
