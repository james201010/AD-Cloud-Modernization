/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class AppCreateRequest {
	  private String name;
	  private String description;
	  
	  
	public AppCreateRequest() {
		
	}

	 // Getter Methods 

	  public String getName() {
	    return name;
	  }

	  public String getDescription() {
	    return description;
	  }

	 // Setter Methods 

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setDescription( String description ) {
	    this.description = description;
	  }



}
