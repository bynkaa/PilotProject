package com.qsoft.pilotproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.model.cc.FeedCCContract;
import com.qsoft.pilotproject.utils.Utilities;

/**
 * User: binhtv
 * Date: 10/15/13
 * Time: 8:35 AM
 */
public class ArrayFeedAdapter extends SimpleCursorAdapter
{

//    ImageLoader imageLoader;

    public ArrayFeedAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
    {
        super(context, layout, c, from, to);
//        imageLoader = new ImageLoader(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        super.bindView(view, context, cursor);    //To change body of overridden methods use File | Settings | File Templates.
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitleFeed);
        TextView tvDisplayName = (TextView) view.findViewById(R.id.tvDisplayNameFeed);
        TextView tvLike = (TextView) view.findViewById(R.id.tvLikeFeed);
        TextView tvComment = (TextView) view.findViewById(R.id.tvCommentFeed);
        TextView tvLastUpdate = (TextView) view.findViewById(R.id.tvLastUpdateStatus);
        ImageView imProfile = (ImageView) view.findViewById(R.id.imAvatarFeed);

        int titleIndex = cursor.getColumnIndexOrThrow(FeedCCContract.TITLE);
        int displayNameIndex = cursor.getColumnIndexOrThrow(FeedCCContract.DISPLAYNAME);
        int likeIndex = cursor.getColumnIndexOrThrow(FeedCCContract.LIKES);
        int commentIndex = cursor.getColumnIndexOrThrow(FeedCCContract.COMMENTS);
        int lastUpdateIndex = cursor.getColumnIndexOrThrow(FeedCCContract.UPDATEDAT);
        int idIndex = cursor.getColumnIndexOrThrow(FeedCCContract._ID);

        tvTitle.setText(cursor.getString(titleIndex));
        tvDisplayName.setText(cursor.getString(displayNameIndex));
        tvLike.setText("Like: " + cursor.getInt(likeIndex));
        tvComment.setText("Comment: " + cursor.getInt(commentIndex));
        String updateTime = cursor.getString(lastUpdateIndex);
        tvLastUpdate.setText(Utilities.calculatorUpdateTime(updateTime));
        Log.e("BYNKAA", "id: " + cursor.getInt(idIndex));

    }

}
