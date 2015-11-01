package com.invest.pro.application.configuration;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.jasypt.properties.PropertyValueEncryptionUtils;

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 7/8/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class EncryptedDatabaseSettings extends DatabaseConfiguration {

    @Override
    public String getUrl() {
        if (PropertyValueEncryptionUtils.isEncryptedValue(super.getUrl())) {
            return PropertyValueEncryptionUtils.decrypt(super.getUrl(), ApplicationProperties.getEncryptor());
        }
        return super.getUrl();
    }

    @Override
    public String getPassword() {
        if (PropertyValueEncryptionUtils.isEncryptedValue(super.getPassword())) {
            return PropertyValueEncryptionUtils.decrypt(super.getPassword(), ApplicationProperties.getEncryptor());
        }
        return super.getPassword();
    }

    @Override
    public String getUser() {
        if (PropertyValueEncryptionUtils.isEncryptedValue(super.getUser())) {
            return PropertyValueEncryptionUtils.decrypt(super.getUser(), ApplicationProperties.getEncryptor());
        }
        return super.getUser();
    }
}
