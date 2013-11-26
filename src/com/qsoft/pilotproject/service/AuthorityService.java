package com.qsoft.pilotproject.service;

import android.content.Context;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import com.qsoft.pilotproject.data.rest.AuthorityRestClient;

import java.util.HashMap;
import java.util.Map;

/**
 * User: binhtv
 * Date: 11/25/13
 * Time: 2:16 PM
 */
@EBean
public class AuthorityService
{
    @RootContext
    Context context;
    @RestService
    AuthorityRestClient authorityRestClient;

    public SignInDTO signIn(String email, String password)
    {
        Map<String, String> mapData = new HashMap<String, String>();
        mapData.put("username", email);
        mapData.put("password", password);
        mapData.put("grant_type", "password");
        mapData.put("client_id", "123456");
        mapData.put("type", "");
        mapData.put("email", "");
        return authorityRestClient.signIn(mapData);
    }
}
