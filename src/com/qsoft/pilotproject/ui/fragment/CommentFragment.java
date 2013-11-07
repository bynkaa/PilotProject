package com.qsoft.pilotproject.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.adapter.CommentAdapter;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.common.commands.GenericStartActivityCommand;
import com.qsoft.pilotproject.model.Comment;
import com.qsoft.pilotproject.provider.CommentDataSource;
import com.qsoft.pilotproject.ui.activity.NewCommentActivity_;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity_;

import java.util.ArrayList;
import java.util.List;

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
    @ViewById(R.id.lvComment)
    ListView lvComment;
    @Bean
    CommandExecutor commandExecutor;

    private List<Comment> commentList;
    private CommentDataSource commentDataSource;

    @AfterViews
    void afterViews()
    {
        commentDataSource = new CommentDataSource(getActivity());
        commentDataSource.open();
        //
        commentList = commentDataSource.getAllComment();
        CommentAdapter commentAdapter = new CommentAdapter(getActivity(), commentList);
        lvComment.setAdapter(commentAdapter);
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

    List<Comment> getModel()
    {
        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < 10; i++)
        {
            comments.add(getComment());
        }
        return comments;

    }

    public Comment getComment()
    {
        Comment comment = new Comment();
        comment.setTitle("Mr. Michale");
        comment.setContent("The Phrase Became a fundamental element");
        comment.setTimeCreated("1 minute ago");
        return comment;
    }
}