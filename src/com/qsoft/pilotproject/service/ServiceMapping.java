package com.qsoft.pilotproject.service;

import com.googlecode.androidannotations.annotations.EBean;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class ServiceMapping {
    HashMap<String, String> tableMapping = new HashMap<String, String>();

    public ServiceMapping() {
        tableMapping.put("profiles", ProfileService.class.getName());
        tableMapping.put("feeds", FeedService.class.getName());
    }

    public String getServiceFromTable(String tableName) {
        return tableMapping.get(tableName);
    }
}
