package com.invest.pro.application.rule;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.invest.pro.application.main.InvestmentProConfiguration;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.ServerCommand;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.lifecycle.ServerLifecycleListener;
import com.yammer.dropwizard.migrations.DbCommand;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.server.Server;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/23/2015
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProServiceRule<C extends InvestmentProConfiguration> implements TestRule {

    private static final Logger _logger = LoggerFactory.getLogger(ProServiceRule.class);

    private Class<C> configurationClass;
    private String configPath;
    private Server jettyServer;
    private C configuration;
    private final Class<? extends Service<C>> serviceClass;
    private Service<C> service;
    private Environment environment;

    public ProServiceRule(Class<? extends Service<C>> serviceClass, Class<C> configurationClass, String configPath) {
        this.configurationClass = configurationClass;
        this.configPath = configPath;
        this.serviceClass = serviceClass;
    }

    public String genTestURL(String path) {
        return String.format("http://localhost:%d%s%s",
                getLocalPort(),
                getRootPath(),
                path);
    }

    public C getConfiguration() {
        return configuration;
    }

    public int getLocalPort() {
        return jettyServer.getConnectors()[0].getLocalPort();
    }

    private String getRootPath() {
        String path = getConfiguration().getHttpConfiguration().getRootPath();
        if (StringUtils.endsWith(path, "/*")) {
            path = StringUtils.substring(path, 0, path.indexOf("/*"));
        }
        return path;
    }

    @Override
    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                _logger.info("Cleaning Database ....");
                cleanDatabase();
                _logger.info("Starting Jetty .... ");
                startIfRequired();
                /*if (createInitialUser) {
                    _logger.info("Creating User ... ");
                    setUpUser();
                }*/
                try {
                    statement.evaluate();
                } finally {
                    jettyServer.stop();
                    jettyServer = null;
                    _logger.info("Stopped Jetty .... ");
                }
            }
        };
    }

    private void cleanDatabase() {
        try {
            service = serviceClass.newInstance();

            final Bootstrap<C> bootstrap = new Bootstrap<C>(service) {
                @Override
                public void runWithBundles(C configuration, Environment environment) throws Exception {
                    environment.addServerLifecycleListener(new ServerLifecycleListener() {
                        @Override
                        public void serverStarted(Server server) {
                            jettyServer = server;
                        }
                    });
                    ProServiceRule.this.configuration = configuration;
                    ProServiceRule.this.environment = environment;
                    super.runWithBundles(configuration, environment);
                }
            };

            service.initialize(bootstrap);
            final DbCommand<C> dbCommand = new DbCommand<C>(new MigrationsBundle<C>() {
                @Override
                public DatabaseConfiguration getDatabaseConfiguration(C configuration) {
                    return configuration.getDatabase();
                }
            }, configurationClass);
            final Namespace dbnamespace = new Namespace(ImmutableMap.<String, Object>of("--confirm-delete-everything", "", "command", "db", "subcommand", "drop-all", "file", configPath));
            dbCommand.run(bootstrap, dbnamespace);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void startIfRequired() {
        if (jettyServer != null) {
            return;
        }

        try {
            service = serviceClass.newInstance();

            final Bootstrap<C> bootstrap = new Bootstrap<C>(service) {
                @Override
                public void runWithBundles(C configuration, Environment environment) throws Exception {
                    environment.addServerLifecycleListener(new ServerLifecycleListener() {
                        @Override
                        public void serverStarted(Server server) {
                            jettyServer = server;
                        }
                    });
                    ProServiceRule.this.configuration = configuration;
                    ProServiceRule.this.environment = environment;
                    super.runWithBundles(configuration, environment);
                }
            };

            service.initialize(bootstrap);
            final DbCommand<C> dbCommand = new DbCommand<C>(new MigrationsBundle<C>() {
                @Override
                public DatabaseConfiguration getDatabaseConfiguration(C configuration) {
                    return configuration.getDatabase();
                }
            }, configurationClass);
            final Namespace dbnamespace = new Namespace(ImmutableMap.<String, Object>of("dry-run", Boolean.FALSE, "command", "db", "subcommand", "migrate", "file", configPath, "contexts", Lists.newArrayList("test")));
            dbCommand.run(bootstrap, dbnamespace);


            final ServerCommand<C> command = new ServerCommand<C>(service);
            final Namespace namespace = new Namespace(ImmutableMap.<String, Object>of("file", configPath));
            command.run(bootstrap, namespace);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
