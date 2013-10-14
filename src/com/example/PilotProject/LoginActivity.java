package com.example.PilotProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 2:34 PM
 */
public class LoginActivity extends Activity
{
    ImageView imDone;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        imDone = (ImageView) findViewById(R.id.imDone);
        imDone.setOnClickListener(btDoneClickListener);
    }

    View.OnClickListener btDoneClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            // validate user and password
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    };
}