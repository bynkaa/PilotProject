package com.qsoft.pilotproject.service;

import android.content.Context;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.data.dao.ProfilesDAO;
import com.qsoft.pilotproject.data.model.entity.ProfileCC;
import com.qsoft.pilotproject.data.rest.InterceptorDecoratorFactory;
import com.qsoft.pilotproject.ui.model.UiProfileModel;

/**
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:03 PM
 */
@EBean
public class ProfileService
{

    @RootContext
    Context context;
    @Bean
    ProfilesDAO profilesDAO;
    @Bean
    ApplicationAccountManager applicationAccountManager;
    @Bean
    InterceptorDecoratorFactory interceptorDecoratorFactory;


    public void updateMyProfile(UiProfileModel profileModel)
    {
        ProfileCC profileCC = (ProfileCC) profilesDAO.get(applicationAccountManager.getUserId());
        Utilities.copyProperties(profileCC, profileModel);
        profilesDAO.updateProfile(profileCC, applicationAccountManager.getUserId());

    }

    public ProfileCC getProfile()
    {
        return (ProfileCC) profilesDAO.get(applicationAccountManager.getUserId());
    }
}
