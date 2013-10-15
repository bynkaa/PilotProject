package com.example.PilotProject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 2:34 PM
 */
public class LoginFragment extends FragmentActivity
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
            Intent intent = new Intent(LoginFragment.this, HomeFragment.class);
            startActivity(intent);
        }
    };
}