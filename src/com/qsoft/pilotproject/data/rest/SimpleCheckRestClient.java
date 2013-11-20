package com.qsoft.pilotproject.data.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 9:37 AM
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/", converters = {
        ResourceHttpMessageConverter.class,
        StringHttpMessageConverter.class})
public interface SimpleCheckRestClient {
    @Get("home-rest?limit=1")
    @Accept(MediaType.ALL)
    String check();

    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);
}
