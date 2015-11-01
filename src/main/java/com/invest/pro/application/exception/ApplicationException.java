package com.invest.pro.application.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable e) {
        super(message, e);
    }
}
