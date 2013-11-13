package com.qsoft.pilotproject.ui.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.support.v4.app.Fragment;
import android.widget.Button;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.rest.OnlineDioClientProxy;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity;
import com.qsoft.pilotproject.ui.controller.CommonController;

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

    @Bean
    CommonController commonController;
    @Bean
    OnlineDioClientProxy onlineDioClientProxy;

    @AfterViews
    void afterViews()
    {
        Account account = applicationAccountManager.getAccount();
        ContentResolver.setIsSyncable(account, OnlineDioContract.CONTENT_AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, OnlineDioContract.CONTENT_AUTHORITY, true);
        commonController.triggerSync();
        Fragment feedListFragment = new HomeListFragment_();
        getChildFragmentManager().beginTransaction().replace(R.id.fragmentListFeeds, feedListFragment).addToBackStack(null).commit();
//        doBackground();

    }

//    @Background
//    void doBackground()
//    {
//        ResponseListFeed listFeed = onlineDioClientProxy.getFeeds("", "", "", "");
//        Log.d(TAG, "size" + listFeed.getFeedDTOs().size());
//
//    }

    @Click(R.id.btNotification)
    void doClickNotification()
    {
        commonController.triggerSync();
    }

    @Click(R.id.btMenu)
    void doClickMenu()
    {
        ((SlideBarActivity) getActivity()).setOpenOption();
    }
}
