/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

import java.util.ArrayList;

/**
 * @author James Schneider
 *
 */
public class RbacUserUpdateRequest {

	public boolean updateGroups;
	public ArrayList<Object> groups = new ArrayList<Object>();
	public User user;

	/**
	 * 
	 */
	public RbacUserUpdateRequest() {
		
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

	  public void setUser( User user ) {
	    this.user = user;
	  }
	  

	public class User {
	  private int id;
	  private int version;
	  private String name;
	  private boolean nameUnique;
	  private boolean builtIn;
	  private String createdBy;
	  private long createdOn;
	  private String modifiedBy;
	  private long modifiedOn;
	  private String displayName;
	  private String password = null;
	  public DefaultRbacRole[] roles;
	  public int[] accountRoleIds;
	  private String email;
	  private String providerUniqueName;
	  private String securityProviderType;
	  private String mobileDeviceUuids = null;


	 // Getter Methods 

	  public int getId() {
	    return id;
	  }

	  public int getVersion() {
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

	  public long getCreatedOn() {
	    return createdOn;
	  }

	  public String getModifiedBy() {
	    return modifiedBy;
	  }

	  public long getModifiedOn() {
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

	  public void setVersion( int version ) {
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

	  public void setCreatedOn( long createdOn ) {
	    this.createdOn = createdOn;
	  }

	  public void setModifiedBy( String modifiedBy ) {
	    this.modifiedBy = modifiedBy;
	  }

	  public void setModifiedOn( long modifiedOn ) {
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

	  public DefaultRbacRole[] getRoles() {
		return roles;
	  }

	  public void setRoles(DefaultRbacRole[] roles) {
		this.roles = roles;
	  }

	  public int[] getAccountRoleIds() {
		return accountRoleIds;
	  }

	  public void setAccountRoleIds(int[] accountRoleIds) {
		this.accountRoleIds = accountRoleIds;
	  }
	  
	  
	  
	}

	

}
