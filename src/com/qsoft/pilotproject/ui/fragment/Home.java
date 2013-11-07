package com.qsoft.pilotproject.ui.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.support.v4.app.Fragment;
import android.widget.Button;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 4:19 PM
 */
@EFragment(R.layout.home)
public class Home extends Fragment
{
    private static final String TAG = "Home";
    @ViewById(R.id.btMenu)
    Button btMenu;
    @ViewById(R.id.btNotification)
    Button btNotification;
    @Bean
    ApplicationAccountManager applicationAccountManager;

    @AfterViews
    void afterViews()
    {
        Account account = applicationAccountManager.getAccount();
        ContentResolver.setIsSyncable(account, OnlineDioContract.CONTENT_AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, OnlineDioContract.CONTENT_AUTHORITY, true);
        ((SlideBarActivity) getActivity()).triggerSync();
        Fragment feedListFragment = new HomeListFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fragmentListFeeds, feedListFragment).addToBackStack(null).commit();

    }

    @Click(R.id.btNotification)
    void doClickNotification()
    {
        ((SlideBarActivity) getActivity()).triggerSync();
    }

    @Click(R.id.btMenu)
    void doClickMenu()
    {
        ((SlideBarActivity) getActivity()).setOpenOption();
    }
}
