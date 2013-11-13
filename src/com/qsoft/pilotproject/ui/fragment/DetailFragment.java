package com.qsoft.pilotproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.pilotproject.R;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 2:16 PM
 */
@EFragment(R.layout.program_detail)
public class DetailFragment extends Fragment
{

    @FragmentArg(ProgramFragment.DETAIL_FRAGMENT)
    String detail;
    @ViewById(R.id.tvDetailContent)
    TextView tvDetail;

    @AfterViews
    void afterViews()
    {
        tvDetail.setText(detail);
    }

}