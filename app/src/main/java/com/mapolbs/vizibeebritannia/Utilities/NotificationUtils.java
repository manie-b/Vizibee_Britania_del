package com.mapolbs.vizibeebritannia.Utilities;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;


import com.mapolbs.vizibeebritannia.DB.DBAdapter;
import com.mapolbs.vizibeebritannia.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *  Created by ARUN on 12/12/2018.
 */
public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;
    final long onehour = 1000 * 60 * 60;
    DBAdapter dbAdapter;
    public NotificationUtils(Context mContext) {
        dbAdapter = new DBAdapter(mContext);
        dbAdapter.open();
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String message, String timeStamp, Intent intent, JSONObject jobj) {
        showNotificationMessage(title, message, timeStamp, intent,"", jobj);
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl, JSONObject jobj) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        int icon = R.drawable.icon;


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    try {
                        showBigNotification(bitmap,mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound,jobj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showSmallNotification(null,mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound,jobj);
                }
            }else
            {
                showSmallNotification(null,mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound,jobj);

            }
        } else {
            showSmallNotification(null,mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound,jobj);

        }
    }



    @TargetApi(26)
    private void showSmallNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound, JSONObject jobj) {
        try {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            inboxStyle.addLine(message);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
           /* if (jobj.getString("type").equalsIgnoreCase("chat"))
            {
               // int not_count = notcount(FCMConfig.notcount,jobj.getString("from_user_id"));
                //title = title+String.valueOf(not_count==0||not_count==1?"":" ("+not_count+" Messages)");
            }
            */
          
           

            Notification notification;
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentIntent(resultPendingIntent)
                    .setContentTitle(title)
                    .setSound(alarmSound)
                    .setStyle(inboxStyle)
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setSmallIcon( R.drawable.icon)
                    .setNumber(FCMConfig.NOTIFICATION_COUNT)
                    .setChannelId("com.mapolbs.vizibeebritannia.ANDROID")
                    .setLargeIcon(bitmap == null ? BitmapFactory.decodeResource(mContext.getResources(), icon) : bitmap)
                    .setContentText(message)
                    .build();
            int smallIconId = mContext.getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
            if (smallIconId != 0) {
                if (notification.contentView!=null)
                    notification.contentView.setViewVisibility(smallIconId, View.INVISIBLE);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel androidChannel = new NotificationChannel("com.mapolbs.vizibeebritannia.ANDROID",
                        "ANDROID CHENNAL", NotificationManager.IMPORTANCE_DEFAULT);
                // Sets whether notifications posted to this channel should display notification lights
                androidChannel.enableLights(true);
                // Sets whether notification posted to this channel should vibrate.
                androidChannel.enableVibration(true);
                // Sets the notification light color for notifications posted to this channel
                androidChannel.setLightColor(Color.GREEN);
                // Sets whether notifications posted to this channel appear on the lockscreen or not
                androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

                notificationManager.createNotificationChannel(androidChannel);
            }
           /* if (jobj.getString("type").equalsIgnoreCase("chat"))
                notificationManager.notify(Integer.parseInt(jobj.getString("from_user_id")), notification);
            else{*/
                notificationManager.notify(FCMConfig.PROJECTNOTIFICATION_ID, notification);
        //}

        }catch (Exception ex)
        {
            Log.e("Notfication ex",ex.getMessage().toString());
        }
    }
    @TargetApi(26)
    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound, JSONObject jobj) {
        try {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
          /*  if (jobj.getString("type").equalsIgnoreCase("chat"))
            {
                int not_count = notcount(FCMConfig.notcount,jobj.getString("from_user_id"));
                title = title+String.valueOf(not_count==0||not_count==1?"":" ("+not_count+" Messages)");
            }*/

            Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
                .setChannelId("com.mapolbs.vizibeebritannia.ANDROID")
                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon( R.drawable.icon)
                .setNumber(FCMConfig.NOTIFICATION_COUNT)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();
            int smallIconId = mContext.getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
            if (smallIconId != 0) {
                if (notification.contentView!=null)
                    notification.contentView.setViewVisibility(smallIconId, View.INVISIBLE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel androidChannel = new NotificationChannel("com.mapolbs.vizibeebritannia.ANDROID",
                        "ANDROID CHENNAL", NotificationManager.IMPORTANCE_DEFAULT);
                // Sets whether notifications posted to this channel should display notification lights
                androidChannel.enableLights(true);
                // Sets whether notification posted to this channel should vibrate.
                androidChannel.enableVibration(true);
                // Sets the notification light color for notifications posted to this channel
                androidChannel.setLightColor(Color.GREEN);
                // Sets whether notifications posted to this channel appear on the lockscreen or not
                androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

                notificationManager.createNotificationChannel(androidChannel);
            }

            //if(jobj.getString("type").equalsIgnoreCase("event"))
            notificationManager.notify(FCMConfig.PROJECTNOTIFICATION_ID, notification);
      //  else
         //   notificationManager.notify(FCMConfig.POLLNOTIFICATION_ID_BIG_IMAGE, notification);

        }catch (Exception ex)
        {
            Log.e("Notfication ex",ex.getMessage().toString());
        }
    }
    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            //Bitmap myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return myBitmap;



        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

   /* public int notcount (ArrayList<NotificationCountClass> object, String user_id) {
        int result = 1;

            for(int i=0;i<object.size();i++) {
                result = 1;
                if (user_id.equalsIgnoreCase(object.get(i).getUser_id())) {
                    result = object.get(i).getNot_count()+1;
                    object.get(i).setNot_count(result);
                }
            }

            if(result == 1)
                object.add(new NotificationCountClass(1,user_id));


        return result;
    }*/

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            /*List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningAppProcesses();
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }*/
            // Get a list of running tasks, we are only interested in the last one,
            // the top most so we give a 1 as parameter so we only get the topmost.
            List<ActivityManager.RunningAppProcessInfo> task = am.getRunningAppProcesses();
            String componentInfo = task.get(0).processName;
            if(componentInfo.equals(context.getPackageName()))
                isInBackground = false;


        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }





    public long getLongAsDate(String Date, String Time) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long datetime=0;
        try {
            java.util.Date date = dateformat.parse(Date+" "+Time);
            datetime= (date).getTime();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            //datetime =calendar.getTimeInMillis();
            return datetime;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ParseException ",e.getMessage().toString());
            return datetime;
        }


    }


}
