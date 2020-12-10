/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author James Schneider
 *
 */
public class EventsServiceConfig {

    //@SerializedName("events-service-endpoint")
    //@Expose
    private String eventsServiceEndpoint;
	
    //@SerializedName("events-service-apikey")
    //@Expose
    private String eventsServiceApikey;
	
    //@SerializedName("controller-global-account")
    //@Expose
    private String controllerGlobalAccount;
    
    
	/**
	 * 
	 */
	public EventsServiceConfig() {
		
	}


	public String getEventsServiceEndpoint() {
		return eventsServiceEndpoint;
	}


	public void setEventsServiceEndpoint(String eventsServiceEndpoint) {
		this.eventsServiceEndpoint = eventsServiceEndpoint;
	}


	public String getEventsServiceApikey() {
		return eventsServiceApikey;
	}


	public void setEventsServiceApikey(String eventsServiceApikey) {
		this.eventsServiceApikey = eventsServiceApikey;
	}


	public String getControllerGlobalAccount() {
		return controllerGlobalAccount;
	}


	public void setControllerGlobalAccount(String controllerGlobalAccount) {
		this.controllerGlobalAccount = controllerGlobalAccount;
	}

	

	
	
}
