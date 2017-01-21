package com.example.lenovo.okhttpdemo.Parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;

/**
 * Created by lenovo on 10/1/17.
 */

public class JSONParser
{
    // URL
    private static final String URL = "http://pratikbutani.x10.mx/json_data.json";

    // TAG
    private static final String TAG = "TAG";

    // Key to Send
    private static final String KEY_USER_ID="user_id";

    // Response
    private static Response response;

    /**
     * Get Table "Booking Charge"..
     * Return json object..
     **/

    public static JSONObject getDataFromAPI()
    {
        try
        {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(URL).build();

            response = client.newCall(request).execute();

            return new JSONObject(response.body().string());

        }
        catch(@NonNull IOException | JSONException e)
        {
            Log.e(TAG," " + e.getLocalizedMessage());
        }
        return null;
    }

}
