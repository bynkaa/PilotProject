package com.qsoft.pilotproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.example.PilotProject.R;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 8:59 AM
 */
public class ProgramFragment extends FragmentActivity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
    }
}