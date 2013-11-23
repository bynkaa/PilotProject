package com.qsoft.pilotproject.data.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/23/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SingletonFactoryHolder
{
    static final Map<Class, Object> singletonHolder = new HashMap<Class, Object>();

    public static Object getSingleton(Class<?> clazz) throws Exception
    {
        if (!singletonHolder.containsKey(clazz))
        {
            try
            {
                Object obj = Class.forName(clazz.getName() + "Customer").newInstance();
                singletonHolder.put(clazz, obj);
                return obj;
            }
            catch (Exception e)
            {
                throw new Exception(e);
                //todo customize exception
            }

        }
        return singletonHolder.get(clazz);
    }
}
