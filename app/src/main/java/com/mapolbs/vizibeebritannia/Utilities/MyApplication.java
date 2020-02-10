package com.mapolbs.vizibeebritannia.Utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import androidx.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RAMMURALI on 3/30/2017.
 */

public class MyApplication extends android.app.Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();
    private static volatile MyApplication mInstance;
    String jsonstring;
    String retailerid;
    String mapaddress;
    ImageView imgsource;
    int camerabar = 0;
    TextView txtsource;
    JSONArray finalobj;
    String selectedmultispinnerid;
    String imagetype;
    String logeduserjsonstring;
    String donetype;
    String formid;
    String logo;
    String mapuniqueid;
    JSONArray mapformarray;
    TextView txtlatlong;
    String auto_geo_location;
    String projectname;
    String projectlogo;
    ImageView imgmapsrc;
    JSONArray formarray;
    JSONArray formrulearray;
    String localformid;
    String nextformid;
    Boolean isnextformid;
    String projectid;
    String surveystarttime;
    View view;
    int project_notificationcount =0;
    Button del_button;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(context);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        if ( isExternalStorageWritable() ) {

            File appDirectory = new File( Environment.getExternalStorageDirectory() + "/VizibeeLog" );
            File logDirectory = new File( appDirectory + "/log" );
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());
            //File logFile = new File( logDirectory, "logcat" + System.currentTimeMillis() + ".txt" );
            File logFile = new File( logDirectory, "logcat" + currentDateandTime + ".txt" );
            // create app folder
            if ( !appDirectory.exists() ) {
                appDirectory.mkdir();
            }

            // create log folder
            if ( !logDirectory.exists() ) {
                logDirectory.mkdir();
            }

            // clear the previous logcat and then write the new one to the file
            try {
                Process process = Runtime.getRuntime().exec("logcat -c");
                process = Runtime.getRuntime().exec("logcat -f " + logFile);
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } else if ( isExternalStorageReadable() ) {
            // only readable
        } else {
            // not accessible
        }
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated"+activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted"+activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {

                Log.d(TAG, "onActivityResumed:"+activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, "onActivityStopped:"+activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.d(TAG, "onActivitySaveInstance:"+activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, "onActivityDestroyed:"+activity.getLocalClassName());
            }
        });
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;
    }



public  MyApplication(){

}
    public static  MyApplication getInstance() {
        if (mInstance == null){ //Check for the first time
            synchronized (MyApplication.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (mInstance == null) mInstance = new MyApplication();
            }

        }
        return mInstance;
    }


    public String getjsonstring() {
        return jsonstring;
    }

    public void setjsonstring(String jsonstring) {
        this.jsonstring = jsonstring;
    }

    public String getRetailerid() {
        return retailerid;
    }

    public void setRetailerid(String retailerid) {
        this.retailerid = retailerid;
    }

    public String getMapaddress() {
        return mapaddress;
    }

    public void setMapaddress(String mapaddress) {
        this.mapaddress = mapaddress;
    }

    public ImageView getImgsource() {
        return imgsource;
    }

    public void setImgsource(ImageView imgsource) {
        this.imgsource = imgsource;
    }

    public int getCamerabar() {
        return camerabar;
    }

    public void setCamerabar(int camerabar) {
        this.camerabar = camerabar;
    }

    public TextView getTxtsource() {
        return txtsource;
    }

    public void setTxtsource(TextView txtsource) {
        this.txtsource = txtsource;
    }

    public JSONArray getFinalobj() {
        return finalobj;
    }

    public void setFinalobj(JSONArray finalobj) {
        this.finalobj = finalobj;
    }

    public String getSelectedmultispinnerid() {
        return selectedmultispinnerid;
    }

    public void setSelectedmultispinnerid(String selectedmultispinnerid) {
        this.selectedmultispinnerid = selectedmultispinnerid;
    }

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public String getLogeduserjsonstring() {
        return logeduserjsonstring;
    }

    public void setLogeduserjsonstring(String logeduserjsonstring) {
        this.logeduserjsonstring = logeduserjsonstring;
    }

    public String getDonetype() {
        return donetype;
    }

    public void setDonetype(String donetype) {
        this.donetype = donetype;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public TextView getTxtlatlong() {
        return txtlatlong;
    }

    public void setTxtlatlong(TextView txtlatlong) {
        this.txtlatlong = txtlatlong;
    }

    public String getMapuniqueid() {
        return mapuniqueid;
    }

    public void setMapuniqueid(String mapuniqueid) {
        this.mapuniqueid = mapuniqueid;
    }

    public JSONArray getMapformarray() {
        return mapformarray;
    }

    public void setMapformarray(JSONArray mapformarray) {
        this.mapformarray = mapformarray;
    }

    public String getAuto_geo_location() {
        return auto_geo_location;
    }

    public void setAuto_geo_location(String auto_geo_location) {
        this.auto_geo_location = auto_geo_location;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectlogo() {
        return projectlogo;
    }

    public void setProjectlogo(String projectlogo) {
        this.projectlogo = projectlogo;
    }

    public ImageView getImgmapsrc() {
        return imgmapsrc;
    }

    public void setImgmapsrc(ImageView imgmapsrc) {
        this.imgmapsrc = imgmapsrc;
    }

    public JSONArray getFormarray() {
        return formarray;
    }

    public void setFormarray(JSONArray formarray) {
        this.formarray = formarray;
    }

    public JSONArray getFormrulearray() {
        return formrulearray;
    }

    public void setFormrulearray(JSONArray formrulearray) {
        this.formrulearray = formrulearray;
    }

    public String getLocalformid() {
        return localformid;
    }

    public void setLocalformid(String localformid) {
        this.localformid = localformid;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getNextformid() {
        return nextformid;
    }

    public void setNextformid(String nextformid) {
        this.nextformid = nextformid;
    }

    public Boolean getIsnextformid() {
        return isnextformid;
    }

    public void setIsnextformid(Boolean isnextformid) {
        this.isnextformid = isnextformid;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getSurveystarttime() {
        return surveystarttime;
    }

    public void setSurveystarttime(String surveystarttime) {
        this.surveystarttime = surveystarttime;
    }

    public int getProject_notificationcount() {
        return project_notificationcount;
    }

    public void setProject_notificationcount(int project_notificationcount) {
        this.project_notificationcount = project_notificationcount;
    }


    /*13-01-2020 mani try*/

    public Button getDel_button() {
        return del_button;
    }

    public void setDel_button(Button del_button) {
        this.del_button = del_button;
    }
}
