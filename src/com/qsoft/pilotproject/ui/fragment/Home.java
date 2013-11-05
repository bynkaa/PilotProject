package com.qsoft.pilotproject.ui.fragment;

import android.accounts.Account;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.j256.ormlite.dao.EagerForeignCollection;
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

    @AfterViews
    void afterViews()
    {
        Account account = ((SlideBarActivity) getActivity()).getAccount();
        ContentResolver.setIsSyncable(account, OnlineDioContract.CONTENT_AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, OnlineDioContract.CONTENT_AUTHORITY, true);
        ((SlideBarActivity) getActivity()).triggerSync();
        Fragment feedListFragment = new HomeListFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmentListFeeds, feedListFragment).commit();

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
