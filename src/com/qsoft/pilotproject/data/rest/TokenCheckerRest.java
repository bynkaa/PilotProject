package com.qsoft.pilotproject.data.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 5:09 PM
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/", converters = {StringHttpMessageConverter.class})
public interface TokenCheckerRest {
    @Get("appconfig-rest")
    @Accept(MediaType.ALL)
    public String getAbout();

    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);
}
