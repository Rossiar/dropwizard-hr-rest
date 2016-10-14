package com.hr;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Provides an interface onto the .yml file that is provided to configure the application.
 */
public class HumanResourcesConfiguration extends Configuration {

    private int pageSize = 1000;

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();


    @JsonProperty("page-size")
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @JsonProperty("page-size")
    public int getPageSize() {
        return pageSize;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

}
