/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

import java.util.List;

/**
 * @author James Schneider
 *
 */
public class BrumAppCreateResponse {
	
	  private int id;
	  private float version;
	  private String name;
	  private boolean nameUnique;
	  private boolean builtIn;
	  private String createdBy;
	  private float createdOn;
	  private String modifiedBy;
	  private float modifiedOn;
	  private String description;
	  private boolean template;
	  private boolean active;
	  private boolean running;
	  private String runningSince = null;
	  private float deployWorkflowId;
	  private float undeployWorkflowId;
	  private String visualization = null;
	  List<Object> environmentProperties;
	  private String eumAppName = null;
	  private String accountGuid = null;
	  private String applicationTypeInfo = null;

		/**
		 * 
		 */
		public BrumAppCreateResponse() {
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

	  public boolean getTemplate() {
	    return template;
	  }

	  public boolean getActive() {
	    return active;
	  }

	  public boolean getRunning() {
	    return running;
	  }

	  public String getRunningSince() {
	    return runningSince;
	  }

	  public float getDeployWorkflowId() {
	    return deployWorkflowId;
	  }

	  public float getUndeployWorkflowId() {
	    return undeployWorkflowId;
	  }

	  public String getVisualization() {
	    return visualization;
	  }

	  public String getEumAppName() {
	    return eumAppName;
	  }

	  public String getAccountGuid() {
	    return accountGuid;
	  }

	  public String getApplicationTypeInfo() {
	    return applicationTypeInfo;
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

	  public void setTemplate( boolean template ) {
	    this.template = template;
	  }

	  public void setActive( boolean active ) {
	    this.active = active;
	  }

	  public void setRunning( boolean running ) {
	    this.running = running;
	  }

	  public void setRunningSince( String runningSince ) {
	    this.runningSince = runningSince;
	  }

	  public void setDeployWorkflowId( float deployWorkflowId ) {
	    this.deployWorkflowId = deployWorkflowId;
	  }

	  public void setUndeployWorkflowId( float undeployWorkflowId ) {
	    this.undeployWorkflowId = undeployWorkflowId;
	  }

	  public void setVisualization( String visualization ) {
	    this.visualization = visualization;
	  }

	  public void setEumAppName( String eumAppName ) {
	    this.eumAppName = eumAppName;
	  }

	  public void setAccountGuid( String accountGuid ) {
	    this.accountGuid = accountGuid;
	  }

	  public void setApplicationTypeInfo( String applicationTypeInfo ) {
	    this.applicationTypeInfo = applicationTypeInfo;
	  }	


}
