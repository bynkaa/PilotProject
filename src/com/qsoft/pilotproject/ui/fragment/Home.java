package com.qsoft.pilotproject.ui.fragment;

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
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 4:19 PM
 */
public class Home extends Fragment
{
    private static final String TAG = "Home";
    Button btMenu;
    Button btNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home, container, false);
        Account account = ((SlideBarActivity) getActivity()).getAccount();
        ContentResolver.setIsSyncable(account, OnlineDioContract.CONTENT_AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, OnlineDioContract.CONTENT_AUTHORITY, true);
        btMenu = (Button) view.findViewById(R.id.btMenu);
        btMenu.setOnClickListener(btMenuClickListener);
        btNotification = (Button) view.findViewById(R.id.btNotification);
        btNotification.setOnClickListener(btNotificationClickListener);
        ((SlideBarActivity) getActivity()).triggerSync();
        Fragment refreshFragment = new ProgressBarFragment();
        Fragment feedListFragment = new HomeListFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmentListFeeds, feedListFragment).commit();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        getFragmentManager().beginTransaction().replace(R.id.fragmentListFeeds, feedListFragment).commit();
        return view;
    }

    View.OnClickListener btNotificationClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ((SlideBarActivity) getActivity()).triggerSync();
        }
    };

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

//    private List<Feed> getModel()
//    {
//        List<Feed> feeds = new ArrayList<Feed>();
//        for (int i = 0; i < 20; i++)
//        {
//            feeds.add(getFeed());
//        }
//        return feeds;
//    }

//    public Feed getFeed()
//    {
//        Feed feed = new Feed();
//        feed.setTitle("Sound of Silence");
//        feed.setComposer("Mr. Bean");
//        feed.setLikeNumber(100);
//        feed.setCommentNumber(9);
//        feed.setUpdateStatus("5 days ago");
//        return feed;
//    }


}
