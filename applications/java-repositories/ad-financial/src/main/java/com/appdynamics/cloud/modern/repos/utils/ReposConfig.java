/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

import java.util.List;

/**
 * @author James Schneider
 *
 */
public class ReposConfig {

	private String creditCheckUrl;
	private String creditCheckLowerRandomIntervalStr;
	private String creditCheckUpperRandomIntervalStr;
	private List<PerformanceModifierConfig> performanceModifiers;

	
	/**
	 * 
	 */
	public ReposConfig() {
		
	}

	public String getCreditCheckUrl() {
		return creditCheckUrl;
	}

	public void setCreditCheckUrl(String creditCheckUrl) {
		this.creditCheckUrl = creditCheckUrl;
	}


	public String getCreditCheckLowerRandomIntervalStr() {
		return creditCheckLowerRandomIntervalStr;
	}

	public void setCreditCheckLowerRandomIntervalStr(String creditCheckLowerRandomIntervalStr) {
		this.creditCheckLowerRandomIntervalStr = creditCheckLowerRandomIntervalStr;
	}

	public String getCreditCheckUpperRandomIntervalStr() {
		return creditCheckUpperRandomIntervalStr;
	}

	public void setCreditCheckUpperRandomIntervalStr(String creditCheckUpperRandomIntervalStr) {
		this.creditCheckUpperRandomIntervalStr = creditCheckUpperRandomIntervalStr;
	}

	public List<PerformanceModifierConfig> getPerformanceModifiers() {
		return performanceModifiers;
	}

	public void setPerformanceModifiers(List<PerformanceModifierConfig> performanceModifiers) {
		this.performanceModifiers = performanceModifiers;
	}

	public long getCreditCheckLowerRandomInterval() {
		return Long.parseLong(creditCheckLowerRandomIntervalStr);
	}

	public long getCreditCheckUpperRandomInterval() {
		return Long.parseLong(creditCheckUpperRandomIntervalStr);
	}

	
	
}
