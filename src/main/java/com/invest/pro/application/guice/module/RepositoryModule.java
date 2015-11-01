package com.invest.pro.application.guice.module;

import com.google.inject.AbstractModule;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/28/2015
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryModule extends AbstractModule {


    @Override
    protected void configure() {
      // bind(MutualFundRepository.class).toProvider(new JpaRepositoryModule<MutualFundRepository>());
        //bind(MutualFundRepository.class);
    }

}
