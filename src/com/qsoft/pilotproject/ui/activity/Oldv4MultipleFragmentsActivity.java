package com.qsoft.pilotproject.ui.activity;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.qsoft.pilotproject.ui.fragment.SimpleFragment;
import com.qsoft.pilotproject.ui.fragment.SimpleFragment_;

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
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.container);

        if (currentFragment == null)
        {
            getFragmentManager().beginTransaction().add(R.id.container, new SimpleFragment_()).addToBackStack(null).commit();
        }
        else
        {
            getFragmentManager().beginTransaction().replace(R.id.container, currentFragment).commit();
        }
    }

    private boolean lastBack = false;

    @Override
    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() > 1)
        {
            getFragmentManager().popBackStack();
//            getFragmentManager().popBackStackImmediate();
            SimpleFragment.count--;
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
