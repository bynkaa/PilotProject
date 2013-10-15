package com.example.PilotProject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 10:47 AM
 */
public class HomeFragment extends FragmentActivity
{
    private ListView lvFeeds;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        lvFeeds = (ListView) findViewById(R.id.lvFeeds);
        ArrayFeedAdapter arrayFeedAdapter = new ArrayFeedAdapter(this, getModel());
        lvFeeds.setAdapter(arrayFeedAdapter);
    }

    private List<Feed> getModel()
    {
        List<Feed> feeds = new ArrayList<Feed>();
        feeds.add(getFeed());
        feeds.add(getFeed());
        feeds.add(getFeed());
        return feeds;
    }

    public Feed getFeed()
    {
        Feed feed = new Feed();
        feed.setTitle("Sound of Silence");
        feed.setComposer("Mr. Bean");
        feed.setLikeNumber(100);
        feed.setCommentNumber(9);
        feed.setUpdateStatus("5 days ago");
        return feed;
    }
}