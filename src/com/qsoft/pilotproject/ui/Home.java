package com.qsoft.pilotproject.ui;

import android.accounts.Account;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.model.Feed;
import com.qsoft.pilotproject.provider.OnlineDioContract;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 4:19 PM
 */
public class Home extends Fragment
{
    Button btMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, container, false);
        btMenu = (Button) view.findViewById(R.id.btMenu);
        btMenu.setOnClickListener(btMenuClickListener);
        Fragment feedListFragment = new HomeListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        Account account = ((SlideBarActivity) getActivity()).getAccount();
        ContentResolver.requestSync(account, OnlineDioContract.CONTENT_AUTHORITY, bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentListFeeds, feedListFragment).commit();
        return view;
    }

    AdapterView.OnItemClickListener feedClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_fragment, new ProgramFragment());
            fragmentTransaction.commit();
        }
    };
    public View.OnClickListener btMenuClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ((SlideBarActivity) getActivity()).setOpenOption();
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
