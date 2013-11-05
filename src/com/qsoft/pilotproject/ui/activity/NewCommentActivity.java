package com.qsoft.pilotproject.ui.activity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.eip.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.model.Comment;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 1:42 PM
 */
@EActivity(R.layout.activity_add_comment)
public class NewCommentActivity extends SuperAnnotationActivity
{
    public static final String COMMENT_EXTRA = "comment";

    @ViewById(R.id.ibNewCommentCancel)
    ImageButton ibCancel;

    @ViewById(R.id.ibNewCommentPost)
    ImageButton ibPost;

    @ViewById(R.id.etAddNewComment)
    EditText etNewComment;

    @Click(R.id.ibNewCommentCancel)
    void doClickCancel()
    {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Click(R.id.ibNewCommentPost)
    void doClickPost()
    {
        Intent intent = getIntent();
        Comment newComment = new Comment();
        newComment.setTitle("User");
        newComment.setContent(etNewComment.getText().toString());
        newComment.setTimeCreated("1 month ago");
        intent.putExtra(COMMENT_EXTRA, newComment);
        setResult(RESULT_OK, intent);
        finish();
    }
}