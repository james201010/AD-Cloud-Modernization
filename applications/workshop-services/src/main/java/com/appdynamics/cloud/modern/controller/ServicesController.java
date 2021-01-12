/**
 * 
 */
package com.appdynamics.cloud.modern.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author James Schneider
 *
 */
@RestController
public class ServicesController {

	/**
	 * 
	 */
	public ServicesController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/workshop/api/recordstep", method = RequestMethod.POST, produces = { "application/json" })
	public String recordWorkshopStep(HttpServletRequest request) {
	
	
		
		
		
		
		
		return "";
			
	}	
	
}
