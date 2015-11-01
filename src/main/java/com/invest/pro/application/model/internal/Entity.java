package com.invest.pro.application.model.internal;

import com.invest.pro.application.enums.ActionType;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Entity<T extends Serializable, S extends Entity> {

        T getId();

        void beforeAdd();

        void beforeUpdate();

        ActionType getAction();

        void init();

        }
