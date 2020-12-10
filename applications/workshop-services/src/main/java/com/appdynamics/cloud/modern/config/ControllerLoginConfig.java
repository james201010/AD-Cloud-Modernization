package com.appdynamics.cloud.modern.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ControllerLoginConfig {


	
    @SerializedName("controller-account")
    @Expose
    private String controllerAccount;
    
    @SerializedName("controller-host-name")
    @Expose
    private String controllerHostName;

    @SerializedName("controller-pass")
    @Expose
    private String controllerPass;

    @SerializedName("controller-port")
    @Expose
    private Integer controllerPort;

    @SerializedName("controller-url-full")
    @Expose
    private String controllerUrlFull;
    
    @SerializedName("controller-username")
    @Expose
    private String controllerUsername;

    

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

    public String getControllerPass() {
        return controllerPass;
    }

    public void setControllerPass(String controllerPass) {
        this.controllerPass = controllerPass;
    }

    public Integer getControllerPort() {
        return controllerPort;
    }

    public void setControllerPort(Integer controllerPort) {
        this.controllerPort = controllerPort;
    }

    public String getControllerUrlFull() {
        return controllerUrlFull;
    }

    public void setControllerUrlFull(String controllerUrlFull) {
        this.controllerUrlFull = controllerUrlFull;
    }

    public String getControllerUsername() {
        return controllerUsername;
    }

    public void setControllerUsername(String controllerUsername) {
        this.controllerUsername = controllerUsername;
    }

}