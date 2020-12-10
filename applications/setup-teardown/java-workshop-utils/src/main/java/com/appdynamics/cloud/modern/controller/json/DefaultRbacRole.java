/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class DefaultRbacRole {
	
	  public int id;
	  public int version;
	  public String name;
	  public boolean nameUnique;
	  public String description;
	  public String displayName;
	  
	/**
	 * 
	 */
	public DefaultRbacRole() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNameUnique() {
		return nameUnique;
	}

	public void setNameUnique(boolean nameUnique) {
		this.nameUnique = nameUnique;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
