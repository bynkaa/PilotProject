package com.qsoft.pilotproject.data.provider;

import com.qsoft.pilotproject.common.helper.GenericDatabaseHelper;
import com.qsoft.pilotproject.data.model.entity.*;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd;

/**
 * User: Le
 * Date: 11/11/13
 */
public class CCContentProvider extends OrmLiteSimpleContentProvider<GenericDatabaseHelper>
{
    @Override
    protected Class<GenericDatabaseHelper> getHelperClass()
    {
        return GenericDatabaseHelper.class;
    }

    @Override
    public boolean onCreate()
    {
        setMatcherController(new MatcherController()
                .add(CommentCC.class, MimeTypeVnd.SubType.DIRECTORY, "", CommentCCContract.CONTENT_URI_PATTERN_MANY)
                .add(CommentCC.class, MimeTypeVnd.SubType.ITEM, "#", CommentCCContract.CONTENT_URI_PATTERN_ONE)
                .add(FeedCC.class, MimeTypeVnd.SubType.DIRECTORY, "", FeedCCContract.CONTENT_URI_PATTERN_MANY)
                .add(FeedCC.class, MimeTypeVnd.SubType.ITEM, "#", FeedCCContract.CONTENT_URI_PATTERN_ONE)
                .add(ProfileCC.class, MimeTypeVnd.SubType.DIRECTORY, "", ProfileCCContract.CONTENT_URI_PATTERN_MANY)
                .add(ProfileCC.class, MimeTypeVnd.SubType.ITEM, "#", ProfileCCContract.CONTENT_URI_PATTERN_ONE)
                .add(SyncToServer.class, MimeTypeVnd.SubType.DIRECTORY, "", SyncToServerContract.CONTENT_URI_PATTERN_MANY)
                .add(SyncToServer.class, MimeTypeVnd.SubType.ITEM, "#", SyncToServerContract.CONTENT_URI_PATTERN_ONE)
        );
        return true;
    }
}