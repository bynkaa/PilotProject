package com.qsoft.pilotproject.ui.activity;

import android.accounts.AccountManager;
import android.widget.Button;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.eip.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.ui.controller.LoginController;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 11:27 AM
 */
@EActivity(R.layout.activity_launcher)
public class LaunchActivity extends SuperAnnotationActivity
{
    public static final String TAG = "LaunchActivity";

    @ViewById(R.id.btLogin)
    Button btLogin;

    Button btSignOut;

    @SystemService
    AccountManager accountManager;

    @Bean
    LoginController loginController;
}