package com.mapolbs.vizibeebritannia.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mapolbs.vizibeebritannia.Activity.SplashScreen;
import com.mapolbs.vizibeebritannia.DB.DBAdapter;
import com.mapolbs.vizibeebritannia.Utilities.ConnectionDetector;
import com.mapolbs.vizibeebritannia.Utilities.FCMConfig;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;
import com.mapolbs.vizibeebritannia.Utilities.NotificationUtils;
import com.mapolbs.vizibeebritannia.Utilities.UserSessionManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class FCMMessagingService extends FirebaseMessagingService {

    private static final String TAG = FCMMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    ConnectionDetector cd;
    UserSessionManager session;
    String ipaddress;
    String in_username;
    int status = 0;
    DBAdapter dbAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        session = new UserSessionManager(getApplicationContext());
        cd = new ConnectionDetector(getApplicationContext());
        HashMap<String, String> ipadd = session.getipDetails();
        ipaddress = ipadd.get(UserSessionManager.IP_CONNECTIONSTRING);



    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String refreshedToken = s;

        if (null!=refreshedToken){
            // Saving reg id to shared preferences
            storeRegIdInPref(refreshedToken);

            // sending reg id to your server

//to avoid inital crash intial crash handle

           /* if (!dbAdapter.getloginusername().equalsIgnoreCase("")&&dbAdapter.getloginusername()!=null) {

                in_username = dbAdapter.getloginusername();
                sendRegistrationToServer();

            }*/
        }



        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(FCMConfig.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;
        //FCMConfig.NOTIFICATION_COUNT ++;
        //I am setting in here.
       // setBadge(getApplicationContext(),FCMConfig.NOTIFICATION_COUNT);

        // Check if message contains a notification payload.
      /*  if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(),String.valueOf(FCMConfig.NOTIFICATION_COUNT));
        }*/


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            checkprojectnotification(remoteMessage.getData().toString());

        }
    }
    private void checkprojectnotification(String datas){

        try {

            JSONObject json =new JSONObject(datas);
            JSONObject data = json.getJSONObject("data");
            /*String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl =   data.getString("image");
            String timestamp = data.getString("timestamp");*/
            JSONObject payload = data.getJSONObject("payload");
            //new 04/06/2019
            String project_id = payload.has("project_id")?payload.getString("project_id"):"0";

            /*Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);*/
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "project_id: " + project_id);

            if (project_id.equalsIgnoreCase("0")){
                //broadcastmessage
                FCMConfig.NOTIFICATION_COUNT ++;
                handleNotification(datas);

            }else {
                String presentprojectid =MyApplication.getInstance().getProjectid()!=null?MyApplication.getInstance().getProjectid():"";
                if (presentprojectid.equalsIgnoreCase(project_id)){
                    //projectnotification
                    FCMConfig.NOTIFICATION_COUNT ++;
                    handleNotification(datas);

                }

            }



        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

    }
    private void handleNotification(String datas) {
        try {
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(FCMConfig.PUSH_NOTIFICATION);
                pushNotification.putExtra("count",  String.valueOf(FCMConfig.NOTIFICATION_COUNT));
                pushNotification.putExtra("datas", datas);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }else{
                // If the app is in background, firebase itself handles the notification
                try {
                    // JSONObject json = new JSONObject(message);
                    // handleDataMessage(json);
                    handleDataMessage(datas);
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        }catch (Exception ex){
            Log.e("handleNotification",ex.getMessage());
        }

    }



    private void handleDataMessage(String datas) {
      //  Log.e(TAG, "push json: " + json.toString());
        Log.e(TAG, "push json: " + datas);

        try {

            JSONObject json =new JSONObject(datas);
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            //String imageUrl ="https://goo.gl/images/3Zkte1";   //test
            String imageUrl =   data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            {
                Intent resultIntent = new Intent(getApplicationContext(),SplashScreen.class);
                resultIntent.putExtra("count",String.valueOf(FCMConfig.NOTIFICATION_COUNT));
                resultIntent.putExtra("datas", datas);
              /*  resultIntent.putExtra("notificationtype", payload.getString("type"));
                resultIntent.putExtra("description", payload.getString("description"));*/

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent, payload);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl, payload);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent, JSONObject jobj) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent,jobj);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl, JSONObject jobj) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl,jobj);
    }

    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            Log.e("classname","null");
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }



    private void storeRegIdInPref(String token) {
        FirebaseMessaging.getInstance().subscribeToTopic(FCMConfig.TOPIC_GLOBAL);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }
    public String getFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("FirebaseID", "Firebase reg id: " + regId);
        return regId;
    }

    public String sendRegistrationToServer() {
        String responsestr = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {

            url = new URL(ipaddress + "user_update?username=" + in_username +"&"+"fcm_key" +getFirebaseRegId());
            urlConnection = (HttpURLConnection) url.openConnection();

            status = urlConnection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                responsestr = readStream(urlConnection.getInputStream());
                Log.v("CatalogClient", responsestr);
            }
            if (status == HttpURLConnection.HTTP_BAD_REQUEST){
                responsestr= readStream(urlConnection.getErrorStream());
                Log.v("CatalogClient", responsestr);
            }

            if (status == 200) {
                return responsestr;

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return responsestr;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}
