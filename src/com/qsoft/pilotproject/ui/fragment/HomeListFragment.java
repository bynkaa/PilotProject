package com.qsoft.pilotproject.ui.fragment;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.SyncStatusObserver;
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
import com.example.PilotProject.R;
import com.qsoft.pilotproject.adapter.ArrayFeedAdapter;
import com.qsoft.pilotproject.authenticator.AuthenticatorService;
import com.qsoft.pilotproject.provider.OnlineDioContract;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity;

/**
 * User: binhtv
 * Date: 11/1/13
 * Time: 10:17 AM
 */
public class HomeListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener
{
    private static final String TAG = "HomeListFragment";
    private static final String[] PROJECTION = new String[]
            {
                    OnlineDioContract.Feed._ID,
                    OnlineDioContract.Feed.COLUMN_TITLE,
                    OnlineDioContract.Feed.COLUMN_DISPLAY_NAME,
                    OnlineDioContract.Feed.COLUMN_LIKES,
                    OnlineDioContract.Feed.COLUMN_COMMENTS,
                    OnlineDioContract.Feed.COLUMN_UPDATED_AT,
                    OnlineDioContract.Feed.COLUMN_AVATAR
            };
    private static final String[] FROM_COLUMNS = new String[]
            {
                    OnlineDioContract.Feed.COLUMN_TITLE,
                    OnlineDioContract.Feed.COLUMN_DISPLAY_NAME,
                    OnlineDioContract.Feed.COLUMN_LIKES,
                    OnlineDioContract.Feed.COLUMN_COMMENTS,
                    OnlineDioContract.Feed.COLUMN_UPDATED_AT,
                    OnlineDioContract.Feed.COLUMN_AVATAR
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
    private Object syncObserverHandler;
    int loadMore = 0;

    private SyncStatusObserver syncStatusObserver = new SyncStatusObserver()
    {
        @Override
        public void onStatusChanged(int i)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Account account = AuthenticatorService.getAccount();
                    if (account == null)
                    {
                        //
                        return;
                    }
                    boolean syncActive = ContentResolver.isSyncActive(account, OnlineDioContract.CONTENT_AUTHORITY);
                    boolean syncPending = ContentResolver.isSyncPending(account, OnlineDioContract.CONTENT_AUTHORITY);
                    // set refresh
                }
            });
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        feedAdapter = new ArrayFeedAdapter(
                getActivity(),
                R.layout.feed,
                null,
                FROM_COLUMNS,
                TO_FIELDS
        );
        feedAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder()
        {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int i)
            {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        setListAdapter(feedAdapter);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Bundle bundle = new Bundle();
        bundle.putLong(FEED_ID, id);
        Fragment programFragment = new ProgramFragment_();
        programFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.replace(R.id.content_fragment, programFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        syncStatusObserver.onStatusChanged(0);
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING | ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        syncObserverHandler = ContentResolver.addStatusChangeListener(mask, syncStatusObserver);

    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (syncObserverHandler != null)

        {
            ContentResolver.removeStatusChangeListener(syncObserverHandler);
        }
        syncObserverHandler = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(getActivity(), OnlineDioContract.Feed.CONTENT_URI, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        feedAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {
        feedAdapter.changeCursor(null);
    }

    int currentFirstVisibleItem;
    int currentVisibleItemCount;
    int currentScrollState;

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState)
    {
        this.currentScrollState = scrollState;
        isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        this.currentFirstVisibleItem = firstVisibleItem;
        this.currentVisibleItemCount = visibleItemCount;
    }

    public void isScrollCompleted()
    {
        if (currentVisibleItemCount > 0 && currentScrollState == SCROLL_STATE_IDLE)
        {

            loadMore++;
            String limit = Integer.toString(loadMore * 10 + 10);
            Bundle bundle = new Bundle();
            bundle.putString("limit", limit);
            getLoaderManager().restartLoader(0, bundle, this);
            ((SlideBarActivity) getActivity()).triggerSync();


        }
    }


}

