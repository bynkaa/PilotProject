package com.qsoft.pilotproject.handler;

import com.qsoft.pilotproject.data.model.dto.SignInDTO;

/**
 * User: binhtv
 * Date: 10/28/13
 * Time: 11:32 AM
 */
public interface AuthenticatorHandler
{
    public SignInDTO signIn(String userName, String pass, String authTokenType) throws Exception;
}
