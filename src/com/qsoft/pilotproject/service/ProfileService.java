package com.qsoft.pilotproject.service;

import android.content.Context;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.data.dao.ProfilesDAO;
import com.qsoft.pilotproject.data.model.dto.ProfileDTO;
import com.qsoft.pilotproject.data.model.entity.ProfileCC;
import com.qsoft.pilotproject.data.rest.InterceptorDecoratorFactory;
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
    ProfilesDAO profilesDAO;
    @Bean
    ApplicationAccountManager applicationAccountManager;
    @Bean
    InterceptorDecoratorFactory interceptorDecoratorFactory;


    public void updateMyProfile(UiProfileModel profileModel)
    {
        ProfileCC profileCC = (ProfileCC) profilesDAO.get(applicationAccountManager.getUserId());
        profileCC.setBirthday(profileModel.getBirthDay());
        profileCC.setDescription(profileModel.getDescription());
        profileCC.setDisplayName(profileModel.getDisplayName());
        profilesDAO.updateProfile(profileCC, applicationAccountManager.getUserId());

    }

    public ProfileCC getProfile()
    {
        //todo
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setPhone("123444");
        profileDTO.setFullName("aaa");
        profileDTO.setBirthday("1999-01-02");
        profileDTO.setCountryId("SA");
        profileDTO.setUserId(545l);
        profileDTO.setDescription("aabbda");
        profileDTO.setGender(1);
        profileDTO.setDisplayName("no");

        ProfileCC profileCC = new ProfileCC();
        Utilities.copyProperties(profileCC, profileDTO);
        // insert or update profile
        ProfileCC profile = (ProfileCC) profilesDAO.get(applicationAccountManager.getUserId());
        if (profile == null)
        {
            profilesDAO.insertProfile(profileCC);
        }
        return profileCC;
    }
}
