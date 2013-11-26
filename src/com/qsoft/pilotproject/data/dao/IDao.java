package com.qsoft.pilotproject.data.dao;

/**
 * User: Qsoft
 * Date: 11/20/13
 * Time: 4:39 PM
 */
public interface IDao<T>
{

    void update(T obj, Long id);

    T get(Long id);
}
