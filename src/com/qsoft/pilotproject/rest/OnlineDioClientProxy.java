package com.qsoft.pilotproject.rest;

import android.app.Activity;
import android.util.Log;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.common.commands.GenericStartActivityCommand;
import com.qsoft.pilotproject.model.ListFeed;
import com.qsoft.pilotproject.model.dto.ProfileDTO;
import com.qsoft.pilotproject.rest.interceptor.OnlineDioInterceptor;
import com.qsoft.pilotproject.ui.activity.LaunchActivity_;
import com.qsoft.pilotproject.ui.activity.StartActivity;
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
    SimpleCheckRestClient simpleCheckRestClient;

    @RestService
    OnlineDioRestClient onlineDioRestClient;
    @Bean
    OnlineDioInterceptor onlineDioInterceptor;
    @RootContext
    Activity activity;
    @Bean
    CommandExecutor commandExecutor;

    @AfterInject
    void init()
    {
        RestTemplate restTemplate = onlineDioRestClient.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(onlineDioInterceptor);
        restTemplate.setInterceptors(interceptors);

        restTemplate = simpleCheckRestClient.getRestTemplate();
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

    @Background
    public void check()
    {
        String returnValue = simpleCheckRestClient.check();
        if (!returnValue.equals("cannot access my apis"))
        {
            Log.d("Ops:", returnValue);
            commandExecutor.execute(activity,
                    new GenericStartActivityCommand(activity, LaunchActivity_.class, StartActivity.RC_LAUCH_ACTIVITY), false);
        }
    }
}
