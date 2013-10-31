package com.qsoft.pilotproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.model.ProgramTab;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 8:59 AM
 */
public class ProgramFragment extends Fragment
{

    FragmentManager manager = getFragmentManager();
    private ImageButton ibProgramBack;

    ProgramTab currentTab = ProgramTab.THUMB_NAIL;
    private RadioGroup rgProgramTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program, container, false);
        rgProgramTab = (RadioGroup) view.findViewById(R.id.rgProgramTab);
        rgProgramTab.setOnCheckedChangeListener(programTabOnCheckChangeListener);
        rgProgramTab.check(R.id.rbThumbnail);
        ibProgramBack = (ImageButton) view.findViewById(R.id.ibProgramBack);

        ibProgramBack.setOnClickListener(ibProgramBackOnClickListener);
        startContentPlayerFragment();
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