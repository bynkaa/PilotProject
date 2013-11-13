package com.qsoft.pilotproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.pilotproject.R;

/**
 * User: Le
 * Date: 11/7/13
 */
@EFragment(R.layout.fragment_simple)
public class SimpleFragment extends Fragment
{
    public static int count = 0;

    private Integer countNow = null;

    @ViewById(R.id.button_next_fragment)
    Button buttonNextFragment;

    @ViewById(R.id.button_add_sub_fragment)
    Button buttonAddSubFragment;

    @ViewById(R.id.textView_title_sf)
    TextView textView;

    Fragment currentHostFragment;

    @AfterViews
    void afterViews()
    {
        if (!(getParentFragment() instanceof SimpleFragment))
        {
            setRetainInstance(true);
        }
        if (countNow == null)
        {
            countNow = count++;
        }
        textView.setText("Fragment " + countNow);
    }

    @Click(R.id.button_next_fragment)
    void doClickNextFragment()
    {
        getFragmentManager().beginTransaction().replace(R.id.container, new SimpleFragment_()).addToBackStack(null).commit();
    }

    @Click(R.id.button_add_sub_fragment)
    void doClickAddSubFragment()
    {
        currentHostFragment = new SimpleFragment_();
        getChildFragmentManager().beginTransaction().replace(R.id.subcontainer, currentHostFragment).addToBackStack(null).commit();
    }
}
