package com.qsoft.pilotproject.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.pilotproject.R;

/**
 * User: binhtv
 * Date: 11/5/13
 * Time: 10:13 AM
 */
public class ProgressBarFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.progress_bar, container, false);
        return view;
    }
}
