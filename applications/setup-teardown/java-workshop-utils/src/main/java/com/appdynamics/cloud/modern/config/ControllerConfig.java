package com.appdynamics.cloud.modern.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ControllerConfig {

    @SerializedName("controller-access-key")
    @Expose
    private String controllerAccessKey;

    @SerializedName("controller-account")
    @Expose
    private String controllerAccount;

    @SerializedName("controller-host-name")
    @Expose
    private String controllerHostName;

    @SerializedName("controller-port")
    @Expose
    private Integer controllerPort;

    @SerializedName("controller-ssl-enabled")
    @Expose
    private Boolean controllerSslEnabled;

    @SerializedName("controller-url-full")
    @Expose
    private String controllerUrlFull;

    public String getControllerAccessKey() {
        return "";
    }

    public void setControllerAccessKey(String controllerAccessKey) {
        this.controllerAccessKey = controllerAccessKey;
    }

    public String getControllerAccount() {
        return controllerAccount;
    }

    public void setControllerAccount(String controllerAccount) {
        this.controllerAccount = controllerAccount;
    }

    public String getControllerHostName() {
        return controllerHostName;
    }

    public void setControllerHostName(String controllerHostName) {
        this.controllerHostName = controllerHostName;
    }

    public Integer getControllerPort() {
        return controllerPort;
    }

    public void setControllerPort(Integer controllerPort) {
        this.controllerPort = controllerPort;
    }

    public Boolean getControllerSslEnabled() {
        return controllerSslEnabled;
    }

    public void setControllerSslEnabled(Boolean controllerSslEnabled) {
        this.controllerSslEnabled = controllerSslEnabled;
    }

    public String getControllerUrlFull() {
        return controllerUrlFull;
    }

    public void setControllerUrlFull(String controllerUrlFull) {
        this.controllerUrlFull = controllerUrlFull;
    }


}