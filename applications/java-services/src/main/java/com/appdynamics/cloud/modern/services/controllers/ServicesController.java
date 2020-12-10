package com.appdynamics.cloud.modern.services.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdynamics.cloud.modern.services.actions.Action;
import com.appdynamics.cloud.modern.services.actions.ActionFactory;
import com.appdynamics.cloud.modern.services.beans.AppGraph;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class ServicesController {

    public final Logger logger = LoggerFactory.getLogger(ServicesController.class);

    @RequestMapping(value = "/**", produces = { "application/json" })
    public String handle(HttpServletRequest request) {
    	
        Gson gson = new Gson();
        String path = request.getRequestURI();        

        if (path.endsWith("/")) {
        	path = path.substring(0, path.length() -1);
        }
        
        Map<String, Object> path_props = gson.fromJson(gson.toJson(AppGraph.graph.get(path)), new TypeToken<Map<String, Object>>() {}.getType());
        if (path_props == null) {
            if (!path.equals("/favicon.ico")) {
                logger.info("Using catch-all");
                path_props = gson.fromJson(gson.toJson(AppGraph.graph.get("*")), new TypeToken<Map<String, Object>>() {}.getType());
            } else {
                Object obj = new Object();
                return gson.toJson(obj);
            }            
        }
        logger.info("Path: " + path);
        Object obj = path_props.get("actions");
        List<Object> actions = gson.fromJson(gson.toJson(obj), new TypeToken<List<Object>>() {}.getType());
        ArrayList<Action> action_list = new ArrayList<>();
        actions.forEach(action -> action_list.add(ActionFactory.GetAction(action)));
        
        String actionResult = "";
        for (Action action : action_list) {
        	
        	actionResult = action.execute(request, actionResult);
        	
        }
        
//        for (Iterator iterator = action_list.iterator(); iterator.hasNext();) {
//			Action action2 = (Action) iterator.next();
//			
//		}
//        action_list.forEach(action -> action.execute());

        
        //return gson.toJson(actions);
        return actionResult;
    }

}