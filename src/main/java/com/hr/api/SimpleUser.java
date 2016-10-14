package com.hr.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hendersonra on 10/10/16.
 */
public class SimpleUser {
    private String name;

    @JsonCreator
    public SimpleUser(@JsonProperty("name") String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

}
