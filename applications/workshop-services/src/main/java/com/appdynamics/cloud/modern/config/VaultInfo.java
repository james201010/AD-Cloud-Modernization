package com.appdynamics.cloud.modern.config;

import java.util.List;

public class VaultInfo {

    private String vaultHost;
    private Boolean validateSsl;
    private String authStr;
    private List<UriPath> uriPaths = null;

    public String getVaultHost() {
        return vaultHost;
    }

    public void setVaultHost(String vaultHost) {
        this.vaultHost = vaultHost;
    }

    public Boolean getValidateSsl() {
        return validateSsl;
    }

    public void setValidateSsl(Boolean validateSsl) {
        this.validateSsl = validateSsl;
    }

    public String getAuthStr() {
        return authStr;
    }

    public void setAuthStr(String authStr) {
        this.authStr = authStr;
    }

    public List<UriPath> getUriPaths() {
        return uriPaths;
    }

    public void setUriPaths(List<UriPath> uriPaths) {
        this.uriPaths = uriPaths;
    }

}
