package com.appdynamics.cloud.modern.config;

import java.io.Serializable;

public class UriPath implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6542721865532166180L;
	private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
