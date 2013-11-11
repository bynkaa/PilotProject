package com.qsoft.pilotproject.handler;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.qsoft.pilotproject.model.dto.ResponseDTO;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 9:37 AM
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest?", converters = {MappingJacksonHttpMessageConverter.class})
public interface FeedRestClient
{
    @Get("limit={limit}&offset={offset}&time_from={time_from}&time_to={time_to}")
    ResponseDTO getFeeds(String limit, String offset, String time_from, String time_to);

    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);
}
