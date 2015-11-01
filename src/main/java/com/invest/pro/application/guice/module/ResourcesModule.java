package com.invest.pro.application.guice.module;

import com.google.inject.AbstractModule;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.ws.rs.Path;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/27/2015
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourcesModule extends AbstractModule {

    private Reflections reflections;

    public ResourcesModule(String[] basePackages) {
        ConfigurationBuilder cfgBldr = new ConfigurationBuilder();
        FilterBuilder filterBuilder = new FilterBuilder();
        for (String basePkg : basePackages) {
            cfgBldr.addUrls(ClasspathHelper.forPackage(basePkg));
            filterBuilder.include(FilterBuilder.prefix(basePkg));
        }

        cfgBldr.filterInputsBy(filterBuilder).setScanners(
                new SubTypesScanner(), new TypeAnnotationsScanner());
        this.reflections = new Reflections(cfgBldr);
    }

    @Override
    protected void configure() {
        Set<Class<?>> resourceClasses = reflections
                .getTypesAnnotatedWith(Path.class);
        for (Class<?> resource : resourceClasses) {
            bind(resource);
            //LOGGER.info("Added resource class: " + resource);
        }
    }
}
