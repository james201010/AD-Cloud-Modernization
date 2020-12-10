/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.util.List;

/**
 * @author James Schneider
 *
 */
public class AnalyticsEventsSourceConfig {
	
	private String eventsSourceClass;
	private String schemaName;
	private String hourlyEventsBatchSize;
	private String publishEventsInterval;
	private List<ConfigProperty> configProperties;
	
	
	
	/**
	 * 
	 */
	public AnalyticsEventsSourceConfig() {
		
	}



	public String getEventsSourceClass() {
		return eventsSourceClass;
	}



	public void setEventsSourceClass(String eventsSourceClass) {
		this.eventsSourceClass = eventsSourceClass;
	}



	public String getSchemaName() {
		return schemaName;
	}



	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}



	public String getHourlyEventsBatchSize() {
		return hourlyEventsBatchSize;
	}



	public void setHourlyEventsBatchSize(String hourlyEventsBatchSize) {
		this.hourlyEventsBatchSize = hourlyEventsBatchSize;
	}



	public String getPublishEventsInterval() {
		return publishEventsInterval;
	}



	public void setPublishEventsInterval(String publishEventsInterval) {
		this.publishEventsInterval = publishEventsInterval;
	}



	public List<ConfigProperty> getConfigProperties() {
		return configProperties;
	}



	public void setConfigProperties(List<ConfigProperty> configProperties) {
		this.configProperties = configProperties;
	}

	
}
