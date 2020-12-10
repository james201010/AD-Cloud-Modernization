/**
 * 
 */
package com.appdynamics.cloud.modern.repos.utils;

/**
 * @author James Schneider
 *
 */
public class PerformanceModifierConfig {

	String name;
	String lowerRandomIntervalStr;
	String upperRandomIntervalStr;
	String lowerSleepTimeMillisStr;
	String upperSleepTimeMillisStr;
		
	/**
	 * 
	 */
	public PerformanceModifierConfig() {
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLowerRandomIntervalStr() {
		return lowerRandomIntervalStr;
	}


	public void setLowerRandomIntervalStr(String lowerRandomIntervalStr) {
		this.lowerRandomIntervalStr = lowerRandomIntervalStr;
	}


	public String getUpperRandomIntervalStr() {
		return upperRandomIntervalStr;
	}


	public void setUpperRandomIntervalStr(String upperRandomIntervalStr) {
		this.upperRandomIntervalStr = upperRandomIntervalStr;
	}


	public String getLowerSleepTimeMillisStr() {
		return lowerSleepTimeMillisStr;
	}


	public void setLowerSleepTimeMillisStr(String lowerSleepTimeMillisStr) {
		this.lowerSleepTimeMillisStr = lowerSleepTimeMillisStr;
	}


	public String getUpperSleepTimeMillisStr() {
		return upperSleepTimeMillisStr;
	}


	public void setUpperSleepTimeMillisStr(String upperSleepTimeMillisStr) {
		this.upperSleepTimeMillisStr = upperSleepTimeMillisStr;
	}


	public int getLowerRandomInterval() {
		return Integer.parseInt(lowerRandomIntervalStr);
	}


	public int getUpperRandomInterval() {
		return Integer.parseInt(upperRandomIntervalStr);
	}


	public long getLowerSleepTimeMillis() {
		return Long.parseLong(lowerSleepTimeMillisStr);
	}


	public long getUpperSleepTimeMillis() {
		return Long.parseLong(upperSleepTimeMillisStr);
	}

	
}
