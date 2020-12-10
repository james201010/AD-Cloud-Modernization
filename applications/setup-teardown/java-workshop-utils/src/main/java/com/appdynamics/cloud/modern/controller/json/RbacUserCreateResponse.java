/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

import java.util.List;

/**
 * @author James Schneider
 *
 */
public class RbacUserCreateResponse {
	
	  private int id;
	  private float version;
	  private String name;
	  private boolean nameUnique;
	  private boolean builtIn;
	  private String createdBy;
	  private float createdOn;
	  private String modifiedBy;
	  private float modifiedOn;
	  private String displayName;
	  private String password = null;
	  List<Object> roles;
	  List<Object> accountRoleIds;
	  private String email;
	  private String providerUniqueName;
	  private String securityProviderType;
	  private String mobileDeviceUuids = null;
	/**
	 * 
	 */
	public RbacUserCreateResponse() {
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

	  public String getDisplayName() {
	    return displayName;
	  }

	  public String getPassword() {
	    return password;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public String getProviderUniqueName() {
	    return providerUniqueName;
	  }

	  public String getSecurityProviderType() {
	    return securityProviderType;
	  }

	  public String getMobileDeviceUuids() {
	    return mobileDeviceUuids;
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

	  public void setDisplayName( String displayName ) {
	    this.displayName = displayName;
	  }

	  public void setPassword( String password ) {
	    this.password = password;
	  }

	  public void setEmail( String email ) {
	    this.email = email;
	  }

	  public void setProviderUniqueName( String providerUniqueName ) {
	    this.providerUniqueName = providerUniqueName;
	  }

	  public void setSecurityProviderType( String securityProviderType ) {
	    this.securityProviderType = securityProviderType;
	  }

	  public void setMobileDeviceUuids( String mobileDeviceUuids ) {
	    this.mobileDeviceUuids = mobileDeviceUuids;
	  }
}
