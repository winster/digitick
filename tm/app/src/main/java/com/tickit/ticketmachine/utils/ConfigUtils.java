package com.tickit.ticketmachine.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wjose on 02/04/2016.
 */
public class ConfigUtils {

    private Context appContext;
    private JSONObject configJson;

    private ConfigUtils(){}

    public void init(Context context){
        if(appContext == null){
            appContext = context;
        }
        try {
            if(configJson==null)
                configJson = new JSONObject(PreferenceManager.getDefaultSharedPreferences(get()).getString("config", "{}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Context getContext(){
        return appContext;
    }

    public static Context get(){
        return getInstance().getContext();
    }

    private JSONObject getConfig(){
        return configJson;
    }

    public static JSONObject getConfigJSON(){
        return getInstance().getConfig();
    }

    private static ConfigUtils instance;

    public static ConfigUtils getInstance(){
        return instance == null ?
                (instance = new ConfigUtils()):
                instance;
    }

    private static String getTitle(String key){
        String result = "";
        try{
            result = getConfigJSON().getJSONObject("route").getString(key);
            result = result.length() > 5 ?result.substring(0, 5).concat("..") :result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getRouteTitle(boolean b) {
        String result = "";
        if(b)
            result = getTitle("org")+" to "+getTitle("dst");
        else
            result = getTitle("dst")+" to "+getTitle("org");
        if(result.length()==0)
            result = "Tickets";
        return result;
    }

}