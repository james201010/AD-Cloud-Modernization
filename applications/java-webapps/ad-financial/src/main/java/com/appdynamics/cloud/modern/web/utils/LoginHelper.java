/**
 * 
 */
package com.appdynamics.cloud.modern.web.utils;

/**
 * @author James Schneider
 *
 */
public class LoginHelper {

	/**
	 * 
	 */
	public LoginHelper() {
		
	}
	
	public static String getLogin(String username) {
		
		switch (username) {
		
		case "batman":
			return "Bruce:Wayne:8729173021";
			

		default:
			return "Bruce:Wayne:8729173021";
			
		}
	}

}
