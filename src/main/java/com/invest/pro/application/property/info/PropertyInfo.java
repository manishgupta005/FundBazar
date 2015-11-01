package com.invest.pro.application.property.info;

import org.hibernate.SessionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PropertyInfo<T> {

    private final Class<T> clazz;
    private final String name;

    public PropertyInfo(Class<T> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public abstract void checkAndMerge(Object source, Object target, SessionFactory sessionFactory) throws Exception;

    public void load(Object source, SessionFactory sessionFactory) throws Exception {
        // EMPTY
    }
}
