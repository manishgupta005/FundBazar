package com.invest.pro.application.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/25/2015
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProModule extends AbstractModule {
    private final String[] basePackages;

    public ProModule(String[] basePackages) {
        this.basePackages = basePackages;
    }


    @Override
    protected void configure() {

        //install(new RepositoryModule());
        install(new ServicesModule());
        install(new ResourcesModule(basePackages));
       // install(new ScanningJpaRepositoryModule("com.invest.pro.application.repository", "my-persistence-unit"));

    }

    @Provides
    @Singleton
    public SessionFactory sessionFactory() {
        return null;
    }
}
