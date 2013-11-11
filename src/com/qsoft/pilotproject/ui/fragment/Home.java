package com.qsoft.pilotproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.handler.FeedRestClient;
import com.qsoft.pilotproject.handler.interceptor.HttpFeedInterceptor;
import com.qsoft.pilotproject.model.ListFeed;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity;
import com.qsoft.pilotproject.ui.controller.CommonController;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
    @RestService
    FeedRestClient feedRestClient;
    @Bean
    HttpFeedInterceptor httpFeedInterceptor;

    @AfterViews
    void afterViews()
    {
//        Account account = applicationAccountManager.getAccount();
//        ContentResolver.setIsSyncable(account, OnlineDioContract.CONTENT_AUTHORITY, 1);
//        ContentResolver.setSyncAutomatically(account, OnlineDioContract.CONTENT_AUTHORITY, true);
//        commonController.triggerSync();
//        Fragment feedListFragment = new HomeListFragment();
//        getChildFragmentManager().beginTransaction().replace(R.id.fragmentListFeeds, feedListFragment).addToBackStack(null).commit();
        doBackground();

    }

    @Background
    void doBackground()
    {
        RestTemplate restTemplate = feedRestClient.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(httpFeedInterceptor);
        restTemplate.setInterceptors(interceptors);
        ListFeed listFeed = feedRestClient.getFeeds("", "", "", "");
        Log.d(TAG, "size" + listFeed.getFeedDTOs().size());

    }

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
