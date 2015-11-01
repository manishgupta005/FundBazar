package com.invest.pro.application.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.Maps;
import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.invest.pro.application.configuration.ProSettings;
import com.invest.pro.application.guice.bundle.ProGuiceBundle;
import com.invest.pro.application.model.master.MAmc;
import com.invest.pro.application.model.master.MBankName;
import com.invest.pro.application.utils.Utils;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import com.yammer.dropwizard.json.GuavaExtrasModule;
import com.yammer.dropwizard.migrations.MigrationsBundle;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public abstract class AbstractMain<T extends ProSettings> extends Service<T> {
    private final HibernateBundle<T> hibernateBundle = new HibernateBundle<T>(MAmc.class, MBankName.class) {
        @Override
        public DatabaseConfiguration getDatabaseConfiguration(T t) {
            return t.getDatabase();
        }
    };

    @Override
    public void initialize(Bootstrap<T> bootstrap) {
        bootstrap.setName("InvestmentPro");
        bootstrap.getObjectMapperFactory().registerModule(new GuavaModule());
        bootstrap.getObjectMapperFactory().registerModule(new GuavaExtrasModule());
        bootstrap.getObjectMapperFactory().registerModule(new JodaModule());
        bootstrap.getObjectMapperFactory().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        bootstrap.getObjectMapperFactory().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        bootstrap.getObjectMapperFactory().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        bootstrap.getObjectMapperFactory().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Map<Class<?>, Class<?>> mixins = Maps.newHashMap();
        mixins.put(XMLGregorianCalendar.class, XMLGregorianCalendarMixIn.class);
        bootstrap.getObjectMapperFactory().setMixinAnnotations(mixins);

//        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
        bootstrap.addBundle(new MigrationsBundle<T>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(T configuration) {
                return configuration.getDatabase();
            }
        });

        bootstrap.addBundle(hibernateBundle);

        bootstrap.addBundle(getGuiceBundle());
    }

    @Override
    public void run(T t, Environment environment) throws Exception {

      //  environment.setJerseyProperty(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES, RequestContextInitializerFactory.class.getCanonicalName());
        // ----- Custom Validation Exception Mapper Start
        // ----- The below section is for adding custom validation exception mapper ----
        // ----- This is a custom hack and will not be required when migrating to DW 0.7.0
        Object invalidEntityExceptionMapper = null;
        for (Object obj : environment.getJerseyResourceConfig().getSingletons()) {
            if ("com.yammer.dropwizard.jersey.InvalidEntityExceptionMapper".equals(obj.getClass().getCanonicalName())) {
                invalidEntityExceptionMapper = obj;
            }
        }
        environment.getJerseyResourceConfig().getSingletons().remove(invalidEntityExceptionMapper);
        // ----- Custom Validation Exception Mapper Ends

        Utils.setSessionFactory(getHibernateBundle().getSessionFactory());
    }

    private void configureModules(GuiceBundle.Builder<T> builder) {
        for (Module module : modulesToConfigure()) {
            builder.addModule(module);
        }
    }

    public abstract String[] packagesForAutoConfig();

    public abstract List<Module> modulesToConfigure();

    public HibernateBundle getHibernateBundle() {
        return hibernateBundle;
    }

    public abstract Class<T> autoConfigClass();

    public abstract ProGuiceBundle<T> getGuiceBundle();

    interface XMLGregorianCalendarMixIn {
        @JsonIgnore
        void setYear(BigInteger year);
    }
}
