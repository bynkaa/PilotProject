package com.qsoft.pilotproject.data.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Put;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.pilotproject.data.model.dto.ResponseProfile;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/23/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/", converters = {
        GsonHttpMessageConverter.class,
        FormHttpMessageConverter.class,
        StringHttpMessageConverter.class,
        ResourceHttpMessageConverter.class})
public interface ProfileRestClient
{
    @Get("user-rest/{userId}")
    @Accept(MediaType.APPLICATION_JSON)
    public ResponseProfile get(long userId);

    @Put("user-rest/{userId}")
    public void update(Object profile, Long userId);

    public RestTemplate getRestTemplate();
}
