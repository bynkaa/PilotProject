package com.qsoft.pilotproject.authenticator;

import com.qsoft.pilotproject.Service.Impl.OnlineDioServiceImpl;
import com.qsoft.pilotproject.Service.OnlineDioService;

/**
 * Created with IntelliJ IDEA.
 * User: binhtv
 * Date: 10/30/13
 * Time: 8:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccountGeneral {
    public static final String ACCOUNT_TYPE = "com.qsoft.OnlineDio";
    public static final String ACCOUNT_NAME = "Online Dio";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "full access";
    public static final OnlineDioService onlineDioService = new OnlineDioServiceImpl();

}
