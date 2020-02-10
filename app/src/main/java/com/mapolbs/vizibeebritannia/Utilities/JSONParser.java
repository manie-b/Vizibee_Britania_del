package com.mapolbs.vizibeebritannia.Utilities;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by venkatesan on 3/6/2017.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String jsonresultstring = "";
    int result = 0;

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
                                      JSONObject params) {

        // Making HTTP request
        try {

            // check for request method
            if(method == "POST"){
                // request method is POST
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                post.setHeader("content-type",
                        "application/json; charset=UTF-8");
                StringEntity entity = new StringEntity(params.toString());
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                jsonresultstring = EntityUtils.toString(resp.getEntity());
                result = resp.getStatusLine().getStatusCode();


            }else if(method == "GET"){
                // request method is GET

                HttpGet httpget = new HttpGet(url);
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(httpget);
                jsonresultstring = EntityUtils.toString(response.getEntity());
                result = response.getStatusLine().getStatusCode();


            }

            else if(method == "PUT"){
                // request method is GET
                HttpClient httpClient = new DefaultHttpClient();
                HttpPut put = new HttpPut(url);
                put.setHeader("content-type",
                        "application/json; charset=UTF-8");
                StringEntity entity = new StringEntity(params.toString());
                put.setEntity(entity);
                HttpResponse resp = httpClient.execute(put);
                jsonresultstring = EntityUtils.toString(resp.getEntity());
                result = resp.getStatusLine().getStatusCode();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(jsonresultstring);
            jObj.put("status",result);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }
}
