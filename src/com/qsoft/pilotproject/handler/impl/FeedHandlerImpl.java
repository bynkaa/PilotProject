package com.qsoft.pilotproject.handler.impl;

import android.util.Log;
import com.qsoft.pilotproject.handler.FeedHandler;
import com.qsoft.pilotproject.model.dto.FeedDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 5:46 PM
 */
public class FeedHandlerImpl implements FeedHandler
{
    private static final String TAG = "FeedHandlerImpl";

    @Override
    public List<FeedDTO> getFeeds(String authToken)
    {
        Log.d(TAG, "getFeeds()");
        List<FeedDTO> feedDTOs = new ArrayList<FeedDTO>();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://192.168.1.222/testing/ica467/trunk/public/home-rest";
        String requestQuery = null;
        int limit = 20;
        requestQuery = String.format("limit=%s&offset=%s&time_from=%s&time_to=%s", limit, "", "", "");
        url = url + "?" + requestQuery;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + authToken);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            Log.d(TAG, responseString);
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }
}
