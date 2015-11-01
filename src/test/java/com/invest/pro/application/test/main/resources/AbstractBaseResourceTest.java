package com.invest.pro.application.test.main.resources;

import com.invest.pro.application.configuration.ProSettings;
import com.invest.pro.application.rule.ProServiceRule;
import com.invest.pro.application.test.main.TestInvestmentProMain;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.junit.Rule;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/23/2015
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractBaseResourceTest {

    @Rule
    public final ProServiceRule RULE = new ProServiceRule(
            TestInvestmentProMain.class,
            ProSettings.class,
            "test-pro.yml");

    public abstract boolean createInitialUser();

    public Client createClient() {
        Client client = Client.create();
        client.addFilter(new LoggingFilter());
        return client;
    }
}
