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
public class CloudAuthConfig {

    @SerializedName("aws-access-key")
    @Expose
	private String accessKey;
	
	
    @SerializedName("aws-access-secret")
    @Expose
	private String accessSecret;
	
	
	/**
	 * 
	 */
	public CloudAuthConfig() {
		
	}


	public String getAccessKey() {
		return accessKey;
	}


	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}


	public String getAccessSecret() {
		return accessSecret;
	}


	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}



	
	
}
