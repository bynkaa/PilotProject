package com.qsoft.pilotproject.service;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.data.dao.FeedDAO;
import com.qsoft.pilotproject.data.dao.IDao;
import com.qsoft.pilotproject.data.dao.ProfileDAO;
import com.qsoft.pilotproject.data.dao.SyncToServiceDAO;
import com.qsoft.pilotproject.data.model.entity.ProfileCC;
import com.qsoft.pilotproject.data.model.entity.SyncToServer;
import com.qsoft.pilotproject.data.rest.OnlineDioClientProxy;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Qsoft
 * Date: 11/20/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class SyncDataService {
    @Bean
    SyncToServiceDAO syncToServiceDAO;
    @Bean
    ProfileDAO profileDAO;
    @Bean
    FeedDAO feedDAO;
    @Bean
    OnlineDioClientProxy onlineDioClientProxy;
    @Bean
    ServiceMapping serviceMapping;

    public void performSync() {
        List<SyncToServer> syncToServers = syncToServiceDAO.getAllData();
        for (SyncToServer syncToServer : syncToServers) {
            Action action = Action.valueOf(syncToServer.getAction());
            switch (action) {
                case UPDATE:
                    String tableName = syncToServer.getTableName();
                    Long id = syncToServer.getRecordId();
                    String serviceName = serviceMapping.getServiceFromTable(tableName);
                    //ToDo

                    //
                    try {
                        IDao classDao = (IDao) Class.forName("com.qsoft.pilotproject.data.dao." + "ProfileDAO").newInstance();
                        Object obj = classDao.get(id);
                        if (obj instanceof ProfileCC) {
                            ProfileCC profileCC = (ProfileCC) obj;
                            HashMap profileUpdate = new HashMap();
                            profileUpdate.put("display_name", profileCC.getDisplayName());
                            profileUpdate.put("full_name", profileCC.getFullName());
                            profileUpdate.put("phone", profileCC.getPhone());
                            profileUpdate.put("birthday", profileCC.getBirthday());
                            profileUpdate.put("gender", profileCC.getGender());
                            profileUpdate.put("country_id", profileCC.getCountryId());
                            profileUpdate.put("description", profileCC.getDescription());
                            onlineDioClientProxy.updateProfile(profileUpdate, profileCC.getUserId());
                            syncToServer.setStatus("synchronized");

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (InstantiationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

            }

        }

    }


}
