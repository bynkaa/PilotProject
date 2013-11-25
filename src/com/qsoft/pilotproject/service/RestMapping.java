package com.qsoft.pilotproject.service;

import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.data.rest.ProfileRestClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class RestMapping
{
    Map<String, Class> tableMapping = new HashMap<String, Class>();

    public RestMapping()
    {
        tableMapping.put("profiles", ProfileRestClient.class);
    }

    public Class getRestFromTable(String tableName)
    {
        return tableMapping.get(tableName);
    }
}
