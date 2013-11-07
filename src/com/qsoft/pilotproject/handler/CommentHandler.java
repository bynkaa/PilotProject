package com.qsoft.pilotproject.handler;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.qsoft.pilotproject.model.dto.CommentDTO;

import java.util.List;

/**
 * User: binhtv
 * Date: 11/7/13
 * Time: 4:53 PM
 */
public interface CommentHandler
{
    public List<CommentDTO> getListComments(AccountManager accountManager, Account account, Long userId);


}
