package com.qsoft.pilotproject.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.pilotproject.imageloader.ImageLoader;
import com.qsoft.pilotproject.model.Feed;
import com.qsoft.pilotproject.utils.Utilities;

/**
 * User: binhtv
 * Date: 11/12/13
 * Time: 3:10 PM
 */
@EViewGroup(R.layout.feed)
public class FeedItemView extends LinearLayout
{
    @ViewById(R.id.tvTitleFeed)
    TextView tvTitle;
    @ViewById(R.id.tvDisplayNameFeed)
    TextView tvDisplayName;
    @ViewById(R.id.tvLikeFeed)
    TextView tvLike;
    @ViewById(R.id.tvCommentFeed)
    TextView tvComment;
    @ViewById(R.id.tvLastUpdateStatus)
    TextView tvLastUpdate;
    @ViewById(R.id.imAvatarFeed)
    ImageView imProfile;
    @Bean
    ImageLoader imageLoader;

    public FeedItemView(Context context)
    {
        super(context);
    }

    public void bind(Feed feed)
    {
        tvTitle.setText(feed.getTitle());
        tvDisplayName.setText(feed.getDisplayName());
        tvLike.setText(feed.getLikes());
        tvComment.setText(feed.getComments());
        tvLastUpdate.setText(Utilities.calculatorUpdateTime(feed.getUpdatedAt()));
        imageLoader.DisplayImage(feed.getAvatar(), imProfile, R.drawable.image_icon);
    }

}
