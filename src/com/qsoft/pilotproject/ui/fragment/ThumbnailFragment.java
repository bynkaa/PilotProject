package com.qsoft.pilotproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.common.imageloader.ImageLoader;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 11:52 AM
 */
@EFragment(R.layout.program_thumnail)
public class ThumbnailFragment extends Fragment {
    @FragmentArg(ProgramFragment.THUMBNAIL)
    String urlThumbnail;
    @ViewById(R.id.imThumbnail)
    ImageView ivThumbnail;

    @AfterViews
    void afterViews() {
        ImageLoader imageLoader = new ImageLoader(getActivity());
        imageLoader.DisplayImage(urlThumbnail, ivThumbnail, R.drawable.content_imagedefault);
    }
}