package com.qsoft.pilotproject.data.dao;

import com.qsoft.pilotproject.data.model.entity.FeedCC;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDao {

    void update();

    FeedCC get(Long feedId);
}
