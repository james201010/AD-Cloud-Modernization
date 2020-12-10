/**
 * 
 */
package com.appdynamics.cloud.modern.web.beans;

import java.util.Map;

/**
 * 
 * @author James Schneider
 *
 */
public class HttpJsonTransport {

	private String serviceOperation;
	private String responseCode;
	private Map<String, String> requestParameters;
	private Map<String, String> jsonPayloads;
	 
	/**
	 * 
	 */
	public HttpJsonTransport() {
		
	}



	public Map<String, String> getRequestParameters() {
		return requestParameters;
	}



	public void setRequestParameters(Map<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}



	public Map<String, String> getJsonPayloads() {
		return jsonPayloads;
	}



	public void setJsonPayloads(Map<String, String> jsonPayloads) {
		this.jsonPayloads = jsonPayloads;
	}



	public String getServiceOperation() {
		return serviceOperation;
	}



	public void setServiceOperation(String serviceOperation) {
		this.serviceOperation = serviceOperation;
	}



	public String getResponseCode() {
		return responseCode;
	}



	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	
	
	
	
}
