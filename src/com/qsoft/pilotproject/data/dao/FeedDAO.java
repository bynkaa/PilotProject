package com.qsoft.pilotproject.data.dao;

import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.data.model.entity.FeedCC;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class FeedDAO implements IDao {
    @Override
    public void update() {

    }

    @Override
    public FeedCC get(Long feedId) {
        return null;
    }
}
