package org.rangde.gsahu.educorp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rangde.gsahu.educorp.activities.DashboardActivity;
import org.rangde.gsahu.educorp.activities.HomeActivity;
import org.rangde.gsahu.educorp.activities.ProfileActivity;
import org.rangde.gsahu.educorp.utils.EduCorpConstants;

/**
 * Created by gasahu on 15-Jan-17.
 */

public class EduCorpReceiver extends BroadcastReceiver {
    public EduCorpReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("EduCorpReceiver", "Broadcast received");

        JSONObject jsonObject;
        String response = intent.getStringExtra(EduCorpConstants.JSON_RESPONSE);
        String status, apiKey;
        String requestType = intent.getStringExtra(EduCorpConstants.REQUEST_TYPE);

        try {
            switch(requestType) {
                case EduCorpConstants.REQUEST_REGISTER:
                    jsonObject = new JSONObject(response);

                    if(jsonObject.has(EduCorpConstants.STATUS)) {
                        status = jsonObject.getString(EduCorpConstants.STATUS);

                        if (status != null && status.equals(EduCorpConstants.STATUS_SUCCESS)) {
                            intent.putExtra(EduCorpConstants.STATUS, status);
                        } else {
                            intent.putExtra(EduCorpConstants.STATUS, EduCorpConstants.STATUS_FAILED);
                        }
                    } else {
                        intent.putExtra(EduCorpConstants.STATUS, EduCorpConstants.STATUS_FAILED);
                    }

                    intent.setClass(context, HomeActivity.class);
                    break;

                case EduCorpConstants.REQUEST_LOGIN:
                    jsonObject = new JSONObject(response);

                    if(jsonObject.has(EduCorpConstants.API_KEY)) {
                        apiKey = jsonObject.getString(EduCorpConstants.API_KEY);

                        if(apiKey != null) {
                            intent.setClass(context, DashboardActivity.class);
                            intent.putExtra(EduCorpConstants.API_KEY, apiKey);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        } else {
                            intent.putExtra(EduCorpConstants.STATUS, EduCorpConstants.STATUS_FAILED);
                            intent.setClass(context, HomeActivity.class);
                        }
                    } else {
                        intent.putExtra(EduCorpConstants.STATUS, EduCorpConstants.STATUS_FAILED);
                        intent.setClass(context, HomeActivity.class);
                    }
                    
                    break;

                case EduCorpConstants.REQUEST_GET_PROFILE:
                    jsonObject = new JSONObject(response);

                    if(jsonObject.has(EduCorpConstants.PROFILE)) {
                        intent.setClass(context, ProfileActivity.class);
                        intent.putExtra(EduCorpConstants.JSON_RESPONSE, response);
                        intent.putExtra(EduCorpConstants.STATUS, EduCorpConstants.STATUS_SUCCESS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {
                        intent.setClass(context, DashboardActivity.class);
                        intent.putExtra(EduCorpConstants.STATUS, EduCorpConstants.STATUS_FAILED);
                    }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        context.startActivity(intent);
    }
}
