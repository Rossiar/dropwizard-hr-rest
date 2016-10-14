package com.hr.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hendersonra on 10/10/16.
 */
public class User {
    private String name;
    private String apiKey;

    @JsonCreator
    public User(@JsonProperty("name") String name, @JsonProperty("apiKey") String apiKey) {
        this.name = name;
        this.apiKey = apiKey;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getApiKey() {
        return apiKey;
    }
}
