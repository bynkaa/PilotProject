package com.qsoft.pilotproject.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.data.model.entity.CommentCCContract;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 1:42 PM
 */
@EActivity(R.layout.activity_add_comment)
public class NewCommentActivity extends SuperAnnotationActivity {
    public static final String COMMENT_EXTRA = "comment";

    @ViewById(R.id.ibNewCommentCancel)
    ImageButton ibCancel;

    @ViewById(R.id.ibNewCommentPost)
    ImageButton ibPost;

    @ViewById(R.id.etAddNewComment)
    EditText etNewComment;

    @Click(R.id.ibNewCommentCancel)
    void doClickCancel() {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Click(R.id.ibNewCommentPost)
    void doClickPost() {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(CommentCCContract.COMMENT, "Ops");
        getContentResolver().insert(CommentCCContract.CONTENT_URI, values);

        Cursor c = getContentResolver().query(CommentCCContract.CONTENT_URI, null, null, null, null);
        while (c.moveToNext()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                Log.d(getClass().getSimpleName(), c.getColumnName(i) + " : " + c.getString(i));
            }
        }
        c.close();
    }
}

