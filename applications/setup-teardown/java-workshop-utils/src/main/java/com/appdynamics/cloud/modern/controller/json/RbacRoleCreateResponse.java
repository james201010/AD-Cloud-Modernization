/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;



/**
 * @author James Schneider
 *
 */
public class RbacRoleCreateResponse {
	
	  private int id;
	  private float version;
	  private String name;
	  private boolean nameUnique;
	  private boolean builtIn;
	  private String createdBy;
	  private float createdOn;
	  private String modifiedBy;
	  private float modifiedOn;
	  private String description = null;
	  private boolean readonly;
	  
	  public Permission[] permissions;
	  
	/**
	 * 
	 */
	public RbacRoleCreateResponse() {
		// TODO Auto-generated constructor stub
	}



	 // Getter Methods 

	  public int getId() {
	    return id;
	  }

	  public float getVersion() {
	    return version;
	  }

	  public String getName() {
	    return name;
	  }

	  public boolean getNameUnique() {
	    return nameUnique;
	  }

	  public boolean getBuiltIn() {
	    return builtIn;
	  }

	  public String getCreatedBy() {
	    return createdBy;
	  }

	  public float getCreatedOn() {
	    return createdOn;
	  }

	  public String getModifiedBy() {
	    return modifiedBy;
	  }

	  public float getModifiedOn() {
	    return modifiedOn;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public boolean getReadonly() {
	    return readonly;
	  }

	 // Setter Methods 

	  public void setId( int id ) {
	    this.id = id;
	  }

	  public void setVersion( float version ) {
	    this.version = version;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setNameUnique( boolean nameUnique ) {
	    this.nameUnique = nameUnique;
	  }

	  public void setBuiltIn( boolean builtIn ) {
	    this.builtIn = builtIn;
	  }

	  public void setCreatedBy( String createdBy ) {
	    this.createdBy = createdBy;
	  }

	  public void setCreatedOn( float createdOn ) {
	    this.createdOn = createdOn;
	  }

	  public void setModifiedBy( String modifiedBy ) {
	    this.modifiedBy = modifiedBy;
	  }

	  public void setModifiedOn( float modifiedOn ) {
	    this.modifiedOn = modifiedOn;
	  }

	  public void setDescription( String description ) {
	    this.description = description;
	  }

	  public void setReadonly( boolean readonly ) {
	    this.readonly = readonly;
	  }

	  public Permission[] getPermissions() {
		return permissions;
	  }


	  public void setPermissions(Permission[] permissions) {
		this.permissions = permissions;
	  }
	  
	  public class Permission {
		  private int id;
		  private int version;
		  private String action;
		  private boolean allowed;

		  AffectedEntity AffectedEntityObject;

		  // Getter Methods 

		  public String getAction() {
			  return action;
		  }

		  public boolean getAllowed() {
			  return allowed;
		  }

		  public AffectedEntity getAffectedEntity() {
			  return AffectedEntityObject;
		  }

		  // Setter Methods 

		  public void setAction( String action ) {
			  this.action = action;
		  }

		  public void setAllowed( boolean allowed ) {
			  this.allowed = allowed;
		  }

		  public void setAffectedEntity( AffectedEntity affectedEntityObject ) {
			  this.AffectedEntityObject = affectedEntityObject;
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



	  }	  
	  

		public class AffectedEntity {
			private int id;
			private int version;
			private int entityId;
			private String entityType;
			private String prettyToString;

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

			public String getPrettyToString() {
				return prettyToString;
			}

			public void setPrettyToString(String prettyToString) {
				this.prettyToString = prettyToString;
			}



		}
		
}
