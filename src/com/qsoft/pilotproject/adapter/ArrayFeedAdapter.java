package com.qsoft.pilotproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.imageloader.ImageLoader;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.utils.Utilities;

/**
 * User: binhtv
 * Date: 10/15/13
 * Time: 8:35 AM
 */
public class ArrayFeedAdapter extends SimpleCursorAdapter
{

    ImageLoader imageLoader;

    public ArrayFeedAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
    {
        super(context, layout, c, from, to);
        imageLoader = new ImageLoader(context);
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
        int avatarIndex = cursor.getColumnIndexOrThrow(OnlineDioContract.Feed.COLUMN_AVATAR);
        String avatarUrl = cursor.getString(avatarIndex);
        imageLoader.DisplayImage(avatarUrl, imProfile, R.drawable.image_icon);
        int titleIndex = cursor.getColumnIndexOrThrow(OnlineDioContract.Feed.COLUMN_TITLE);
        tvTitle.setText(cursor.getString(titleIndex));
        int displayNameIndex = cursor.getColumnIndexOrThrow(OnlineDioContract.Feed.COLUMN_DISPLAY_NAME);
        tvDisplayName.setText(cursor.getString(displayNameIndex));
        int likeIndex = cursor.getColumnIndexOrThrow(OnlineDioContract.Feed.COLUMN_LIKES);
        tvLike.setText("Like: " + cursor.getInt(likeIndex));
        int commentIndex = cursor.getColumnIndexOrThrow(OnlineDioContract.Feed.COLUMN_COMMENTS);
        tvComment.setText("Comment: " + cursor.getInt(commentIndex));
        int lastUpdateIndex = cursor.getColumnIndexOrThrow(OnlineDioContract.Feed.COLUMN_UPDATED_AT);
        String updateTime = cursor.getString(lastUpdateIndex);
        tvLastUpdate.setText(Utilities.calculatorUpdateTime(updateTime));
        int idIndex = cursor.getColumnIndexOrThrow(OnlineDioContract.Feed._ID);
        Log.e("BYNKAA", "id: " + cursor.getInt(idIndex));

    }

}
