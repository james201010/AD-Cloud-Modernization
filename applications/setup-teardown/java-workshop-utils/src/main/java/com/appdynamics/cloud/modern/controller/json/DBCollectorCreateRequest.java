/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

import java.util.ArrayList;

/**
 * @author James Schneider
 *
 */
public class DBCollectorCreateRequest {

	private String hostname;
	  private String databaseName;
	  private String sid;
	  private boolean orapkiSslEnabled;
	  private String orasslTruststoreLoc = null;
	  private String orasslTruststoreType = null;
	  private String orasslTruststorePassword = null;
	  private boolean orasslClientAuthEnabled;
	  private String orasslKeystoreLoc = null;
	  private String orasslKeystoreType = null;
	  private String orasslKeystorePassword = null;
	  private String type;
	  private String username;
	  private String password;
	  private String agentName;
	  private String name;
	  private String port;
	  private boolean enabled;
	  ArrayList<Object> subConfigs = new ArrayList<Object>();

	/**
	 * 
	 */
	public DBCollectorCreateRequest() {
		
	}


	 // Getter Methods 

	  public String getHostname() {
	    return hostname;
	  }

	  public String getDatabaseName() {
	    return databaseName;
	  }

	  public String getSid() {
	    return sid;
	  }

	  public boolean getOrapkiSslEnabled() {
	    return orapkiSslEnabled;
	  }

	  public String getOrasslTruststoreLoc() {
	    return orasslTruststoreLoc;
	  }

	  public String getOrasslTruststoreType() {
	    return orasslTruststoreType;
	  }

	  public String getOrasslTruststorePassword() {
	    return orasslTruststorePassword;
	  }

	  public boolean getOrasslClientAuthEnabled() {
	    return orasslClientAuthEnabled;
	  }

	  public String getOrasslKeystoreLoc() {
	    return orasslKeystoreLoc;
	  }

	  public String getOrasslKeystoreType() {
	    return orasslKeystoreType;
	  }

	  public String getOrasslKeystorePassword() {
	    return orasslKeystorePassword;
	  }

	  public String getType() {
	    return type;
	  }

	  public String getUsername() {
	    return username;
	  }

	  public String getPassword() {
	    return password;
	  }

	  public String getAgentName() {
	    return agentName;
	  }

	  public String getName() {
	    return name;
	  }

	  public String getPort() {
	    return port;
	  }

	  public boolean getEnabled() {
	    return enabled;
	  }

	 // Setter Methods 

	  public void setHostname( String hostname ) {
	    this.hostname = hostname;
	  }

	  public void setDatabaseName( String databaseName ) {
	    this.databaseName = databaseName;
	  }

	  public void setSid( String sid ) {
	    this.sid = sid;
	  }

	  public void setOrapkiSslEnabled( boolean orapkiSslEnabled ) {
	    this.orapkiSslEnabled = orapkiSslEnabled;
	  }

	  public void setOrasslTruststoreLoc( String orasslTruststoreLoc ) {
	    this.orasslTruststoreLoc = orasslTruststoreLoc;
	  }

	  public void setOrasslTruststoreType( String orasslTruststoreType ) {
	    this.orasslTruststoreType = orasslTruststoreType;
	  }

	  public void setOrasslTruststorePassword( String orasslTruststorePassword ) {
	    this.orasslTruststorePassword = orasslTruststorePassword;
	  }

	  public void setOrasslClientAuthEnabled( boolean orasslClientAuthEnabled ) {
	    this.orasslClientAuthEnabled = orasslClientAuthEnabled;
	  }

	  public void setOrasslKeystoreLoc( String orasslKeystoreLoc ) {
	    this.orasslKeystoreLoc = orasslKeystoreLoc;
	  }

	  public void setOrasslKeystoreType( String orasslKeystoreType ) {
	    this.orasslKeystoreType = orasslKeystoreType;
	  }

	  public void setOrasslKeystorePassword( String orasslKeystorePassword ) {
	    this.orasslKeystorePassword = orasslKeystorePassword;
	  }

	  public void setType( String type ) {
	    this.type = type;
	  }

	  public void setUsername( String username ) {
	    this.username = username;
	  }

	  public void setPassword( String password ) {
	    this.password = password;
	  }

	  public void setAgentName( String agentName ) {
	    this.agentName = agentName;
	  }

	  public void setName( String name ) {
	    this.name = name;
	  }

	  public void setPort( String port ) {
	    this.port = port;
	  }

	  public void setEnabled( boolean enabled ) {
	    this.enabled = enabled;
	  }
}
