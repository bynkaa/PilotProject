package com.qsoft.pilotproject.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.service.FeedService;
import com.qsoft.pilotproject.ui.model.UiFeedModel;

/**
 * User: binhtv
 * Date: 10/15/13
 * Time: 8:35 AM
 */
public class ArrayFeedAdapter extends SimpleCursorAdapter {

//    ImageLoader imageLoader;

    public ArrayFeedAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
//        imageLoader = new ImageLoader(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //super.bindView(view, context, cursor);    //To change body of overridden methods use File | Settings | File Templates.

        //change data
        FeedService feedService = new FeedService();
        UiFeedModel uiFeedModel = feedService.getModel(cursor);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitleFeed);
        TextView tvDisplayName = (TextView) view.findViewById(R.id.tvDisplayNameFeed);
        TextView tvLike = (TextView) view.findViewById(R.id.tvLikeFeed);
        TextView tvComment = (TextView) view.findViewById(R.id.tvCommentFeed);
        TextView tvLastUpdate = (TextView) view.findViewById(R.id.tvLastUpdateStatus);
        ImageView imProfile = (ImageView) view.findViewById(R.id.imAvatarFeed);
        //binding UIModel
        tvTitle.setText(uiFeedModel.getTitle());
        tvDisplayName.setText(uiFeedModel.getDisplayName());
        tvLike.setText("Like: " + uiFeedModel.getLike());
        tvComment.setText("Comment: " + uiFeedModel.getComment());
        tvLastUpdate.setText(uiFeedModel.getUpdateTime());
    }

}
