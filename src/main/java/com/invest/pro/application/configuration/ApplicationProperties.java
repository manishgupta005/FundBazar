package com.invest.pro.application.configuration;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.invest.pro.application.utils.Utils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 7/8/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class ApplicationProperties {

    private static StringEncryptor encryptor;

    public static StringEncryptor getEncryptor() {
        if (encryptor == null) {
            StandardPBEStringEncryptor tempEncryptor = new StandardPBEStringEncryptor();
            tempEncryptor.setPassword("3r1-82c");
            tempEncryptor.setAlgorithm("PBEWithMD5AndDES");
            encryptor = tempEncryptor;
        }
        return encryptor;
    }


    public static String getNamedStringProperty(String property) {
        String value = Utils.getInjector().getInstance(Key.get(String.class, Names.named(property)));
        if (PropertyValueEncryptionUtils.isEncryptedValue(value)) {
            value = PropertyValueEncryptionUtils.decrypt(value, getEncryptor());
        }
        return value;
    }


    public static String getErrorMessageAsString(String errorCode) {
        return Utils.getInjector().getInstance(Key.get(String.class, Names.named(errorCode)));
    }

    public static Integer getNamedIntegerProperty(String property) {
        return Utils.getInjector().getInstance(Key.get(Integer.class, Names.named(property)));
    }

    public interface Constants {
        String ROOT_PATH = "rootPath";
        String COOKIE_NAME = "Pro-Auth";
    }
}
