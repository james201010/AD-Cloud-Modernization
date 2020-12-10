/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

/**
 * @author James Schneider
 *
 */
public class RbacRoleCreateRequest {
	
	  public Permission[] permissions;
	  private String name;
	  
	/**
	 * 
	 */
	public RbacRoleCreateRequest() {
		
	}


	 // Getter Methods 

	  public String getName() {
	    return name;
	  }

	 // Setter Methods 

	  public void setName( String name ) {
	    this.name = name;
	  }
	  
	  

	  public Permission[] getPermissions() {
		return permissions;
	  }


	  public void setPermissions(Permission[] permissions) {
		this.permissions = permissions;
	  }



	public class Permission {
		  private String action;
		  private boolean allowed;
		  AffectedEntity affectedEntity;


		 // Getter Methods 

		  public String getAction() {
		    return action;
		  }

		  public boolean getAllowed() {
		    return allowed;
		  }

		  public AffectedEntity getAffectedEntity() {
		    return affectedEntity;
		  }

		 // Setter Methods 

		  public void setAction( String action ) {
		    this.action = action;
		  }

		  public void setAllowed( boolean allowed ) {
		    this.allowed = allowed;
		  }

		  public void setAffectedEntity( AffectedEntity affectedEntity ) {
		    this.affectedEntity = affectedEntity;
		  }
		}	  
	  

		public class AffectedEntity {
			  private int entityId;
			  private String entityType;


			 // Getter Methods 

			  public int getEntityId() {
			    return entityId;
			  }

			  public String getEntityType() {
			    return entityType;
			  }

			 // Setter Methods 

			  public void setEntityId( int entityId ) {
			    this.entityId = entityId;
			  }

			  public void setEntityType( String entityType ) {
			    this.entityType = entityType;
			  }
			}
	  
	   	  
}







