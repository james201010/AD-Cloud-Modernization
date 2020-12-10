/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import java.io.Serializable;

/**
 * @author James Schneider
 *
 */
public class RbacUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7453666693194279794L;
	public int userId;
	public String userName;
	public String userPwd;
	public String email;
	public String accessKey;
	

}
