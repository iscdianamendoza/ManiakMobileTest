package com.mendoza.diana.mobiletest.sync;

import android.util.Log;

import com.mendoza.diana.mobiletest.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Synchronization {

    private static final String URL_LOGIN = "https://tokam.maniak.co/api/login";
    private static final String URL_SETTINGS = "https://tokam.maniak.co/api/settings";
    private static final String STATUS = "status";
    private static final String SUCCESS = "success";
    private static final String TOKEN = "token";
    private static final String DATA = "data";
    private static final String SOCIAL = "social";

    private WebServiceManager wsManager;

    public Object loginSync(UserModel user) {
        wsManager = new WebServiceManager();
        try {
            String params = "?mobile="+user.getUsername()+"&password="+user.getPassword()+"&push_id=1";
            Object res = wsManager.postToWeb(URL_LOGIN, params);

            if (res instanceof Exception){
                return res;
            }
            else {
                JSONObject jsonObject = new JSONObject(res.toString());
                return getToken(jsonObject);
            }
        } catch(Exception e){
            Log.e("loginSync", e.toString());
            return e;
        }
    }

    public Object socialSync(String token) {
        wsManager = new WebServiceManager();
        try {
            Object res = wsManager.getToWeb(URL_SETTINGS, token);

            if (res instanceof Exception){
                return res;
            }
            else {
                JSONObject jsonObject = new JSONObject(res.toString());
                return getSocial(jsonObject);
            }
        } catch(Exception e){
            Log.e("socialSync", e.toString());
            return e;
        }
    }

    private Object getSocial (JSONObject jsonObject) throws JSONException {
        ArrayList<String> socials = new ArrayList<>();
        String status = jsonObject.getString(STATUS);
        if (status.equals(SUCCESS)){
            JSONObject data = (JSONObject) jsonObject.get(DATA);
            JSONObject social = (JSONObject) data.get(SOCIAL);
            Iterator<String> keys = social.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                if (social.get(key) instanceof String) {
                    socials.add(social.getString(key));
                }
            }
        }
        else {
            return new Exception();
        }
        return socials;
    }

    private Object getToken(JSONObject jsonObject) throws JSONException {
        String status = jsonObject.getString(STATUS);
        if (status.equals(SUCCESS)){
            return jsonObject.getString(TOKEN);
        } else {
            return new Exception();
        }
    }

}
