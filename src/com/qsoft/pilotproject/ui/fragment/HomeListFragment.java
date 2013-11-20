package com.qsoft.pilotproject.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.config.AppSetting;
import com.qsoft.pilotproject.data.model.entity.FeedCCContract;
import com.qsoft.pilotproject.ui.adapter.ArrayFeedAdapter;
import com.qsoft.pilotproject.ui.controller.CommonController;
import com.qsoft.pilotproject.ui.controller.CommonController_;

/**
 * User: binhtv
 * Date: 11/1/13
 * Time: 10:17 AM
 */
public class HomeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener {
    CommonController commonController;

    private static final String TAG = "HomeListFragment";
    private static final String[] PROJECTION = new String[]
            {
                    FeedCCContract._ID,
                    FeedCCContract.TITLE,
                    FeedCCContract.DISPLAYNAME,
                    FeedCCContract.LIKES,
                    FeedCCContract.COMMENTS,
                    FeedCCContract.UPDATEDAT,
                    FeedCCContract.AVATAR
            };
    private static final String[] FROM_COLUMNS = new String[]
            {

                    FeedCCContract.TITLE,
                    FeedCCContract.DISPLAYNAME,
                    FeedCCContract.LIKES,
                    FeedCCContract.COMMENTS,
                    FeedCCContract.UPDATEDAT,
                    FeedCCContract.AVATAR
            };
    private static final int[] TO_FIELDS = new int[]
            {
                    R.id.tvTitleFeed,
                    R.id.tvDisplayNameFeed,
                    R.id.tvLikeFeed,
                    R.id.tvCommentFeed,
                    R.id.tvLastUpdateStatus,
                    R.id.imAvatarFeed

            };
    public static final String FEED_ID = "_ID";
    private SimpleCursorAdapter feedAdapter;
    int loadMore = 0;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedAdapter = new ArrayFeedAdapter(
                getActivity(),
                R.layout.feed,
                null,
                FROM_COLUMNS,
                TO_FIELDS
        );
        feedAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        setListAdapter(feedAdapter);
        getLoaderManager().initLoader(0, null, this);
        commonController = CommonController_.getInstance_(getActivity());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Bundle bundle = new Bundle();
        bundle.putLong(FEED_ID, id);
        Fragment programFragment = new ProgramFragment_();
        programFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getParentFragment().getFragmentManager().beginTransaction();
        Fragment playerFragment = getFragmentManager().findFragmentById(R.id.content_fragment);
        if (playerFragment != null) {
            fragmentTransaction.remove(playerFragment);
        }
        fragmentTransaction.replace(R.id.content_fragment, programFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(getActivity(), OnlineDioContract.Feed.CONTENT_URI, PROJECTION, null, null, null);

        return new CursorLoader(getActivity(), FeedCCContract.CONTENT_URI, PROJECTION, null, null, FeedCCContract.CREATEDAT + " DESC limit " + AppSetting.VIEW_PAGING);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        feedAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        feedAdapter.changeCursor(null);
    }

    int currentFirstVisibleItem;
    int currentVisibleItemCount;
    int currentScrollState;

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        this.currentScrollState = scrollState;
        isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
    }

    public void isScrollCompleted() {
        if (currentVisibleItemCount > 0 && currentScrollState == SCROLL_STATE_IDLE) {

            loadMore++;
            String limit = Integer.toString(loadMore * 10 + 10);
            Bundle bundle = new Bundle();
            bundle.putString("limit", limit);
            getLoaderManager().restartLoader(0, bundle, this);
            commonController.triggerSync();


        }
    }

}

