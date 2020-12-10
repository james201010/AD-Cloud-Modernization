package com.appdynamics.cloud.modern.services.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdynamics.cloud.modern.services.beans.HttpJsonTransport;
import com.google.gson.Gson;

public abstract class Action {
    
    String name = "";
    transient Map<String, Object> properties = null;
    String type = "";

    public transient final Logger logger = LoggerFactory.getLogger(Action.class);

    public Action(String name, Map<String, Object> properties) {
        this.name = name;
        this.properties = properties;
    }

    public abstract String execute(HttpServletRequest request, String priorActionReturn);

    public String processActionReturns(String priorActionReturn, String currentActionReturn) {
    	
        if (priorActionReturn != null && !priorActionReturn.equals("")) {
        	
        	try {
        		
        		Gson gson = new Gson();
        		
        		HttpJsonTransport priorTrans = gson.fromJson(priorActionReturn, HttpJsonTransport.class);
        		HttpJsonTransport currentTrans = gson.fromJson(currentActionReturn, HttpJsonTransport.class);
        		
        		priorTrans.setResponseCode(currentTrans.getResponseCode());
        		priorTrans.setServiceOperation(currentTrans.getServiceOperation());
        		
        		Map<String, String> priorPayloads = priorTrans.getJsonPayloads();
        		Map<String, String> currentPayloads = currentTrans.getJsonPayloads();
        		if (currentPayloads != null && !currentPayloads.isEmpty()) {
        			if (priorPayloads == null) {
        				priorPayloads = new HashMap<String, String>();
        				priorTrans.setJsonPayloads(priorPayloads);
        			}
        			
        			Set<String> currPayloadKeys = currentPayloads.keySet();
            		
        			
            		for (String key : currPayloadKeys) {
            			priorPayloads.put(key, currentPayloads.get(key));
            		}
        			
        			
        		}
        		        		
        		return gson.toJson(priorTrans, HttpJsonTransport.class);
        		
        	} catch (Throwable ex) {
        		ex.printStackTrace();
        		
        	}
        }
	
        return currentActionReturn;
    }
}