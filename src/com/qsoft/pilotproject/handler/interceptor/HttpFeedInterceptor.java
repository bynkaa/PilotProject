package com.qsoft.pilotproject.handler.interceptor;

import android.accounts.AccountManager;
import android.util.Log;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.api.Scope;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * User: binhtv
 * Date: 11/11/13
 * Time: 9:50 AM
 */
@EBean(scope = Scope.Singleton)
public class HttpFeedInterceptor implements ClientHttpRequestInterceptor
{

    private static final String TAG = "HttpFeedInterceptor";
    @Bean
    ApplicationAccountManager applicationAccountManager;
    @SystemService
    AccountManager accountManager;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException
    {
        String authToken = accountManager.peekAuthToken(applicationAccountManager.getAccount(), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
        httpRequest.getHeaders().add("Authorization", "Bearer " + authToken);
        httpRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        HttpStatus status = response.getStatusCode();
        if (status.value() == 200)
        {
            Log.d(TAG, "success code 200");
        }
        return response;
    }
}
