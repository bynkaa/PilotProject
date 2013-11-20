package com.qsoft.pilotproject.ui.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.data.provider.OnlineDioContract;
import com.qsoft.pilotproject.ui.adapter.CommentAdapter;

/**
 * User: BinkaA
 * Date: 11/7/13
 * Time: 10:57 PM
 */
public class CommentListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "CommentListFragment";
    private static final String[] PROJECTION = new String[]
            {
                    OnlineDioContract.Comment._ID,
                    OnlineDioContract.Comment.COLUMN_DISPLAY_NAME,
                    OnlineDioContract.Comment.COLUMN_CONTENT,
                    OnlineDioContract.Comment.COLUMN_AVATAR,
                    OnlineDioContract.Comment.COLUMN_CREATED_AT
            };
    private static final String[] FROM_COLUMNS = new String[]
            {
                    OnlineDioContract.Comment.COLUMN_DISPLAY_NAME,
                    OnlineDioContract.Comment.COLUMN_CONTENT,
                    OnlineDioContract.Comment.COLUMN_AVATAR,
                    OnlineDioContract.Comment.COLUMN_CREATED_AT
            };
    private static final int[] TO_FIELDS = new int[]
            {
                    R.id.tvCommentTitle,
                    R.id.tvCommentContent,
                    R.id.ivCommentIcon,
                    R.id.tvCommentTimeCreate

            };
    CommentAdapter commentAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commentAdapter = new CommentAdapter(
                getActivity(),
                R.layout.program_comment_list,
                null,
                FROM_COLUMNS,
                TO_FIELDS
        );
        commentAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        setListAdapter(commentAdapter);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), OnlineDioContract.Comment.CONTENT_URI, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        commentAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        commentAdapter.changeCursor(null);
    }
}
