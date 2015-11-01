package com.invest.pro.application.test.main.resources;


public class AuthenticatedBaseResourceTest extends AbstractBaseResourceTest {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean createInitialUser() {
        return true;
    }
}
