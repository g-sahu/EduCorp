package org.rangde.gsahu.educorp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rangde.gsahu.educorp.R;
import org.rangde.gsahu.educorp.utils.EduCorpConstants;
import org.rangde.gsahu.educorp.utils.HttpConnector;

/**
 * Created by gasahu on 15-Jan-17.
 */

public class EduCorpService extends IntentService {

    public EduCorpService() {
        super("EduCorpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("EduCorpService", "Inside EduCorpService");

        String formattedUrl = intent.getStringExtra(EduCorpConstants.URL);
        String requestType = intent.getStringExtra(EduCorpConstants.REQUEST_TYPE);
        HttpConnector sh = new HttpConnector();

        //Making a request to url and getting response
        String response = sh.makeServiceCall(formattedUrl);

        Resources res = getResources();
        String url = res.getString(R.string.get_profile_url);
        String apiKey, name = null, accountType = null;

        try {
            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject.has(EduCorpConstants.API_KEY)) {
                apiKey = jsonObject.getString(EduCorpConstants.API_KEY);
                String profileUrl = String.format(url, apiKey);
                String profileResponse = sh.makeServiceCall(profileUrl);

                JSONObject profileObj = new JSONObject(profileResponse);

                if(profileObj.has(EduCorpConstants.PROFILE)) {
                    JSONArray jsonArray = profileObj.getJSONArray(EduCorpConstants.PROFILE);
                    JSONObject innerObj = jsonArray.getJSONObject(0);

                    name = innerObj.getString(EduCorpConstants.NAME);
                    accountType = innerObj.getString(EduCorpConstants.ACCOUNT_TYPE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Sending broadcast indicating that EduCorpService has finished registering/logging in the user
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(EduCorpConstants.ACTION_SERVICE_BROADCAST);
        broadcastIntent.putExtra(EduCorpConstants.JSON_RESPONSE, response);
        broadcastIntent.putExtra(EduCorpConstants.REQUEST_TYPE, requestType);
        broadcastIntent.putExtra(EduCorpConstants.NAME, name);
        broadcastIntent.putExtra(EduCorpConstants.ACCOUNT_TYPE, accountType);
        sendBroadcast(broadcastIntent);
    }
}