package com.mapolbs.vizibeebritannia.Utilities;



/**
 * Created by ARUN on 12/12/2018.
 */

public class FCMConfig {

    //Global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    //Broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    //Id to handle the notification in the notification tray
    public static final int PROJECTNOTIFICATION_ID = 100;
    public static int NOTIFICATION_COUNT = 0;
    public static int PROJECTNOTIFICATION_COUNT = 0;


   //public static ArrayList<NotificationCountClass> notcount = new ArrayList<NotificationCountClass>();

    public static final String SHARED_PREF = "ah_firebase";
}