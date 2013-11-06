package com.qsoft.pilotproject.handler;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.qsoft.pilotproject.authenticator.InvalidTokenException;
import com.qsoft.pilotproject.model.dto.FeedDTO;

import java.util.List;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 5:42 PM
 */
public interface FeedHandler
{
    public List<FeedDTO> getFeeds(AccountManager accountManager, Account account) throws InvalidTokenException;
}
