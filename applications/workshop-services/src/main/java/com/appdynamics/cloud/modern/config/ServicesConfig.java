/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.util.List;

/**
 * @author James Schneider
 *
 */
public class ServicesConfig {

	private EventsServiceConfig eventsServiceConfig;
	private List<AnalyticsEventsSourceConfig> analyticsEventsSources;
	private VaultInfo vaultInfo;
	
	/**
	 * 
	 */
	public ServicesConfig() {
		
	}

	public EventsServiceConfig getEventsServiceConfig() {
		return eventsServiceConfig;
	}

	public void setEventsServiceConfig(EventsServiceConfig eventsServiceConfig) {
		this.eventsServiceConfig = eventsServiceConfig;
	}

	public List<AnalyticsEventsSourceConfig> getAnalyticsEventsSources() {
		return analyticsEventsSources;
	}

	public void setAnalyticsEventsSources(List<AnalyticsEventsSourceConfig> analyticsEventsSources) {
		this.analyticsEventsSources = analyticsEventsSources;
	}

	public VaultInfo getVaultInfo() {
		return vaultInfo;
	}

	public void setVaultInfo(VaultInfo vaultInfo) {
		this.vaultInfo = vaultInfo;
	}

	
	
}
