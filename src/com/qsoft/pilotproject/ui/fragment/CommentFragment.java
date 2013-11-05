package com.qsoft.pilotproject.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.PilotProject.R;
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
public class CommentFragment extends Fragment
{
    public static final int REQUEST_CODE = 1;
    View.OnClickListener tvAddNewCommentOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(CommentFragment.this.getActivity(), NewCommentActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };
    private TextView tvAddNewComment;
    private ListView lvComment;
    private List<Comment> commentList;
    private CommentDataSource commentDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program_comment, container, false);
        commentDataSource = new CommentDataSource(getActivity());
        commentDataSource.open();

        tvAddNewComment = (TextView) view.findViewById(R.id.tvNewComment);
        tvAddNewComment.setOnClickListener(tvAddNewCommentOnClickListener);
        lvComment = (ListView) view.findViewById(R.id.lvComment);
        //
        commentList = commentDataSource.getAllComment();
        CommentAdapter commentAdapter = new CommentAdapter(getActivity(), commentList);
        lvComment.setAdapter(commentAdapter);
        return view;
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