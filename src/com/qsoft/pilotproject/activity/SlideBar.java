package com.qsoft.pilotproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.adapter.SideBarItemAdapter;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 10:47 AM
 */
public class SlideBar extends FragmentActivity
{
    public static final String[] SIDE_BAR_ITEMS = new String[]{"HomeFragment", "Favorite", "Following", "Audience",
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
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton ibMyStation;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidebar);

        dlSlideBar = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvSlideBar = (ListView) findViewById(R.id.lvSlideBar);
        leftDrawerView = findViewById(R.id.left_drawer_home);
        setListViewSlideBar();
        lvSlideBar.setOnItemClickListener(itemSideBarClickListner);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                dlSlideBar,         /* DrawerLayout object */
                R.drawable.ic_launcher,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */);

        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, homeFragment).commit();
        dlSlideBar.setDrawerListener(actionBarDrawerToggle);
        ibMyStation = (ImageButton) findViewById(R.id.ibMyStation);

        ibMyStation.setOnClickListener(ibMyStationOnClickListener);
    }

    View.OnClickListener ibMyStationOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(SlideBar.this,ProfileSetupFragment.class);
            startActivity(intent);
        }
    };

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
}