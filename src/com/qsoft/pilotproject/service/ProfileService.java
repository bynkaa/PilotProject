package com.qsoft.pilotproject.service;

import android.content.Context;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.data.dao.ProfileDAO;
import com.qsoft.pilotproject.data.model.dto.ProfileDTO;
import com.qsoft.pilotproject.data.model.entity.ProfileCC;
import com.qsoft.pilotproject.data.rest.OnlineDioClientProxy;
import com.qsoft.pilotproject.ui.model.UiProfileModel;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class ProfileService
{

    @RootContext
    Context context;
    @Bean
    ProfileDAO profileDAO;
    @Bean
    ApplicationAccountManager applicationAccountManager;
    @Bean
    OnlineDioClientProxy onlineDioClientProxy;


    public void updateMyProfile(UiProfileModel profileModel)
    {
        ProfileCC profileCC = profileDAO.getProfile(applicationAccountManager.getUserId());
        profileCC.setBirthday(profileModel.getBirthDay());
        profileCC.setDescription(profileModel.getDescription());
        profileCC.setDisplayName(profileModel.getDisplayName());
        profileDAO.updateProfile(profileCC, applicationAccountManager.getUserId());
    }

    public ProfileCC getProfile()
    {
        ProfileDTO profileDTO = onlineDioClientProxy.getProfile(applicationAccountManager.getUserId());
        ProfileCC profileCC = new ProfileCC(profileDTO);
        // insert or update profile
        ProfileCC profile = profileDAO.getProfile(applicationAccountManager.getUserId());
        if (profile == null)
        {
            profileDAO.insertProfile(profileCC);
        }
        return profileCC;
    }
}
