package com.qsoft.pilotproject.service;

import android.database.Cursor;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.data.model.entity.FeedCCContract;
import com.qsoft.pilotproject.ui.model.UiFeedModel;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedService {

    public UiFeedModel getModel(Cursor cursor) {
        //get data
        int titleIndex = cursor.getColumnIndexOrThrow(FeedCCContract.TITLE);
        int displayNameIndex = cursor.getColumnIndexOrThrow(FeedCCContract.DISPLAYNAME);
        int likeIndex = cursor.getColumnIndexOrThrow(FeedCCContract.LIKES);
        int commentIndex = cursor.getColumnIndexOrThrow(FeedCCContract.COMMENTS);
        int lastUpdateIndex = cursor.getColumnIndexOrThrow(FeedCCContract.UPDATEDAT);
        int idIndex = cursor.getColumnIndexOrThrow(FeedCCContract._ID);
        String title = cursor.getString(titleIndex);
        String disPlayName = cursor.getString(displayNameIndex);
        String like = cursor.getString(likeIndex);
        String comment = cursor.getString(commentIndex);
        String lastUpdate = Utilities.calculatorUpdateTime(cursor.getString(lastUpdateIndex));
        return new UiFeedModel(title, disPlayName, like, comment, lastUpdate);

    }
}
