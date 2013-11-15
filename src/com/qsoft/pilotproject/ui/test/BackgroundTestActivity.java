package com.qsoft.pilotproject.ui.test;

import android.app.Activity;
import android.widget.Button;
import android.widget.ProgressBar;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.R;

/**
 * User: binhtv
 * Date: 11/14/13
 * Time: 10:16 AM
 */
@EActivity(R.layout.progress_bar)
public class BackgroundTestActivity extends Activity
{
    @ViewById(R.id.button)
    Button button;
    @ViewById(R.id.progressBar1)
    ProgressBar progressBar;
    @InstanceState
    int progressBarStatus;

    @AfterViews
    void afterViews()
    {
        if (progressBarStatus < 0)
        {
            progressBarStatus = 0;
        }
        processProgressBar();
    }

    //    @Click(R.id.button)
//    void btClick()
//    {
//        processProgressBar();
//    }
    @Background
    void processProgressBar()
    {
        while (progressBarStatus < 100)
        {
            progressBarStatus += 1;
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            postProgressBar();
        }

    }

    @UiThread
    void postProgressBar()
    {
        progressBar.setProgress(progressBarStatus);
    }


}