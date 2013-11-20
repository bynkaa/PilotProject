package com.qsoft.pilotproject.common.authenticator;

import com.qsoft.pilotproject.handler.AuthenticatorHandler;
import com.qsoft.pilotproject.handler.impl.AuthenticatorHandlerImpl;

/**
 * User: binhtv
 * Date: 10/30/13
 * Time: 8:38 AM
 */
public class AccountGeneral {
    public static final String INVALIDTOKEN_MESSAGE = "cannot access my apis";
    public static final String ACCOUNT_TYPE = "com.qsoft.onlinedio";
    public static final String ACCOUNT_NAME = "Online Dio";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "full access";
    public static final AuthenticatorHandler onlineDioService = new AuthenticatorHandlerImpl();

}
