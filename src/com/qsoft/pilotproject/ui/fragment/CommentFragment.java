package com.qsoft.pilotproject.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.pilotproject.adapter.CommentAdapter;
import com.qsoft.pilotproject.model.Comment;
import com.qsoft.pilotproject.provider.CommentDataSource;
import com.qsoft.pilotproject.ui.activity.NewCommentActivity;

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
    public static final int REQUEST_CODE = 1;


    @ViewById(R.id.tvNewComment)
    private TextView tvAddNewComment;
    @ViewById(R.id.lvComment)
    private ListView lvComment;
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
    void  doClickNewComment()
    {
        Intent intent = new Intent(CommentFragment.this.getActivity(), NewCommentActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
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