package com.qsoft.pilotproject.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.adapter.SideBarItemAdapter;
import com.qsoft.pilotproject.model.Comment;
import com.qsoft.pilotproject.provider.CommentDataSource;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.ui.fragment.CommentFragment;
import com.qsoft.pilotproject.ui.fragment.Home;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 10:47 AM
 */
public class SlideBarActivity extends FragmentActivity
{
    private Account account;
    private String authToken;
    private AccountManager accountManager;
    private CommentDataSource commentDataSource;
    private static final String TAG = "SlideBarActivity";
    public static final int REQUEST_CODE = 0;
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


    private ListView lvSlideBar;
    private View leftDrawerView;
    private DrawerLayout dlSlideBar;
    private ImageButton ibMyStation;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);
        accountManager = AccountManager.get(getApplicationContext());
        account = getIntent().getParcelableExtra(StartActivity.ACCOUNT_KEY);
        commentDataSource = new CommentDataSource(this);
        commentDataSource.open();
        dlSlideBar = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvSlideBar = (ListView) findViewById(R.id.lvSlideBar);
        leftDrawerView = findViewById(R.id.left_drawer_home);
        setListViewSlideBar();
        lvSlideBar.setOnItemClickListener(itemSideBarClickListner);
        Fragment homeFragment = new Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, homeFragment).commit();
        ibMyStation = (ImageButton) findViewById(R.id.ibMyStation);
        ibMyStation.setOnClickListener(ibMyStationOnClickListener);
    }

    View.OnClickListener ibMyStationOnClickListener = new View.OnClickListener()
    {


        @Override
        public void onClick(View view)
        {
            Log.d(TAG, "profile setup");
            Intent intent = new Intent(SlideBarActivity.this, ProfileSetupActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                // do something here

            }
            setOpenOption();
        }
        if (resultCode == Activity.RESULT_OK && requestCode == CommentFragment.REQUEST_CODE)
        {
            if (data.hasExtra(NewCommentActivity.COMMENT_EXTRA))
            {
                Comment comment = (Comment) data.getExtras().get(NewCommentActivity.COMMENT_EXTRA);
                commentDataSource.createComment(comment);
            }
        }
    }

    AdapterView.OnItemClickListener itemSideBarClickListner = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            // on item click
        }
    };

    public void setListViewSlideBar()
    {
        SideBarItemAdapter sideBarItemAdapter = new SideBarItemAdapter(this, R.layout.menu, SIDE_BAR_ITEMS);
        lvSlideBar.setAdapter(sideBarItemAdapter);
    }

    public void setOpenOption()
    {
        dlSlideBar.openDrawer(leftDrawerView);

    }

    public void setCloseOption()
    {
        dlSlideBar.closeDrawer(leftDrawerView);
    }

    public Account getAccount()
    {
        return account;
    }

    public void triggerSync()
    {
        Log.d(TAG, "TriggerSync > account");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
        ContentResolver.requestSync(account, OnlineDioContract.CONTENT_AUTHORITY, bundle);
        getContentResolver().notifyChange(OnlineDioContract.Feed.CONTENT_URI, null, false);
    }

}