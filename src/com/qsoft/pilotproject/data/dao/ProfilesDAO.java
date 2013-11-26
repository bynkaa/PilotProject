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
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:20 PM
 */
@EBean
public class ProfilesDAO implements IDao<ProfileCC>
{

    @RootContext
    Context context;
    ContentResolver contentResolver;
    @Bean
    SyncToServiceDAO syncToServiceDAO;

    public ProfilesDAO(Context context)
    {
        this.context = context;
        afterInject();
    }

    @AfterInject
    void afterInject()
    {
        contentResolver = context.getContentResolver();
    }

    public void updateProfile(ProfileCC profile, Long userId)
    {
        update(profile, userId);
        Cursor cursor = contentResolver.query(ProfileCCContract.CONTENT_URI, new String[]{ProfileCCContract.USERID},
                ProfileCCContract.USERID + "=?", new String[]{userId.toString()}, null);
        Long id = null;
        if (cursor.moveToFirst())
        {
            id = cursor.getLong(0);
        }
        SyncToServer syncToServer = new SyncToServer();
        syncToServer.setTableName("profiles");
        syncToServer.setRecordId(id);
        syncToServer.setSerial(0);
        syncToServer.setPriority(1);
        syncToServer.setStatus("not sync");
        syncToServer.setAction("update");
        syncToServiceDAO.insertOrUpdateToSync(syncToServer);

    }

    public ProfileCC get(Long userId)
    {
        Cursor cursor = contentResolver.query(ProfileCCContract.CONTENT_URI, null, ProfileCCContract.USERID + "=?", new String[]{userId.toString()}, null);
        if (cursor.moveToFirst())
        {
            return ProfileCC.fromCursor(cursor);
        }
        return null;
    }

    public void insertProfile(ProfileCC profileCC)
    {
        contentResolver.insert(ProfileCCContract.CONTENT_URI, profileCC.getContentValues());
    }

    @Override
    public void update(ProfileCC profile, Long id)
    {
        contentResolver.update(ProfileCCContract.CONTENT_URI, profile.getContentValues(),
                ProfileCCContract.USERID + "=?", new String[]{id.toString()});
    }


}
