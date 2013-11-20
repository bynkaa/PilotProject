package com.qsoft.pilotproject.data.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.qsoft.pilotproject.data.model.entity.ProfileCC;
import com.qsoft.pilotproject.data.model.entity.ProfileCCContract;
import com.qsoft.pilotproject.data.model.entity.SyncToServer;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class ProfileDAO
{

    @RootContext
    Context context;
    ContentResolver contentResolver;
    @Bean
    SyncToServiceDAO syncToServiceDAO;

    @AfterInject
    void afterInject()
    {
        contentResolver = context.getContentResolver();
    }

    public void updateProfile(ProfileCC profile, Long userId)
    {

        contentResolver.update(ProfileCCContract.CONTENT_URI, profile.getContentValues(),
                ProfileCCContract.USERID + "=?", new String[]{userId.toString()});

        Long id = profile.getId();
        SyncToServer syncToServer = new SyncToServer();
        syncToServer.setTableName("profiles");
        syncToServer.setRecordId(id);
        syncToServer.setGroup(0);
        syncToServer.setPriority(1);
        syncToServer.setStatus("not sync");
        syncToServer.setAction("update");
        syncToServiceDAO.insertToSync(syncToServer);

    }

    public ProfileCC getProfile(Long userId)
    {
        Cursor cursor = contentResolver.query(ProfileCCContract.CONTENT_URI, null, ProfileCCContract.USERID + "=?", new String[]{userId.toString()}, null);
        if (cursor.moveToFirst())
        {
            return ProfileCC.fromCursor(cursor);
        }
        return null;
    }
}
