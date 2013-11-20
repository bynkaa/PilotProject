package com.qsoft.pilotproject.ui.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.commands.GenericStartActivityCommand;
import com.qsoft.pilotproject.ui.adapter.SideBarItemAdapter;
import com.qsoft.pilotproject.ui.fragment.Home_;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 10:47 AM
 */
@EActivity(R.layout.slidebar)
public class SlideBarActivity extends FragmentActivity {
    @SystemService
    AccountManager accountManager;

    private static final String TAG = "SlideBarActivity";

    public static final int RC_PROFILE_SETUP_ACTIVITY = 0;
    public static final int RC_COMMENT_FRAGMENT = 1;

    public static final String[] SIDE_BAR_ITEMS = new String[]{"Home", "Favorite", "Following", "Audience",
            "Genres", "Setting", "Help Center", "Sign Out"};
    public static final Integer[] SIDE_BAR_ICONS = new Integer[]{
            R.drawable.sidebar_imageicon_home,
            R.drawable.sidebar_image_icon_favorite,
            R.drawable.sidebar_image_icon_following,
            R.drawable.sidebar_image_icon_audience,
            R.drawable.sidebar_image_icon_genres,
            R.drawable.sidebar_image_icon_setting,
            R.drawable.sidebar_image_icon_helpcenter,
            R.drawable.sidebar_image_icon_logout
    };

    @ViewById(R.id.lvSlideBar)
    ListView lvSlideBar;

    @ViewById(R.id.left_drawer_home)
    View leftDrawerView;

    @ViewById(R.id.drawer_layout)
    DrawerLayout dlSlideBar;

    @ViewById(R.id.ibMyStation)
    ImageButton ibMyStation;

    @Bean
    ApplicationAccountManager applicationAccountManager;

    @Bean
    CommandExecutor commandExecutor;

    @AfterViews
    void afterViews() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_fragment);

        if (currentFragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_fragment, new Home_()).addToBackStack(null).commit();
        }

        setListViewSlideBar();
    }

    @Click(R.id.ibMyStation)
    void onClickMyStation(View view) {
        Log.d(TAG, "profile setup");
        commandExecutor.execute(this,
                new GenericStartActivityCommand(this, ProfileSetupActivity_.class, RC_PROFILE_SETUP_ACTIVITY), false);
    }

    @OnActivityResult(RC_PROFILE_SETUP_ACTIVITY)
    void onProfileResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // do something here

        }
        setOpenOption();
    }

    @OnActivityResult(RC_COMMENT_FRAGMENT)
    void onCommentResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data.hasExtra(NewCommentActivity.COMMENT_EXTRA)) {
//                Comment comment = (Comment) data.getExtras().get(NewCommentActivity.COMMENT_EXTRA);
            }
        }
    }

    @ItemClick(R.id.lvSlideBar)
    void doItemClick() {
        // on item click
    }

    public void setListViewSlideBar() {
        SideBarItemAdapter sideBarItemAdapter = new SideBarItemAdapter(this, R.layout.menu, SIDE_BAR_ITEMS);
        lvSlideBar.setAdapter(sideBarItemAdapter);
    }

    public void setOpenOption() {
        dlSlideBar.openDrawer(leftDrawerView);

    }

    public void setCloseOption() {
        dlSlideBar.closeDrawer(leftDrawerView);
    }

    private boolean lastBack = false;

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (lastBack) {
                finish();
            }
            Toast toast = Toast.makeText(this, "Press Back again to exit program", Toast.LENGTH_LONG);
            toast.show();
            lastBack = true;
        }
    }
}