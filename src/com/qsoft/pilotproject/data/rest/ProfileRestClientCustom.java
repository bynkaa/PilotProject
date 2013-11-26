package com.qsoft.pilotproject.data.rest;

import com.qsoft.pilotproject.data.model.dto.UpdateProfileDTO;

import java.util.Map;

/**
 * User: Qsoft
 * Date: 11/23/13
 * Time: 3:51 PM
 */
public class ProfileRestClientCustom extends ProfileRestClient_ implements IRest
{

    @Override
    public void update(Object object, Long id)
    {
        UpdateProfileDTO updateProfileDTO = (UpdateProfileDTO) object;
        Map mapValues = updateProfileDTO.toMapValues();
        update(mapValues, id);
    }

    @Override
    public void create(Object object)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Long id)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void get()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}