package com.qsoft.pilotproject.service;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.utils.Utilities;
import com.qsoft.pilotproject.config.AppSetting;
import com.qsoft.pilotproject.data.dao.FeedDAO;
import com.qsoft.pilotproject.data.dao.IDao;
import com.qsoft.pilotproject.data.dao.ProfilesDAO;
import com.qsoft.pilotproject.data.dao.SyncToServiceDAO;
import com.qsoft.pilotproject.data.model.entity.ITransformableDTO;
import com.qsoft.pilotproject.data.model.entity.SyncToServer;
import com.qsoft.pilotproject.data.rest.IRest;
import com.qsoft.pilotproject.data.rest.InterceptorDecoratorFactory;
import com.qsoft.pilotproject.data.rest.SingletonFactoryHolder;

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
    ProfilesDAO profilesDAO;
    @Bean
    FeedDAO feedDAO;
    @Bean
    InterceptorDecoratorFactory interceptorDecoratorFactory;
    @Bean
    RestMapping restMapping;
    @Bean
    ApplicationAccountManager applicationAccountManager;

    public void performSync() throws Exception
    {
        List<SyncToServer> syncToServers = syncToServiceDAO.getAllData();

        for (SyncToServer syncToServer : syncToServers)
        {
            if (syncToServer.getSerial() == 0)
            {
                Action action = Action.getAction(syncToServer.getAction());
                String tableName = syncToServer.getTableName();
                Long id = syncToServer.getRecordId();
                Class restClass = restMapping.getRestFromTable(tableName);
                IRest iRest = (IRest) SingletonFactoryHolder.getSingleton(restClass);

                switch (action)
                {
                    case UPDATE:


//                            IRest iRest = (IRest) Class.forName(AppSetting.REST_PACKAGE + restName).newInstance();
                        IDao classDaoUpdate = (IDao) Class.forName(AppSetting.DAO_PACKAGE
                                + Utilities.convertFromTableToDAOClassName(tableName)).newInstance();
                        ITransformableDTO transformableDTO = (ITransformableDTO) classDaoUpdate.get(id);
                        Object obj = transformableDTO.transformToDTO();
                        iRest.update(obj, id);
                        break;
                    case DELETE:
                        iRest.delete(id);
                        break;
                    case INSERT:
                        IDao classDaoInsert = (IDao) Class.forName(AppSetting.DAO_PACKAGE +
                                Utilities.convertFromTableToDAOClassName(tableName)).newInstance();
                        ITransformableDTO transformableDTO1 = (ITransformableDTO) classDaoInsert.get(id);
                        iRest.create(transformableDTO1.transformToDTO());

                }
                syncToServer.setStatus("synchronized");

            }
            else
            {
                SyncTransactionalDataService.performTransaction(syncToServers, syncToServer.getSerial());
            }

        }

    }


}
