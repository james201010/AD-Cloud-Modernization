package com.appdynamics.cloud.modern.services.actions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public final class HttpAction extends Action {    

    private HttpActionProperties props;

    public HttpAction(final String name, final Map<String, Object> properties) {
        super(name, properties);  
        this.type = "HttpAction";
        Gson gson = new Gson();
        this.props = gson.fromJson(gson.toJson(this.properties), HttpActionProperties.class);   
        
        logger.info("HttpActionProperties for " + this.name + ": " + gson.toJson(this.props));
    }

    @Override
    public String execute(HttpServletRequest request, String priorActionReturn) {                        
        logger.info("Executing HttpAction");
        try {            
            String currentActionReturn = WebConnect.makeWebRequest(request, this.props);
            logger.debug(currentActionReturn);
            
            return this.processActionReturns(priorActionReturn, currentActionReturn);
        
            
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }    
        
        return "";
    }
    
    /**
     * HttpActionProperties
     */
    public class HttpActionProperties {

        private String requestMethod;
        private String url;

        /**
         * Gets the request method for the Http Action to take
         * @return The request method
         */
        public String getRequestMethod() {
            return requestMethod;
        }

        /**
         * Sets the request method for the Http Action to take
         * @param requestMethod The request method (GET, POST, PUT, DELETE)
         */
        public void setRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
        }

        /**
         * Gets the Url for the Http Action
         * @return The Url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Sets the Url for the Http Action
         * @param url The Url
         */
        public void setUrl(String url) {
            this.url = url;
        }

    }

    /**
     * WebConnect
     */
    static class WebConnect {

        static final Logger logger = LoggerFactory.getLogger(WebConnect.class);

        /**
         * Makes an Http Web Request
         * @param props Http Web Request Properties
         * @return Output from the web request
         */
        public static String makeWebRequest(HttpServletRequest request, HttpActionProperties props) {

            try {

                Enumeration<String> parmNamesEnum = request.getParameterNames();
                List<String> parmNames = null;
                
                if (parmNamesEnum.hasMoreElements()) {
                	parmNames =  Collections.list(parmNamesEnum);
                }
                
            	String urlStr = props.getUrl();
            	
            	if (props.getRequestMethod().equals("GET")) {
            		
            		if (parmNames != null) {
            			urlStr += "?";
            			parmNamesEnum = request.getParameterNames();
                        while (parmNamesEnum.hasMoreElements()) {
                        	String parmName = parmNamesEnum.nextElement();
                        	urlStr += parmName + "=" + request.getParameter(parmName);
                        	
                        	if (parmNamesEnum.hasMoreElements()) {
                        		urlStr += "&";
                        	}
                        }            		
            			
            		}
            		
            		logger.info("GET URL with Params = " + urlStr);
            	}
            	
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(props.getRequestMethod());
                
                // TODO revisit, this is not working for some reason
                String parmValue = "";
                if (props.getRequestMethod().equals("POST")) {
                	
                	if (parmNames != null) {
                        for (String parmName : parmNames) {
                        	parmValue = request.getParameter(parmName);
                        	logger.info("POST Adding Param: Key=" + parmName + " Value=" + parmValue);
                        	connection.setRequestProperty(parmName, parmValue);
           
                        }
                		
                	}
                	
                }
                
                
                
                
                //connection.
                

                // TODO: add support for request headers and parameters                

                connection.connect();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer buf = new StringBuffer();

                while ((line = in.readLine()) != null) {
                    buf.append(line);
                }

                in.close();
                connection.disconnect();

                return buf.toString();
            } catch (Exception e) {                
                logger.error("Exception from makeWebRequest", e);
                return "";
            }
        }
    }

}