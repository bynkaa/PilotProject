package com.qsoft.pilotproject.Service;

import com.qsoft.pilotproject.model.SignInDTO;
import com.qsoft.pilotproject.model.User;

/**
 * User: binhtv
 * Date: 10/28/13
 * Time: 11:32 AM
 */
public interface OnlineDioService {
    public SignInDTO signIn(String userName, String pass,String authTokenType) throws Exception;
}
