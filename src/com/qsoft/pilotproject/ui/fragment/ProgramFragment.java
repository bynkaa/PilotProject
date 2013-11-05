package com.qsoft.pilotproject.ui.fragment;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.model.Feed;
import com.qsoft.pilotproject.model.ProgramTab;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity;
import com.qsoft.pilotproject.utils.Utilities;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 8:59 AM
 */
public class ProgramFragment extends Fragment
{

    private static final String TAG = "ProgramFragment";
    FragmentManager manager = getFragmentManager();
    private ImageButton ibProgramBack;

    ProgramTab currentTab = ProgramTab.THUMB_NAIL;
    private RadioGroup rgProgramTab;
    private TextView tvTitle;
    private TextView tvDisplayName;
    private TextView tvLikes;
    private TextView tvPlayed;
    private TextView tvLooks;
    private TextView tvUpdated;
    Feed feed = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program, container, false);

        rgProgramTab = (RadioGroup) view.findViewById(R.id.rgProgramTab);
        rgProgramTab.setOnCheckedChangeListener(programTabOnCheckChangeListener);
        rgProgramTab.check(R.id.rbThumbnail);
        ibProgramBack = (ImageButton) view.findViewById(R.id.ibProgramBack);

        ibProgramBack.setOnClickListener(ibProgramBackOnClickListener);
        tvTitle = (TextView) view.findViewById(R.id.tvProgramTitle);
        tvDisplayName = (TextView) view.findViewById(R.id.tvProgramDisplayNameFeed);
        tvLikes = (TextView) view.findViewById(R.id.tvContentLike);
        tvPlayed = (TextView) view.findViewById(R.id.tvContentPlay);
        tvLooks = (TextView) view.findViewById(R.id.tvContentLook);
        tvUpdated = (TextView) view.findViewById(R.id.tvLastUpdate);
        startContentPlayerFragment();
        final ContentResolver contentResolver = getActivity().getContentResolver();
        Bundle bundle = getArguments();
        Long id = bundle.getLong(HomeListFragment.FEED_ID);
        if (id == null)
        {
            Log.e(TAG, "ProgramFragment ERROR");
        }
        Uri singleUri = ContentUris.withAppendedId(OnlineDioContract.Feed.CONTENT_URI, id);
        // get all
        Cursor cursor = contentResolver.query(singleUri, null, null, null, null);
        assert cursor != null;

        while (cursor.moveToNext())
        {
            feed = Feed.fromCursor(cursor);
        }
        tvTitle.setText(feed.getTitle());
        tvDisplayName.setText(feed.getDisplayName());
        tvLikes.setText(Integer.toString(feed.getLikes()));
        tvLooks.setText(Integer.toString(feed.getViewed()));
        String played = feed.getPlayed();
        if (played == null)
        {
            played = 0 + "";
        }
        tvPlayed.setText(played);
        tvUpdated.setText(Utilities.calculatorUpdateTime(feed.getUpdatedAt()));
        cursor.close();
        return view;
    }


    View.OnClickListener ibProgramBackOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(getActivity(), SlideBarActivity.class);
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


    RadioGroup.OnCheckedChangeListener programTabOnCheckChangeListener = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i)
        {
            int checkedRbTab = rgProgramTab.getCheckedRadioButtonId();
            switch (checkedRbTab)
            {
                case R.id.rbThumbnail:
                    currentTab = ProgramTab.THUMB_NAIL;
                    break;
                case R.id.rbDetail:
                    currentTab = ProgramTab.DETAIL;
                    break;
                case R.id.rbComment:
                    currentTab = ProgramTab.COMMENT;
                    break;
            }
            updateProgramFragment();
        }
    };

    private void startContentPlayerFragment()
    {
        Fragment playerFragment = getFragmentManager().findFragmentById(R.id.contentPlayerFragment);
        if (playerFragment == null)
        {
            playerFragment = new ContentPlayerFragment();
            getFragmentManager().beginTransaction().add(R.id.contentPlayerFragment, playerFragment).commit();
        }

    }

    private void updateProgramFragment()
    {
        Fragment fragmentContainer = getFragmentManager().findFragmentById(R.id.fragmentContainer);
        switch (currentTab)
        {
            case DETAIL:
                fragmentContainer = new DetailFragment();
                break;
            case COMMENT:
                fragmentContainer = new CommentFragment();
                break;
            case THUMB_NAIL:
                fragmentContainer = new ThumbnailFragment();
                break;
        }

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragmentContainer).commit();

    }


}