package com.qsoft.pilotproject.ui.fragment;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.SyncStatusObserver;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.widget.Button;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.data.provider.CCContract;
import com.qsoft.pilotproject.data.rest.OnlineDioClientProxy;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity;
import com.qsoft.pilotproject.ui.controller.CommonController;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 4:19 PM
 */
@EFragment(R.layout.home)
public class Home extends Fragment {
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
    private Object syncObserverHandler;
    Menu optionsMenu;

    ProgressDialog progressDialog;

    private SyncStatusObserver syncStatusObserver = new SyncStatusObserver() {
        @Override
        public void onStatusChanged(int i) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Account account = applicationAccountManager.getAccount();
                    if (account == null) {
                        //
                        return;
                    }
                    boolean syncActive = ContentResolver.isSyncActive(account, CCContract.AUTHORITY);
                    boolean syncPending = ContentResolver.isSyncPending(account, CCContract.AUTHORITY);
                    // set refresh
                    if (!(syncActive || syncPending)) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                }
            });
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        syncStatusObserver.onStatusChanged(0);
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING | ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        syncObserverHandler = ContentResolver.addStatusChangeListener(mask, syncStatusObserver);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (syncObserverHandler != null)

        {
            ContentResolver.removeStatusChangeListener(syncObserverHandler);
        }
        syncObserverHandler = null;
    }


    @AfterViews
    void afterViews() {
        Account account = applicationAccountManager.getAccount();
        ContentResolver.setIsSyncable(account, CCContract.AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, CCContract.AUTHORITY, true);
        Fragment feedListFragment = new HomeListFragment();
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
    void doClickNotification() {
        progressDialog = ProgressDialog.show(getActivity(), "Progress Dialog", "Loading...");
        commonController.triggerSync();
    }

    @Click(R.id.btMenu)
    void doClickMenu() {
        ((SlideBarActivity) getActivity()).setOpenOption();
    }

//    @Background
//    public void updateFeeds()
//    {
//        List<FeedCC> feedCCList = onlineDioClientProxy.getFeeds("","","","");
//        Log.d(TAG,"size: " + feedCCList.size());
//        for (FeedCC feedCC : feedCCList)
//        {
//            getActivity().getContentResolver().insert(FeedCCContract.CONTENT_URI,feedCC.getContentValues());
//        }
//    }
}
