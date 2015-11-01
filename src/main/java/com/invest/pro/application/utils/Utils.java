package com.invest.pro.application.utils;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.hibernate.SessionFactory;

public class Utils {
    private static Injector injector;
    private static SessionFactory sessionFactory;

    public static String getNamedProperty(String key) {
        return injector.getProvider(Key.get(String.class, Names.named(key))).get();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        Utils.sessionFactory = sessionFactory;
    }

    public static Injector getInjector() {
        return injector;
    }

    public static void setInjector(Injector injector) {
        Utils.injector = injector;
    }
}
