package com.qsoft.pilotproject.common;

/**
 * User: Le
 * Date: 10/21/13
 */
public interface IActivityCommand extends ICommand
{
    void execute(SuperActivity activity, IModelContainer modelable, String... parameters) throws Exception;
}
