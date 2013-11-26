package com.qsoft.pilotproject.data.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.Map;

/**
 * User: binhtv
 * Date: 11/25/13
 * Time: 2:03 PM
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/", converters = {
        GsonHttpMessageConverter.class,
        FormHttpMessageConverter.class,
        StringHttpMessageConverter.class,
        ResourceHttpMessageConverter.class})
public interface AuthorityRestClient
{
    @Post("auth-rest")
    @Accept(MediaType.APPLICATION_JSON)
    public SignInDTO signIn(Map data);
}
