package com.invest.pro.application.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.invest.pro.application.configuration.EncryptedDatabaseSettings;
import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 8/16/2015
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class InvestmentProConfiguration extends Configuration {


    @Valid
    @NotNull
    @JsonProperty("databaseInvestPro") // This property will read parameters from .yml files
    private EncryptedDatabaseSettings database = new EncryptedDatabaseSettings();

    public EncryptedDatabaseSettings getDatabase() {
        return database;
    }

    public void setDatabase(EncryptedDatabaseSettings database) {
        this.database = database;
    }
}
