package com.qsoft.pilotproject.data.rest;

import com.googlecode.androidannotations.annotations.rest.*;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.qsoft.pilotproject.data.model.dto.ResponseComment;
import com.qsoft.pilotproject.data.model.dto.ResponseListFeed;
import com.qsoft.pilotproject.data.model.dto.ResponseProfile;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 9:37 AM
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/", converters = {
        MappingJackson2HttpMessageConverter.class,
        FormHttpMessageConverter.class,
        GsonHttpMessageConverter.class,
        StringHttpMessageConverter.class,
        ResourceHttpMessageConverter.class})
public interface OnlineDioRestClient {
    @Get("home-rest?limit=1")
    @Accept(MediaType.ALL)
    String check();

    @Get("home-rest?limit={limit}&offset={offset}&time_from={timeFrom}&time_to={timeTo}")
    @Accept(MediaType.APPLICATION_JSON)
    ResponseListFeed getFeeds(String limit, String offset, String timeFrom, String timeTo);

    @Get("user-rest/{userId}")
    @Accept(MediaType.APPLICATION_JSON)
    public ResponseProfile getProfile(long userId);

    @Get("comment-rest?sound_id={soundId}&limit={limit}&offset={offset}&updated_at={updateAt}")
    @Accept(MediaType.APPLICATION_JSON)
    public ResponseComment getComments(long soundId, String limit, String offset, String updateAt);

    @Post("auth-rest")
    @Accept(MediaType.APPLICATION_JSON)
    public SignInDTO signIn(MultiValueMap data);

    @Put("user-rest/{userId}")
    public void updateProfile(HashMap profile, Long userId);

    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);
}
