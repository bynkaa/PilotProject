package com.example.PilotProject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private ListView lvSlideBar;
    private View drawerView;
    private DrawerLayout dlSlideBar;
    Button btMenu;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        lvFeeds = (ListView) findViewById(R.id.lvFeeds);
        ArrayFeedAdapter arrayFeedAdapter = new ArrayFeedAdapter(this, getModel());
        lvFeeds.setAdapter(arrayFeedAdapter);
        dlSlideBar = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvSlideBar = (ListView) findViewById(R.id.lvSlideBar);
        drawerView = findViewById(R.id.left_drawer);
        setListViewSlideBar();
        btMenu = (Button) findViewById(R.id.btMenu);
        btMenu.setOnClickListener(btMenuClickListener);

    }

    public View.OnClickListener btMenuClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            dlSlideBar.openDrawer(drawerView);
        }
    };

    public void setListViewSlideBar()
    {
        String[] items = new String[]{"My Station", "Home", "Favorite", "Setting"};
        lvSlideBar.setAdapter(new ArrayAdapter<String>(this, R.layout.list_menu, items));
    }

    private List<Feed> getModel()
    {
        List<Feed> feeds = new ArrayList<Feed>();
        for (int i = 0; i < 20; i++)
        {
            feeds.add(getFeed());
        }
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