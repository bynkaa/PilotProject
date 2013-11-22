package com.qsoft.pilotproject.service;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.config.AppSetting;
import com.qsoft.pilotproject.data.dao.FeedDAO;
import com.qsoft.pilotproject.data.dao.IDao;
import com.qsoft.pilotproject.data.dao.ProfileDAO;
import com.qsoft.pilotproject.data.dao.SyncToServiceDAO;
import com.qsoft.pilotproject.data.model.entity.SyncToServer;
import com.qsoft.pilotproject.data.rest.OnlineDioClientProxy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class SyncDataService
{
    @Bean
    SyncToServiceDAO syncToServiceDAO;
    @Bean
    ProfileDAO profileDAO;
    @Bean
    FeedDAO feedDAO;
    @Bean
    OnlineDioClientProxy onlineDioClientProxy;
    @Bean
    RestMapping restMapping;

    @Bean
    ApplicationAccountManager applicationAccountManager;

    public void performSync()
    {
        List<SyncToServer> syncToServers = syncToServiceDAO.getAllData();

        for (SyncToServer syncToServer : syncToServers)
        {
            if (syncToServer.getSerial() == 0)
            {
                Action action = Action.valueOf(syncToServer.getAction());
                switch (action)
                {
                    case UPDATE:
                        String tableName = syncToServer.getTableName();
                        Long id = syncToServer.getRecordId();
                        String restName = restMapping.getServiceFromTable(tableName);
                        try
                        {
//                            IRest iRest = (IRest) Class.forName(AppSetting.REST_PACKAGE + restName).newInstance();
                            IDao classDao = (IDao) Class.forName(AppSetting.DAO_PACKAGE
                                    + Utilities.convertFromTableToDAOClassName(tableName)).newInstance();
                            Object obj = classDao.get(id);

                            //Todo: need to generic update method
//                            UpdateProfileDTO updateProfileDTO = new UpdateProfileDTO();
//                            onlineDioClientProxy.updateProfile(Utilities.copyProperties(updateProfileDTO, obj),applicationAccountManager.getUserId());
//                            syncToServertatus("synchronized");

                            //Todo; update status to persistent

                        }
                        catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        catch (InstantiationException e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        catch (IllegalAccessException e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }

                }
            }
            else
            {
                SyncTransactionalDataService.performTransaction(syncToServers, syncToServer.getSerial());
            }

        }

    }


}
