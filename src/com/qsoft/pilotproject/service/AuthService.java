package com.qsoft.pilotproject.service;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import com.qsoft.pilotproject.data.rest.AuthRestClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/25/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class AuthService
{
    @RestService
    AuthRestClient authRestClient;

    public SignInDTO signIn(String userName, String password)
    {
        Map<String, String> mapData = new HashMap<String, String>();
        mapData.put("username", userName);
        mapData.put("password", password);
        mapData.put("grant_type", "password");
        mapData.put("client_id", "123456");
        return authRestClient.signIn(mapData);
    }

}
