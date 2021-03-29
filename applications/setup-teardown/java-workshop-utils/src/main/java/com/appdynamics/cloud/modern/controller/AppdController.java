/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.config.ControllerConfig;
import com.appdynamics.cloud.modern.config.ControllerLoginConfig;
import com.appdynamics.cloud.modern.config.SetupConfig;
import com.appdynamics.cloud.modern.config.TeardownConfig;
import com.appdynamics.cloud.modern.controller.json.ApmAppCreateResponse;
import com.appdynamics.cloud.modern.controller.json.BrumAppCreateResponse;
import com.appdynamics.cloud.modern.controller.json.BrumAppsGetResponse;
import com.appdynamics.cloud.modern.controller.json.ClusterAgentListRequest;
import com.appdynamics.cloud.modern.controller.json.ClusterAgentListResponse;
import com.appdynamics.cloud.modern.controller.json.DBCollectorCreateRequest;
import com.appdynamics.cloud.modern.controller.json.DBCollectorCreateResponse;
import com.appdynamics.cloud.modern.controller.json.LicenseRuleCreateRequest;
import com.appdynamics.cloud.modern.controller.json.MachineAgentListRequest;
import com.appdynamics.cloud.modern.controller.json.MachineAgentListResponse;
import com.appdynamics.cloud.modern.controller.json.RbacRoleCreateRequest;
import com.appdynamics.cloud.modern.controller.json.RbacRoleCreateResponse;
import com.appdynamics.cloud.modern.controller.json.RbacUserCreateRequest;
import com.appdynamics.cloud.modern.controller.json.RbacUserCreateResponse;
import com.appdynamics.cloud.modern.controller.json.RbacUserGetResponse;
import com.appdynamics.cloud.modern.controller.json.RbacUserUpdateRequest;
import com.google.gson.Gson;

/**
 * @author James Schneider
 *
 */
public class AppdController {

	//public final static Logger logr = Logger.getLogger(AppdController.class.getName());
	public Logger logr;
		
	private RequestConfig globalConfig;
	private CookieStore cookieStore;
	private HttpClientContext context;
	private CloseableHttpClient client;
	private String xCsrkToken;
	

	private ControllerLoginConfig loginConfig;
    

	public AppdController(ControllerConfig config, ControllerLoginConfig loginConfig) {

		this.loginConfig = loginConfig;
	}
	
	
	public void initClientForSetup(SetupConfig setupConfig) throws Throwable {
		logr = new Logger(AppdController.class.getSimpleName(), setupConfig.isDebugLogging());
		logr.debug("Initializing Client for Setup");
		this.globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
		this.cookieStore = new BasicCookieStore();
		this.context = HttpClientContext.create();
		this.context.setCookieStore(cookieStore);
		this.client = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();	
		this.auth();
		
	}
	public void initClientForTeardown(TeardownConfig teardownConfig) throws Throwable {
		logr = new Logger(AppdController.class.getSimpleName());
		logr.debug("Initializing Client for Teardown");
		this.globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
		this.cookieStore = new BasicCookieStore();
		this.context = HttpClientContext.create();
		this.context.setCookieStore(cookieStore);
		this.client = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();	
		this.auth();
		
	}
	
	public void closeClient() throws Throwable {
		logr.debug("Closing Client");
		client.close();
	}

