/**
 * 
 */
package com.appdynamics.cloud.modern.controller.json;

import java.util.ArrayList;

/**
 * @author James Schneider
 *
 */
public class DBCollectorCreateResponse {
	
	  private int id;
	  private float version;
	  private String name;
	  private boolean nameUnique;
	  private boolean builtIn;
	  private String createdBy = null;
	  private String createdOn = null;
	  private String modifiedBy = null;
	  private String modifiedOn = null;
	  private String type;
	  private String hostname;
	  private boolean useWindowsAuth;
	  private String username;
	  private String password;
	  private float port;
	  private boolean loggingEnabled;
	  private boolean enabled;
	  private String excludedSchemas = null;
	  ArrayList<Object> jdbcConnectionProperties = new ArrayList<Object>();
	  private String databaseName;
	  private String failoverPartner = null;
	  private boolean connectAsSysdba;
	  private boolean useServiceName;
	  private String sid;
	  private String customConnectionString = null;
	  private boolean enterpriseDB;
	  private boolean useSSL;
	  private boolean enableOSMonitor;
	  private String hostOS = null;
	  private boolean useLocalWMI;
	  private String hostDomain = null;
	  private String hostUsername = null;
	  private String hostPassword = null;
	  private String dbInstanceIdentifier = null;
	  private String region = null;
	  private boolean certificateAuth;
	  private boolean removeLiterals;
	  private float sshPort;
	  private String agentName;
	  private boolean dbCyberArkEnabled;
	  private String dbCyberArkApplication = null;
	  private String dbCyberArkSafe = null;
	  private String dbCyberArkFolder = null;
	  private String dbCyberArkObject = null;
	  private boolean hwCyberArkEnabled;
	  private String hwCyberArkApplication = null;
	  private String hwCyberArkSafe = null;
	  private String hwCyberArkFolder = null;
	  private String hwCyberArkObject = null;
	  private boolean orapkiSslEnabled;
	  private boolean orasslClientAuthEnabled;
	  private String orasslTruststoreLoc = null;
	  private String orasslTruststoreType = null;
	  private String orasslTruststorePassword = null;
	  private String orasslKeystoreLoc = null;
	  private String orasslKeystoreType = null;
	  private String orasslKeystorePassword = null;
	  private boolean ldapEnabled;
	  private String customMetrics = null;
	  ArrayList<Object> subConfigs = new ArrayList<Object>();
	  private float jmxPort;
	  ArrayList<Object> backendIds = new ArrayList<Object>();
	  ArrayList<Object> extraProperties = new ArrayList<Object>();

