package com.invest.pro.application.property.info;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SessionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 5/2/13
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrimitiveDataTypePropertyInfo<T> extends PropertyInfo<T> {

    public PrimitiveDataTypePropertyInfo(Class<T> clazz, String name) {
        super(clazz, name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void checkAndMerge(Object source, Object target, SessionFactory sessionFactory) throws Exception {
        T value = (T) PropertyUtils.getSimpleProperty(source, getName());
        if (value != null) {
            PropertyUtils.setProperty(target, getName(), value);
        }
    }
}