	public void deleteClusterAgent(String clusterAgentName) throws Throwable {
		
		String deleteUrl = getControllerBaseUrl() + "/controller/sim/v2/user/machines/deleteMachines?ids=&instanceIds=";
	
		ClusterAgentListResponse calr = this.getClusterAgentList();
		ClusterAgentListResponse.Data[] caData = calr.getData();
		
		int caId = 0;
		
		if (caData != null && caData.length > 0) {
			for (int i = 0; i < caData.length; i++) {
				if (caData[i].getHostName().equals(clusterAgentName)) {
					caId = caData[i].getMachineId();
				}
			}
			
			deleteUrl = deleteUrl + caId;
			
			logr.debug("Delete URL: " + deleteUrl);
			
			HttpDelete httpReq = new HttpDelete(deleteUrl);
			
		    this.addCommonHeaders(httpReq, true, false);	    
			
		    CloseableHttpResponse response = client.execute(httpReq, context);
		    
		    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
		    
		    response.close();
			
		    logr.info("   - Finished Deleting Cluster Agent");
		    
		} else {
			logr.info("   - No Cluster Agent to Delete");
		}
		
	
	}
	
	
	public ClusterAgentListResponse getClusterAgentList() throws Throwable {

		logr.info("   - Finding Cluster Agents");
		
		HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/agents/list/cluster");
	    
	    this.addCommonHeaders(httpReq, true, true);	    
		
	    ClusterAgentListRequest caReq = new ClusterAgentListRequest();
	    
	    Gson gson = new Gson();
	    
	    String json = caReq.getJsonRequest();
	    
	    logr.debug("Get Cluster Agent List JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
	    
		ClusterAgentListResponse resObj = gson.fromJson(resp, ClusterAgentListResponse.class);
		
		logr.debug("Get Cluster Agent List JSON Response");
		logr.debug(resp);
				
		logr.info("   - Finished Finding Cluster Agents");
		
		return resObj;
	    
		
	}

	public int createLicenseRule(LicenseRule rule, ControllerTaskResults taskResults) throws Throwable {
		
		HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/licenseRule/create");
	    
	    this.addCommonHeaders(httpReq, true, true);	    
		
	    LicenseRuleCreateRequest lrcReq = new LicenseRuleCreateRequest();
	    
	    String json = lrcReq.getJsonRequest(rule);
	    
	    logr.debug("Creating License Rule JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    int statusCode = response.getStatusLine().getStatusCode();
	    logr.debug("HTTP Status: " + statusCode);
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
	    
		//ClusterAgentListResponse resObj = gson.fromJson(resp, ClusterAgentListResponse.class);
		
		logr.debug("Creating License Rule JSON Response");
		logr.debug(resp);
		
		
		return statusCode;
	    
		
	}

	
	public void deleteMachineAgents(MachineAgentListResponse malr) throws Throwable {
		
		String deleteUrl = getControllerBaseUrl() + "/controller/sim/v2/user/machines/deleteMachines?ids=";
		
		MachineAgentListResponse.MachineKey[] keys = malr.getMachineKeys();
		
		
		logr.debug("Machine Agent Keys length = " + keys.length);
		
		
		if (keys != null && keys.length > 0) {
			
			String idsStr = "";
			
			for (int i = 0; i < keys.length; i++) {
				idsStr = idsStr + keys[i].getMachineId() + ",";
			}
			
			idsStr = idsStr.substring(0, idsStr.length() - 1);
			
			deleteUrl = deleteUrl + idsStr + "&instanceIds=";
			
			logr.debug("Delete URL: " + deleteUrl);
			
			HttpDelete httpReq = new HttpDelete(deleteUrl);
			
		    this.addCommonHeaders(httpReq, true, false);	    
			
		    CloseableHttpResponse response = client.execute(httpReq, context);
		    
		    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
		    
		    response.close();
			
		    logr.info("   - Finished Deleting Machine Agents");
		} else {
			logr.info("   - No Machine Agents to Delete");
		}

	}
	
	
	public MachineAgentListResponse getMachineAgentListForApmApps(List<Integer> apmAppIds) throws Throwable {
	    
		HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/sim/v2/user/machines/keys");
	    
	    this.addCommonHeaders(httpReq, true, true);	    
	    	 
	    MachineAgentListRequest maReq = new MachineAgentListRequest();
	    MachineAgentListRequest.Filter filter = maReq.new Filter();
	    MachineAgentListRequest.Sorter sorter = maReq.new Sorter();
	    
	    int[] appIds = new int[apmAppIds.size()];
	    int cntr = 0;
	    
	    for (Integer id : apmAppIds) {
	    	appIds[cntr] = id.intValue();
	    	cntr++;
	    }
	    filter.setAppIds(appIds);
	    
	    String[] types = new String[2];
	    types[0] = "PHYSICAL";
	    types[1] = "CONTAINER_AWARE";
	    filter.setTypes(types);
	    
	    long endTime = Calendar.getInstance().getTimeInMillis();
	    long startTime = endTime - 900000;
	    filter.setTimeRangeStart(startTime);
	    filter.setTimeRangeEnd(endTime);
	    
	    maReq.setFilter(filter);
	    
	    sorter.setField("HEALTH");
	    sorter.setDirection("ASC");
	    maReq.setSorter(sorter);
	    
	    Gson gson = new Gson();
	    
	    String json = gson.toJson(maReq);
	    
	    logr.debug("Get Machine Agent List JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		
		MachineAgentListResponse resObj = gson.fromJson(resp, MachineAgentListResponse.class);
		
		logr.debug("Get Machine Agent List JSON Response");
		logr.debug(resp);
				
		logr.info("   - Finished Finding Machine Agents");
		
		return resObj;
		
	}
	
	public void deleteBrumApplication(int appId) throws Throwable {		
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/allApplications/deleteApplication");
	 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	    	  
	    String json = "" + appId;
	    logr.debug("Delete BRUM Application JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
	}	
	
	public int createBrumApplication(String appName, ControllerTaskResults taskResults) throws Throwable {		
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/allApplications/createApplication?applicationType=WEB");
	 
	    this.addCommonHeaders(httpReq, true, true);	    
	    
	    // {"name":"JRS-Test-Web-App","description":""}
	    String json = "{\"name\":\"" + appName + "\",\"description\":\"\"}";
	    logr.debug("Create BRUM Application JSON Request");
	    logr.debug(json);
	    
	    BrumApp brumApp = new BrumApp();
	    brumApp.appName = appName;
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();

		Gson gson = new Gson();
		BrumAppCreateResponse resObj = gson.fromJson(resp, BrumAppCreateResponse.class);
		logr.debug("Create BRUM Application JSON Response");
		logr.debug(resp);
		
		int retryBrumAppMax = 5;
		boolean retryBrumAppSuccess = false;
		
		for (int brumRetryCntr = 0; brumRetryCntr < retryBrumAppMax; brumRetryCntr++) {
			try {
				
				logr.info("   - Waiting for BRUM Application Creation ...");
				
				Thread.currentThread().sleep(3000);
				
				getBrumAppKey(appName, brumApp);
				
				
				if (brumApp.appId > -1 && brumApp.eumKey != null && brumApp.eumKey.length() > 5) {
					retryBrumAppSuccess = true;
					break;
				}
				
			} catch (Throwable e) {
				if (brumRetryCntr == (retryBrumAppMax -1)) {
					if (!retryBrumAppSuccess) {
						e.printStackTrace();
						throw e;
					}
					
				}
			}
			
		}
		
				
		
		taskResults.brumApps.add(brumApp);
		
		// TODO create and call method to update BRUM config with boolean parameter for "useStaticThresholds"
		
		logr.debug("BRUM App Name = " + brumApp.appName);
		logr.debug("BRUM App Id = " + brumApp.appId);
		logr.debug("BRUM App EUM Key = " + brumApp.eumKey);
		
		return brumApp.appId;
	    
	}
	
	private void getBrumAppKey(String brumAppName, BrumApp brumApp) throws Throwable {		
		
	    HttpGet httpReq = new HttpGet(getControllerBaseUrl() + "/controller/restui/eumApplications/getAllEumApplicationsData?time-range=last_1_hour.BEFORE_NOW.-1.-1.60");
	 
	    this.addCommonHeaders(httpReq, true, false);	    
	    
	    logr.debug("Getting EUM Key for BRUM App " + brumAppName);

	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		
		logr.debug("Get BRUM EUM Key JSON Response");
		logr.debug(resp);
		
		Gson gson = new Gson();
		BrumAppsGetResponse[] resArray = gson.fromJson(resp, BrumAppsGetResponse[].class);
		
		String eumAppKey = "";
		for (int i = 0; i < resArray.length; i++) {
			if (resArray[i].getName().equals(brumAppName)) {
				eumAppKey = resArray[i].getAppKey();
				brumApp.eumKey = resArray[i].getAppKey();
				brumApp.appId = resArray[i].getId();
				return;
			}
		}		  
		
		if (eumAppKey != null) {
			logr.debug("EUM Key for BRUM App " + brumAppName + " = " + eumAppKey);
		}
	    
	}

	public void deleteLicenseRule(String licenseRequestId) throws Throwable {		
		
	    HttpGet httpReq = new HttpGet(getControllerBaseUrl() + "/controller/restui/licenseRule/delete/" + licenseRequestId);
	 
	    this.addCommonHeaders(httpReq, true, false);	    
	    
	    logr.debug("Deleting License Rule with Id " + licenseRequestId);

	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
	    
	}
	
	
	public void deleteApmApplication(int appId) throws Throwable {		
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/allApplications/deleteApplication");
	 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	    	  
	    String json = "" + appId;
	    logr.debug("Delete APM Application JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
	}	
	
	
	public int createApmApplication(String appName, String appType, ControllerTaskResults taskResults) throws Throwable {		
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/allApplications/createApplication?applicationType=APM");
	 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	    	  
	    // {"name":"Jrs-Test-App","description":""}
	    String json = "{\"name\":\"" + appName + "\",\"description\":\"\"}";
	    logr.debug("Create APM Application JSON Request");
	    logr.debug(json);
	    
	    ApmApp apmApp = new ApmApp();
	    apmApp.appName = appName;
	    apmApp.appType = appType;
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		Gson gson = new Gson();
		ApmAppCreateResponse resObj = gson.fromJson(resp, ApmAppCreateResponse.class);
		
	    logr.debug("Create APM Application JSON Request");
	    logr.debug(json);
		
		apmApp.appId = resObj.getId();
		taskResults.apmApps.add(apmApp);
		
		logr.debug("APM App Name = " + apmApp.appName);
		logr.debug("APM App Id = " + apmApp.appId);
		logr.debug("APM App Type = " + apmApp.appType);
		
		return resObj.getId();
	    
	}
	public void deleteRbacUser(int userId) throws Throwable {		
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/userAdministrationUiService/users/delete");
	    // controller/restui/userAdministrationUiService/users/delete
	 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	    	  
	    String json = "[" + userId + "]";
	    logr.debug("Delete RBAC User JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
	    response.close();
	    
	    logr.info("     - Finished Deleting RBAC User : " + userId);
	    
	}
	
	public int createRbacUser(RbacUser user, ControllerTaskResults taskResults) throws Throwable {
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/userAdministrationUiService/users/create");
		 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	 
	    RbacUserCreateRequest rbacReq = new RbacUserCreateRequest();
	    
	    taskResults.rbacUser = user;
	    
	    rbacReq.setUpdateGroups(true);
	    rbacReq.getUser().setName(user.userName);
	    rbacReq.getUser().setDisplayName(user.userName);
	    rbacReq.getUser().setEmail(user.email);
	    rbacReq.getUser().setPassword(user.userPwd);
	    
	    Gson gson = new Gson();
	    
	    String json = gson.toJson(rbacReq);
	    
	    logr.debug("Create RBAC User JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		
		RbacUserCreateResponse resObj = gson.fromJson(resp, RbacUserCreateResponse.class);
		
		logr.debug("Create RBAC User JSON Response");
		logr.debug(resp);
		
		user.userId = resObj.getId();

		logr.debug("RBAC User Name = " + user.userName);
		logr.debug("RBAC User Id = " + user.userId);
		
		return resObj.getId();
		
	}
	
	
	public void addDefaultRolesToUser(RbacUser user, int[] roleIds) throws Throwable {
		
		RbacUserGetResponse userData = getRbacUser(user.userId);
		RbacUserUpdateRequest updateReq = new RbacUserUpdateRequest();
		
		RbacUserUpdateRequest.User rUser = updateReq.new User();
		
		updateReq.setUpdateGroups(true);
		rUser.setId(userData.getId());
		rUser.setVersion(userData.getVersion());
		rUser.setName(userData.getName());
		rUser.setNameUnique(userData.getNameUnique());
		rUser.setBuiltIn(userData.getBuiltIn());
		rUser.setCreatedBy(userData.getCreatedBy());
		rUser.setCreatedOn(userData.getCreatedOn());
		rUser.setModifiedBy(userData.getModifiedBy());
		rUser.setModifiedOn(userData.getModifiedOn());
		rUser.setDisplayName(userData.getDisplayName());
		rUser.setPassword(null);
		rUser.setEmail(userData.getEmail());
		rUser.setProviderUniqueName(userData.getProviderUniqueName());
		rUser.setSecurityProviderType(userData.getSecurityProviderType());
		rUser.setMobileDeviceUuids(null);
		
		rUser.setRoles(userData.getRoles());
		
		List<Integer> newRoleIds = new ArrayList<Integer>();
		
		int[] uRoleIds = rUser.getAccountRoleIds();
		if (uRoleIds != null && uRoleIds.length > 0) {
			
			
			for (int i = 0; i < uRoleIds.length; i++) {
				newRoleIds.add(uRoleIds[i]);
			}
		
		}
		
		for (int i = 0; i < roleIds.length; i++) {
			newRoleIds.add(roleIds[i]);
		}
		
		
		Integer[] nRoleArray = new Integer[newRoleIds.size()];
		
		nRoleArray = newRoleIds.toArray(nRoleArray);
		
		int[] iRoleArray = new int[nRoleArray.length];
		
		for (int i = 0; i < nRoleArray.length; i++) {
			iRoleArray[i] = nRoleArray[i].intValue();	
		}
		
		
		//logr.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  AccountRoleIds = " + iRoleArray.length);
		rUser.setAccountRoleIds(iRoleArray);
		
		updateReq.setUser(rUser);
		

		HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/userAdministrationUiService/users/update");
		 
	    this.addCommonHeaders(httpReq, true, true);	    

	    Gson gson = new Gson();
	    
	    String json = gson.toJson(updateReq);
	    
	    logr.debug("Add Default Role To User JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		
		logr.debug("Add Default Role To User JSON Response");
		logr.debug(resp);		
		
	}
	
	private RbacUserGetResponse getRbacUser(int userId) throws Throwable {		
		
	    HttpGet httpReq = new HttpGet(getControllerBaseUrl() + "/controller/restui/userAdministrationUiService/users/" + userId);
	 
	    this.addCommonHeaders(httpReq, true, false);	    
	    
	    logr.debug("Getting RBAC User " + userId);

	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		
		logr.debug("Get RBAC User JSON Response");
		logr.debug(resp);
		
		Gson gson = new Gson();
		RbacUserGetResponse res = gson.fromJson(resp, RbacUserGetResponse.class);
		
		return res;
	    
	}
	
	public void addUserToRole(RbacUser rbacUser, RbacRole rbacRole) throws Throwable {
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/accountRoleAdministrationUiService/accountRoles/" + rbacRole.roleId + "/addUsers");
		 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	    	  
	    String json = "[" + rbacUser.userId + "]";
	    
	    logr.debug("Add RBAC User to Role JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		logr.debug("RBAC User Name = " + rbacUser.userName);
		logr.debug("RBAC User Id = " + rbacUser.userId);
		logr.debug("RBAC Role Name = " + rbacRole.roleName);
		logr.debug("RBAC Role Id = " + rbacRole.roleId);
	    
		
	}
	
	public void deleteRbacRole(int roleId) throws Throwable {		
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/accountRoleAdministrationUiService/accountRoles/delete");
	 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	    	  
	    String json = "[" + roleId + "]";
	    logr.debug("Delete RBAC Role JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
	    response.close();
	    logr.info("     - Finished Deleting RBAC Role : " + roleId);
	    
	}

	public void deleteDBCollector(int collId) throws Throwable {		
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/databasesui/collectors/configuration/batchDelete");
	 
	    this.addCommonHeaders(httpReq, true, true);	    
	    	    	  
	    String json = "[" + collId + "]";
	    logr.debug("Delete DB Collector JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
	}
	
	// assumes DBCollectors are already populated inside ControllerTaskResults
	public DBCollector createDBCollector(String collectorName, ControllerTaskResults taskResults) throws Throwable {
		
	    DBCollector col = findDBCollector(collectorName, taskResults);
	    
	    //if (col != null) {
	    	
		    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/databasesui/collectors/createConfiguration");
			
		    this.addCommonHeaders(httpReq, true, true);
	    
		    DBCollectorCreateRequest req = generateDBCollectorCreateRequest(collectorName, col);
		   		    
		    Gson gson = new Gson();
		    
		    String json = gson.toJson(req);
		    
		    logr.debug("Create DB Collector JSON Request");
		    logr.debug(json);
		    
		    StringEntity entity = new StringEntity(json);
		    httpReq.setEntity(entity);
		    
		    CloseableHttpResponse response = client.execute(httpReq, context);
		    
		    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
		    
			String resp = "";
	        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            out.append(line);
	        }			
			
	        resp = out.toString();
			reader.close();
			
			DBCollectorCreateResponse resObj = gson.fromJson(resp, DBCollectorCreateResponse.class);
			
			logr.debug("Create DB Collector JSON Response");
			logr.debug(resp);
			
			col.collectorId = resObj.getId();

			logr.debug("DB Collector Name = " + col.collectorName);
			logr.debug("DB Collector Id = " + col.collectorId);
			
			return col;		
	    	
	    //}
	    
	    //return null;
	}

	private DBCollectorCreateRequest generateDBCollectorCreateRequest(String collectorName, DBCollector collector) throws Throwable {
		
		DBCollectorCreateRequest req = new DBCollectorCreateRequest();

		req.setAgentName(collector.dbAgentName);
		req.setDatabaseName("");
		req.setEnabled(true);
		req.setHostname(collector.dbHost);
		req.setName(collector.collectorName);
		req.setOrapkiSslEnabled(false);
		req.setOrasslClientAuthEnabled(false);
		req.setPassword(collector.dbPassword);
		req.setPort(collector.dbPort);
		req.setSid("");
		req.setType(collector.collectorType);
		req.setUsername(collector.dbUsername);
		return req;
					
	}
	
	private DBCollector findDBCollector(String collectorName, ControllerTaskResults taskResults) throws Throwable {
		List<DBCollector> dbs = taskResults.dbCollectors;
		
		if (dbs != null && !dbs.isEmpty()) {
			for (DBCollector col : dbs) {
				if (col.collectorName.equals(collectorName)) {
					
					return col;
					
				}
			}
		}
		return null;
	}
	
	// assumes ApmApps, BrumApps, DBCollectors, Dashboards, are already populated inside ControllerTaskResults
	public RbacRole createRbacRole(String roleName, ControllerTaskResults taskResults, int cloudNativeAppId) throws Throwable {
		
	    HttpPost httpReq = new HttpPost(getControllerBaseUrl() + "/controller/restui/accountRoleAdministrationUiService/accountRoles/create");
		
	    this.addCommonHeaders(httpReq, true, true);
	     
	    RbacRoleCreateRequest rbacReq = generateRbacRoleCreateRequest(roleName, taskResults, cloudNativeAppId);
	    
	    RbacRole role = new RbacRole();
	    role.roleName = roleName;
	    taskResults.rbacRole = role;
	    
	    Gson gson = new Gson();
	    
	    String json = gson.toJson(rbacReq);
	    
	    logr.debug("Create RBAC Role JSON Request");
	    logr.debug(json);
	    
	    StringEntity entity = new StringEntity(json);
	    httpReq.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		
		RbacRoleCreateResponse resObj = gson.fromJson(resp, RbacRoleCreateResponse.class);
		
		logr.debug("Create RBAC Role JSON Response");
		logr.debug(resp);
		
		role.roleId = resObj.getId();

		logr.debug("RBAC Role Name = " + role.roleName);
		logr.debug("RBAC Role Id = " + role.roleId);
		
		return role;		
	}
	
	// assumes ApmApps, BrumApps, DBCollectors, Dashboards, are already populated inside ControllerTaskResults
	private RbacRoleCreateRequest generateRbacRoleCreateRequest(String roleName, ControllerTaskResults taskResults, int CloudNativeAppId) throws Throwable {
		
		
		RbacRoleCreateRequest req = new RbacRoleCreateRequest();
		req.setName(roleName);
		
		List<RbacRoleCreateRequest.Permission> permList = new ArrayList<RbacRoleCreateRequest.Permission>();
		
		// set default permissions for all other apps
		generateAppPermissions(req, permList, 0, false, false);
		
		// set permissions on APM apps
		List<ApmApp> apmApps = taskResults.apmApps;
		for (ApmApp apmApp : apmApps) {
			generateAppPermissions(req, permList, apmApp.appId, true, false);
			
		}
		
		// TODO fix hack for native cloud app
		generateAppPermissions(req, permList, CloudNativeAppId, false, true);
		
		// set permissions on BRUM apps
		List<BrumApp> brumApps = taskResults.brumApps;
		if (brumApps != null && !brumApps.isEmpty()) {
			for (BrumApp brumApp : brumApps) {
				generateAppPermissions(req, permList, brumApp.appId, true, false);
			}			
		}
		
		
		// set permissions on DB Collectors
		List<DBCollector> cols = taskResults.dbCollectors;
		if (cols != null && !cols.isEmpty()) {
			for (DBCollector col : cols) {
				generateDBCollectorPermissions(req, permList, col.collectorId, true);
			}			
		}
		
		// set permissions on Dashboards
		List<Dashboard> dashes = taskResults.dashboards;
		if (dashes != null && !dashes.isEmpty()) {
			for (Dashboard dash : dashes) {
				generateDashboardPermissions(req, permList, dash);
				
				//logr.info(" !!!!!!!!!!!!!!!!!!!!!!!!  " + dash.dashboardId + " - " +dash.allowViewing + " !!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}			
		} else {
			
			//logr.info(" !!!!!!!!!!!!!!!!!!!!!!!!  NO DASHBOARDS FOUND IN TASK RESULTS !!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		
		
		
		// set the permissions array on the request
		RbacRoleCreateRequest.Permission[] permArray = new RbacRoleCreateRequest.Permission[permList.size()];
		permArray = permList.toArray(permArray);
		req.setPermissions(permArray);
		
		return req;
		
	}

	private void generateDashboardPermissions(RbacRoleCreateRequest req, List<RbacRoleCreateRequest.Permission> perms, Dashboard dashboard) throws Throwable {	

		RbacRoleCreateRequest.Permission perm;
		RbacRoleCreateRequest.AffectedEntity ae;
		List<String> actions;
				
		actions = new ArrayList<String>();
		actions.add("VIEW");		
		for (String action : actions) {
			
			perm = req.new Permission();
			ae = req.new AffectedEntity();
			
			perm.setAction(action);
			perm.setAllowed(dashboard.allowViewing);
			ae.setEntityId(dashboard.dashboardId);
			ae.setEntityType("DASHBOARD");
			perm.setAffectedEntity(ae);
			perms.add(perm);
		}

		actions = new ArrayList<String>();
		actions.add("EDIT");		
		for (String action : actions) {
			
			perm = req.new Permission();
			ae = req.new AffectedEntity();
			
			perm.setAction(action);
			perm.setAllowed(false);
			ae.setEntityId(dashboard.dashboardId);
			ae.setEntityType("DASHBOARD");
			perm.setAffectedEntity(ae);
			perms.add(perm);
		}

		actions = new ArrayList<String>();
		actions.add("DELETE");		
		for (String action : actions) {
			
			perm = req.new Permission();
			ae = req.new AffectedEntity();
			
			perm.setAction(action);
			perm.setAllowed(false);
			ae.setEntityId(dashboard.dashboardId);
			ae.setEntityType("DASHBOARD");
			perm.setAffectedEntity(ae);
			perms.add(perm);
		}

		actions = new ArrayList<String>();
		actions.add("SHARE");		
		for (String action : actions) {
			
			perm = req.new Permission();
			ae = req.new AffectedEntity();
			
			perm.setAction(action);
			perm.setAllowed(false);
			ae.setEntityId(dashboard.dashboardId);
			ae.setEntityType("DASHBOARD");
			perm.setAffectedEntity(ae);
			perms.add(perm);
		}
		
	
	}
	
	private void generateDBCollectorPermissions(RbacRoleCreateRequest req, List<RbacRoleCreateRequest.Permission> perms, int entityId, boolean access) throws Throwable {	

		RbacRoleCreateRequest.Permission perm;
		RbacRoleCreateRequest.AffectedEntity ae;
		List<String> actions;
		
		actions = new ArrayList<String>();
		actions.add("VIEW");
		actions.add("EDIT");
		actions.add("DELETE");
		for (String action : actions) {
			
			perm = req.new Permission();
			ae = req.new AffectedEntity();
			
			perm.setAction(action);
			perm.setAllowed(access);
			ae.setEntityId(entityId);
			ae.setEntityType("DB_MONITOR_CONFIG");
			perm.setAffectedEntity(ae);
			perms.add(perm);
		}

		
		actions = new ArrayList<String>();
		actions.add("VIEW");		
		for (String action : actions) {
			
			perm = req.new Permission();
			ae = req.new AffectedEntity();
			
			perm.setAction(action);
			perm.setAllowed(access);
			ae.setEntityId(entityId);
			ae.setEntityType("DBMON_COLLECTOR_DATA");
			perm.setAffectedEntity(ae);
			perms.add(perm);
		}

		
	
	}
	
	private void generateAppPermissions(RbacRoleCreateRequest req, List<RbacRoleCreateRequest.Permission> perms, int entityId, boolean access, boolean readOnly) throws Throwable {
		
		List<String> actions = new ArrayList<String>();
		actions.add("VIEW");
		actions.add("DELETE");
		actions.add("CONFIG_TRANSACTION_DETECTION");
		actions.add("CONFIG_BACKEND_DETECTION");
		actions.add("CONFIG_ERROR_DETECTION");
		actions.add("CONFIG_DIAGNOSTIC_DATA_COLLECTORS");
		actions.add("CONFIG_CALLGRAPH_SETTINGS");
		actions.add("CONFIG_JMX");
		actions.add("CONFIG_MEMORY_MONITORING");
		actions.add("CONFIG_EUM");
		actions.add("CONFIG_INFO_POINTS");
		actions.add("CONFIG_POLICIES");
		actions.add("CONFIG_EVENT_REACTOR");
		actions.add("CONFIG_ACTIONS");
		actions.add("CONFIG_BUSINESS_TRANSACTIONS");
		actions.add("CONFIG_BASELINES");
		actions.add("CONFIG_SQL_BIND_VARIABLES");
		actions.add("CONFIG_AGENT_PROPERTIES");
		actions.add("CONFIG_SERVICE_ENDPOINTS");
		actions.add("ENABLE_DEVELOPMENT_MODE");
		actions.add("MANAGE_CUSTOM_DASHBOARD_TEMPLATES");
		actions.add("CONFIG_SIM");
		actions.add("CONFIG_AGENT_OPERATIONS");
		actions.add("CONFIG_TRIGGER_DIAGNOSTIC_SESSION");
		actions.add("ENABLE_JMX_OPERATIONS");
		actions.add("VIEW_SENSITIVE_DATA");
		actions.add("VIEW_SIM");
		actions.add("NETVIZ_AGENT_PCAP_TRIGGER");
		actions.add("ACI_APIC_TROUBLESHOOTING");
		actions.add("CREATE_EVENTS");	
		
		RbacRoleCreateRequest.Permission perm;
		RbacRoleCreateRequest.AffectedEntity ae;
		
		for (String action : actions) {
			
			perm = req.new Permission();
			ae = req.new AffectedEntity();
			
			perm.setAction(action);
			
			// action.equals("VIEW")
			if (!access && readOnly && action.equals("VIEW")) {
				
				perm.setAllowed(true);
			
			} else {
				perm.setAllowed(access);
			}
			
			
			
			ae.setEntityId(entityId);
			ae.setEntityType("APPLICATION");
			perm.setAffectedEntity(ae);
			perms.add(perm);
		}
		
		// VIEW
		// DELETE
		// CONFIG_TRANSACTION_DETECTION
		// CONFIG_BACKEND_DETECTION
		// CONFIG_ERROR_DETECTION
		// CONFIG_DIAGNOSTIC_DATA_COLLECTORS
		// CONFIG_CALLGRAPH_SETTINGS
		// CONFIG_JMX
		// CONFIG_MEMORY_MONITORING
		// CONFIG_EUM
		// CONFIG_INFO_POINTS
		// CONFIG_POLICIES
		// CONFIG_EVENT_REACTOR
		// CONFIG_ACTIONS
		// CONFIG_BUSINESS_TRANSACTIONS
		// CONFIG_BASELINES
		// CONFIG_SQL_BIND_VARIABLES
		// CONFIG_AGENT_PROPERTIES
		// CONFIG_SERVICE_ENDPOINTS
		// ENABLE_DEVELOPMENT_MODE
		// MANAGE_CUSTOM_DASHBOARD_TEMPLATES
		// CONFIG_SIM
		// CONFIG_AGENT_OPERATIONS
		// CONFIG_TRIGGER_DIAGNOSTIC_SESSION
		// ENABLE_JMX_OPERATIONS
		// VIEW_SENSITIVE_DATA
		// VIEW_SIM
		// NETVIZ_AGENT_PCAP_TRIGGER
		// ACI_APIC_TROUBLESHOOTING
		// CREATE_EVENTS
		
	}
	

//	public String getRole(int roleId) throws Throwable {		
//		
//	    HttpGet httpReq = new HttpGet(getControllerBaseUrl() + "/controller/restui/accountRoleAdministrationUiService/accountRoles/" + roleId);
//	 
//	    this.addCommonHeaders(httpReq, true, false);	    
//	    	    	    
//	    CloseableHttpResponse response = client.execute(httpReq, context);
//	    
//	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
//	    
//		String resp = "";
//        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//        StringBuilder out = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            out.append(line);
//        }			
//		
//        resp = out.toString();
//		reader.close();
//
//		logr.debug(resp);
//		return resp;
//	    
//	}
	
	private void auth() throws Throwable {		
		
	    HttpGet httpReq = new HttpGet(getControllerBaseUrl() + "/controller/auth?action=login");
	 
	    this.addCommonHeaders(httpReq, false, false);	    
	    httpReq.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		// If the login config is passed in, use that. Otherwise, default to previous implementation.
		String userCredentials = null;
		//if (this.loginConfig != null) {
		
			userCredentials = this.loginConfig.getControllerUsername() + "@" + this.loginConfig.getControllerAccount() + ":" + this.loginConfig.getControllerPass();
			
		//} else {
			//userCredentials = this.loginConfig.getControllerUsername()+"@"+this.loginConfig.getControllerAccount()+":"+this.loginConfig.getControllerPass();
		//}
	    
	    byte[] encoded = Base64.encodeBase64(userCredentials.getBytes());
	    httpReq.setHeader("Authorization", "Basic " + new String(encoded));
	    
	    logr.debug("Authenicating to Controller");
	    
	    CloseableHttpResponse response = client.execute(httpReq, context);
	    
	    logr.debug("HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
        
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
        	if (header.getValue().startsWith("X-CSRF-TOKEN")) {
        		String csrf = header.getValue().substring(13);
        		//logr.info("X-CSRF-TOKEN 1 | " + csrf);
        		this.xCsrkToken = csrf.substring(0, csrf.indexOf(";"));
        		//logr.info("X-CSRF-TOKEN 2 | " + this.xCsrkToken);
        	}
        	//logr.info("Header | Key : " + header.getName() + " , Value : " + header.getValue());
        }        
        
		reader.close();
		
		logr.debug("Authenticate to Controller JSON Response");
		logr.debug(resp);
		
		//List<Cookie> cookies = context.getCookieStore().getCookies();
		//for (Cookie c : cookies) {
			//logr.info(c.getName() + " : " + c.getValue());
			//logr.info(c.getPath() + " : " + c.getDomain());
		//}  
	}
		
	private void addCommonHeaders(HttpRequestBase httpReq, boolean includeCsrfToken, boolean isPost) throws Throwable {
	    httpReq.addHeader("Accept", "application/json, text/plain, */*");
	    httpReq.addHeader("Accept-Encoding", "gzip, deflate, br");
	    httpReq.addHeader("Connection", "keep-alive");
	    httpReq.addHeader("Accept-Language", "en-US,en;q=0.9");
	    httpReq.addHeader("Host", this.loginConfig.getControllerHostName());
	    httpReq.addHeader("Referer", this.loginConfig.getControllerUrlFull() + "/controller/");
	    httpReq.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36 OPR/70.0.3728.178");
	    
	    if (isPost) {
	    	httpReq.addHeader("Content-Type", "application/json;charset=UTF-8");
	    }
	    
	    if (includeCsrfToken) {
	    	httpReq.addHeader("X-CSRF-TOKEN", this.xCsrkToken);
	    }
	}
	
	private String getControllerBaseUrl() throws Throwable {
		//return "https://" + init(Integer.parseInt(init(28/2/2+7*2+1, V).substring(init(28/2/2+7*2+1, V).length()-2)), R);
		return this.loginConfig.getControllerUrlFull();
		
	}
	
	
	
}
