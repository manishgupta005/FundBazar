package com.invest.pro.application.guice.bundle;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.util.Modules;
import com.hubspot.dropwizard.guice.DropwizardEnvironmentModule;
import com.hubspot.dropwizard.guice.GuiceContainer;
import com.hubspot.dropwizard.guice.JerseyContainerModule;
import com.invest.pro.application.utils.Utils;
import com.sun.jersey.guice.JerseyServletModule;
import com.yammer.dropwizard.ConfiguredBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rahul
 * Date: 8/20/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProGuiceBundle<T extends Configuration> implements ConfiguredBundle<T> {

    final Logger logger = LoggerFactory.getLogger(ProGuiceBundle.class);
    private final ProAutoConfig autoConfig;
    private final List<Module> modules;
    private Injector injector;
    private JerseyContainerModule jerseyContainerModule;
    private DropwizardEnvironmentModule dropwizardEnvironmentModule;
    private Optional<Class<T>> configurationClass;
    private GuiceContainer container;

    public ProGuiceBundle(ProAutoConfig autoConfig, List<Module> modules, Optional<Class<T>> configurationClass) {
        Preconditions.checkNotNull(modules);
        Preconditions.checkArgument(!modules.isEmpty());
        this.modules = modules;
        this.autoConfig = autoConfig;
        this.configurationClass = configurationClass;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        container = new GuiceContainer();
        jerseyContainerModule = new JerseyContainerModule(container);
        if (configurationClass.isPresent()) {
            dropwizardEnvironmentModule = new DropwizardEnvironmentModule<T>(configurationClass.get());
        } else {
            dropwizardEnvironmentModule = new DropwizardEnvironmentModule<Configuration>(Configuration.class);
        }
        modules.add(Modules.override(new JerseyServletModule()).with(jerseyContainerModule));
        modules.add(dropwizardEnvironmentModule);
        /**
         * Commenting code for Injector creation. As we don't have any bundle
         *
         * injector = Guice.createInjector(modules);
         if (autoConfig != null) {
         autoConfig.initialize(bootstrap, injector);
         }*/
    }

    @Override
    public void run(final T configuration, final Environment environment) {
        //modules.add(createJPAModule(configuration));
        injector = Guice.createInjector(modules);
        Utils.setInjector(injector);
        container.setResourceConfig(environment.getJerseyResourceConfig());
        environment.setJerseyServletContainer(container);
        //environment.addFilter(injector.getInstance(PersistFilter.class), configuration.getHttpConfiguration().getRootPath());
        environment.addFilter(GuiceFilter.class, configuration.getHttpConfiguration().getRootPath());
        setEnvironment(configuration, environment);

        if (autoConfig != null) {
            autoConfig.run(environment, injector);
        }
    }

    //@SuppressWarnings("unchecked")
    private void setEnvironment(final T configuration, final Environment environment) {
        dropwizardEnvironmentModule.setEnvironmentData(configuration, environment);
    }

    public Injector getInjector() {
        return injector;
    }
}
