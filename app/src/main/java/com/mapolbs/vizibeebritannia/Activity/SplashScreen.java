package com.mapolbs.vizibeebritannia.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mapolbs.vizibeebritannia.DB.DBAdapter;
import com.mapolbs.vizibeebritannia.DB.DBHelper;
import com.mapolbs.vizibeebritannia.Model.OfflineClass;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.ConnectionDetector;
import com.mapolbs.vizibeebritannia.Utilities.MultipartUtility;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;
import com.mapolbs.vizibeebritannia.Utilities.UserSessionManager;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

  ConnectionDetector cd;
  UserSessionManager session;
  ArrayList<String> imagefiles;
  private static int SPLASH_TIME_OUT = 3000;
  String ipaddress;
  String devicedate;
  ProgressDialog pDialog;
  DBAdapter dbAdapter;
  JSONArray jarray;
  String surveyid="";
  ArrayList<OfflineClass> offlinelist;
  private long totalSize = 0;
  final Context context = this;
  private SQLiteDatabase mDb;
  int uploadcount = 0;
  private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
  private static final String IMAGE_DIRECTORY_NAME = "vizibee";
  JSONObject jObject = null;
  Bundle bundle =null;
  String msgcount ="";
  String msgdatas ="";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);
    init();
    bundle = getIntent().getExtras();
    if (null!=bundle) {
      msgcount = bundle.containsKey("count")?  bundle.getString("count"):"";
      msgdatas = bundle.containsKey("datas")?  bundle.getString("datas"):"";

    }

  }

  public void init()
  {
    try
    {
      cd = new ConnectionDetector(getApplicationContext());

      session = new UserSessionManager(getApplicationContext());
      //session.createIpSession("http://britannia.vizibee.in/api/"); //Live
      //session.createIpSession("http://35.237.67.215/britannia/public/api/");//new by arun
      //session.createIpSession("http://reports.vizibee.in/api/"); //Live
      //session.createIpSession("http://vizibee.in/api/"); //Local
      //session.createIpSession("http://106.51.1.175:80/survey_apptest/public/api/"); //Local
      //session.createIpSession("http://106.51.1.175/survey/demo/public/api/"); //Demo

      // session.createIpSession("http://vizibee.in/api/"); //Local
      //session.createIpSession("http://106.51.1.175/survey/v3.0/public/api/"); //Local
      //session.createIpSession("http://192.168.0.87:81/survey/v3.0/public/api/"); //Local
      //session.createIpSession("http://106.51.1.175/vizibee/public/api/"); //Local
      //session.createIpSession("http://106.51.1.175/survey_4/public/api/"); //Local
      //session.createIpSession("http://192.168.0.17/survey/v3.0/public/api/"); //local db mani
      //session.createIpSession("http://106.51.1.175/survey/v3.0/public/api/"); //Local db new
      session.createIpSession("http://106.51.1.175/survey/v3.0/public/api/");  //Local db new 28-01-2020

      HashMap<String, String> ipadd = session.getipDetails();
      ipaddress = ipadd.get(UserSessionManager.IP_CONNECTIONSTRING);

      MyApplication.getInstance().setSurveystarttime(getDateTime());

      dbAdapter = new DBAdapter(this);
      dbAdapter.open();

      if(cd.isConnectingToInternet())
      {
        DBHelper dbHelper = new DBHelper(SplashScreen.this);
        mDb = dbHelper.getReadableDatabase();
        String jsonstring = "";
        offlinelist = new ArrayList<OfflineClass>();
        Cursor c1 = mDb.rawQuery("SELECT surveyid,stringdata FROM Offlinedetails ", null);
        if (c1.moveToFirst()) {
          do {
            //assing values
            jsonstring = c1.getString(c1.getColumnIndex("stringdata"));
            surveyid = c1.getString(c1.getColumnIndex("surveyid"));
            offlinelist.add(new OfflineClass(new JSONObject(jsonstring),surveyid));
          } while (c1.moveToNext());
        }
        c1.close();

        if(offlinelist.size()>0) {

          pDialog = new ProgressDialog(SplashScreen.this,R.style.MyAlertDialogStyle);
          pDialog.setMessage("Uploading Offline Survey Data Please Wait...");
          pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.vizilogo));
          pDialog.setIndeterminate(false);
          pDialog.setCanceledOnTouchOutside(false);
          uploadcount=0;
          try {
            new PostDataTask().execute(offlinelist.get(uploadcount).getJobj().getString("data"),offlinelist.get(uploadcount).getJobj().getString("loggedUser"),offlinelist.get(uploadcount).getJobj().getString("surveystarttime"),offlinelist.get(uploadcount).getJobj().getString("surveyDate"),offlinelist.get(uploadcount).getSurveyid(),offlinelist.get(uploadcount).getJobj().getString("settings"));

          }catch (Exception e){
            Log.e("test",e.getMessage());
          }

        }else{
          intent();
        }
      }else{
        intent();
      }



    }
    catch(Exception ex)
    {
      Log.e("Splash INIT",ex.getMessage().toString());
    }

  }

  public void intent()
  {
    try{
      DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
      dateFormatter.setLenient(false);
      Date today = new Date();
      devicedate = dateFormatter.format(today);
      DBHelper dbHelper = new DBHelper(context);
      mDb = dbHelper.getReadableDatabase();

      Cursor c1 = mDb.rawQuery("SELECT stringdata FROM Stringdetails ", null);
      if(c1.moveToFirst()){
        do{
          //assing values
          String jsonstring = c1.getString(c1.getColumnIndex("stringdata"));
          jObject = new JSONObject(jsonstring);
          MyApplication.getInstance().setjsonstring(jsonstring);

        }while(c1.moveToNext());
      }
      c1.close();

      Cursor c = mDb.rawQuery("SELECT logindate FROM Logindetails ", null);
      if(c.moveToFirst()){
        do{
          //assing values
          String serverlogindate = c.getString(c.getColumnIndex("logindate"));

          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
          try {
            Date date = format.parse(serverlogindate);
            System.out.println(dateFormatter.format(date));
            if((dateFormatter.format(date)).equalsIgnoreCase(devicedate))
            {
              new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                  JSONObject project_settingsObject = null;
                  try {
                    project_settingsObject = jObject.getJSONObject("project_settings");
                    if(project_settingsObject.getString("is_retailer").equalsIgnoreCase("1")) {
                      Intent i = new Intent(SplashScreen.this, DefaultForm.class);
                      if (!msgcount.equalsIgnoreCase(""))
                        i.putExtra("count",msgcount);
                      if (!msgdatas.equalsIgnoreCase(""))
                        i.putExtra("datas",msgdatas);
                      startActivity(i);
                      overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                      finish();
                    }else
                    {
                      JSONArray formarray = new JSONArray();
                      MyApplication.getInstance().setRetailerid(project_settingsObject.getString("process_id").toString());
                      MyApplication.getInstance().setDonetype(String.valueOf(true));

                      JSONObject formobj = new JSONObject();
                      formobj.put("form_id", "1");
                      JSONArray questionarray = new JSONArray();
                      JSONObject questionobj = new JSONObject();
                      questionobj.put("unique_id","1");
                      questionobj.put("question_id","1");
                      questionobj.put("id","1");
                      questionobj.put("question_type", "qmdts");
                      questionobj.put("is_required", "1");
                      questionobj.put("answer_id", project_settingsObject.getString("process_id").toString());
                      questionobj.put("is_other", "");
                      questionobj.put("others", "");
                      questionobj.put("answer", project_settingsObject.getString("process_id").toString());
                      questionobj.put("form_id", "");
                      questionobj.put("imagename", "");
                      questionobj.put("imagepath", "");
                      questionarray.put(questionobj);
                      formobj.put("questions", questionarray);
                      formarray.put(formobj);



                      JSONObject visitformobj = new JSONObject();
                      visitformobj.put("form_id", "");
                      JSONArray visitquestionarray = new JSONArray();
                      JSONObject visitquestionobj = new JSONObject();
                      visitquestionobj.put("unique_id","6");
                      visitquestionobj.put("question_id","1");
                      visitquestionobj.put("id","1");
                      visitquestionobj.put("question_type", "");
                      visitquestionobj.put("is_required", "1");
                      visitquestionobj.put("answer_id", "true");
                      visitquestionobj.put("is_other", "");
                      visitquestionobj.put("others", "");
                      visitquestionobj.put("answer", "true");
                      visitquestionobj.put("form_id", "");
                      visitquestionobj.put("imagename", "");
                      visitquestionobj.put("imagepath", "");
                      visitquestionarray.put(visitquestionobj);
                      visitformobj.put("questions", visitquestionarray);
                      formarray.put(visitformobj);




                      MyApplication.getInstance().setAuto_geo_location(project_settingsObject.getString("AUTOMATIC_GEO_LOCIATON"));
                      MyApplication.getInstance().setRetailerid(project_settingsObject.getString("process_id").toString());
                      MyApplication.getInstance().setDonetype(String.valueOf(true));
                      MyApplication.getInstance().setFinalobj(formarray);
                      Intent i = new Intent(SplashScreen.this, SurveyForm.class);
                      if (!msgcount.equalsIgnoreCase(""))
                        i.putExtra("count",msgcount);
                      if (!msgdatas.equalsIgnoreCase(""))
                        i.putExtra("datas",msgdatas);
                      startActivityForResult(i, 500);
                      overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }

                  } catch (JSONException e) {
                    Log.e("Splash",e.getMessage());
                    movetologin();
                  }
                }
              }, SPLASH_TIME_OUT);
            }else
            {

              movetologin();
            }
          } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          //Do something Here with values

        }while(c.moveToNext());
      }else
      {
        movetologin();
      }
      c.close();
      mDb.close();
    }
    catch(Exception ex)
    {
      Log.e("Splash INIT",ex.getMessage().toString());
    }
  }
  private void movetologin(){

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent i = new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
      }
    }, SPLASH_TIME_OUT);
  }
  private class PostDataTask extends AsyncTask<String, Integer, String> {
    JSONArray formarray;
    JSONObject logeduser;
    String  surveystarttime;
    String surveydate;
    String surveyids="";
    String settings="";
    int statusCode =0;
    @Override
    protected void onPreExecute() {
      if (!pDialog.isShowing())
        pDialog.show();

      pDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
      // Making progress bar visible
      // updating progress bar value
      pDialog.setProgress(progress[0]);

      // updating percentage value
      //txtPercentage.setText(String.valueOf(progress[0]) + "%");
    }

    @Override
    protected String doInBackground(String... params) {
      Log.e("PostDataTask : ", "doInBackground");
      try {
        formarray = new JSONArray(params[0]);
        logeduser = new JSONObject(params[1]);
        surveystarttime =params[2];
        surveydate = params[3];
        surveyids =  params[4];
        settings = params[5];
        String jsonStr = uploadFile(params[0]);

        if (jsonStr != null) {
          // try {
          Log.e("PostDataTask Status: ", jsonStr);

        } else {
          Log.e("PostDataTask Status: ", "Not Yet Received");
        }

        return jsonStr;
      }catch(Exception ex)
      {
        Log.e("Exception",ex.getMessage().toString());
        return null;
      }
    }

    @SuppressWarnings("rawtypes")
    private String uploadFile(String jsonString) {

      String charset = "UTF-8";
      String responseString = null;
      try {
        MultipartUtility client = new MultipartUtility(ipaddress + "post_data", charset);

        imagefiles = new ArrayList<String>();
        try {
          for (int k = 0; k < formarray.length(); k++) {
            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
            for (int l = 0; l < questionsarray.length(); l++) {
              if (questionsarray.getJSONObject(l).getString("question_type").toString().equalsIgnoreCase("qmpi")||questionsarray.getJSONObject(l).getString("question_type").toString().equalsIgnoreCase("qmar")||questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase("11")) {
                // Adding file data to http body
                if(questionsarray.getJSONObject(l).has("imagepath")){
                  if (questionsarray.getJSONObject(l).getString("imagepath").toString().equalsIgnoreCase("null") || questionsarray.getJSONObject(l).getString("imagepath").toString().equalsIgnoreCase("")) {

                  } else {
                    File file = new File(questionsarray.getJSONObject(l).getString("imagepath").toString());
                    String fname = file.getName();
                    int pos = fname.lastIndexOf(".");
                    if (pos > 0) {
                      fname = fname.substring(0, pos);
                    }
                    if(file.exists()) {
                      long filelength = file.length() / 1024;
                      if (filelength > 200) {

                        if(questionsarray.getJSONObject(l).getString("question_type").toString().equalsIgnoreCase("qmpi")) {
                          Bitmap bmp = BitmapFactory.decodeFile(questionsarray.getJSONObject(l).getString("imagepath").toString());
                          ByteArrayOutputStream bos = new ByteArrayOutputStream();
                          bmp.compress(Bitmap.CompressFormat.JPEG, 90, bos);

                                                    /*InputStream in = new ByteArrayInputStream(bos.toByteArray());
                                                    ContentBody foto = new InputStreamBody(in, "image/jpeg", "filename");*/
                          client.addFilePart(fname, file);
                          //entity.addPart(fname, foto);
                          imagefiles.add(questionsarray.getJSONObject(l).getString("imagepath").toString());
                        }else{
                          Bitmap bmp = BitmapFactory.decodeFile(questionsarray.getJSONObject(l).getString("imagepath").toString());
                          ByteArrayOutputStream bos = new ByteArrayOutputStream();
                          //bmp.compress(Bitmap.CompressFormat.JPEG, 90, bos);
                          client.addFilePart( fname, file);
                          imagefiles.add(questionsarray.getJSONObject(l).getString("imagepath").toString());
                        }

                      } else {
                                               /* Bitmap bmp = BitmapFactory.decodeFile(questionsarray.getJSONObject(l).getString("imagepath").toString());
                                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 90, bos);*/
                        client.addFilePart(fname, file);
                        imagefiles.add(questionsarray.getJSONObject(l).getString("imagepath").toString());
                      }
                    }
                  }
                }}
            }

          }
        } catch (Exception ex) {
          Log.e("Json exception", ex.getMessage().toString());
        }

        // Extra parameters if you want to pass to server
        client.addFormField("forms", jsonString);
        client.addFormField("loggedUser",logeduser.toString());
        client.addFormField("surveystarttime",surveystarttime);
        client.addFormField("surveyDate",surveydate);
        client.addFormField("settings",settings.toString());

        List<String> response = client.finish();
        statusCode = Integer.parseInt(response.get(0).toString());
        if (statusCode == 200) {
          // Server response
          // responseString ="Retailer Name";
          responseString = response.get(1).toString();
        } else {
          responseString = "Error occurred! Http Status Code: "
                  + statusCode;
        }

      } catch (ClientProtocolException e) {
        responseString = e.toString();
      } catch (IOException e) {
        responseString = e.toString();
      }

      return responseString;

    }

    @Override
    protected void onPostExecute(final String result) {
      super.onPostExecute(result);
      if (statusCode == 200) {

        if (uploadcount == offlinelist.size()-1) {
          if (pDialog.isShowing())
            pDialog.dismiss();

        }
        uploadcount++;

      }
      Log.e("PostDataPostExecute: ", result);
      SplashScreen.this.runOnUiThread(new Runnable() {

        @Override
        public void run() {

          try{
            if (statusCode == 200) {
              File destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root"+File.separator+"CaptureImages");
              if (destination.exists()) {
                if (destination.isDirectory()) {

                  for(int i=0;i<imagefiles.size();i++) {
                    // Adding file data to http body
                    if (imagefiles.get(i).toString() == null || imagefiles.get(i).toString().isEmpty()) {

                    } else {
                      new File(imagefiles.get(i).toString()).delete();
                    }
                  }
                }
              }
              DBHelper dbHelper = new DBHelper(context);
              mDb = dbHelper.getWritableDatabase();
              long x = dbAdapter.deletedata(surveyids);

              if(x>0) {
                if (uploadcount < offlinelist.size()) {
                  new PostDataTask().execute(offlinelist.get(uploadcount).getJobj().getString("data"), offlinelist.get(uploadcount).getJobj().getString("loggedUser"), offlinelist.get(uploadcount).getJobj().getString("surveydate"), offlinelist.get(uploadcount).getSurveyid(), offlinelist.get(uploadcount).getJobj().getString("settings"));
                } else {
                  File captureimgdestination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root"+File.separator+"CaptureImages");
                  if (captureimgdestination.isDirectory())
                  {
                    String[] captureimgchildren = captureimgdestination.list();
                    for (int i = 0; i < captureimgchildren.length; i++)
                    {
                      new File(captureimgdestination, captureimgchildren[i]).delete();
                    }
                  }

                  File  audiodestination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root/"+AUDIO_RECORDER_FOLDER);
                  if (audiodestination.isDirectory())
                  {
                    String[] audiochildren = audiodestination.list();
                    for (int i = 0; i < audiochildren.length; i++)
                    {
                      new File(audiodestination, audiochildren[i]).delete();
                    }
                  }
                  if (pDialog.isShowing())
                    pDialog.dismiss();
                  intent();
                }
              }else
              {
                Toast.makeText(SplashScreen.this, "Failed to Delete Offline Data",
                        Toast.LENGTH_LONG).show();
              }

            }else if(statusCode == 400)
            {
              try {
                pDialog.setProgress(0);
                JSONObject jObject = new JSONObject(result);
                Toast.makeText(SplashScreen.this, jObject.getString("status").toString(), Toast.LENGTH_LONG).show();
              } catch (Exception ex) {
                Log.e("Login Log", ex.getMessage().toString());
              }
            }
            else {
              pDialog.setProgress(0);
              Toast.makeText(SplashScreen.this, "Failed to upload "+result+" Status code="+statusCode,
                      Toast.LENGTH_LONG).show();
              if (pDialog.isShowing())
                pDialog.dismiss();
            }
          }catch(Exception ex){
            Log.e("Json Exception",ex.getMessage().toString());
          }
        }
      });

    }
  }

  private String getDateTime() {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
    return sdf.format(c.getTime());
  }

}