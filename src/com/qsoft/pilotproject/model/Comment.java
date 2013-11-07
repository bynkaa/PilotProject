package com.qsoft.pilotproject.model;

import android.content.ContentValues;
import com.qsoft.pilotproject.model.dto.CommentDTO;
import com.qsoft.pilotproject.provider.OnlineDioContract;

/**
 * User: BinkaA
 * Date: 10/18/13
 * Time: 1:58 AM
 */
public class Comment extends CommentDTO
{
    private Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = super.getContentValues();
        values.put(OnlineDioContract.Comment._ID, id);
        return values;
    }
}
