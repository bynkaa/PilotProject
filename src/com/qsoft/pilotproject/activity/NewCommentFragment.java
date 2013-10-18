package com.qsoft.pilotproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.PilotProject.R;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 1:42 PM
 */
public class NewCommentFragment extends Activity
{
    private ImageButton ibCancel;
    private ImageButton ibPost;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_add_comment);
        ibCancel = (ImageButton) findViewById(R.id.ibNewCommentCancel);
        ibCancel.setOnClickListener(ibCancelOnClickListener);
    }
    View.OnClickListener ibCancelOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = getIntent();
            setResult(RESULT_CANCELED,intent);
            finish();
        }
    };
}