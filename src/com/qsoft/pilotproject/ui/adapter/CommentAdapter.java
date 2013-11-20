package com.qsoft.pilotproject.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;

/**
 * User: BinkaA
 * Date: 10/18/13
 * Time: 2:06 AM
 */
public class CommentAdapter extends SimpleCursorAdapter
{

//    ImageLoader imageLoader;

    public CommentAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
    {
        super(context, layout, c, from, to);
//        imageLoader = new ImageLoader(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        super.bindView(view, context, cursor);    //To change body of overridden methods use File | Settings | File Templates.
//        TextView tvCommentTitle = (TextView) view.findViewById(R.id.tvCommentTitle);
//        TextView tvCommentContent = (TextView) view.findViewById(R.id.tvCommentContent);
//        TextView tvCommentTimeCreated = (TextView) view.findViewById(R.id.tvCommentTimeCreate);
//        ImageView ivCommentAvatar = (ImageView) view.findViewById(R.id.ivCommentIcon);
//        int titleIndex = cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_DISPLAY_NAME);
//        int contentIndex = cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_CONTENT);
//        int timeCreatedIndex = cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_CREATED_AT);
//        int avatarIndex = cursor.getColumnIndex(OnlineDioContract.Comment.COLUMN_AVATAR);
//        tvCommentTitle.setText(cursor.getString(titleIndex));
//        tvCommentContent.setText(cursor.getString(contentIndex));
//        tvCommentTimeCreated.setText(Utilities.calculatorUpdateTime(cursor.getString(timeCreatedIndex)));
//        imageLoader.DisplayImage(cursor.getString(avatarIndex), ivCommentAvatar, R.drawable.content_icon_comment);
    }
}
