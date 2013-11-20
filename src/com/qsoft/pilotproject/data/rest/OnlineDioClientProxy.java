package com.qsoft.pilotproject.data.rest;

import android.app.Activity;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.data.model.dto.ProfileDTO;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import com.qsoft.pilotproject.data.model.entity.CommentCC;
import com.qsoft.pilotproject.data.model.entity.FeedCC;
import com.qsoft.pilotproject.data.rest.interceptor.OnlineDioInterceptor;
import com.qsoft.pilotproject.ui.controller.CommonController;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 8:27 AM
 */
@EBean
public class OnlineDioClientProxy {
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
    void init() {
        RestTemplate restTemplate = onlineDioRestClient.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(onlineDioInterceptor);
        restTemplate.setInterceptors(interceptors);

        restTemplate = simpleCheckRestClient.getRestTemplate();
        restTemplate.setInterceptors(interceptors);
        tokenCheckerRest.getRestTemplate().setInterceptors(interceptors);
    }

    public List<FeedCC> getFeeds(String limit, String offset, String timeFrom, String timeTo) {
        invalidToken();
        return onlineDioRestClient.getFeeds(limit, offset, timeFrom, timeTo).getFeedDTOs();
    }


    public ProfileDTO getProfile(long userId) {
        invalidToken();
        return onlineDioRestClient.getProfile(userId).getProfile();
    }

    public void updateProfile(HashMap profile, Long userId) {
        invalidToken();
        onlineDioRestClient.updateProfile(profile, userId);
    }

    public List<CommentCC> getComments(long soundId, String limit, String offset, String updateAt) {
        invalidToken();
        return onlineDioRestClient.getComments(soundId, limit, offset, updateAt).getComments();
    }

    private void invalidToken() {
        String checkToken = tokenCheckerRest.getAbout();
        if (checkToken.equals(ERROR_MESSAGE)) {
            commonController.refreshToken();
        }
    }

    public SignInDTO signIn(String name, String password) {
        MultiValueMap data = new LinkedMultiValueMap();
        data.add("username", name);
        data.add("password", password);
        data.add("grant_type", "password");
        data.add("client_id", "123456789");
        data.add("type", "password");
        data.add("email", "123456789");
        return onlineDioRestClient.signIn(data);
    }

}
