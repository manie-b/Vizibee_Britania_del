package com.mapolbs.vizibeebritannia.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapolbs.vizibeebritannia.DB.DBAdapter;
import com.mapolbs.vizibeebritannia.DB.DBHelper;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.ConnectionDetector;
import com.mapolbs.vizibeebritannia.Utilities.FCMConfig;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;
import com.mapolbs.vizibeebritannia.Utilities.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LoginScreen extends AppCompatActivity  {

    EditText edt_username;
    EditText edt_password;
    Button btn_submit;
    DBAdapter dbAdapter;
    String in_username;
    String in_Password;
    String ipaddress;
    String Serverlogindate;
    String exceptionerrors;
    static final String loginTable="Logindetails";
    String devicedate;
    int status = 0;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    UserSessionManager session;
    final Context context = this;
    private SQLiteDatabase mDb;
    boolean doubleBackToExitPressedOnce = false;
    public static final int RequestPermissionCode = 3;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        //verifyStoragePermissions(this);
        //verifylocationPermissions(this);
        init();
        initcallback();
    }

    public void init() {

        session = new UserSessionManager(getApplicationContext());
        cd = new ConnectionDetector(getApplicationContext());
        HashMap<String, String> ipadd = session.getipDetails();
        ipaddress = ipadd.get(UserSessionManager.IP_CONNECTIONSTRING);

        ImageView imageView = (ImageView)findViewById(R.id.imageView6) ;
        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in_out);
        imageView.startAnimation(animZoomIn);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        btn_submit = (Button) findViewById(R.id.btn_submit);
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        //edt_username.setText("BRT1000");
        //edt_password.setText("123");
    }

    public void initcallback() {

        if(checkPermission()){

        }
        else {

            requestPermission();
        }

        edt_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (checkPermission()) {
                        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
                        dateFormatter.setLenient(false);
                        Date today = new Date();
                        devicedate = dateFormatter.format(today);
                        in_username = edt_username.getText().toString();
                        in_Password = edt_password.getText().toString();

                        if (in_username.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please Enter Your User ID.", Toast.LENGTH_LONG).show();
                        } else if (in_Password.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please Enter Your Password.", Toast.LENGTH_LONG).show();
                        } else {

                            cd = new ConnectionDetector(getApplicationContext());
                            if (cd.isConnectingToInternet() == true) {
                                new Login().execute();
                            } else {
                                Toast.makeText(LoginScreen.this, "Please Check your Internet Connection.", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(LoginScreen.this, "Please Allow Camera and Storage access permission to Proceed.", Toast.LENGTH_LONG).show();
                        requestPermission();
                    }
                }
                return false;
            }

        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
                    dateFormatter.setLenient(false);
                    Date today = new Date();
                    devicedate = dateFormatter.format(today);
                    in_username = edt_username.getText().toString().trim();
                    in_Password = edt_password.getText().toString().trim();

                    if (in_username.equals("")) {
                        Toast.makeText(getApplicationContext(),"Please Enter Your User ID.",Toast.LENGTH_LONG).show();
                    } else if (in_Password.equals("")) {
                        Toast.makeText(getApplicationContext(),"Please Enter Your Password.",Toast.LENGTH_LONG).show();
                    } else {

                        cd = new ConnectionDetector(getApplicationContext());
                        if (cd.isConnectingToInternet() == true) {
                            new Login().execute();
                        }
                        else {
                            Toast.makeText(LoginScreen.this, "Please Check your Internet Connection.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Toast.makeText(LoginScreen.this, "Please Allow Camera and Storage access permission to Proceed.", Toast.LENGTH_LONG).show();
                    requestPermission();
                }

            }
        });

    }

    //onBackKeyPressed
    @Override
    public void onBackPressed() {

            super.onBackPressed();

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            LoginScreen.this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    class Login extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreen.this,R.style.MyAlertDialogStyle);
            pDialog.setMessage("Logging in Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.vizilogo));
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            String responsestr = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                dbAdapter.deletedofflineimagedata();
                url = new URL(ipaddress + "login?username=" + in_username + "&" + "password=" + in_Password+ "&" + "fcm_key=" +getFirebaseRegId());
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

                    File destination = new File(Environment.getExternalStorageDirectory().getPath(), File.separator + ".Vizibeekat_Root" + File.separator + "DoNotDelete_Vizibee");
                    if (destination.isDirectory()) {
                        String[] children = destination.list();
                        for (int i = 0; i < children.length; i++) {
                            new File(destination, children[i]).delete();
                        }
                    }
                   try{
                    JSONObject jObject = new JSONObject(responsestr);
                    JSONObject loggedUserObject = jObject.getJSONObject("logged_user");
                    MyApplication.getInstance().setLogo(loggedUserObject.has("logo") ? loggedUserObject.getString("logo") : "");
                    if (loggedUserObject.has("logo")) {
                        if (!loggedUserObject.getString("logo").toString().equalsIgnoreCase(""))
                        {

                                InputStream in = new java.net.URL(loggedUserObject.has("logo") ? loggedUserObject.getString("logo") : "").openStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(in);
                                saveimage(bitmap, loggedUserObject.has("logo") ? loggedUserObject.getString("logo") : "", "logo");

                        }

                    }
                    JSONArray formjarray = new JSONArray(jObject.getString("forms"));

                    for (int k = 0; k < formjarray.length(); k++) {
                        if (formjarray.getJSONObject(k).has("questions")) {
                            JSONArray quearray = new JSONArray(formjarray.getJSONObject(k).getString("questions"));
                            for (int l = 0; l < quearray.length(); l++) {
                                if (quearray.getJSONObject(l).getString("type").equalsIgnoreCase("qmir")) {

                                        if(!quearray.getJSONObject(l).getString("image_path").toString().equalsIgnoreCase("")||!quearray.getJSONObject(l).getString("image_path").toString().equalsIgnoreCase("null")) {
                                            InputStream in = new java.net.URL(quearray.getJSONObject(l).getString("image_path").toString()).openStream();
                                            Bitmap bitmap = BitmapFactory.decodeStream(in);
                                            saveimage(bitmap, quearray.getJSONObject(l).getString("image_path").toString(), quearray.getJSONObject(l).getString("id").toString());
                                        }
                                }
                                if (quearray.getJSONObject(l).has("is_sub_question")) {
                                    if (quearray.getJSONObject(l).getString("is_sub_question").equalsIgnoreCase("1")) {
                                        JSONArray valuesjarray = new JSONArray(quearray.getJSONObject(l).getString("values"));
                                        for (int m = 0; m < valuesjarray.length(); m++) {
                                            JSONArray subquejarray = new JSONArray(valuesjarray.getJSONObject(m).getString("sub_questions"));
                                            for (int n = 0; n < subquejarray.length(); n++) {
                                                if (subquejarray.getJSONObject(n).getString("type").equalsIgnoreCase("qmir")) {
                                                        if(!subquejarray.getJSONObject(n).getString("image_path").toString().equalsIgnoreCase("")|| !subquejarray.getJSONObject(n).getString("image_path").toString().equalsIgnoreCase("null")) {
                                                            InputStream in = new java.net.URL(subquejarray.getJSONObject(n).getString("image_path").toString()).openStream();
                                                            Bitmap bitmap = BitmapFactory.decodeStream(in);
                                                            saveimage(bitmap, subquejarray.getJSONObject(n).getString("image_path").toString(), subquejarray.getJSONObject(n).getString("sub_question_id").toString());
                                                        }
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }catch(JSONException ex)
                   {
            Log.e("login_response",ex.getMessage());
                   }

                }
            } catch (IOException e) {
                e.printStackTrace();
                exceptionerrors = e.getMessage().toString();
            }
            return responsestr;
        }

        protected void onPostExecute(String result) {

            if(result != ""){
                if(status == 200) {
                    try {
                        MyApplication.getInstance().setjsonstring(result);
                        JSONObject jObject = new JSONObject(result);
                        JSONObject loggedUserObject = jObject.getJSONObject("logged_user");
                        String loggedUserId = loggedUserObject.getString("logged_user_id");
                        String loggedUsername = loggedUserObject.getString("logged_username");
                        Serverlogindate = loggedUserObject.getString("logged_datetime");
                        String isSupervisor = loggedUserObject.getString("is_supervisor");
                        String supervisorId = loggedUserObject.getString("supervisor_id");
                        String supervisorName = loggedUserObject.getString("supervisor_name");
                        String projectId = loggedUserObject.getString("project_id");
                        //int notification_count = loggedUserObject.getInt("notification_count");
                        int notification_count = 0; //try 18-01-2020
                        MyApplication.getInstance().setProject_notificationcount(notification_count);
                        MyApplication.getInstance().setProjectid(loggedUserObject.getString("project_id"));
                        JSONObject project_settingsObject = jObject.getJSONObject("project_settings");

                        MyApplication.getInstance().setAuto_geo_location(project_settingsObject.getString("AUTOMATIC_GEO_LOCIATON"));
                        dbAdapter.deletelogindate();
                        dbAdapter.deletejsondata();



                        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        try {
                            Date date = format.parse(Serverlogindate);
                            System.out.println(dateFormatter.format(date));
                            if((dateFormatter.format(date)).equalsIgnoreCase(devicedate))
                            {
                                DBHelper dbHelper = new DBHelper(context);
                                mDb = dbHelper.getWritableDatabase();
                                dbAdapter.deletelogindate();
                                long rx = dbAdapter.deleteretailer();
                                long x = dbAdapter.logindate(Serverlogindate,in_username);
                                long xx = dbAdapter.jsondata(result);
                                if(x>0 && xx >0) {
                                    pDialog.dismiss();
                                   // LoginScreen.this.finish();
                                    if(project_settingsObject.getString("is_retailer").equalsIgnoreCase("1")) {
                                        Intent i = new Intent(LoginScreen.this, DefaultForm.class);
                                        session.createusersession(in_username, in_Password);
                                        startActivityForResult(i, 500);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        LoginScreen.this.finish();
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

                                        MyApplication.getInstance().setFinalobj(formarray);
                                        Intent i = new Intent(LoginScreen.this, SurveyForm.class);
                                        startActivityForResult(i, 500);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        LoginScreen.this.finish();
                                    }
                                }
                                else
                                {
                                    pDialog.dismiss();
                                    Toast.makeText(LoginScreen.this,"Error in Internal Database, Try again.",Toast.LENGTH_LONG).show();
                                }
                            }else
                            {
                                pDialog.dismiss();
                                Toast.makeText(LoginScreen.this,"Please Set Correct Date in Device.",Toast.LENGTH_LONG).show();
                            }
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                    catch(Exception ex){
                        pDialog.dismiss();
                        ex.printStackTrace();
                        exceptionerrors = ex.getMessage().toString();
                    }
                }else
                {
                    pDialog.dismiss();
                    if(status == 400) {
                        try {
                            JSONObject jObject = new JSONObject(result);
                            Toast.makeText(LoginScreen.this, jObject.getString("status").toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                            Log.e("Login Log", ex.getMessage().toString());
                        }
                    }else {
                        Toast.makeText(LoginScreen.this, "Server Connection Problem.", Toast.LENGTH_LONG).show();
                    }
                }
            } else{
                pDialog.dismiss();
                if(status == 400) {
                    try {
                        JSONObject jObject = new JSONObject(result);
                        Toast.makeText(LoginScreen.this, jObject.getString("status").toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Log.e("Login Log", ex.getMessage().toString());
                    }
                }else {
                    Toast.makeText(LoginScreen.this, "Server Connection Problem.", Toast.LENGTH_LONG).show();
                }
            }
        }
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


    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    };

    protected void saveimage(Bitmap result,String httpurl,String questionid){

        try{
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            Calendar c1 = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
            String devicedate1 = sdf1.format(c1.getTime());
            File destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root"+File.separator+"DoNotDelete_Vizibee");
            File downloadfile = new File(destination, "/IMG_"+questionid+".jpg");
             if(!destination.exists())
            {
                destination.mkdirs();
            }

            FileOutputStream fo;
            try {

                fo = new FileOutputStream(downloadfile);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }catch (Exception ex)
        {

        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(LoginScreen.this, new String[]
                {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                       // Manifest.permission.ACCESS_FINE_LOCATION
                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadsroragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean WritestoragePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    //boolean locationPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadsroragePermission && WritestoragePermission) {


                    }
                    else {


                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int cameraPermissionResult = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int readPermissionResult = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermissionResult = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //int locationPermissionResult = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        return cameraPermissionResult == PackageManager.PERMISSION_GRANTED &&
                readPermissionResult == PackageManager.PERMISSION_GRANTED &&
                writePermissionResult == PackageManager.PERMISSION_GRANTED;
                //locationPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
    public String getFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("FirebaseID", "Firebase reg id: " + regId);
        return regId;
    }

}
