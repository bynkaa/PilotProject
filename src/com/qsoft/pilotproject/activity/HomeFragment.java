package com.qsoft.pilotproject.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.adapter.ArrayFeedAdapter;
import com.qsoft.pilotproject.model.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 4:19 PM
 */
public class HomeFragment extends Fragment
{
    Button btMenu;
    private ListView lvFeeds;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home,container,false);
        lvFeeds = (ListView) view.findViewById(R.id.lvFeeds);
        ArrayFeedAdapter arrayFeedAdapter = new ArrayFeedAdapter(getActivity(), getModel());
        lvFeeds.setAdapter(arrayFeedAdapter);
        lvFeeds.setOnItemClickListener(feedClickListener);
        btMenu = (Button) view.findViewById(R.id.btMenu);
        btMenu.setOnClickListener(btMenuClickListener);
        return view;
    }

    AdapterView.OnItemClickListener feedClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_fragment,new ProgramFragment());
            fragmentTransaction.commit();
        }
    };
    public View.OnClickListener btMenuClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ((SlideBar)getActivity()).setOpenOption();
        }
    };
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
