package com.example.PilotProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 11:27 AM
 */
public class LaunchActivity extends Activity
{
    Button btLoginFB;
    Button btLogin;
    Button btSignOut;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laucher);
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(btLoginClickListener);
    }

    private View.OnClickListener btLoginClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(LaunchActivity.this, LoginFragment.class);
            startActivity(intent);
        }
    };
}