package com.example.a13051_000.buffetmealsystem;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shubin on 2016/4/29.
 */
public class Json {
    public static String parseJSONWithJOSNObject(String jsonData) {
        Log.d("Data3",jsonData);
        String status = "";
        String nick_name = "";
        try {
                JSONObject jsonObject = new JSONObject(jsonData);
                status = jsonObject.optString("status");
                nick_name = jsonObject.optString("nick_name");
            } catch (JSONException e1) {
            Log.d("Data1",e1.toString());
        }
        Log.d("Data","status"+status);
        Log.d("Data","nick_name"+nick_name);
        if(true){
            return nick_name;
        }
        else{
            return "-1";
        }
    }
}
