package com.qsoft.pilotproject.data.rest;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import com.qsoft.pilotproject.data.rest.interceptor.OnlineDioInterceptor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 8:27 AM
 */
@EBean(scope = Scope.Singleton)
public class InterceptorDecoratorFactory
{
    private static final String ERROR_MESSAGE = "cannot access my apis";
    @Bean
    OnlineDioInterceptor onlineDioInterceptor;

    public IRest wrap(IRest iRest)
    {
        RestTemplate restTemplate = iRest.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(onlineDioInterceptor);
        restTemplate.setInterceptors(interceptors);
        return iRest;
    }


}
