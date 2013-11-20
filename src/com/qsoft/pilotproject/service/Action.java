package com.qsoft.pilotproject.service;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Action {
    UPDATE("update"),
    DELETE("delete"),
    INSERT("insert");
    private String alias;

    private Action(String value) {
        alias = value;
    }

    public String getValue() {
        return alias;
    }
}
