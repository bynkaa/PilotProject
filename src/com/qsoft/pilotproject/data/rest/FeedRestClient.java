package com.qsoft.pilotproject.data.rest;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.pilotproject.data.model.dto.ResponseListFeed;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * User: Qsoft
 * Date: 11/23/13
 * Time: 3:39 PM
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/", converters = {
        GsonHttpMessageConverter.class,
        FormHttpMessageConverter.class,
        StringHttpMessageConverter.class,
        ResourceHttpMessageConverter.class})
public interface FeedRestClient
{
    @Get("home-rest?limit={limit}&offset={offset}&time_from={timeFrom}&time_to={timeTo}")
    @Accept(MediaType.APPLICATION_JSON)
    ResponseListFeed getFeeds(String limit, String offset, String timeFrom, String timeTo);

    public RestTemplate getRestTemplate();
}
