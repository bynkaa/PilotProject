package com.qsoft.pilotproject.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.qsoft.pilotproject.ui.fragment.Oldv4SimpleFragment;
import com.qsoft.pilotproject.ui.fragment.Oldv4SimpleFragment_;

/**
 * User: Le
 * Date: 11/7/13
 */
@EActivity(R.layout.activity_multiple_fragments)
public class Oldv4MultipleFragmentsActivity extends FragmentActivity
{

    @AfterViews
    void afterViews()
    {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (currentFragment == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new Oldv4SimpleFragment_()).addToBackStack(null).commit();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, currentFragment).commit();
        }
    }

    private boolean lastBack = false;

    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
        {
            getSupportFragmentManager().popBackStack();
//            getFragmentManager().popBackStackImmediate();
            Oldv4SimpleFragment.count--;
        }
        else
        {
            if (lastBack)
            {
                finish();
            }
            Toast toast = Toast.makeText(this, "Press Back again to exit program", Toast.LENGTH_LONG);
            toast.show();
            lastBack = true;
        }
    }
}
