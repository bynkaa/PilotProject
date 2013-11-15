package com.qsoft.pilotproject.provider.cc;

import com.qsoft.pilotproject.common.helper.GenericDatabaseHelper;
import com.qsoft.pilotproject.model.cc.CommentCC;
import com.qsoft.pilotproject.model.cc.CommentCCContract;
import com.qsoft.pilotproject.model.cc.FeedCC;
import com.qsoft.pilotproject.model.cc.FeedCCContract;
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
        );
        return true;
    }
}