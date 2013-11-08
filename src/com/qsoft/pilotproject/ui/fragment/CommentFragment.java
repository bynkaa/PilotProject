package com.qsoft.pilotproject.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.common.commands.GenericStartActivityCommand;
import com.qsoft.pilotproject.ui.activity.NewCommentActivity_;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 2:20 PM
 */
@EFragment(R.layout.program_comment)
public class CommentFragment extends Fragment
{
    private static final int RC_NEW_COMMENT_ACTIVITY = 3;


    @ViewById(R.id.tvNewComment)
    TextView tvAddNewComment;
    @Bean
    CommandExecutor commandExecutor;


    @AfterViews
    void afterViews()
    {
        Fragment commentListFragment = new CommentListFragment();
        getFragmentManager().beginTransaction().replace(R.id.lvComment, commentListFragment).commit();
    }


    @Click(R.id.tvNewComment)
    void doClickNewComment()
    {
        commandExecutor.execute(getActivity(),
                new GenericStartActivityCommand(getActivity(), NewCommentActivity_.class, RC_NEW_COMMENT_ACTIVITY)
                {
                    @Override
                    public void overrideExtra(Intent intent)
                    {
                    }
                }, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(CommentFragment.class.getName(), "on ui result");
    }

}