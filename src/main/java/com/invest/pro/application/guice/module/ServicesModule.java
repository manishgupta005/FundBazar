package com.invest.pro.application.guice.module;

import com.google.inject.AbstractModule;
import com.invest.pro.application.service.mutualfund.MutualFundService;
import com.invest.pro.application.service.mutualfund.impl.MutualFundServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/27/2015
 * Time: 11:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MutualFundService.class).to(MutualFundServiceImpl.class);
    }
}