	/**
	 * 
	 */
	public DBCollectorCreateResponse() {
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

	  public String getCreatedOn() {
	    return createdOn;
	  }

	  public String getModifiedBy() {
	    return modifiedBy;
	  }

	  public String getModifiedOn() {
	    return modifiedOn;
	  }

	  public String getType() {
	    return type;
	  }

	  public String getHostname() {
	    return hostname;
	  }

	  public boolean getUseWindowsAuth() {
	    return useWindowsAuth;
	  }

	  public String getUsername() {
	    return username;
	  }

	  public String getPassword() {
	    return password;
	  }

	  public float getPort() {
	    return port;
	  }

	  public boolean getLoggingEnabled() {
	    return loggingEnabled;
	  }

	  public boolean getEnabled() {
	    return enabled;
	  }

	  public String getExcludedSchemas() {
	    return excludedSchemas;
	  }

	  public String getDatabaseName() {
	    return databaseName;
	  }

	  public String getFailoverPartner() {
	    return failoverPartner;
	  }

	  public boolean getConnectAsSysdba() {
	    return connectAsSysdba;
	  }

	  public boolean getUseServiceName() {
	    return useServiceName;
	  }

	  public String getSid() {
	    return sid;
	  }

	  public String getCustomConnectionString() {
	    return customConnectionString;
	  }

	  public boolean getEnterpriseDB() {
	    return enterpriseDB;
	  }

	  public boolean getUseSSL() {
	    return useSSL;
	  }

	  public boolean getEnableOSMonitor() {
	    return enableOSMonitor;
	  }

	  public String getHostOS() {
	    return hostOS;
	  }

	  public boolean getUseLocalWMI() {
	    return useLocalWMI;
	  }

	  public String getHostDomain() {
	    return hostDomain;
	  }

	  public String getHostUsername() {
	    return hostUsername;
	  }

	  public String getHostPassword() {
	    return hostPassword;
	  }

	  public String getDbInstanceIdentifier() {
	    return dbInstanceIdentifier;
	  }

	  public String getRegion() {
	    return region;
	  }

	  public boolean getCertificateAuth() {
	    return certificateAuth;
	  }

	  public boolean getRemoveLiterals() {
	    return removeLiterals;
	  }

	  public float getSshPort() {
	    return sshPort;
	  }

	  public String getAgentName() {
	    return agentName;
	  }

	  public boolean getDbCyberArkEnabled() {
	    return dbCyberArkEnabled;
	  }

	  public String getDbCyberArkApplication() {
	    return dbCyberArkApplication;
	  }

	  public String getDbCyberArkSafe() {
	    return dbCyberArkSafe;
	  }

	  public String getDbCyberArkFolder() {
	    return dbCyberArkFolder;
	  }

	  public String getDbCyberArkObject() {
	    return dbCyberArkObject;
	  }

	  public boolean getHwCyberArkEnabled() {
	    return hwCyberArkEnabled;
	  }

	  public String getHwCyberArkApplication() {
	    return hwCyberArkApplication;
	  }

	  public String getHwCyberArkSafe() {
	    return hwCyberArkSafe;
	  }

	  public String getHwCyberArkFolder() {
	    return hwCyberArkFolder;
	  }

	  public String getHwCyberArkObject() {
	    return hwCyberArkObject;
	  }

	  public boolean getOrapkiSslEnabled() {
	    return orapkiSslEnabled;
	  }

	  public boolean getOrasslClientAuthEnabled() {
	    return orasslClientAuthEnabled;
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

	  public String getOrasslKeystoreLoc() {
	    return orasslKeystoreLoc;
	  }

	  public String getOrasslKeystoreType() {
	    return orasslKeystoreType;
	  }

	  public String getOrasslKeystorePassword() {
	    return orasslKeystorePassword;
	  }

	  public boolean getLdapEnabled() {
	    return ldapEnabled;
	  }

	  public String getCustomMetrics() {
	    return customMetrics;
	  }

	  public float getJmxPort() {
	    return jmxPort;
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

	  public void setCreatedOn( String createdOn ) {
	    this.createdOn = createdOn;
	  }

	  public void setModifiedBy( String modifiedBy ) {
	    this.modifiedBy = modifiedBy;
	  }

	  public void setModifiedOn( String modifiedOn ) {
	    this.modifiedOn = modifiedOn;
	  }

	  public void setType( String type ) {
	    this.type = type;
	  }

	  public void setHostname( String hostname ) {
	    this.hostname = hostname;
	  }

	  public void setUseWindowsAuth( boolean useWindowsAuth ) {
	    this.useWindowsAuth = useWindowsAuth;
	  }

	  public void setUsername( String username ) {
	    this.username = username;
	  }

	  public void setPassword( String password ) {
	    this.password = password;
	  }

	  public void setPort( float port ) {
	    this.port = port;
	  }

	  public void setLoggingEnabled( boolean loggingEnabled ) {
	    this.loggingEnabled = loggingEnabled;
	  }

	  public void setEnabled( boolean enabled ) {
	    this.enabled = enabled;
	  }

	  public void setExcludedSchemas( String excludedSchemas ) {
	    this.excludedSchemas = excludedSchemas;
	  }

	  public void setDatabaseName( String databaseName ) {
	    this.databaseName = databaseName;
	  }

	  public void setFailoverPartner( String failoverPartner ) {
	    this.failoverPartner = failoverPartner;
	  }

	  public void setConnectAsSysdba( boolean connectAsSysdba ) {
	    this.connectAsSysdba = connectAsSysdba;
	  }

	  public void setUseServiceName( boolean useServiceName ) {
	    this.useServiceName = useServiceName;
	  }

	  public void setSid( String sid ) {
	    this.sid = sid;
	  }

	  public void setCustomConnectionString( String customConnectionString ) {
	    this.customConnectionString = customConnectionString;
	  }

	  public void setEnterpriseDB( boolean enterpriseDB ) {
	    this.enterpriseDB = enterpriseDB;
	  }

	  public void setUseSSL( boolean useSSL ) {
	    this.useSSL = useSSL;
	  }

	  public void setEnableOSMonitor( boolean enableOSMonitor ) {
	    this.enableOSMonitor = enableOSMonitor;
	  }

	  public void setHostOS( String hostOS ) {
	    this.hostOS = hostOS;
	  }

	  public void setUseLocalWMI( boolean useLocalWMI ) {
	    this.useLocalWMI = useLocalWMI;
	  }

	  public void setHostDomain( String hostDomain ) {
	    this.hostDomain = hostDomain;
	  }

	  public void setHostUsername( String hostUsername ) {
	    this.hostUsername = hostUsername;
	  }

	  public void setHostPassword( String hostPassword ) {
	    this.hostPassword = hostPassword;
	  }

	  public void setDbInstanceIdentifier( String dbInstanceIdentifier ) {
	    this.dbInstanceIdentifier = dbInstanceIdentifier;
	  }

	  public void setRegion( String region ) {
	    this.region = region;
	  }

	  public void setCertificateAuth( boolean certificateAuth ) {
	    this.certificateAuth = certificateAuth;
	  }

	  public void setRemoveLiterals( boolean removeLiterals ) {
	    this.removeLiterals = removeLiterals;
	  }

	  public void setSshPort( float sshPort ) {
	    this.sshPort = sshPort;
	  }

	  public void setAgentName( String agentName ) {
	    this.agentName = agentName;
	  }

	  public void setDbCyberArkEnabled( boolean dbCyberArkEnabled ) {
	    this.dbCyberArkEnabled = dbCyberArkEnabled;
	  }

	  public void setDbCyberArkApplication( String dbCyberArkApplication ) {
	    this.dbCyberArkApplication = dbCyberArkApplication;
	  }

	  public void setDbCyberArkSafe( String dbCyberArkSafe ) {
	    this.dbCyberArkSafe = dbCyberArkSafe;
	  }

	  public void setDbCyberArkFolder( String dbCyberArkFolder ) {
	    this.dbCyberArkFolder = dbCyberArkFolder;
	  }

	  public void setDbCyberArkObject( String dbCyberArkObject ) {
	    this.dbCyberArkObject = dbCyberArkObject;
	  }

	  public void setHwCyberArkEnabled( boolean hwCyberArkEnabled ) {
	    this.hwCyberArkEnabled = hwCyberArkEnabled;
	  }

	  public void setHwCyberArkApplication( String hwCyberArkApplication ) {
	    this.hwCyberArkApplication = hwCyberArkApplication;
	  }

	  public void setHwCyberArkSafe( String hwCyberArkSafe ) {
	    this.hwCyberArkSafe = hwCyberArkSafe;
	  }

	  public void setHwCyberArkFolder( String hwCyberArkFolder ) {
	    this.hwCyberArkFolder = hwCyberArkFolder;
	  }

	  public void setHwCyberArkObject( String hwCyberArkObject ) {
	    this.hwCyberArkObject = hwCyberArkObject;
	  }

	  public void setOrapkiSslEnabled( boolean orapkiSslEnabled ) {
	    this.orapkiSslEnabled = orapkiSslEnabled;
	  }

	  public void setOrasslClientAuthEnabled( boolean orasslClientAuthEnabled ) {
	    this.orasslClientAuthEnabled = orasslClientAuthEnabled;
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

	  public void setOrasslKeystoreLoc( String orasslKeystoreLoc ) {
	    this.orasslKeystoreLoc = orasslKeystoreLoc;
	  }

	  public void setOrasslKeystoreType( String orasslKeystoreType ) {
	    this.orasslKeystoreType = orasslKeystoreType;
	  }

	  public void setOrasslKeystorePassword( String orasslKeystorePassword ) {
	    this.orasslKeystorePassword = orasslKeystorePassword;
	  }

	  public void setLdapEnabled( boolean ldapEnabled ) {
	    this.ldapEnabled = ldapEnabled;
	  }

	  public void setCustomMetrics( String customMetrics ) {
	    this.customMetrics = customMetrics;
	  }

	  public void setJmxPort( float jmxPort ) {
	    this.jmxPort = jmxPort;
	  }
}
