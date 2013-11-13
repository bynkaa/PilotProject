package com.qsoft.pilotproject.ui.activity;

import android.app.Activity;
import com.googlecode.androidannotations.annotations.EActivity;
import com.qsoft.pilotproject.R;

/**
 * User: Le
 * Date: 11/7/13
 */
@EActivity(R.layout.activity_multiple_fragments)
public class MultipleFragmentsActivity extends Activity
{

//    @AfterViews
//    void afterViews()
//    {
//        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.container);
//
//        if (currentFragment == null)
//        {
//            getFragmentManager().beginTransaction().add(R.id.container, new SimpleFragment_()).addToBackStack(null).commit();
//        }
//        else
//        {
//            getFragmentManager().beginTransaction().replace(R.id.container, currentFragment).commit();
//        }
//    }
//
//    private boolean lastBack = false;
//
//    @Override
//    public void onBackPressed()
//    {
//        if (getFragmentManager().getBackStackEntryCount() > 1)
//        {
//            super.onBackPressed();
////            getFragmentManager().popBackStackImmediate();
//            SimpleFragment.count--;
//        }
//        else
//        {
//            if (lastBack)
//            {
//                finish();
//            }
//            Toast toast = Toast.makeText(this, "Press Back again to exit program", Toast.LENGTH_LONG);
//            toast.show();
//            lastBack = true;
//        }
//    }
}
