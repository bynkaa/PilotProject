package com.qsoft.pilotproject.handler;

import android.accounts.AccountManager;
import com.qsoft.pilotproject.model.dto.ProfileDTO;

/**
 * User: BinkaA
 * Date: 11/4/13
 * Time: 10:41 PM
 */
public interface ProfileHandler
{
    public ProfileDTO getProfile(long userId);
}
