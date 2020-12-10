/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class RbacUserCreateRequest {
	  private boolean updateGroups;
	  public Object[] groups;
	  public User user = new User();

	/**
	 * 
	 */
	public RbacUserCreateRequest() {
		
	}


	 // Getter Methods 

	  public boolean getUpdateGroups() {
	    return updateGroups;
	  }

	  public User getUser() {
	    return user;
	  }

	 // Setter Methods 

	  public void setUpdateGroups( boolean updateGroups ) {
	    this.updateGroups = updateGroups;
	  }

	  public void setUser( User userObject ) {
	    this.user = userObject;
	  }
	
	  
		public Object[] getGroups() {
		return groups;
	}


	public void setGroups(Object[] groups) {
		this.groups = groups;
	}


		public class User {
			  private String name;
			  private String displayName;
			  private String email;
			  public Object[] accountRoleIds;
			  private String password;


			 // Getter Methods 

			  public String getName() {
			    return name;
			  }

			  public String getDisplayName() {
			    return displayName;
			  }

			  public String getEmail() {
			    return email;
			  }

			  public String getPassword() {
			    return password;
			  }

			 // Setter Methods 

			  public void setName( String name ) {
			    this.name = name;
			  }

			  public void setDisplayName( String displayName ) {
			    this.displayName = displayName;
			  }

			  public void setEmail( String email ) {
			    this.email = email;
			  }

			  public void setPassword( String password ) {
			    this.password = password;
			  }

			public Object[] getAccountRoleIds() {
				return accountRoleIds;
			}

			public void setAccountRoleIds(Object[] accountRoleIds) {
				this.accountRoleIds = accountRoleIds;
			}
			  
			  

		   }	  

}

