package com.invest.pro.application.property;

import com.invest.pro.application.property.info.PropertyInfo;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PropertiesHolder<T> {

    Set<PropertyInfo> getPropertiesInfoToBeMerged();
}
