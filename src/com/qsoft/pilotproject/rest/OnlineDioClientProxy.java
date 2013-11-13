package com.qsoft.pilotproject.rest;

import android.app.Activity;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.model.dto.CommentDTO;
import com.qsoft.pilotproject.model.dto.FeedDTO;
import com.qsoft.pilotproject.model.dto.ProfileDTO;
import com.qsoft.pilotproject.rest.interceptor.OnlineDioInterceptor;
import com.qsoft.pilotproject.ui.controller.CommonController;
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
    private static final String ERROR_MESSAGE = "cannot access my apis";
    @RestService
    SimpleCheckRestClient simpleCheckRestClient;

    @RestService
    OnlineDioRestClient onlineDioRestClient;
    @RestService
    TokenCheckerRest tokenCheckerRest;
    @Bean
    OnlineDioInterceptor onlineDioInterceptor;
    @Bean
    CommonController commonController;
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
        tokenCheckerRest.getRestTemplate().setInterceptors(interceptors);
    }

    public List<FeedDTO> getFeeds(String limit, String offset, String timeFrom, String timeTo)
    {
        String checkToken = tokenCheckerRest.getAbout();
        if (checkToken.equals(ERROR_MESSAGE))
        {
            commonController.refreshToken();
        }
        return onlineDioRestClient.getFeeds(limit, offset, timeFrom, timeTo).getFeedDTOs();
    }

    public ProfileDTO getProfile(long userId)
    {
        String checkToken = tokenCheckerRest.getAbout();
        if (checkToken.equals(ERROR_MESSAGE))
        {
            commonController.refreshToken();
        }
        return onlineDioRestClient.getProfile(userId).getProfileDTO();
    }

    public List<CommentDTO> getComments(long soundId, String limit, String offset, String updateAt)
    {
        String checkToken = tokenCheckerRest.getAbout();
        if (checkToken.equals(ERROR_MESSAGE))
        {
            commonController.refreshToken();
        }
        return onlineDioRestClient.getComments(soundId, limit, offset, updateAt).getComments();
    }

}
