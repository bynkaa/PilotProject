package com.qsoft.pilotproject.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.model.ProgramTab;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 8:59 AM
 */
public class ProgramFragment extends FragmentActivity
{

    FragmentManager manager;
    ProgramTab currentTab = ProgramTab.THUMB_NAIL;
    View.OnClickListener btCommentOnclickListner = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (currentTab != ProgramTab.COMMENT)
            {
                currentTab = ProgramTab.COMMENT;
                updateProgramFragment();
            }
        }

    };
    View.OnClickListener btDetailOnclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (currentTab != ProgramTab.DETAIL)
            {
                currentTab = ProgramTab.DETAIL;
                updateProgramFragment();
            }
        }
    };
    View.OnClickListener btThumbnailOnclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (currentTab != ProgramTab.THUMB_NAIL)
            {
                currentTab = ProgramTab.THUMB_NAIL;
                updateProgramFragment();
            }
        }
    };
    private ImageButton btThumbnail;
    private ImageButton btDetail;
    private ImageButton btComment;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        btThumbnail = (ImageButton) findViewById(R.id.ibThumbnail);
        manager = getSupportFragmentManager();
        btThumbnail.setOnClickListener(btThumbnailOnclickListener);
        btDetail = (ImageButton) findViewById(R.id.ibDetail);
        btDetail.setOnClickListener(btDetailOnclickListener);
        btComment = (ImageButton) findViewById(R.id.ibComment);
        btComment.setOnClickListener(btCommentOnclickListner);
        updateProgramFragment();
    }

    private void updateProgramFragment()
    {
        Fragment fragmentContainer = manager.findFragmentById(R.id.fragmentContainer);
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
        manager.beginTransaction().replace(R.id.fragmentContainer, fragmentContainer).commit();

    }


}