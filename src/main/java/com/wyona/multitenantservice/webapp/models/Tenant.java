package com.wyona.multitenantservice.webapp.models;

/**
 *
 */
public class Tenant {

    private String name;

    /**
     *
     */
    public Tenant() {
    }

    public Tenant(String name) {
        this.name = name;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }
}
