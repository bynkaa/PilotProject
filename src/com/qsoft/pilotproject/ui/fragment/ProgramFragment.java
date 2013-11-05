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
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
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
@EFragment(R.layout.program)
public class ProgramFragment extends Fragment
{

    private static final String TAG = "ProgramFragment";
    FragmentManager manager = getFragmentManager();
    @ViewById(R.id.ibProgramBack)
    private ImageButton ibProgramBack;

    ProgramTab currentTab = ProgramTab.THUMB_NAIL;
    @ViewById(R.id.rgProgramTab)
    private RadioGroup rgProgramTab;
    @ViewById(R.id.tvProgramTitle)
    private TextView tvTitle;
    @ViewById(R.id.tvProgramDisplayNameFeed)
    private TextView tvDisplayName;
    @ViewById(R.id.tvContentLike)
    private TextView tvLikes;
    @ViewById(R.id.tvContentPlay)
    private TextView tvPlayed;
    @ViewById(R.id.tvContentLook)
    private TextView tvLooks;
    @ViewById(R.id.tvLastUpdate)
    private TextView tvUpdated;
    Feed feed = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program, container, false);
        rgProgramTab.setOnCheckedChangeListener(programTabOnCheckChangeListener);
        rgProgramTab.check(R.id.rbThumbnail);
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


    @Click(R.id.ibProgramBack)
            void doClickBack()
    {
        Intent intent = new Intent(getActivity(), SlideBarActivity.class);
        startActivity(intent);
    }

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