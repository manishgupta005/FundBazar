package com.invest.pro.application.model.internal;

import com.invest.pro.application.property.PropertiesHolder;
import org.hibernate.SessionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */

public interface MergeSupport<T> {

    void merge(MergeSupport<T> updated, SessionFactory em);

    PropertiesHolder<T> getPropertyHolder();

    void load(MergeSupport<T> entity, SessionFactory em);
}
