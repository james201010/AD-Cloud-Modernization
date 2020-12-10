/**
 * 
 */
package com.appdynamics.cloud.modern.config;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4380206613825782495L;
	private String tagKey;
	private String tagValue;
	private String tagValueResult;
	//private boolean valueUpdated = false;
	
	/**
	 * 
	 */
	public Tag() {
		
	}


	public String getTagKey() {
		return tagKey;
	}


	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}


	public String getTagValue() {
		return tagValue;
	}


	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}


	public String getTagValueResult() {
		return tagValueResult;
	}


	public void setTagValueResult(String tagValueResult) {
		this.tagValueResult = tagValueResult;
	}

	

//	public boolean isValueUpdated() {
//		return valueUpdated;
//	}
//
//
//	public void setValueUpdated(boolean valueUpdated) {
//		this.valueUpdated = valueUpdated;
//	}

	
	
}
