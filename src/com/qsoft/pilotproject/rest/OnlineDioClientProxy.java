package com.qsoft.pilotproject.rest;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.pilotproject.model.ListFeed;
import com.qsoft.pilotproject.model.dto.ProfileDTO;
import com.qsoft.pilotproject.rest.interceptor.OnlineDioInterceptor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 8:27 AM
 */
@EBean
public class OnlineDioClientProxy
{
    @RestService
    OnlineDioRestClient onlineDioRestClient;
    @Bean
    OnlineDioInterceptor onlineDioInterceptor;

    @AfterInject
    void init()
    {
        RestTemplate restTemplate = onlineDioRestClient.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(onlineDioInterceptor);
        restTemplate.setInterceptors(interceptors);
    }

    public ListFeed getFeeds(String limit, String offset, String timeFrom, String timeTo)
    {
        return onlineDioRestClient.getFeeds(limit, offset, timeFrom, timeTo);
    }

    public ProfileDTO getProfile(long userId)
    {
        return onlineDioRestClient.getProfile(userId).getProfileDTO();

    }

}
