package com.qsoft.pilotproject.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.adapter.CommentAdapter;
import com.qsoft.pilotproject.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 2:20 PM
 */
public class CommentFragment extends Fragment
{
    private EditText etNewComment;
    private ListView lvComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program_comment, container, false);
        etNewComment = (EditText) view.findViewById(R.id.etNewComment);
        lvComment = (ListView) view.findViewById(R.id.lvComment);
        CommentAdapter commentAdapter = new CommentAdapter(getActivity(), getModel());
        lvComment.setAdapter(commentAdapter);
        return view;
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
        comment.setTitle("The Phrase Became a fundamental element");
        comment.setTimeCreated("1 minute ago");
        return comment;
    }
}