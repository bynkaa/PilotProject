package com.qsoft.pilotproject.data.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * User: binhtv
 * Date: 10/29/13
 * Time: 3:18 PM
 */
public class SignInDTO implements Serializable
{
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("expires")
    private String expires;
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getExpires()
    {
        return expires;
    }

    public void setExpires(String expires)
    {
        this.expires = expires;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }
}
