package com.invest.pro.application.main;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Module;
import com.invest.pro.application.configuration.ProSettings;
import com.invest.pro.application.guice.bundle.ProAutoConfig;
import com.invest.pro.application.guice.bundle.ProGuiceBundle;
import com.invest.pro.application.guice.module.ProModule;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 8/16/2015
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvestmentProMain extends AbstractMain<ProSettings>{

    public static void main(String [] args) throws Exception{
        new InvestmentProMain().run(args);
    }

    @Override
    public Class<ProSettings> autoConfigClass() {
        return ProSettings.class;
    }

    @Override
    public ProGuiceBundle<ProSettings> getGuiceBundle() {
        return new ProGuiceBundle<ProSettings>(new ProAutoConfig(packagesForAutoConfig()), modulesToConfigure(), Optional.of(autoConfigClass()));
    }

    @Override
    public String[] packagesForAutoConfig() {
        return new String[]{
               // "com.invest.pro.application.exception",
                "com.invest.pro.application.repository",
                "com.invest.pro.application.resources"
        };
    }

    @Override
    public List<Module> modulesToConfigure() {
        return Lists.newArrayList(
                (Module) new ProModule(packagesForAutoConfig())
        );
    }


}
