package com.mapolbs.vizibeebritannia.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mapolbs.vizibeebritannia.CustomAdapter.DropDownListAdapter;
import com.mapolbs.vizibeebritannia.CustomAdapter.DropdownCustomAdapter;
import com.mapolbs.vizibeebritannia.CustomAdapter.GridFilterCustomAdapter;
import com.mapolbs.vizibeebritannia.CustomAdapter.ListCustomAdapter;
import com.mapolbs.vizibeebritannia.CustomAdapter.MultiCheckListCustomAdapter;
import com.mapolbs.vizibeebritannia.CustomAdapter.SimpleDropdownCustomAdapter;
import com.mapolbs.vizibeebritannia.DB.DBAdapter;
import com.mapolbs.vizibeebritannia.DB.DBHelper;
import com.mapolbs.vizibeebritannia.Model.DisplayorderClass;
import com.mapolbs.vizibeebritannia.Model.DropDownData;
import com.mapolbs.vizibeebritannia.Model.GridFilterClass;
import com.mapolbs.vizibeebritannia.Model.GridFromLayoutdata;
import com.mapolbs.vizibeebritannia.Model.ListClass;
import com.mapolbs.vizibeebritannia.Model.SimpleDropDownData;
import com.mapolbs.vizibeebritannia.Model.SimpleListClass;
import com.mapolbs.vizibeebritannia.Model.Surveyformclass;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.CameraUtility;
import com.mapolbs.vizibeebritannia.Utilities.CaptureActivityPortrait;
import com.mapolbs.vizibeebritannia.Utilities.ConnectionDetector;
import com.mapolbs.vizibeebritannia.Utilities.GPSTracker;
import com.mapolbs.vizibeebritannia.Utilities.ImagePathUtil;
import com.mapolbs.vizibeebritannia.Utilities.MultipartUtility;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;
import com.mapolbs.vizibeebritannia.Utilities.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.mapolbs.vizibeebritannia.Utilities.MyApplication.TAG;

public class SurveyForm extends Activity {
    ViewFlipper viewflipper;
    Button imgnext;
    Button imgprev;
    ArrayList<DropDownData> Dropdownlist;
    ProgressDialog pDialog;
    ArrayList<String> startaudio;
    ArrayList<String> stopaudio;
    JSONArray quejarray;
    JSONArray formarray;
    JSONArray gridformarray;
    String description;
    Button btnsubmit;
    JSONObject formobj;
    JSONObject questionobj;
    JSONArray questionarray;
    String retailerid;
    ImageView mapimgsrc;
    String strimagename = "";
    String strimagepath = "";
    File captureimgfile;
    int resID = 0;
    int uniqueid = 20;
    int radioid = 12000;
    int quessid = 0;
    int isfiltergrid = 0;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    String locformid="";
    ArrayList<EditText> listedittext;
    private int TAKE_PHOTO_CODE = 0;
    private Bitmap photo = null;
    ImageView imgsource;
    TextView txtvisitdetails;
    int camerabar = 0;
    public TextView maptextview;
    public static boolean[] checkSelected;
    private boolean expanded;
    private PopupWindow pw;
    JSONArray formsjarray = null;
    int gridformcontrolheight = 0;
    int gridformemptyheight = 0;
    String[] surveyformtitle;
    String[] surveyformids;
    private Uri fileUri;
    String nextformid = "";
    ArrayList<Integer> sessiondisplay;
    int ids = 0;
    String ipaddress;
    String strdependids="";
    int pagecount=0;
    int titlecount = 0;
    int tablerowid = 5000;
    String absfilepath="";
    String btn1code = "",btn2code="";
    private long totalSize = 0;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 147;
    boolean doubleBackToExitPressedOnce = false;
    private static final int CAMERA_REQUEST = 1888;
    ArrayList<String> imagefiles;
    ArrayList<DisplayorderClass> displayclass;
    ArrayList<Surveyformclass> surveyformclass;
    private ImagePathUtil imagePathUtil;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String IMAGE_DIRECTORY_NAME = "vizibee";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    JSONArray formsarray;
    private GoogleApiClient client;
    UserSessionManager session;
    ConnectionDetector cd;
    DBAdapter dbAdapter;
    int PERMISSION_REQUEST_COARSE_LOCATION = 100;
    private SQLiteDatabase mDb;
    //AudioRecorder
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    private String outputFile = null;
    final Boolean click = true;
    String devicedate;
    String devicedate1;
    String Userid;
    String donetype;
    JSONArray formrule_array;
    int marginleftpx =  0;
    private static int marginleft = 0;
    int marginrightpx =  0;
    private static int marginright = 0;
    ImageView imghome;
    int controlmarginleftpx =  0;
    private static int controlmarginleft = 0;
    int controlmarginrightpx =  0;
    private static int controlmarginright = 0;
    ArrayList<String> retlist;
    GPSTracker gps;
    JSONObject settings_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_form);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        session = new UserSessionManager(getApplicationContext());
        imagePathUtil = new ImagePathUtil(this);
        cd = new ConnectionDetector(getApplicationContext());
        HashMap<String, String> ipadd = session.getipDetails();
        ipaddress = ipadd.get(UserSessionManager.IP_CONNECTIONSTRING);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        devicedate = sdf.format(c.getTime());
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        try {
            JSONObject jobj = new JSONObject(MyApplication.getInstance().getjsonstring());
            JSONObject jsonobj = jobj.getJSONObject("logged_user");
            MyApplication.getInstance().setLogeduserjsonstring(jsonobj.toString());
            Userid = jsonobj.getString("logged_user_id");

            formsjarray = new JSONArray(jobj.getString("forms"));
        }catch (Exception ex)
        {

        }

        verifyStoragePermissions(this);
        init();
        initcallback();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    public void init() {
        try {

            btnsubmit = (Button)findViewById(R.id.btnsubmit);
            btnsubmit.setVisibility(View.INVISIBLE);
            imghome = (ImageView)findViewById(R.id.imghome);
            txtvisitdetails = (TextView) findViewById(R.id.txt_visitdetails);
            formarray = MyApplication.getInstance().getFinalobj();
            displayclass = new ArrayList<DisplayorderClass>();
            surveyformclass = new ArrayList<Surveyformclass>();
            sessiondisplay = new ArrayList<Integer>();
            viewflipper = (ViewFlipper) findViewById(R.id.view_flipper);
            imgnext = (Button) findViewById(R.id.btnnext);
            imgprev = (Button) findViewById(R.id.btnprevious);
            JSONObject jobj = new JSONObject(MyApplication.getInstance().getjsonstring());
            retailerid = MyApplication.getInstance().getRetailerid();
            formsjarray = new JSONArray(jobj.getString("forms"));
            surveyformtitle = new String[formsjarray.length()];
            surveyformids = new String[formsjarray.length()];
            sessiondisplay.add(0);
            MyApplication.getInstance().setNextformid("");
            marginleftpx =  getResources().getDimensionPixelSize(R.dimen.marginleft);
            marginleft = (int)(marginleftpx / getResources().getDisplayMetrics().density);
            marginrightpx =  getResources().getDimensionPixelSize(R.dimen.marginright);
            marginright = (int)(marginrightpx / getResources().getDisplayMetrics().density);


            controlmarginleftpx =  getResources().getDimensionPixelSize(R.dimen.controlmarginleft);
            controlmarginleft = (int)(controlmarginleftpx / getResources().getDisplayMetrics().density);
            controlmarginrightpx =  getResources().getDimensionPixelSize(R.dimen.controlmarginright);
            controlmarginright = (int)(controlmarginrightpx / getResources().getDisplayMetrics().density);

           startaudio =new ArrayList<String>();
           stopaudio =new ArrayList<String>();


            MyApplication.getInstance().setFormid("");
            try {
                for (int i = 0; i < formsjarray.length(); i++) {
                    if(formsjarray.getJSONObject(i).has("form_filters"))
                    {   int retailerformcount = 0;
                        JSONArray jarray = new JSONArray(formsjarray.getJSONObject(i).getString("form_filters").toString());
                        for(int k=0;k<jarray.length();k++)
                        {
                            if(jarray.getJSONObject(k).getString("id").toString().equalsIgnoreCase(retailerid))
                            {
                                retailerformcount++;
                            }
                        }
                        if(retailerformcount > 0)
                        {
                            if (MyApplication.getInstance().getDonetype().equalsIgnoreCase("TRUE")) {
                                if (!formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("DEFAULT") && !formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("SUBMIT") && !formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("GRID")) {
                                    surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                                } else if (formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("SUBMIT")) {
                                    surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                                } else if (formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("GRID")) {

                                    surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                                }
                            } else {
                                if (formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("SUBMIT")) {
                                    surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                                }
                                btnsubmit.setVisibility(View.VISIBLE);
                                imgnext.setVisibility(View.INVISIBLE);
                            }
                        }
                    }else {
                        if (MyApplication.getInstance().getDonetype().equalsIgnoreCase("TRUE")) {
                            if (!formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("DEFAULT") && !formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("SUBMIT") && !formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("GRID")) {
                                surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                            } else if (formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("SUBMIT")) {
                                surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                            } else if (formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("GRID")) {

                                surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                            }
                        } else {
                            if (formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("SUBMIT")) {
                                surveyformclass.add(new Surveyformclass(formsjarray.getJSONObject(i).getString("form_id").toString(), formsjarray.getJSONObject(i).getString("title").toString(), formsjarray.getJSONObject(i), formsjarray.getJSONObject(i).getString("type").toString(), new JSONArray(formsjarray.getJSONObject(i).has("form_rules") ? formsjarray.getJSONObject(i).getString("form_rules").toString() : "[]")));
                            }
                            btnsubmit.setVisibility(View.VISIBLE);
                            imgnext.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }catch(Exception ex) {

            }
            pagesetup(surveyformclass.get(0).getJsonobj());

        } catch (Exception ex) {
            Log.e("Init Error", ex.getMessage().toString());
        }
    }

    public void pagesetup(JSONObject jobj)
    {
        locformid = "";
        String quesid = "";
        try{
                formobj = new JSONObject();
                int subqueid= 1000;
                listedittext = new ArrayList<EditText>();
                 locformid = jobj.getString("form_id").toString();

                txtvisitdetails.setText(jobj.getString("title").toString());

                displayclass.add(new DisplayorderClass(jobj.getString("form_id").toString(),jobj.getString("title").toString(),jobj,jobj.getString("type").toString(),new JSONArray(jobj.has("form_rules")?jobj.getString("form_rules").toString():"[]")));
                if (!jobj.getString("type").toString().equalsIgnoreCase("DEFAULT") && !jobj.getString("type").toString().equalsIgnoreCase("GRID")) {

                    formobj.put("form_id", jobj.getString("form_id").toString());
                    description = jobj.getString("description").toString();
                    quejarray = new JSONArray(jobj.getString("questions").toString());
                    formrule_array = new JSONArray(jobj.has("form_rules")?jobj.getString("form_rules").toString():"[]");
                    ScrollView scroll = new ScrollView(this);
                    ScrollView.LayoutParams scrollparams = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    scrollparams.setMargins(marginleft, 0, marginright, 0);
                    scroll.setLayoutParams(scrollparams);
                    scroll.setFocusableInTouchMode(true);
                    scroll.setFocusable(true);
                    scroll.setVerticalScrollBarEnabled(false);
                    scroll.setHorizontalScrollBarEnabled(false);
                    LinearLayout li = new LinearLayout(this);
                    LinearLayout.LayoutParams liparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    li.setLayoutParams(liparams);
                    li.setFocusableInTouchMode(true);
                    li.setFocusable(true);
                    li.setTag(jobj.getString("form_id").toString());
                    li.setOrientation(LinearLayout.VERTICAL);
                    li.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                    li.requestFocus(View.FOCUS_FORWARD);
                    questionarray = new JSONArray();
                    for (int j = 0; j < quejarray.length(); j++) {
                        uniqueid++;
                        quesid = quejarray.getJSONObject(j).getString("id").toString();
                        questionobj = new JSONObject();
                        questionobj.put("unique_id", uniqueid);
                        questionobj.put("question_id", quejarray.getJSONObject(j).getString("id").toString());
                        questionobj.put("id", quejarray.getJSONObject(j).has("sub_question_id") ? quejarray.getJSONObject(j).getString("sub_question_id").toString() : quejarray.getJSONObject(j).getString("id").toString());
                        questionobj.put("question_type", quejarray.getJSONObject(j).getString("type").toString());
                        questionobj.put("is_required", quejarray.getJSONObject(j).has("is_required") ? quejarray.getJSONObject(j).getString("is_required").toString() : "0");
                        questionobj.put("dummy_is_required", quejarray.getJSONObject(j).has("is_required") ? quejarray.getJSONObject(j).getString("is_required").toString() : "0");
                        questionobj.put("answer_id", "");
                        questionobj.put("answer", "");
                        questionobj.put("form_id", "");
                        questionobj.put("is_other", "");
                        questionobj.put("visual", "1");
                        questionobj.put("validationtype", "");
                        questionobj.put("validationvalue", "");
                        questionobj.put("parent", "1");
                        questionobj.put("subquestion", quejarray.getJSONObject(j).has("is_sub_question") ? quejarray.getJSONObject(j).getString("is_sub_question").toString() : "0");
                        String subquestionobjid = "";
                        if (quejarray.getJSONObject(j).has("is_sub_question")){
                            if (quejarray.getJSONObject(j).getString("is_sub_question").toString().equalsIgnoreCase("1")) {
                                JSONArray jarray = new JSONArray(quejarray.getJSONObject(j).getString("values").toString());
                                for (int s = 0; s < jarray.length(); s++) {
                                    JSONArray subquearray = new JSONArray(jarray.getJSONObject(s).getString("sub_questions").toString());
                                    for (int t = 0; t < subquearray.length(); t++) {
                                        subquestionobjid += subquearray.getJSONObject(t).getString("sub_question_id").toString() + ",";
                                    }
                                }
                            }
                    }
                        questionobj.put("subquestionid", subquestionobjid.length()>0?subquestionobjid.substring(0,subquestionobjid.length()-1):subquestionobjid);
                        questionobj.put("isrule", false);
                        questionarray.put(questionobj);
                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                        if(quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmyn")) {
                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                            controls(quejarray.getJSONObject(j).getString("type").toString(), li, quejarray.getJSONObject(j).getString("title").toString(), quejarray.getJSONObject(j).has("values") ? quejarray.getJSONObject(j).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(j).has("target") ? quejarray.getJSONObject(j).getString("target").toString() : "", quejarray.getJSONObject(j).getString("id").toString(), quejarray.getJSONObject(j).has("validation") ? quejarray.getJSONObject(j).getString("validation").toString() : "", quejarray.getJSONObject(j).has("from_lable") ? quejarray.getJSONObject(j).getString("from_lable").toString() : "", quejarray.getJSONObject(j).has("to_lable") ? quejarray.getJSONObject(j).getString("to_lable").toString() : "", quejarray.getJSONObject(j).has("from_to_number") ? quejarray.getJSONObject(j).getString("from_to_number").toString() : "", quejarray.getJSONObject(j).has("row_values") ? quejarray.getJSONObject(j).getString("row_values").toString() : "", quejarray.getJSONObject(j).has("column_values") ? quejarray.getJSONObject(j).getString("column_values").toString() : "", quejarray.getJSONObject(j).has("is_required")?quejarray.getJSONObject(j).getString("is_required").toString():"0", uniqueid, "0", uniqueid,Integer.parseInt(quejarray.getJSONObject(j).getString("id").toString()),0,quejarray.getJSONObject(j).has("image_path")?quejarray.getJSONObject(j).getString("image_path"):"",quejarray.getJSONObject(j).has("is_other")?quejarray.getJSONObject(j).getString("is_other").toString():"",quejarray.getJSONObject(j).has("sub_question_id")?quejarray.getJSONObject(j).getString("sub_question_id").toString():quejarray.getJSONObject(j).getString("id").toString(),0,quejarray.getJSONObject(j).has("decimal")?quejarray.getJSONObject(j).getString("decimal").toString():"0","#FFFFFF",0,0,0,"","",1,0);

                            if (quejarray.getJSONObject(j).getString("is_sub_question").toString().equalsIgnoreCase("1")) {
                                JSONArray jarray = new JSONArray(quejarray.getJSONObject(j).getString("values").toString());
                                for (int s = 0; s < jarray.length(); s++) {
                                    JSONArray subquearray = new JSONArray(jarray.getJSONObject(s).getString("sub_questions").toString());
                                    for (int t = 0; t < subquearray.length(); t++) {
                                        uniqueid++;
                                        questionobj = new JSONObject();
                                        quesid = subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString();
                                        questionobj.put("unique_id", uniqueid);
                                        questionobj.put("question_id", subquearray.getJSONObject(t).getString("id").toString());
                                        questionobj.put("id", subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString());
                                        questionobj.put("question_type", subquearray.getJSONObject(t).getString("type").toString());
                                        questionobj.put("is_required", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0");
                                        questionobj.put("dummy_is_required", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0");
                                        questionobj.put("answered", "");
                                        questionobj.put("answer_id", "");
                                        questionobj.put("answer", "");
                                        questionobj.put("form_id", "");
                                        questionobj.put("is_other", "");
                                        questionobj.put("visual", "0");
                                        questionobj.put("validationtype", "");
                                        questionobj.put("validationvalue", "");
                                        questionobj.put("parent_option_id", jarray.getJSONObject(s).getString("code").toString());
                                        questionobj.put("parent", "0");
                                        questionobj.put("subquestion", "0");
                                        questionobj.put("subquestionid", "");
                                        questionobj.put("isrule", false);
                                        questionarray.put(questionobj);
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"","#EFEFEF",0,0,0,"","",0,0);
                                        if(!subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmyn"))
                                            controls("title", li, subquearray.getJSONObject(t).getString("title").toString(), "", uniqueid, "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", "", "", "", "", "", "","",uniqueid,"1",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                        controls(subquearray.getJSONObject(t).getString("type").toString(), li, subquearray.getJSONObject(t).getString("title").toString(), subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", uniqueid, "0", uniqueid,Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()),0,subquearray.getJSONObject(t).has("image_path")?subquearray.getJSONObject(t).getString("image_path"):"",subquearray.getJSONObject(t).has("is_other")?subquearray.getJSONObject(t).getString("is_other").toString():"",subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString(),0,subquearray.getJSONObject(t).has("decimal")?subquearray.getJSONObject(t).getString("decimal").toString():"0","#EFEFEF",0,0,0,"","",0,0);
                                        if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmds")) {
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            controls("remarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","",subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString(),0,"0","#EFEFEF",0,0,0,"","",0,0);
                                        }
                                        else if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            controls("qmdmremarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                        }
                                        else if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            controls("qmrbremarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                        }
                                    }
                                }
                            }
                        }
                        else  {
                            int iswhitebg = 0;
                            if(quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmpi")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmar")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmls")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmbc")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmgt")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmir")) {
                                controls("divideline", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "", "", "", 0, "0", "#FFFFFF", R.drawable.whiterow_border_leftright, 0,0,"","",1,0);
                                iswhitebg = R.drawable.whiterow_border_leftright;
                            }
                            controls("title", li, quejarray.getJSONObject(j).getString("title").toString(), "", uniqueid, "", quejarray.getJSONObject(j).has("is_required")?quejarray.getJSONObject(j).getString("is_required").toString():"0", "", "", "", "", "", "","",uniqueid,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(j).getString("id").toString()),0,"","","",0,"0","#FFFFFF",iswhitebg,0,0,"","",1,0);
                            controls(quejarray.getJSONObject(j).getString("type").toString(), li, "", quejarray.getJSONObject(j).has("values") ? quejarray.getJSONObject(j).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(j).has("target") ? quejarray.getJSONObject(j).getString("target").toString() : "", quejarray.getJSONObject(j).getString("id").toString(), quejarray.getJSONObject(j).has("validation") ? quejarray.getJSONObject(j).getString("validation").toString() : "", quejarray.getJSONObject(j).has("from_lable") ? quejarray.getJSONObject(j).getString("from_lable").toString() : "", quejarray.getJSONObject(j).has("to_lable") ? quejarray.getJSONObject(j).getString("to_lable").toString() : "", quejarray.getJSONObject(j).has("from_to_number") ? quejarray.getJSONObject(j).getString("from_to_number").toString() : "", quejarray.getJSONObject(j).has("row_values") ? quejarray.getJSONObject(j).getString("row_values").toString() : "", quejarray.getJSONObject(j).has("column_values") ? quejarray.getJSONObject(j).getString("column_values").toString() : "",quejarray.getJSONObject(j).has("is_required")?quejarray.getJSONObject(j).getString("is_required").toString():"0",uniqueid,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(j).getString("id").toString()),0,quejarray.getJSONObject(j).has("image_path")?quejarray.getJSONObject(j).getString("image_path"):"",quejarray.getJSONObject(j).has("is_other")?quejarray.getJSONObject(j).getString("is_other").toString():"",quejarray.getJSONObject(j).has("sub_question_id")?quejarray.getJSONObject(j).getString("sub_question_id").toString():quejarray.getJSONObject(j).getString("id").toString(),0,quejarray.getJSONObject(j).has("decimal")?quejarray.getJSONObject(j).getString("decimal").toString():"0","#FFFFFF",iswhitebg,0,0,"","",1,0);
                            if(quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmpi")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmar")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmls")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmbc")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmgt")||quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmir")) {
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",iswhitebg,0,0,"","",1,0);
                                controls("divideline", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "", "", "", 0, "0", "#FFFFFF", R.drawable.whiterow_border_leftright,0,0,"","",1,0);
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                            }
                            if(quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmds")) {
                               //uniqueid++;
                                questionobj.put("other", "");
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                                controls("remarks", li, "", quejarray.getJSONObject(j).has("values") ? quejarray.getJSONObject(j).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(j).has("target") ? quejarray.getJSONObject(j).getString("target").toString() : "", quejarray.getJSONObject(j).getString("id").toString(), quejarray.getJSONObject(j).has("validation") ? quejarray.getJSONObject(j).getString("validation").toString() : "", quejarray.getJSONObject(j).has("from_lable") ? quejarray.getJSONObject(j).getString("from_lable").toString() : "", quejarray.getJSONObject(j).has("to_lable") ? quejarray.getJSONObject(j).getString("to_lable").toString() : "", quejarray.getJSONObject(j).has("from_to_number") ? quejarray.getJSONObject(j).getString("from_to_number").toString() : "", quejarray.getJSONObject(j).has("row_values") ? quejarray.getJSONObject(j).getString("row_values").toString() : "", quejarray.getJSONObject(j).has("column_values") ? quejarray.getJSONObject(j).getString("column_values").toString() : "", quejarray.getJSONObject(j).has("is_required")?quejarray.getJSONObject(j).getString("is_required").toString():"0",0,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(j).getString("id").toString()),0,"","","",0,"0","#EFEFEF",0,0,0,"","",1,0);
                                if(quejarray.getJSONObject(j).getString("is_sub_question").toString().equalsIgnoreCase("1"))
                                {
                                    JSONArray jarray = new JSONArray(quejarray.getJSONObject(j).getString("values").toString());
                                    for(int s=0;s<jarray.length();s++)
                                    {
                                        JSONArray subquearray = new JSONArray(jarray.getJSONObject(s).getString("sub_questions").toString());
                                        for(int t=0;t<subquearray.length();t++) {
                                            uniqueid++;
                                            questionobj = new JSONObject();
                                            questionobj.put("unique_id", uniqueid);
                                            quesid = subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString();
                                            questionobj.put("question_id", subquearray.getJSONObject(t).getString("id").toString());
                                            questionobj.put("id", subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString());
                                            questionobj.put("question_type", subquearray.getJSONObject(t).getString("type").toString());
                                            questionobj.put("is_required", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0");
                                            questionobj.put("dummy_is_required", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0");
                                            questionobj.put("answered", "");
                                            questionobj.put("answer_id", "");
                                            questionobj.put("answer",  "");
                                            questionobj.put("form_id", "");
                                            questionobj.put("is_other", "");
                                            questionobj.put("validationtype", "");
                                            questionobj.put("validationvalue", "");
                                            questionobj.put("visual", "0");
                                            questionobj.put("parent", "0");
                                            questionobj.put("subquestion", "0");
                                            questionobj.put("subquestionid", "");
                                            questionobj.put("isrule", false);
                                            questionobj.put("parent_option_id", jarray.getJSONObject(s).getString("code").toString());
                                            questionarray.put(questionobj);
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            if(!subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmyn"))
                                                controls("title", li, subquearray.getJSONObject(t).getString("title").toString(), "", uniqueid, "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", "", "", "", "", "", "","",uniqueid,"1",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            controls(subquearray.getJSONObject(t).getString("type").toString(), li, subquearray.getJSONObject(t).getString("title").toString(), subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", uniqueid, "0", uniqueid,Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()),0,subquearray.getJSONObject(t).has("image_path")?subquearray.getJSONObject(t).getString("image_path"):"",subquearray.getJSONObject(t).has("is_other")?subquearray.getJSONObject(t).getString("is_other").toString():"",subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString(),0,subquearray.getJSONObject(t).has("decimal")?subquearray.getJSONObject(t).getString("decimal").toString():"0","#EFEFEF",0,0,0,"","",0,0);
                                            if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmds")) {
                                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                                controls("remarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            }
                                            else if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                                controls("qmdmremarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            }
                                           else if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                                controls("qmrbremarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            }
                                        }
                                    }
                                }
                            }
                            else if(quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                //uniqueid++;
                                questionobj.put("other", "");
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                                controls("qmrbremarks", li, "", quejarray.getJSONObject(j).has("values") ? quejarray.getJSONObject(j).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(j).has("target") ? quejarray.getJSONObject(j).getString("target").toString() : "", quejarray.getJSONObject(j).getString("id").toString(), quejarray.getJSONObject(j).has("validation") ? quejarray.getJSONObject(j).getString("validation").toString() : "", quejarray.getJSONObject(j).has("from_lable") ? quejarray.getJSONObject(j).getString("from_lable").toString() : "", quejarray.getJSONObject(j).has("to_lable") ? quejarray.getJSONObject(j).getString("to_lable").toString() : "", quejarray.getJSONObject(j).has("from_to_number") ? quejarray.getJSONObject(j).getString("from_to_number").toString() : "", quejarray.getJSONObject(j).has("row_values") ? quejarray.getJSONObject(j).getString("row_values").toString() : "", quejarray.getJSONObject(j).has("column_values") ? quejarray.getJSONObject(j).getString("column_values").toString() : "", quejarray.getJSONObject(j).has("is_required")?quejarray.getJSONObject(j).getString("is_required").toString():"0",0,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(j).getString("id").toString()),0,"","","",0,"0","#EFEFEF",0,0,0,"","",1,0);

                                if(quejarray.getJSONObject(j).getString("is_sub_question").toString().equalsIgnoreCase("1"))
                                {
                                    JSONArray jarray = new JSONArray(quejarray.getJSONObject(j).getString("values").toString());
                                    for(int s=0;s<jarray.length();s++)
                                    {
                                        JSONArray subquearray = new JSONArray(jarray.getJSONObject(s).getString("sub_questions").toString());
                                        for(int t=0;t<subquearray.length();t++) {
                                            uniqueid++;
                                            questionobj = new JSONObject();
                                            questionobj.put("unique_id", uniqueid);
                                            quesid = subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString();
                                            questionobj.put("question_id", subquearray.getJSONObject(t).getString("id").toString());
                                            questionobj.put("id", subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString());
                                            questionobj.put("question_type", subquearray.getJSONObject(t).getString("type").toString());
                                            questionobj.put("is_required", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0");
                                            questionobj.put("dummy_is_required", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0");
                                            questionobj.put("answered", "");
                                            questionobj.put("answer_id", "");
                                            questionobj.put("answer",  "");
                                            questionobj.put("form_id", "");
                                            questionobj.put("is_other", "");
                                            questionobj.put("validationtype", "");
                                            questionobj.put("validationvalue", "");
                                            questionobj.put("visual", "0");
                                            questionobj.put("parent", "0");
                                            questionobj.put("subquestion", "0");
                                            questionobj.put("subquestionid", "");
                                            questionobj.put("isrule", false);
                                            questionobj.put("parent_option_id", jarray.getJSONObject(s).getString("code").toString());
                                            questionarray.put(questionobj);
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            if(!subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmyn"))
                                                controls("title", li, subquearray.getJSONObject(t).getString("title").toString(), "", uniqueid, "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", "", "", "", "", "", "","",uniqueid,"1",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            controls(subquearray.getJSONObject(t).getString("type").toString(), li, subquearray.getJSONObject(t).getString("title").toString(), subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", uniqueid, "0", uniqueid,Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()),0,subquearray.getJSONObject(t).has("image_path")?subquearray.getJSONObject(t).getString("image_path"):"",subquearray.getJSONObject(t).has("is_other")?subquearray.getJSONObject(t).getString("is_other").toString():"",subquearray.getJSONObject(t).has("sub_question_id")?subquearray.getJSONObject(t).getString("sub_question_id").toString():subquearray.getJSONObject(t).getString("id").toString(),0,subquearray.getJSONObject(t).has("decimal")?subquearray.getJSONObject(t).getString("decimal").toString():"0","#EFEFEF",0,0,0,"","",0,0);
                                            if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmds")) {
                                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                                controls("remarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            }
                                            else if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                                controls("qmdmremarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            }
                                            else if(subquearray.getJSONObject(t).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                                controls("qmrbremarks", li, "", subquearray.getJSONObject(t).has("values") ? subquearray.getJSONObject(t).getString("values").toString() : "", uniqueid, subquearray.getJSONObject(t).has("target") ? subquearray.getJSONObject(t).getString("target").toString() : "", subquearray.getJSONObject(t).getString("id").toString(), subquearray.getJSONObject(t).has("validation") ? subquearray.getJSONObject(t).getString("validation").toString() : "", subquearray.getJSONObject(t).has("from_lable") ? subquearray.getJSONObject(t).getString("from_lable").toString() : "", subquearray.getJSONObject(t).has("to_lable") ? subquearray.getJSONObject(t).getString("to_lable").toString() : "", subquearray.getJSONObject(t).has("from_to_number") ? subquearray.getJSONObject(t).getString("from_to_number").toString() : "", subquearray.getJSONObject(t).has("row_values") ? subquearray.getJSONObject(t).getString("row_values").toString() : "", subquearray.getJSONObject(t).has("column_values") ? subquearray.getJSONObject(t).getString("column_values").toString() : "", subquearray.getJSONObject(t).has("is_required")?subquearray.getJSONObject(t).getString("is_required").toString():"0", 0, "0", uniqueid, Integer.parseInt(subquearray.getJSONObject(t).getString("id").toString()), 0, "","","",0,"0","#EFEFEF",0,0,0,"","",0,0);
                                            }
                                        }
                                    }
                                }
                            }
                            //added(07/07/2017)
                            else if(quejarray.getJSONObject(j).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                //uniqueid++;
                                questionobj.put("other", "");
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                                controls("qmdmremarks", li, "", quejarray.getJSONObject(j).has("values") ? quejarray.getJSONObject(j).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(j).has("target") ? quejarray.getJSONObject(j).getString("target").toString() : "", quejarray.getJSONObject(j).getString("id").toString(), quejarray.getJSONObject(j).has("validation") ? quejarray.getJSONObject(j).getString("validation").toString() : "", quejarray.getJSONObject(j).has("from_lable") ? quejarray.getJSONObject(j).getString("from_lable").toString() : "", quejarray.getJSONObject(j).has("to_lable") ? quejarray.getJSONObject(j).getString("to_lable").toString() : "", quejarray.getJSONObject(j).has("from_to_number") ? quejarray.getJSONObject(j).getString("from_to_number").toString() : "", quejarray.getJSONObject(j).has("row_values") ? quejarray.getJSONObject(j).getString("row_values").toString() : "", quejarray.getJSONObject(j).has("column_values") ? quejarray.getJSONObject(j).getString("column_values").toString() : "", quejarray.getJSONObject(j).has("is_required")?quejarray.getJSONObject(j).getString("is_required").toString():"0",0,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(j).getString("id").toString()),0,"","","",0,"0","#EFEFEF",0,0,0,"","",1,0);
                            }



                        }

                    }
                    formobj.put("questions", questionarray);
                    formarray.put(formobj);
                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,0,"","","",0,"0","#FFFFFF",0,0,0,"","",1,0);
                    scroll.addView(li);
                    viewflipper.addView(scroll);
                    int qmtrcount = 0;
                   for(int i=0;i<questionarray.length();i++)
                   {
                       if(questionarray.getJSONObject(i).getString("question_type").equalsIgnoreCase("qmtr"))
                           qmtrcount++;
                   }
                    if(formrule_array.length()>0 && qmtrcount>0)
                    {
                      for(int i=0;i<formrule_array.length();i++)
                      {
                          String method = formrule_array.getJSONObject(i).getString("method").toString();
                          JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(i).getString("condition").toString());
                          JSONArray show_array = new JSONArray(formrule_array.getJSONObject(i).getString("show").toString());
                          JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(i).getString("hide").toString());
                          int conditioncount=0;
                          ArrayList<String> values=new ArrayList<String>();
                          for(int j=0;j<condition_array.length();j++)
                          {
                              values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                              for(int l=0;l<questionarray.length();l++)
                              {
                                  if(condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionarray.getJSONObject(l).getString("id").toString())) {
                                      if (questionarray.getJSONObject(l).getString("question_type").toString().equalsIgnoreCase("qmtr")){
                                          if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Contains")) {
                                              JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                              if (questionarray.getJSONObject(l).getString("answer").toString().contains(conditionvalueobj.getString("condition").toString())) {
                                                  questionarray.getJSONObject(l).put("isrule", true);
                                                  conditioncount++;
                                              }else
                                                  questionarray.getJSONObject(l).put("isrule", false);
                                          } else if(condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Not Contains")){
                                              JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                              if (!questionarray.getJSONObject(l).getString("answer").toString().contains(conditionvalueobj.getString("condition").toString())) {
                                                  conditioncount++;
                                                  questionarray.getJSONObject(l).put("isrule", true);
                                              }else
                                                  questionarray.getJSONObject(l).put("isrule", false);
                                          }
                                  }
                                  }
                              }
                          }



                          if(method.equalsIgnoreCase("1")){
                              if(conditioncount >0)
                              {
                                  for(int m=0;m<show_array.length();m++) {
                                      for(int n=0;n<questionarray.length();n++)
                                      {
                                          if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionarray.getJSONObject(n).getString("id").toString())) {
                                              if (questionarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                  //if (questionarray.getJSONObject(n).getString("unique_id").toString().equalsIgnoreCase(questionarray.getJSONObject(n).getString("unique_id").toString())) {
                                                  if (questionarray.getJSONObject(n).getString("is_required").toString().equalsIgnoreCase("1"))
                                                      questionarray.getJSONObject(n).put("is_required", "1");
                                                  else
                                                      questionarray.getJSONObject(n).put("is_required", "0");
                                                  questionarray.getJSONObject(n).put("answered", "yes");
                                                  questionarray.getJSONObject(n).put("visual", "1");

                                                  //}
                                                  showcontrol(questionarray.getJSONObject(n).getString("unique_id").toString(), questionarray.getJSONObject(n).getString("question_type").toString());
                                              }
                                          }
                                      }
                                  }
                                  for(int m=0;m<hide_array.length();m++) {
                                      for(int n=0;n<questionarray.length();n++)
                                      {
                                          if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionarray.getJSONObject(n).getString("id").toString())) {
                                              //if (questionarray.getJSONObject(n).getString("unique_id").toString().equalsIgnoreCase(questionarray.getJSONObject(i).getString("unique_id").toString())) {
                                              if (questionarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                  questionarray.getJSONObject(n).put("dummy_is_required", questionarray.getJSONObject(n).has("dummy_is_required") ? questionarray.getJSONObject(n).getString("dummy_is_required").toString() : questionarray.getJSONObject(n).getString("is_required").toString());
                                                  questionarray.getJSONObject(n).put("is_required", "0");
                                                  questionarray.getJSONObject(n).put("answer_id", "");
                                                  questionarray.getJSONObject(n).put("answer", "");
                                                  questionarray.getJSONObject(n).put("is_other", "");
                                                  questionarray.getJSONObject(n).put("visual", "0");

                                                  // }
                                                  hiddencontrol(String.valueOf(getResources().getIdentifier(questionarray.getJSONObject(n).getString("unique_id").toString(), "id", "com.mapolbs.vizibeebritannia")), questionarray.getJSONObject(n).getString("question_type").toString());
                                              }
                                          }
                                      }
                                  }
                              }
                          }else
                          {   HashSet<String> hashSet = new HashSet<String>();
                              hashSet.addAll(values);
                              values.clear();
                              values.addAll(hashSet);
                              int scuusscondition=0;
                              for(int q =0;q<questionarray.length();q++)
                              {
                                  if(questionarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                  {
                                      scuusscondition++;
                                  }
                              }
                                if(condition_array.length()==conditioncount)
                                {
                                    for(int m=0;m<show_array.length();m++) {
                                        for(int n=0;n<questionarray.length();n++)
                                        {
                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionarray.getJSONObject(n).getString("id").toString())) {
                                                //if (questionarray.getJSONObject(n).getString("unique_id").toString().equalsIgnoreCase(questionarray.getJSONObject(n).getString("unique_id").toString())) {
                                                if (questionarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                    if (questionarray.getJSONObject(n).getString("is_required").toString().equalsIgnoreCase("1"))
                                                        questionarray.getJSONObject(n).put("is_required", "1");
                                                    else
                                                        questionarray.getJSONObject(n).put("is_required", "0");
                                                    questionarray.getJSONObject(n).put("answered", "yes");
                                                    questionarray.getJSONObject(n).put("visual", "1");

                                                    //}
                                                    showcontrol(questionarray.getJSONObject(n).getString("unique_id").toString(), questionarray.getJSONObject(n).getString("question_type").toString());
                                                }
                                            }
                                        }
                                    }
                                    for(int m=0;m<hide_array.length();m++) {
                                        for(int n=0;n<questionarray.length();n++)
                                        {
                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionarray.getJSONObject(n).getString("id").toString())) {
                                                //if (questionarray.getJSONObject(i).getString("unique_id").toString().equalsIgnoreCase(questionarray.getJSONObject(i).getString("unique_id").toString())) {
                                                if (questionarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                    questionarray.getJSONObject(n).put("dummy_is_required", questionarray.getJSONObject(n).has("dummy_is_required") ? questionarray.getJSONObject(n).getString("dummy_is_required").toString() : questionarray.getJSONObject(n).getString("is_required").toString());
                                                    questionarray.getJSONObject(n).put("is_required", "0");
                                                    questionarray.getJSONObject(n).put("answer_id", "");
                                                    questionarray.getJSONObject(n).put("answer", "");
                                                    questionarray.getJSONObject(n).put("is_other", "");
                                                    questionarray.getJSONObject(n).put("visual", "0");

                                                    // }
                                                    hiddencontrol(questionarray.getJSONObject(n).getString("unique_id").toString(), questionarray.getJSONObject(n).getString("question_type").toString());
                                                }
                                            }
                                        }
                                    }
                                }
                          }

                      }


                    }

                    for (int k = 0; k < formarray.length(); k++) {
                        if (formarray.getJSONObject(k).getString("form_id").equalsIgnoreCase(locformid)){
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                            quessid = 0;
                            for (int i = 0; i < questionsarray.length(); i++) {
                                if(quessid != Integer.parseInt(questionsarray.getJSONObject(i).getString("question_id").toString()))
                                {
                                    quessid = Integer.parseInt(questionsarray.getJSONObject(i).getString("question_id").toString());
                                }
                                else
                                {


                                if (questionsarray.getJSONObject(i).getString("unique_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(i).getString("unique_id").toString())) {
                                    questionsarray.getJSONObject(i).put("dummy_is_required", questionsarray.getJSONObject(i).has("dummy_is_required") ? questionsarray.getJSONObject(i).getString("dummy_is_required").toString() : questionsarray.getJSONObject(i).getString("is_required").toString());
                                    questionsarray.getJSONObject(i).put("is_required", "0");
                                    questionsarray.getJSONObject(i).put("answer_id", "");
                                    questionsarray.getJSONObject(i).put("answer", "");
                                    questionsarray.getJSONObject(i).put("is_other", "");
                                    questionsarray.getJSONObject(i).put("visual", "0");

                                }
                                    hiddencontrol(questionsarray.getJSONObject(i).getString("unique_id").toString(),questionsarray.getJSONObject(i).getString("question_type").toString());



                                }
                            }
                            formarray.getJSONObject(k).put("questions", questionsarray);
                        }
                    }

                } else if (jobj.getString("type").toString().equalsIgnoreCase("GRID")) {

                    formobj.put("form_id", jobj.getString("form_id").toString());
                    description = jobj.getString("description").toString();
                    gridformarray = new JSONArray(jobj.getString("values").toString());
                    quejarray = new JSONArray(jobj.getString("questions").toString());
                    formrule_array = new JSONArray(jobj.has("form_rules")?jobj.getString("form_rules").toString():"[]");
                    LinearLayout li = null;
                    ScrollView scroll = null;
                    LinearLayout lll = null;
                    ListView lstv = null;
                    scroll = new ScrollView(this);
                    ScrollView.LayoutParams scrollparams = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    scrollparams.setMargins(marginleft, 0, marginright, 0);
                    scroll.setLayoutParams(scrollparams);
                    scroll.setFocusableInTouchMode(true);
                    scroll.setFocusable(true);
                    scroll.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    scroll.setVerticalScrollBarEnabled(false);
                    scroll.setHorizontalScrollBarEnabled(false);
                    scroll.setTag(quejarray.length());
                    li = new LinearLayout(this);
                    LinearLayout.LayoutParams liparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    li.setLayoutParams(liparams);
                    li.setFocusableInTouchMode(true);
                    li.setFocusable(true);

                    li.setOrientation(LinearLayout.VERTICAL);
                    li.setTag(jobj.getString("form_id").toString());
                    isfiltergrid = 0;
                    if(jobj.has("filter1") || jobj.has("filter2") ) {
                        btn1code = "All";
                        btn2code="All";

                        final JSONArray filter1array = new JSONArray(jobj.has("filter1_values") ? jobj.getString("filter1_values").toString() : "[]");
                        final JSONArray filter2array = new JSONArray(jobj.has("filter2_values") ? jobj.getString("filter2_values").toString() : "[]");
                        LayoutInflater filterheadinflater = SurveyForm.this.getLayoutInflater();
                        View view = filterheadinflater.inflate(R.layout.grid_header_row, null, false);
                        final Button btnfilter1 = (Button) view.findViewById(R.id.btnfilter1);
                        btnfilter1.setTag(jobj.has("filter1") ? jobj.getString("filter1").toString() : "");
                        final ImageView imgfilter1 = (ImageView) view.findViewById(R.id.imgfilter1);
                        if (filter1array.length() > 0) {
                            btnfilter1.setVisibility(View.VISIBLE);
                            imgfilter1.setVisibility(View.VISIBLE);
                        }
                        else {
                            btnfilter1.setVisibility(View.INVISIBLE);
                            imgfilter1.setVisibility(View.INVISIBLE);
                        }
                        final Button btnfilter2 = (Button) view.findViewById(R.id.btnfilter2);
                        btnfilter2.setTag(jobj.has("filter2") ? jobj.getString("filter2").toString() : "");
                        final ImageView imgfilter2 = (ImageView) view.findViewById(R.id.imgfilter2);
                        if (filter2array.length() > 0) {
                            btnfilter2.setVisibility(View.VISIBLE);
                            imgfilter2.setVisibility(View.VISIBLE);
                        }
                        else {
                            btnfilter2.setVisibility(View.INVISIBLE);
                            imgfilter2.setVisibility(View.INVISIBLE);
                        }
                        final ArrayList<GridFilterClass> gridfilterclass = new ArrayList<GridFilterClass>();
                        btnfilter1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                                        SurveyForm.this);
                                gridfilterclass.clear();
                                gridfilterclass.add(new GridFilterClass("All", "All"));
                                try {
                                    for (int i = 0; i < filter1array.length(); i++) {
                                        gridfilterclass.add(new GridFilterClass(filter1array.getJSONObject(i).getString("code"), filter1array.getJSONObject(i).getString("name")));
                                    }
                                } catch (Exception ex) {
                                    Log.e("Error", ex.getMessage().toString());
                                }
                                final GridFilterCustomAdapter adapter = new GridFilterCustomAdapter(SurveyForm.this, R.layout.grid_form_filter_row, gridfilterclass);

                                builderSingle.setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                builderSingle.setAdapter(adapter,
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                btnfilter1.setText(adapter.getItem(which).getName());
                                                btn1code = adapter.getItem(which).getCode().toString();
                                                String tagvalue = btnfilter1.getTag().toString();
                                                LinearLayout parentlilayout = (LinearLayout)btnfilter1.getParent().getParent();
                                                ScrollView scrollview = (ScrollView)parentlilayout.getParent();
                                                if(scrollview.getTag().toString().equalsIgnoreCase("1")) {
                                                    for (int i = 2; i < parentlilayout.getChildCount(); i++) {
                                                        LinearLayout child = (LinearLayout) parentlilayout.getChildAt(i);
                                                        if(child!= null) {
                                                            TextView txtfilter1 = (TextView) child.findViewById(R.id.txtfilter1);
                                                            TextView txtfilter2 = (TextView) child.findViewById(R.id.txtfilter2);
                                                            if (!btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code) && txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);


                                                            } else if (!btn1code.equalsIgnoreCase("All") && btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else if (btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else {
                                                                child.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }

                                                }else {
                                                    for (int i = 1; i < parentlilayout.getChildCount(); i++) {
                                                        TableRow child = (TableRow) parentlilayout.getChildAt(i);
                                                        RelativeLayout rellayout = (RelativeLayout) child.findViewById(R.id.rellayout);
                                                        if (rellayout != null) {
                                                            TextView txtfilter1 = (TextView) rellayout.findViewById(R.id.txtfilter1);
                                                            TextView txtfilter2 = (TextView) rellayout.findViewById(R.id.txtfilter2);
                                                            if (!btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code) && txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);


                                                            } else if (!btn1code.equalsIgnoreCase("All") && btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else if (btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else {
                                                                child.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }


                                                }

                                            }
                                        });
                                AlertDialog alert = builderSingle.create();
                                alert.show();
                                alert.setCanceledOnTouchOutside(false);
                            }
                        });
                        btnfilter2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                                        SurveyForm.this);
                                gridfilterclass.clear();
                                gridfilterclass.add(new GridFilterClass("All", "All"));
                                try {
                                    for (int i = 0; i < filter2array.length(); i++) {
                                        gridfilterclass.add(new GridFilterClass(filter2array.getJSONObject(i).getString("code"), filter2array.getJSONObject(i).getString("name")));
                                    }
                                } catch (Exception ex) {
                                    Log.e("Error", ex.getMessage().toString());
                                }
                                final GridFilterCustomAdapter adapter = new GridFilterCustomAdapter(SurveyForm.this, R.layout.grid_form_filter_row, gridfilterclass);


                                builderSingle.setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                builderSingle.setAdapter(adapter,
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                btnfilter2.setText(adapter.getItem(which).getName());
                                                btn2code = adapter.getItem(which).getCode().toString();
                                               /* btnfilter1.setTag(adapter.getItem(which).getCode());*/
                                                String tagvalue = btnfilter2.getTag().toString();
                                                LinearLayout parentlilayout = (LinearLayout)btnfilter2.getParent().getParent();
                                                ScrollView scrollview = (ScrollView)parentlilayout.getParent();
                                                if(scrollview.getTag().toString().equalsIgnoreCase("1")) {
                                                    for (int i = 2; i < parentlilayout.getChildCount(); i++) {
                                                        LinearLayout child = (LinearLayout) parentlilayout.getChildAt(i);
                                                        if(child!= null) {
                                                            TextView txtfilter1 = (TextView) child.findViewById(R.id.txtfilter1);
                                                            TextView txtfilter2 = (TextView) child.findViewById(R.id.txtfilter2);
                                                            if (!btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code) && txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);


                                                            } else if (!btn1code.equalsIgnoreCase("All") && btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else if (btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else {
                                                                child.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }

                                                }else
                                                {
                                                    for (int i = 1; i < parentlilayout.getChildCount(); i++) {
                                                        TableRow child = (TableRow) parentlilayout.getChildAt(i);
                                                        RelativeLayout rellayout = (RelativeLayout)child.findViewById(R.id.rellayout);
                                                        if(rellayout!= null) {
                                                            TextView txtfilter1 = (TextView) rellayout.findViewById(R.id.txtfilter1);
                                                            TextView txtfilter2 = (TextView) rellayout.findViewById(R.id.txtfilter2);
                                                            if (!btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code) && txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);


                                                            } else if (!btn1code.equalsIgnoreCase("All") && btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter1.getText().toString().equalsIgnoreCase(btn1code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else if (btn1code.equalsIgnoreCase("All") && !btn2code.equalsIgnoreCase("All")) {
                                                                if (txtfilter2.getText().toString().equalsIgnoreCase(btn2code))
                                                                    child.setVisibility(View.VISIBLE);
                                                                else
                                                                    child.setVisibility(View.GONE);
                                                            } else {
                                                                child.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        });
                                AlertDialog alert = builderSingle.create();
                                alert.show();
                                alert.setCanceledOnTouchOutside(false);
                            }
                        });
                        if (filter1array.length() > 0 || filter2array.length() > 0) {
                            li.addView(view);
                            isfiltergrid = 1;

                        }
                    }

                    if (quejarray.length() == 1) {
                        LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                        View headrow = headinflater.inflate(R.layout.grid_form_row_header, null, false);
                        int gridheaderheight = (int) getResources().getDimension(R.dimen.gridque_headerheight);
                        LinearLayout.LayoutParams viewparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridheaderheight);

                        headrow.setLayoutParams(viewparams);
                        TextView headtxtquestion = (TextView) headrow.findViewById(R.id.txtheadquestion);
                        TextView txtheadtitle = (TextView) headrow.findViewById(R.id.txtheadtitle);
                        TextView txtmandatory = (TextView) headrow.findViewById(R.id.txtmandatory);
                        txtheadtitle.setText(quejarray.getJSONObject(0).getString("title").toString());
                        headtxtquestion.setText("Name");
                        if(quejarray.getJSONObject(0).getString("is_required").toString().equalsIgnoreCase("1"))
                            txtmandatory.setVisibility(View.VISIBLE);
                        else
                            txtmandatory.setVisibility(View.INVISIBLE);
                        li.addView(headrow);
                    }
                    if (jobj.getString("grid_type").toString().equalsIgnoreCase("1")) {
                        int idcount = 17641;
                        ArrayList<GridFromLayoutdata> gridformdata = new ArrayList<GridFromLayoutdata>();
                        questionarray = new JSONArray();
                        for (int k = 0; k < gridformarray.length(); k++) {
                            String questionid = gridformarray.getJSONObject(k).getString("code").toString();
                            String questionname = gridformarray.getJSONObject(k).getString("name").toString();
                            String filter1tag = jobj.has("filter1")?jobj.getString("filter1").toString().toString():"";
                            String filter1value = gridformarray.getJSONObject(k).has(filter1tag)?gridformarray.getJSONObject(k).getString(filter1tag).toString():"";
                            String filter2tag = jobj.has("filter2")?jobj.getString("filter2").toString().toString():"";
                            String filter2value = gridformarray.getJSONObject(k).has(filter2tag)?gridformarray.getJSONObject(k).getString(filter2tag).toString():"";

                            if (quejarray.length() > 1) {
                                if (k != 0) {
                                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,0);
                                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,0);
                                }
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,0);
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,0);
                                controls("griddivideline", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,0);
                                controls("gridformtitle", li, questionname, "", 0, "", questionid, "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,0);

                                for (int l = 0; l < quejarray.length(); l++) {
                                    uniqueid++;
                                    questionobj = new JSONObject();
                                    quesid =  quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString();
                                    questionobj.put("unique_id", uniqueid);
                                    questionobj.put("item_id", questionid);
                                    questionobj.put("question_id", quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("id", quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("question_type", quejarray.getJSONObject(l).getString("type").toString());
                                    questionobj.put("is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("dummy_is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("answer_id", "");
                                    questionobj.put("answer",  "");
                                    questionobj.put("form_id", "");
                                    questionobj.put("is_other", "");
                                    questionobj.put("visual", "1");
                                    questionobj.put("validationtype", "");
                                    questionobj.put("validationvalue", "");
                                    questionobj.put("parent", "1");
                                    questionobj.put("subquestion", "0");
                                    questionobj.put("subquestionid", "");
                                    questionobj.put("isrule", false);
                                    questionarray.put(questionobj);
                                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmyn")) {
                                        controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls(quejarray.getJSONObject(l).getString("type").toString(), li, quejarray.getJSONObject(l).getString("title").toString(), quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0", 0, "0", uniqueid,Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString()),quejarray.length(),quejarray.getJSONObject(l).has("image_path")?quejarray.getJSONObject(l).getString("image_path"):"",quejarray.getJSONObject(l).has("is_other")?quejarray.getJSONObject(l).getString("is_other").toString():"",quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString(),0,quejarray.getJSONObject(l).has("decimal")?quejarray.getJSONObject(l).getString("decimal").toString():"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));

                                        }
                                        else {
                                        controls("title", li, quejarray.getJSONObject(l).getString("title").toString(), "", uniqueid, "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                         controls(quejarray.getJSONObject(l).getString("type").toString(), li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "",quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",uniqueid+1,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString()),quejarray.length(),quejarray.getJSONObject(l).has("image_path")?quejarray.getJSONObject(l).getString("image_path"):"",quejarray.getJSONObject(l).has("is_other")?quejarray.getJSONObject(l).getString("is_other").toString():"",quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString(),0,quejarray.getJSONObject(l).has("decimal")?quejarray.getJSONObject(l).getString("decimal").toString():"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmds")) {
                                           questionobj.put("other", "");
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("remarks", li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                           questionobj.put("other", "");
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("qmdmremarks", li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                            questionobj.put("other", "");
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("qmrbremarks", li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }
                                    }
                                    if(l == quejarray.length()-1) {
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("griddivideline", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    }

                                }
                                formobj.put("questions", questionarray);


                            } else {

                                for (int l = 0; l < quejarray.length(); l++) {
                                    uniqueid++;
                                    LayoutInflater inflater = SurveyForm.this.getLayoutInflater();
                                    View row = inflater.inflate(R.layout.grid_form_row, null, false);
                                    TextView txtquestion = (TextView) row.findViewById(R.id.txtquestion);
                                    TextView txtquestionid = (TextView) row.findViewById(R.id.txtquestionid);
                                    TextView txtfilter1 = (TextView) row.findViewById(R.id.txtfilter1);
                                    TextView txtfilter2 = (TextView) row.findViewById(R.id.txtfilter2);
                                    txtfilter1.setTag(filter1tag);
                                    txtfilter2.setTag(filter2tag);
                                    txtfilter1.setText(filter1value);
                                    txtfilter2.setText(filter2value);
                                    txtquestionid.setText(questionid);
                                    txtquestion.setText(questionname);
                                    LinearLayout lilayout = (LinearLayout) row.findViewById(R.id.lilayout);
                                    questionobj = new JSONObject();
                                    quesid =  quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString();
                                    questionobj.put("unique_id", uniqueid);
                                    questionobj.put("item_id", questionid);
                                    questionobj.put("question_id", quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("id", quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("question_type", quejarray.getJSONObject(l).getString("type").toString());
                                    questionobj.put("is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("dummy_is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("answer_id", "");
                                    questionobj.put("answer", "");
                                    questionobj.put("form_id", "");
                                    questionobj.put("is_other", "");
                                    questionobj.put("visual", "1");
                                    questionobj.put("validationtype", "");
                                    questionobj.put("validationvalue", "");
                                    questionobj.put("parent", "1");
                                    questionobj.put("subquestion", "0");
                                    questionobj.put("subquestionid", "");
                                    questionobj.put("isrule", false);
                                    questionarray.put(questionobj);
                                        controls("gridempty", lilayout, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls(quejarray.getJSONObject(l).getString("type").toString(), lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0", uniqueid + 1, "0", uniqueid, Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString()),quejarray.length(),quejarray.getJSONObject(l).has("image_path")?quejarray.getJSONObject(l).getString("image_path"):"",quejarray.getJSONObject(l).has("is_other")?quejarray.getJSONObject(l).getString("is_other").toString():"",quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString(),1,quejarray.getJSONObject(l).has("decimal")?quejarray.getJSONObject(l).getString("decimal").toString():"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        if (quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmds")) {
                                            questionobj.put("other", "");
                                            controls("empty", lilayout, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0,1,"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("remarks", lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0", 0, "0", uniqueid, 0,1,"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                            questionobj.put("other", "");
                                            controls("empty", lilayout, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("qmdmremarks", lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                            questionobj.put("other", "");
                                            controls("empty", lilayout, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("qmrbremarks", lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }

                                    controls("gridempty", lilayout, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));


                                    LinearLayout.LayoutParams viewrowsparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridformemptyheight + gridformcontrolheight + gridformemptyheight);

                                    row.setLayoutParams(viewrowsparams);
                                    li.addView(row);

                                }
                                formobj.put("questions", questionarray);

                            }
                        }
                        formarray.put(formobj);
                    } else if (jobj.getString("grid_type").toString().equalsIgnoreCase("0")) {
                        int idcount = 17641;
                        ArrayList<GridFromLayoutdata> gridformdata = new ArrayList<GridFromLayoutdata>();
                        questionarray = new JSONArray();
                        for (int k = 0; k < gridformarray.length(); k++) {
                            String questionid = gridformarray.getJSONObject(k).getString("code").toString();
                            String questionname = gridformarray.getJSONObject(k).getString("name").toString();
                            String filter1tag = jobj.has("filter1")?jobj.getString("filter1").toString().toString():"";
                            String filter1value = gridformarray.getJSONObject(k).has(filter1tag)?gridformarray.getJSONObject(k).getString(filter1tag).toString():"";
                            String filter2tag = jobj.has("filter2")?jobj.getString("filter2").toString().toString():"";
                            String filter2value = gridformarray.getJSONObject(k).has(filter2tag)?gridformarray.getJSONObject(k).getString(filter2tag).toString():"";
                            if (quejarray.length() > 1) {
                                if (k != 0) {
                                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                }
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                controls("griddivideline", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                controls("gridformtitle", li, questionname, "", 0, "", questionid, "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                for (int l = 0; l < quejarray.length(); l++) {
                                    uniqueid++;
                                    questionobj = new JSONObject();
                                    quesid =  quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString();
                                    questionobj.put("unique_id", uniqueid);
                                    questionobj.put("item_id", questionid);
                                    questionobj.put("question_id", quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("id", quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("question_type", quejarray.getJSONObject(l).getString("type").toString());
                                    questionobj.put("is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("dummy_is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("answer_id", "");
                                    questionobj.put("answer", "");
                                    questionobj.put("form_id", "");
                                    questionobj.put("is_other", "");
                                    questionobj.put("visual", "1");
                                    questionobj.put("validationtype", "");
                                    questionobj.put("validationvalue", "");
                                    questionobj.put("parent", "1");
                                    questionobj.put("subquestion", "0");
                                    questionobj.put("subquestionid", "");
                                    questionobj.put("isrule", false);
                                    questionarray.put(questionobj);
                                    controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmyn")) {
                                        controls("empty", li, "", "", 0, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls(quejarray.getJSONObject(l).getString("type").toString(), li, quejarray.getJSONObject(l).getString("title").toString(), quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0", 0, "0", uniqueid,Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString()),quejarray.length(),quejarray.getJSONObject(l).has("image_path")?quejarray.getJSONObject(l).getString("image_path"):"",quejarray.getJSONObject(l).has("is_other")?quejarray.getJSONObject(l).getString("is_other").toString():"",quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString(),0,quejarray.getJSONObject(l).has("decimal")?quejarray.getJSONObject(l).getString("decimal").toString():"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                         }
                                        else {
                                        controls("title", li, quejarray.getJSONObject(l).getString("title").toString(), "", uniqueid, "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls(quejarray.getJSONObject(l).getString("type").toString(), li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "",quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",uniqueid+1,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString()),quejarray.length(),quejarray.getJSONObject(l).has("image_path")?quejarray.getJSONObject(l).getString("image_path"):"",quejarray.getJSONObject(l).has("is_other")?quejarray.getJSONObject(l).getString("is_other").toString():"",quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString(),0,quejarray.getJSONObject(l).has("decimal")?quejarray.getJSONObject(l).getString("decimal").toString():"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmds")) {
                                            questionobj.put("other", "");
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("remarks", li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                            questionobj.put("other", "");
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("qmdmremarks", li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                            questionobj.put("other", "");
                                            controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                            controls("qmrbremarks", li, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        }
                                    }
                                    if(l == quejarray.length()-1) {
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("griddivideline", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",R.drawable.row_border_leftright,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("empty", li, "", "", uniqueid, "", "", "", "", "", "", "", "", "", 0, "0", uniqueid, 0, 1, "","","",0,"0","#FFFFFF",0,0,isfiltergrid,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));


                                    }

                                }
                                formobj.put("questions", questionarray);

                            } else {

                                for (int l = 0; l < quejarray.length(); l++) {
                                    uniqueid++;
                                    LayoutInflater inflater = SurveyForm.this.getLayoutInflater();
                                    View row = inflater.inflate(R.layout.grid_form_row, null, false);
                                    TextView txtquestion = (TextView) row.findViewById(R.id.txtquestion);
                                    TextView txtquestionid = (TextView) row.findViewById(R.id.txtquestionid);
                                    txtquestionid.setText(questionid);
                                    txtquestion.setText(questionname);
                                    LinearLayout lilayout = (LinearLayout) row.findViewById(R.id.lilayout);
                                    questionobj = new JSONObject();
                                    quesid = quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString();
                                    questionobj.put("unique_id", uniqueid);
                                    questionobj.put("item_id", questionid);
                                    questionobj.put("question_id", quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("id", quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString());
                                    questionobj.put("question_type", quejarray.getJSONObject(l).getString("type").toString());
                                    questionobj.put("is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("dummy_is_required", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0");
                                    questionobj.put("answer_id", "");
                                    questionobj.put("answer", "");
                                    questionobj.put("form_id", "");
                                    questionobj.put("is_other", "");
                                    questionobj.put("visual", "1");
                                    questionobj.put("validationtype", "");
                                    questionobj.put("validationvalue", "");
                                    questionobj.put("parent", "1");
                                    questionobj.put("subquestion", "0");
                                    questionobj.put("subquestionid", "");
                                    questionobj.put("isrule", false);
                                    questionarray.put(questionobj);
                                    controls("gridempty", lilayout, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    controls(quejarray.getJSONObject(l).getString("type").toString(), lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "",quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",uniqueid+1,"0",uniqueid,Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString()),quejarray.length(),quejarray.getJSONObject(l).has("image_path")?quejarray.getJSONObject(l).getString("image_path"):"",quejarray.getJSONObject(l).has("is_other")?quejarray.getJSONObject(l).getString("is_other").toString():"",quejarray.getJSONObject(l).has("sub_question_id")?quejarray.getJSONObject(l).getString("sub_question_id").toString():quejarray.getJSONObject(l).getString("id").toString(),1,quejarray.getJSONObject(l).has("decimal")?quejarray.getJSONObject(l).getString("decimal").toString():"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmds")) {
                                        int queid = Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString())+subqueid;
                                        questionobj.put("other", "");
                                        controls("empty", lilayout, "", "", uniqueid+1, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("remarks", lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid+1, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    }
                                    else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmdm")) {
                                        int queid = Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString())+subqueid;
                                        questionobj.put("other", "");
                                        controls("empty", lilayout, "", "", uniqueid+1, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("qmdmremarks", lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid+1, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    }
                                    else if(quejarray.getJSONObject(l).getString("type").toString().equalsIgnoreCase("qmrb")) {
                                        int queid = Integer.parseInt(quejarray.getJSONObject(l).getString("id").toString())+subqueid;
                                        questionobj.put("other", "");
                                        controls("empty", lilayout, "", "", uniqueid+1, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                        controls("qmrbremarks", lilayout, "", quejarray.getJSONObject(l).has("values") ? quejarray.getJSONObject(l).getString("values").toString() : "", uniqueid+1, quejarray.getJSONObject(l).has("target") ? quejarray.getJSONObject(l).getString("target").toString() : "", quejarray.getJSONObject(l).getString("id").toString(), quejarray.getJSONObject(l).has("validation") ? quejarray.getJSONObject(l).getString("validation").toString() : "", quejarray.getJSONObject(l).has("from_lable") ? quejarray.getJSONObject(l).getString("from_lable").toString() : "", quejarray.getJSONObject(l).has("to_lable") ? quejarray.getJSONObject(l).getString("to_lable").toString() : "", quejarray.getJSONObject(l).has("from_to_number") ? quejarray.getJSONObject(l).getString("from_to_number").toString() : "", quejarray.getJSONObject(l).has("row_values") ? quejarray.getJSONObject(l).getString("row_values").toString() : "", quejarray.getJSONObject(l).has("column_values") ? quejarray.getJSONObject(l).getString("column_values").toString() : "", quejarray.getJSONObject(l).has("is_required")?quejarray.getJSONObject(l).getString("is_required").toString():"0",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    }

                                    controls("gridempty", lilayout, "", "", uniqueid, "", "", "", "", "", "", "", "","",0,"0",uniqueid,0,quejarray.length(),"","","",1,"0","#FFFFFF",R.drawable.row_border_leftright,1,0,filter1tag+","+filter1value,filter2tag+","+filter2value,0,Integer.parseInt(questionid));
                                    LinearLayout.LayoutParams viewrowsparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridformemptyheight + gridformcontrolheight + gridformemptyheight);

                                    row.setLayoutParams(viewrowsparams);
                                    li.addView(row);

                                }
                                formobj.put("questions", questionarray);

                            }

                        }
                        formarray.put(formobj);
                    }
                    scroll.addView(li);
                    viewflipper.addView(scroll);
                }

            MyApplication.getInstance().setView(SurveyForm.this.getCurrentFocus());

        } catch (Exception ex) {
            Log.e("Init Error", "From id-"+locformid +" Que Id-"+quesid+"  "+ex.getMessage().toString());
        }



    }

    public void initcallback() {



        imghome.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             try {

                                                 DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                         switch (which){
                                                             case DialogInterface.BUTTON_POSITIVE:
                                                                 SurveyForm.this.finish();
                                                                 if(VisitDetails.instance != null) {
                                                                     try {
                                                                         VisitDetails.instance.finish();
                                                                     } catch (Exception e) {}
                                                                 }
                                                                 break;

                                                             case DialogInterface.BUTTON_NEGATIVE:
                                                                 //No button clicked
                                                                 break;
                                                         }
                                                     }
                                                 };

                                                 android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SurveyForm.this,R.style.ConfirmDialogStyle);
                                                 builder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure Want to Exit to Home?</font>")).setPositiveButton("Yes", dialogClickListener)
                                                         .setNegativeButton("No", dialogClickListener);

                                                 android.app.AlertDialog dialog = builder.create();
                                                 dialog.setCanceledOnTouchOutside(false);
                                                 dialog.show();

                                                 Button bnegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                                 Button bpositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                                 int btnheightpx =  getResources().getDimensionPixelSize(R.dimen.confirmbtndialog_height);
                                                 int btnheight = (int)(btnheightpx / getResources().getDisplayMetrics().density);
                                                 int btnwidthpx =  getResources().getDimensionPixelSize(R.dimen.confirmbtndialog_width);
                                                 int btnwidth = (int)(btnwidthpx / getResources().getDisplayMetrics().density);
                                                 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                         btnwidth,
                                                         btnheight
                                                 );
                                                 params.setMargins(20,0,0,0);
                                                 bpositive.setLayoutParams(params);
                                                 bnegative.setLayoutParams(params);
                                                 if(bnegative != null) {
                                                     bnegative.setTextColor(Color.parseColor("#FFFFFF"));
                                                     bnegative.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_button));
                                                 }
                                                 if(bpositive != null) {
                                                     bpositive.setTextColor(Color.parseColor("#FFFFFF"));
                                                     bpositive.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button));
                                                 }
                                             } catch (ActivityNotFoundException e) {
                                                 // TODO: handle exception

                                             }


                                         }
                                     });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean islocation = false;
                    settings_tag = new JSONObject();
                    if (MyApplication.getInstance().getAuto_geo_location().equalsIgnoreCase("1")) {
                        gps = new GPSTracker(SurveyForm.this);
                        // check if GPS enabled
                        if (gps.canGetLocation()) {
                            islocation = true;
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            settings_tag.put("auto_geo_location", latitude + "," + longitude);

                        } else {

                            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(SurveyForm.this,R.style.MyAlertDialogStyle);
                            // Setting Dialog Title
                            alertDialog.setTitle("GPS is settings");
                            // Setting Dialog Message
                            alertDialog.setMessage(Html.fromHtml("<font color='#000000'>GPS is not enabled. Do you want to go to settings menu?</font>"));
                            // On pressing Settings button
                            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                }
                            });
                            // on pressing cancel button
                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            // Showing Alert Message
                            alertDialog.show();
                        }

                        if(islocation == true)
                            save();
                    }else {
                        save();
                    }

                }catch (Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }

            }
        });


        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startaudio.size() == stopaudio.size()) {
                    int displayedChild = viewflipper.getDisplayedChild();
                    int childCount = viewflipper.getChildCount();

                    try {
                        View view = viewflipper.getChildAt(displayedChild);
                        View li = ((ViewGroup) view).getChildAt(0);
                         String formid = li.getTag().toString();


                        try {
                            int errorcount = 0;
                            int validationerrorcount = 0;
                            String error="";
                            for (int k = 0; k < formarray.length(); k++) {
                                if (formarray.getJSONObject(k).getString("form_id").equalsIgnoreCase(formid)) {
                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    for (int i = 0; i < questionsarray.length(); i++) {
                                        if (nextformid.equalsIgnoreCase("")) {
                                            nextformid = questionsarray.getJSONObject(i).getString("form_id").toString();
                                         //   if(!nextformid.equalsIgnoreCase(""))
                                         //   MyApplication.getInstance().setFormid(questionsarray.getJSONObject(i).getString("form_id").toString());
                                        }

                                        if (questionsarray.getJSONObject(i).getString("is_required").toString().equalsIgnoreCase("1")) {
                                            if (questionsarray.getJSONObject(i).getString("answer").toString().trim().equalsIgnoreCase("") || questionsarray.getJSONObject(i).getString("answer").toString().equalsIgnoreCase("Select")) {
                                                errorcount++;
                                            }else {
                                                if (questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmdm"))
                                                {
                                                    JSONArray ansJarray = new JSONArray(questionsarray.getJSONObject(i).getString("answer").toString());
                                                    for (int j = 0; j < ansJarray.length(); j++) {
                                                        String answer = ansJarray.getJSONObject(j).getString("answer");
                                                            if (answer.trim().equalsIgnoreCase("Others")) {
                                                                if (questionsarray.getJSONObject(i).has("others")) {
                                                                    if (questionsarray.getJSONObject(i).getString("others").toString().trim().equalsIgnoreCase("")) {
                                                                        errorcount++;
                                                                    }
                                                                }
                                                            }
                                                    }
                                                }else{
                                                    if (questionsarray.getJSONObject(i).getString("answer").toString().trim().equalsIgnoreCase("Others")) {
                                                        if (questionsarray.getJSONObject(i).has("others")) {
                                                            if (questionsarray.getJSONObject(i).getString("others").toString().trim().equalsIgnoreCase("")) {
                                                                errorcount++;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                            if(questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmte")|| questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmtn"))
                                            {
                                                if(questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmte")) {
                                                    if (!questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("")) {
                                                        if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("greater")) {
                                                            if (questionsarray.getJSONObject(i).getString("answer").toString().length() <= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Greater Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                            }
                                                        } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("less")) {
                                                            if (questionsarray.getJSONObject(i).getString("answer").toString().length() >= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Less Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                            }
                                                        } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("equal")) {
                                                            if (questionsarray.getJSONObject(i).getString("answer").toString().length() != Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Equal " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                            }
                                                        } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("between")) {
                                                            String[] valvalues = questionsarray.getJSONObject(i).getString("validationvalue").toString().split(",");
                                                            if (questionsarray.getJSONObject(i).getString("answer").toString().length() >= Integer.parseInt(valvalues[0]) && questionsarray.getJSONObject(i).getString("answer").toString().length() <= Integer.parseInt(valvalues[1])) {

                                                            } else {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should Between " + valvalues[0] + " and " + valvalues[1] + " Character(s)";
                                                            }
                                                        }

                                                    }
                                                }else{
                                                    if (!questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("")) {
                                                        int qmtnlength =0;
                                                          if(questionsarray.getJSONObject(i).getString("answer").toString().contains("."))
                                                          {
                                                              String txt = questionsarray.getJSONObject(i).getString("answer").toString();
                                                              String txts = txt.replace(".",",");
                                                              String[] tmtetext = txts.split(",");
                                                              qmtnlength = tmtetext[0].length();
                                                          }else
                                                          {
                                                              qmtnlength = questionsarray.getJSONObject(i).getString("answer").toString().length();
                                                          }
                                                        if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("greater")) {
                                                            if (qmtnlength <= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Greater Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                            }
                                                        } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("less")) {
                                                            if (qmtnlength >= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Less Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                            }
                                                        } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("equal")) {
                                                            if (qmtnlength != Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Equal " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                            }
                                                        } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("between")) {
                                                            String[] valvalues = questionsarray.getJSONObject(i).getString("validationvalue").toString().split(",");
                                                            if (qmtnlength >= Integer.parseInt(valvalues[0]) && qmtnlength <= Integer.parseInt(valvalues[1])) {

                                                            } else {
                                                                validationerrorcount++;
                                                                error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should Between " + valvalues[0] + " and " + valvalues[1] + " Character(s)";
                                                            }
                                                        }

                                                    }
                                                }
                                            }


                                    }
                                }
                            }


                            if(errorcount > 0 && validationerrorcount > 0)
                            {
                                Toast.makeText(SurveyForm.this, "Please Select/Fill All Mandatory Fields.\n"+error, Toast.LENGTH_LONG).show();
                            }
                            else if (errorcount > 0 && validationerrorcount == 0)
                                Toast.makeText(SurveyForm.this, "Please Select/Fill All Mandatory Fields.", Toast.LENGTH_LONG).show();
                            else if(validationerrorcount > 0 && errorcount == 0)
                            {
                                Toast.makeText(SurveyForm.this, error, Toast.LENGTH_LONG).show();
                            }
                            else {
                                View views = viewflipper.getChildAt(displayedChild+1);
                                if(views == null) {

                                    if (nextformid.equalsIgnoreCase("")) {
                                        for (int i = 0; i < surveyformclass.size(); i++) {
                                            if (surveyformclass.get(i).getFormid().toString().equalsIgnoreCase(formid)) {
                                                pagesetup(surveyformclass.get(i + 1).getJsonobj());
                                                txtvisitdetails.setText(surveyformclass.get(i + 1).getFormtitle());
                                                formrule_array = surveyformclass.get(i + 1).getFormrulearray();
                                                if(surveyformclass.get(i + 1).getFormtype().equalsIgnoreCase("SUBMIT"))

                                                {
                                                    btnsubmit.setVisibility(View.VISIBLE);
                                                    imgnext.setVisibility(View.INVISIBLE);
                                                }
                                                else
                                                {
                                                    btnsubmit.setVisibility(View.INVISIBLE);
                                                    imgnext.setVisibility(View.VISIBLE);
                                                }
                                            }

                                        }
                                        nextformid = "";
                                        viewflipper.showNext();

                                    } else {
                                        for (int i = 0; i < surveyformclass.size(); i++) {

                                            if (surveyformclass.get(i).getFormid().toString().equalsIgnoreCase(nextformid)) {
                                                pagesetup(surveyformclass.get(i).getJsonobj());
                                                formrule_array = surveyformclass.get(i).getFormrulearray();
                                                txtvisitdetails.setText(surveyformclass.get(i).getFormtitle());
                                                if(surveyformclass.get(i).getFormtype().equalsIgnoreCase("SUBMIT"))

                                                {
                                                    btnsubmit.setVisibility(View.VISIBLE);
                                                    imgnext.setVisibility(View.INVISIBLE);
                                                }
                                                else
                                                {
                                                    btnsubmit.setVisibility(View.INVISIBLE);
                                                    imgnext.setVisibility(View.VISIBLE);
                                                }
                                            }

                                        }
                                        viewflipper.showNext();
                                        nextformid = "";
                                    }
                                }else {
                                    String formidsnxt="";
                                    String formidsdispnxt="";
                                    for(int j=0;j<surveyformclass.size();j++)
                                    {
                                        if (surveyformclass.get(j).getFormid().toString().equalsIgnoreCase(formid)) {
                                            formidsnxt = surveyformclass.get(j+1).getFormid().toString();
                                        }
                                    }

                                    if (displayclass.get(displayedChild).getFormid().toString().equalsIgnoreCase(formid)) {
                                        formidsdispnxt = displayclass.get(displayedChild+1).getFormid().toString();
                                    }



                                    if(nextformid.equalsIgnoreCase(""))
                                    {
                                        if(formidsnxt.equalsIgnoreCase(formidsdispnxt))
                                        {
                                            for (int i = 0; i < displayclass.size(); i++) {
                                                if (displayclass.get(i).getFormid().toString().equalsIgnoreCase(formidsnxt)) {
                                                    txtvisitdetails.setText(displayclass.get(i).getFormtitle());
                                                    formrule_array = displayclass.get(i).getJarrayformrule();
                                                    if(displayclass.get(i).getFormtype().equalsIgnoreCase("SUBMIT"))

                                                    {
                                                        btnsubmit.setVisibility(View.VISIBLE);
                                                        imgnext.setVisibility(View.INVISIBLE);
                                                    }
                                                    else
                                                    {
                                                        btnsubmit.setVisibility(View.INVISIBLE);
                                                        imgnext.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }
                                            locformid = formidsnxt;
                                            nextformid = "";
                                            viewflipper.showNext();
                                        }else
                                        {
                                            int childcount = viewflipper.getChildCount();
                                            int diccount = childcount;
                                            for (int k = 0; k < childcount; k++) {
                                                if (k > displayedChild) {
                                                    diccount--;
                                                    viewflipper.removeViewAt(diccount);
                                                    formarray.remove(diccount + 2);
                                                    displayclass.remove(diccount);
                                                }
                                            }
                                            for (int i = 0; i < surveyformclass.size(); i++) {
                                                if (surveyformclass.get(i).getFormid().toString().equalsIgnoreCase(formid)) {
                                                    pagesetup(surveyformclass.get(i + 1).getJsonobj());
                                                    formrule_array = surveyformclass.get(i + 1).getFormrulearray();
                                                    txtvisitdetails.setText(surveyformclass.get(i + 1).getFormtitle());
                                                    if(surveyformclass.get(i + 1).getFormtype().equalsIgnoreCase("SUBMIT"))

                                                    {
                                                        btnsubmit.setVisibility(View.VISIBLE);
                                                        imgnext.setVisibility(View.INVISIBLE);
                                                    }
                                                    else
                                                    {
                                                        btnsubmit.setVisibility(View.INVISIBLE);
                                                        imgnext.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }
                                            nextformid = "";
                                            viewflipper.showNext();
                                        }
                                    }else {
                                        String formidssdispnxt = "";

                                        if (displayclass.get(displayedChild).getFormid().toString().equalsIgnoreCase(formid)) {
                                            formidssdispnxt = displayclass.get(displayedChild + 1).getFormid().toString();
                                        }

                                        if (formidssdispnxt.equalsIgnoreCase(nextformid)) {

                                            for (int i = 0; i < displayclass.size(); i++) {
                                                if (displayclass.get(i).getFormid().toString().equalsIgnoreCase(nextformid)) {
                                                    txtvisitdetails.setText(displayclass.get(i).getFormtitle());
                                                    formrule_array = displayclass.get(i).getJarrayformrule();
                                                    if(displayclass.get(i).getFormtype().equalsIgnoreCase("SUBMIT"))

                                                    {
                                                        btnsubmit.setVisibility(View.VISIBLE);
                                                        imgnext.setVisibility(View.INVISIBLE);
                                                    }
                                                    else
                                                    {
                                                        btnsubmit.setVisibility(View.INVISIBLE);
                                                        imgnext.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }
                                            nextformid = "";
                                            viewflipper.showNext();

                                        } else {

                                            int childcount = viewflipper.getChildCount();
                                            int diccount = childcount;
                                            for (int k = 0; k < childcount; k++) {
                                                if (k > displayedChild) {
                                                    diccount--;
                                                    viewflipper.removeViewAt(diccount);
                                                    formarray.remove(diccount + 2);
                                                    displayclass.remove(diccount);
                                                }
                                            }

                                            for (int i = 0; i < surveyformclass.size(); i++) {

                                                if (surveyformclass.get(i).getFormid().toString().equalsIgnoreCase(nextformid)) {
                                                    pagesetup(surveyformclass.get(i).getJsonobj());
                                                    formrule_array = surveyformclass.get(i).getFormrulearray();
                                                    txtvisitdetails.setText(surveyformclass.get(i).getFormtitle());
                                                    if (surveyformclass.get(i).getFormtype().equalsIgnoreCase("SUBMIT"))

                                                    {
                                                        btnsubmit.setVisibility(View.VISIBLE);
                                                        imgnext.setVisibility(View.INVISIBLE);
                                                    } else {
                                                        btnsubmit.setVisibility(View.INVISIBLE);
                                                        imgnext.setVisibility(View.VISIBLE);
                                                    }
                                                }

                                            }
                                            viewflipper.showNext();
                                            nextformid = "";


                                        }
                                    }
                                }

                            }
                            Log.e("Submit", formarray.toString());
                        } catch (Exception ex) {
                            Log.e("Subit Exception", ex.getMessage().toString());
                        }
                    } catch (Exception ex) {
                        Log.e("Default Form", ex.getMessage().toString());
                    }
                }else
                {
                    Toast.makeText(SurveyForm.this,"Please Stop the Recording",Toast.LENGTH_SHORT).show();
                }
            }
        });


        imgprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int displayedChild = viewflipper.getDisplayedChild();
                if (displayedChild != 0) {
                    try {
                        View viewss = viewflipper.getChildAt(displayedChild-1);
                        View liss = ((ViewGroup) viewss).getChildAt(0);
                        locformid = liss.getTag().toString();
                        MyApplication.getInstance().setFormid(locformid);
                        nextformid="";
                        viewflipper.showPrevious();
                        formrule_array = displayclass.get(displayedChild - 1).getJarrayformrule();
                        txtvisitdetails.setText(displayclass.get(displayedChild - 1).getFormtitle());
                        if(displayclass.get(displayedChild - 1).getFormtype().equalsIgnoreCase("SUBMIT"))

                        {
                            btnsubmit.setVisibility(View.VISIBLE);
                            imgnext.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            btnsubmit.setVisibility(View.INVISIBLE);
                            imgnext.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception ex) {
                        Log.e("Exception",ex.getMessage().toString());
                    }
                } else {
                    try {

                        if (doubleBackToExitPressedOnce) {
                            SurveyForm.this.finish();

                        }

                        doubleBackToExitPressedOnce = true;
                        Toast.makeText(SurveyForm.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);

                    }catch (Exception ex)
                    {

                    }
                }
            }
        });

    }

    public void save(){
        try{
            if(startaudio.size() == stopaudio.size()) {
                try {
                    int displayedChild = viewflipper.getDisplayedChild();
                    int childCount = viewflipper.getChildCount();

                    View views = viewflipper.getChildAt(displayedChild);
                    View li = ((ViewGroup) views).getChildAt(0);
                    String formid = li.getTag().toString();
                    int errorcount = 0;
                    int validationerrorcount = 0;
                    String error="";
                    for (int k = 0; k < formarray.length(); k++) {
                        if (formarray.getJSONObject(k).getString("form_id").equalsIgnoreCase(formid)) {
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                            for (int i = 0; i < questionsarray.length(); i++) {
                                if (nextformid.equalsIgnoreCase("")) {
                                    nextformid = questionsarray.getJSONObject(i).getString("form_id").toString();
                                    if(!nextformid.equalsIgnoreCase(""))
                                        MyApplication.getInstance().setFormid(questionsarray.getJSONObject(i).getString("form_id").toString());
                                }

                                if (questionsarray.getJSONObject(i).getString("is_required").toString().equalsIgnoreCase("1")) {
                                    if (questionsarray.getJSONObject(i).getString("answer").toString().trim().equalsIgnoreCase("") || questionsarray.getJSONObject(i).getString("answer").toString().equalsIgnoreCase("Select")) {
                                        errorcount++;
                                    }else {
                                        if (questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmdm"))
                                        {
                                            JSONArray ansJarray = new JSONArray(questionsarray.getJSONObject(i).getString("answer").toString());
                                            for (int j = 0; j < ansJarray.length(); j++) {
                                                String answer = ansJarray.getJSONObject(j).getString("answer");
                                                if (answer.trim().equalsIgnoreCase("Others")) {
                                                    if (questionsarray.getJSONObject(i).has("others")) {
                                                        if (questionsarray.getJSONObject(i).getString("others").toString().trim().equalsIgnoreCase("")) {
                                                            errorcount++;
                                                        }
                                                    }
                                                }
                                            }
                                        }else{
                                            if (questionsarray.getJSONObject(i).getString("answer").toString().trim().equalsIgnoreCase("Others")) {
                                                if (questionsarray.getJSONObject(i).has("others")) {
                                                    if (questionsarray.getJSONObject(i).getString("others").toString().trim().equalsIgnoreCase("")) {
                                                        errorcount++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if(questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmte")|| questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmtn"))
                                {
                                    if(questionsarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmte")) {
                                        if (!questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("")) {
                                            if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("greater")) {
                                                if (questionsarray.getJSONObject(i).getString("answer").toString().length() <= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Greater Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                }
                                            } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("less")) {
                                                if (questionsarray.getJSONObject(i).getString("answer").toString().length() >= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Less Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                }
                                            } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("equal")) {
                                                if (questionsarray.getJSONObject(i).getString("answer").toString().length() != Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Equal " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                }
                                            } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("between")) {
                                                String[] valvalues = questionsarray.getJSONObject(i).getString("validationvalue").toString().split(",");
                                                if (questionsarray.getJSONObject(i).getString("answer").toString().length() >= Integer.parseInt(valvalues[0]) && questionsarray.getJSONObject(i).getString("answer").toString().length() <= Integer.parseInt(valvalues[1])) {

                                                } else {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should Between " + valvalues[0] + " and " + valvalues[1] + " Character(s)";
                                                }
                                            }

                                        }
                                    }else{
                                        if (!questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("")) {
                                            int qmtnlength =0;
                                            if(questionsarray.getJSONObject(i).getString("answer").toString().contains("."))
                                            {
                                                String txt = questionsarray.getJSONObject(i).getString("answer").toString();
                                                String txts = txt.replace(".",",");
                                                String[] tmtetext = txts.split(",");
                                                qmtnlength = tmtetext[0].length();
                                            }else
                                            {
                                                qmtnlength = questionsarray.getJSONObject(i).getString("answer").toString().length();
                                            }
                                            if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("greater")) {
                                                if (qmtnlength <= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Greater Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                }
                                            } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("less")) {
                                                if (qmtnlength >= Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Less Than " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                }
                                            } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("equal")) {
                                                if (qmtnlength != Integer.parseInt(questionsarray.getJSONObject(i).getString("validationvalue").toString())) {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should be Equal " + questionsarray.getJSONObject(i).getString("validationvalue").toString() + " Character(s)";
                                                }
                                            } else if (questionsarray.getJSONObject(i).getString("validationtype").toString().equalsIgnoreCase("between")) {
                                                String[] valvalues = questionsarray.getJSONObject(i).getString("validationvalue").toString().split(",");
                                                if (qmtnlength >= Integer.parseInt(valvalues[0]) && qmtnlength <= Integer.parseInt(valvalues[1])) {

                                                } else {
                                                    validationerrorcount++;
                                                    error += "\n" + questionsarray.getJSONObject(i).getString("title").toString() + " Should Between " + valvalues[0] + " and " + valvalues[1] + " Character(s)";
                                                }
                                            }

                                        }
                                    }
                                }


                            }
                        }
                    }


                    if(errorcount > 0 && validationerrorcount > 0)
                    {
                        Toast.makeText(SurveyForm.this, "Please Select/Fill All Mandatory Fields.\n"+error, Toast.LENGTH_LONG).show();
                    }
                    else if (errorcount > 0 && validationerrorcount == 0)
                        Toast.makeText(SurveyForm.this, "Please Select/Fill All Mandatory Fields.", Toast.LENGTH_LONG).show();
                    else if(validationerrorcount > 0 && errorcount == 0)
                    {
                        Toast.makeText(SurveyForm.this, error, Toast.LENGTH_LONG).show();
                    }
                    else {


                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        try {


                                            formsarray = new JSONArray();
                                            for (int k = 0; k < formarray.length(); k++) {
                                                JSONObject formobj = new JSONObject();
                                                formobj.put("form_id",formarray.getJSONObject(k).getString("form_id"));
                                                JSONArray quejarray = new JSONArray();
                                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                                for (int i = 0; i < questionsarray.length(); i++) {
                                                    JSONObject quejobj = new JSONObject();
                                                    JSONObject obj =  questionsarray.getJSONObject(i);
                                                    if(obj.has("visual")) {
                                                        if (obj.getString("visual").equalsIgnoreCase("1")) {
                                                            quejobj.put("unique_id",obj.getString("unique_id"));
                                                            if(obj.has("item_id"))
                                                                quejobj.put("item_id",obj.getString("item_id"));
                                                            quejobj.put("question_id",obj.getString("question_id"));
                                                            quejobj.put("id",obj.getString("id"));
                                                            quejobj.put("question_type",obj.getString("question_type"));
                                                            quejobj.put("is_required",obj.getString("is_required"));
                                                            quejobj.put("answer_id",obj.getString("answer_id").equalsIgnoreCase("Select")?"":obj.getString("answer_id"));
                                                            quejobj.put("is_other",obj.has("is_other") ?obj.getString("is_other"):"");
                                                            quejobj.put("others",obj.has("others") ?obj.getString("others"):"");
                                                            if(obj.getString("question_type").equalsIgnoreCase("qmdm") || obj.getString("question_type").equalsIgnoreCase("qmgc") || obj.getString("question_type").equalsIgnoreCase("qmgm")) {
                                                                if(obj.getString("question_type").equalsIgnoreCase("qmdm")){
                                                                    JSONArray jarray= null;
                                                                    if(obj.getString("answer").equalsIgnoreCase("")|| obj.getString("answer").equalsIgnoreCase("Select"))
                                                                        jarray = new JSONArray("[]");
                                                                    else
                                                                        jarray = new JSONArray(obj.getString("answer"));
                                                                    for(int t=0;t<jarray.length();t++)
                                                                    {
                                                                        if(jarray.getJSONObject(t).getString("answer").equalsIgnoreCase("Others"))
                                                                        {
                                                                            jarray.remove(t);
                                                                        }
                                                                    }
                                                                    quejobj.put("answer", jarray);
                                                                }else if(obj.getString("question_type").equalsIgnoreCase("qmgm"))
                                                                {
                                                                    JSONArray jarray= null;
                                                                    if(obj.getString("answer").equalsIgnoreCase("")|| obj.getString("answer").equalsIgnoreCase("Select"))
                                                                        jarray = new JSONArray("[]");
                                                                    else {
                                                                        jarray = new JSONArray(obj.getString("answer"));
                                                                        for(int s=0;s<jarray.length();s++)
                                                                        {
                                                                            JSONArray emptyjarray= null;
                                                                            if(jarray.getJSONObject(s).getString("answer").toString().equalsIgnoreCase(""))
                                                                            {
                                                                                emptyjarray = new JSONArray("[]");
                                                                            }else
                                                                            {
                                                                                emptyjarray = new JSONArray(jarray.getJSONObject(s).getString("answer").toString());
                                                                            }
                                                                            jarray.getJSONObject(s).put("answer",emptyjarray);
                                                                        }
                                                                    }
                                                                    quejobj.put("answer", jarray);
                                                                }else {
                                                                    JSONArray jarray= null;
                                                                    if(obj.getString("answer").equalsIgnoreCase("")|| obj.getString("answer").equalsIgnoreCase("Select"))
                                                                        jarray = new JSONArray("[]");
                                                                    else
                                                                        jarray = new JSONArray(obj.getString("answer"));
                                                                    quejobj.put("answer", jarray);
                                                                }
                                                            }
                                                            else
                                                                quejobj.put("answer",obj.getString("answer").equalsIgnoreCase("Select")?"":obj.getString("answer"));
                                                            quejobj.put("form_id",obj.has("form_id")?obj.getString("form_id"):"");
                                                            quejobj.put("is_other",obj.has("is_other") ?obj.getString("is_other"):"");
                                                            quejobj.put("imagename",obj.has("imagename") ?obj.getString("imagename"):"");
                                                            quejobj.put("imagepath",obj.has("imagepath") ?obj.getString("imagepath"):"");
                                                            //quejobj.put("visual",obj.getString("visual"));
                                                            if(obj.has("parent_option_id"))
                                                                quejobj.put("parent_option_id",obj.getString("parent_option_id"));
                                                            quejarray.put(quejobj);
                                                        }
                                                    }
                                                    else
                                                    {
                                                        quejobj.put("unique_id",obj.getString("unique_id"));
                                                        if(obj.has("item_id"))
                                                            quejobj.put("item_id",obj.getString("item_id"));
                                                        quejobj.put("question_id",obj.getString("question_id"));
                                                        quejobj.put("id",obj.has("id")?obj.getString("id"):obj.getString("question_id"));
                                                        quejobj.put("question_type",obj.getString("question_type"));
                                                        quejobj.put("is_required",obj.getString("is_required"));
                                                        quejobj.put("answer_id",obj.getString("answer_id").equalsIgnoreCase("Select")?"":obj.getString("answer_id"));
                                                        quejobj.put("is_other",obj.has("is_other") ?obj.getString("is_other"):"");
                                                        quejobj.put("others",obj.has("others") ?obj.getString("others"):"");
                                                        if(obj.getString("question_type").equalsIgnoreCase("qmdm") || obj.getString("question_type").equalsIgnoreCase("qmgc") || obj.getString("question_type").equalsIgnoreCase("qmgm")) {
                                                            if(obj.getString("question_type").equalsIgnoreCase("qmdm")){
                                                                JSONArray jarray= null;
                                                                if(obj.getString("answer").equalsIgnoreCase("")|| obj.getString("answer").equalsIgnoreCase("Select"))
                                                                    jarray = new JSONArray("[]");
                                                                else
                                                                    jarray = new JSONArray(obj.getString("answer"));
                                                                for(int t=0;t<jarray.length();t++)
                                                                {
                                                                    if(jarray.getJSONObject(t).getString("answer").equalsIgnoreCase("Others"))
                                                                    {
                                                                        jarray.remove(t);
                                                                    }
                                                                }
                                                                quejobj.put("answer", jarray);
                                                            }else if(obj.getString("question_type").equalsIgnoreCase("qmgm"))
                                                            {
                                                                JSONArray jarray= null;
                                                                if(obj.getString("answer").equalsIgnoreCase("")|| obj.getString("answer").equalsIgnoreCase("Select"))
                                                                    jarray = new JSONArray("[]");
                                                                else {
                                                                    jarray = new JSONArray(obj.getString("answer"));
                                                                    for(int s=0;s<jarray.length();s++)
                                                                    {
                                                                        JSONArray emptyjarray= null;
                                                                        if(jarray.getJSONObject(s).getString("answer").toString().equalsIgnoreCase(""))
                                                                        {
                                                                            emptyjarray = new JSONArray("[]");
                                                                        }else
                                                                        {
                                                                            emptyjarray = new JSONArray(jarray.getJSONObject(s).getString("answer").toString());
                                                                        }
                                                                        jarray.getJSONObject(s).put("answer",emptyjarray);
                                                                    }
                                                                }
                                                                quejobj.put("answer", jarray);
                                                            }
                                                            else {
                                                                JSONArray jarray= null;
                                                                if(obj.getString("answer").equalsIgnoreCase("")|| obj.getString("answer").equalsIgnoreCase("Select"))
                                                                    jarray = new JSONArray("[]");
                                                                else
                                                                    jarray = new JSONArray(obj.getString("answer"));
                                                                quejobj.put("answer", jarray);
                                                            }
                                                        }
                                                        else
                                                            quejobj.put("answer",obj.getString("answer").equalsIgnoreCase("Select")?"":obj.getString("answer"));
                                                        quejobj.put("form_id",obj.has("form_id")?obj.getString("form_id"):"");
                                                        quejobj.put("is_other",obj.has("is_other") ?obj.getString("is_other"):"");
                                                        quejobj.put("imagename",obj.has("imagename") ?obj.getString("imagename"):"");
                                                        quejobj.put("imagepath",obj.has("imagepath") ?obj.getString("imagepath"):"");
                                                        if(obj.has("parent_option_id"))
                                                            quejobj.put("parent_option_id",obj.getString("parent_option_id"));
                                                        quejarray.put(quejobj);
                                                    }


                                                }
                                                formobj.put("questions",quejarray);
                                                formsarray.put(formobj);
                                            }

                                            retlist = new ArrayList<String>();
                                            DBHelper dbHelper = new DBHelper(SurveyForm.this);
                                            mDb = dbHelper.getReadableDatabase();
                                            Cursor rc = mDb.rawQuery("SELECT retailercode FROM Retailersdetails where projectcode='"+MyApplication.getInstance().getProjectid()+"' and retailercode='"+retailerid+"'", null);
                                            if (rc.moveToFirst()) {
                                                do {

                                                    retlist.add(rc.getString(rc.getColumnIndex("retailercode")));

                                                } while (rc.moveToNext());
                                            }
                                            rc.close();
                                            cd = new ConnectionDetector(getApplicationContext());
                                            if (cd.isConnectingToInternet() == true) {
                                                new PostDataTask().execute(formsarray.toString());
                                            } else {

                                                JSONObject jobj = new JSONObject();
                                                jobj.put("data", formsarray.toString());
                                                jobj.put("loggedUser", MyApplication.getInstance().getLogeduserjsonstring());
                                                jobj.put("surveydate", devicedate);
                                                jobj.put("settings", settings_tag);


                                                long x = dbAdapter.insertdata(jobj.toString());

                                                if (x > 0) {
                                                    Toast.makeText(SurveyForm.this, "Survey Data Stored in Offline.", Toast.LENGTH_LONG).show();
                                                    if(retlist.size() == 0) {
                                                        long xx = dbAdapter.insertretailer(retailerid, MyApplication.getInstance().getProjectid());
                                                    }
                                                    quit();
                                                } else {
                                                    Toast.makeText(SurveyForm.this, "Offline data Failed to Insert.", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } catch (Exception ex) {
                                            Log.e("Exception", ex.getMessage().toString());
                                        }
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };



                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SurveyForm.this,R.style.MyAlertDialogStyle);
                        builder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure Want to Store Survey Data?</font>")).setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener);

                        android.app.AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                        Button bnegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        Button bpositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        int btnheightpx =  getResources().getDimensionPixelSize(R.dimen.confirmbtndialog_height);
                        int btnheight = (int)(btnheightpx / getResources().getDisplayMetrics().density);
                        int btnwidthpx =  getResources().getDimensionPixelSize(R.dimen.confirmbtndialog_width);
                        int btnwidth = (int)(btnwidthpx / getResources().getDisplayMetrics().density);
                        int px = getResources().getDimensionPixelSize(R.dimen.confirmyesrno_textsize);
                        float size = px / getResources().getDisplayMetrics().density;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                btnwidth,
                                btnheight
                        );
                        params.setMargins(20,0,0,0);
                        bpositive.setLayoutParams(params);
                        bnegative.setLayoutParams(params);
                        if(bnegative != null) {
                            bnegative.setTextColor(Color.parseColor("#FFFFFF"));
                            bnegative.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_button));
                            bnegative.setTextSize(size);
                        }
                        if(bpositive != null) {
                            bpositive.setTextColor(Color.parseColor("#FFFFFF"));
                            bpositive.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button));
                            bpositive.setTextSize(size);
                        }


                    }
                } catch (Exception ex) {
                    Log.e("Exception", ex.getMessage().toString());
                }
            }else
            {
                Toast.makeText(SurveyForm.this,"Please Stop the Recording",Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex)
        {
            Log.e("Exception",ex.getMessage().toString());
        }
    }

    public void controls(final String type, final LinearLayout tl, String title, String values, final int id, final String targetid, final String questionid, String validation, String fromlabel, String tolabel, String fromtonumber, String rowvalues, String columnvalues, String isrequired, final int childid, final String subquestion, int subqueuniqueid, int quesid, int grid,String imagepath,String isother,String subquestionid,int isgrid,final String decimallength,String bgcolour,int leftrightbg,int issinglegrid,int gridfilter,String filter1,String filter2,int rowtype,int item_id) {
        try {
            tablerowid++;
            final TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.parseColor(bgcolour));
            row.setId(tablerowid);
            if(leftrightbg != 0) {
                row.setBackground(getResources().getDrawable(leftrightbg));
            }
            Dropdownlist = new ArrayList<DropDownData>();

            if (type.equalsIgnoreCase("qmdt")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmdt_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmdtparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                row.setGravity(Gravity.CENTER);
                JSONArray valuearray = new JSONArray(values);
                Dropdownlist.add(new DropDownData("Select", "Select", questionid));
                for (int i = 0; i < valuearray.length(); i++) {
                    Dropdownlist.add(new DropDownData(valuearray.getJSONObject(i).getString("code"), valuearray.getJSONObject(i).getString("name"), questionid));
                }
                final DropdownCustomAdapter adapter = new DropdownCustomAdapter(SurveyForm.this, Dropdownlist);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.spinner, null, false);

                view.setLayoutParams(params);
                final Spinner spn = (Spinner) view.findViewById(R.id.spinner);
                spn.setBackgroundResource(R.drawable.spinner);
                int paddingleft = (int) getResources().getDimension(R.dimen.defaultform_qmdts_paddingleft);
                int paddingtop = (int) getResources().getDimension(R.dimen.defaultform_qmdts_paddingtop);
                spn.setPadding(paddingleft,paddingtop,0,0);
                spn.setLayoutParams(qmdtparams);
                spn.setTag(id);
                spn.setId(id);
                spn.setAdapter(adapter);
                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View arg1,
                                               int pos, long arg3) {
                        try {
                            spn.requestFocus();

                            View view = (View) arg1.getParent();
                            if (view != null) {
                                TextView txtid = (TextView) view.findViewById(R.id.txt_id);
                                TextView txtname = (TextView) view.findViewById(R.id.txt_name);
                                TextView txt_queid = (TextView) view.findViewById(R.id.txt_queid);
                                for (int k = 0; k < formarray.length(); k++) {
                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    for (int l = 0; l < questionsarray.length(); l++) {
                                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(txt_queid.getText().toString())) {
                                            questionsarray.getJSONObject(l).put("answer_id", txtid.getText().toString());
                                            questionsarray.getJSONObject(l).put("answer", txtname.getText().toString());
                                            questionsarray.getJSONObject(l).put("is_other", "");
                                        }
                                    }
                                    formarray.getJSONObject(k).put("questions", questionsarray);

                                }
                                questionobj.put("answer_id", txtid.getText().toString());
                                questionobj.put("answer", txtname.getText().toString());
                                questionobj.put("is_other", "");
                                DropdownCustomAdapter adapters = null;
                                Dropdownlist = new ArrayList<DropDownData>();
                                for (int i = 0; i < quejarray.length(); i++) {
                                    if (quejarray.getJSONObject(i).getString("hierarchy_code").toString().equalsIgnoreCase(targetid)) {
                                        JSONArray valuesarray = new JSONArray(quejarray.getJSONObject(i).getString("values").toString());
                                        Dropdownlist.clear();
                                        Dropdownlist.add(new DropDownData("Select", "Select", quejarray.getJSONObject(i).getString("id").toString()));
                                        for (int j = 0; j < valuesarray.length(); j++) {
                                            if (valuesarray.getJSONObject(j).getString(targetid).toString().equalsIgnoreCase(txtid.getText().toString())) {
                                                Dropdownlist.add(new DropDownData(valuesarray.getJSONObject(j).getString("code"), valuesarray.getJSONObject(j).getString("name"), quejarray.getJSONObject(i).getString("id").toString()));
                                            }
                                        }
                                        adapters = new DropdownCustomAdapter(SurveyForm.this, Dropdownlist);
                                    }
                                }
                                resID = getResources().getIdentifier(String.valueOf(spn.getId() + 1), "id", "com.mapolbs.vizibeebritannia");
                                Spinner spnnext = (Spinner) findViewById(resID);
                                spnnext.setAdapter(adapters);
                            }
                        } catch (Exception ex) {
                            Log.e("Default Form", ex.getMessage().toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });
                row.addView(view);
                questionarray.put(questionobj);
            } else if (type.equalsIgnoreCase("title")) {
                int height = (int) getResources().getDimension(R.dimen.title_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int margintoptpx =  getResources().getDimensionPixelSize(R.dimen.title_margintop);
                int margintop = (int)(margintoptpx / getResources().getDisplayMetrics().density);
                int marginbottompx =  getResources().getDimensionPixelSize(R.dimen.title_marginbottom);
                int marginbottom = (int)(marginbottompx / getResources().getDisplayMetrics().density);
                params.setMargins(controlmarginleft, margintop, controlmarginright, marginbottom);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.title, null, false);
                view.setLayoutParams(params);
                final TextView txtheader = (TextView) view.findViewById(R.id.txttitle);
                final TextView txtmandatory = (TextView) view.findViewById(R.id.txtmandatory);
                txtmandatory.setVisibility(View.GONE);
                String text="";
                int paddingleft = (int) getResources().getDimension(R.dimen.title_paddingleft);
                //txtheader.setText(title);
                //txtmandatory.setPadding(paddingleft, 0, 0, 0);
                if(isgrid == 0){
                    if (questionid.equalsIgnoreCase("1")) {
                        //txtmandatory.setVisibility(View.VISIBLE);
                        text = "<font>"+title+"</font> <font color=#D50000>*</font>";
                    }else{
                        text = "<font>"+title+"</font> <font color=#D50000></font>";
                        //xtmandatory.setVisibility(View.GONE);
                    }

                }else
                {
                    //txtmandatory.setVisibility(View.GONE);
                    text = "<font>"+title+"</font> <font color=#D50000></font>";
                }

                txtheader.setText(Html.fromHtml(text));

                row.addView(view);
            }
            else if (type.equalsIgnoreCase("divideline")) {
                int height = (int) getResources().getDimension(R.dimen.title_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                row.setBackgroundColor(Color.parseColor("#DCDCDC"));
                /*row.setId(id);*/
                row.setLayoutParams(params);
            }
            else if (type.equalsIgnoreCase("griddivideline")) {
                int height = (int) getResources().getDimension(R.dimen.title_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                row.setBackgroundColor(Color.parseColor("#DCDCDC"));
                row.setId(id);
                row.setLayoutParams(params);
            }
            else if (type.equalsIgnoreCase("gridformtitle")) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_gridformtitle_paddingleft);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_gridformtitle_paddingtop);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_gridformtitle_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_gridformtitle_paddingbottom);
                row.setPadding(paddingleft, paddingtop, paddingright, paddingbottom);
                row.setLayoutParams(params);
                RelativeLayout rellayout = new RelativeLayout(this);
                rellayout.setGravity(Gravity.CENTER);
                TextView txtheader = new TextView(this);
                txtheader.setGravity(Gravity.LEFT);
                txtheader.setTextColor(Color.parseColor("#2CC1A1"));
                int paddinglefts = (int) getResources().getDimension(R.dimen.surveyform_gridformtitletxtheader_paddingleft);
                int paddingtops = (int) getResources().getDimension(R.dimen.surveyform_gridformtitletxtheader_paddingtop);
                int paddingrights = (int) getResources().getDimension(R.dimen.surveyform_gridformtitletxtheader_paddingright);
                int paddingbottoms = (int) getResources().getDimension(R.dimen.surveyform_gridformtitletxtheader_paddingbottom);
                txtheader.setPadding(paddinglefts, paddingtops, paddingrights, paddingbottoms);
                int size = getResources().getDimensionPixelSize(R.dimen.surveyform_gridformtitletxtheader_textsize);
                txtheader.setTextSize(size);
                txtheader.setTypeface(Typeface.DEFAULT_BOLD);
                txtheader.setText(title);
                txtheader.setTag(questionid);
                rellayout.addView(txtheader);
                row.addView(rellayout);
            } else if (type.equalsIgnoreCase("empty")) {
                int headerHeight = (int) getResources().getDimension(R.dimen.surveyform_empty_height);
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight));
                TextView txtheader = new TextView(this);

                row.addView(txtheader);
            } else if (type.equalsIgnoreCase("gridempty")) {
                int gridheaderHeight = (int) getResources().getDimension(R.dimen.surveyform_gridempty_height);
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridheaderHeight));
                TextView txtheader = new TextView(this);
                gridformemptyheight = gridheaderHeight;
                row.addView(txtheader);
            } else if (type.equalsIgnoreCase("dividerline")) {
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView txtheader = new TextView(this);
                int dividerlineHeight = (int) getResources().getDimension(R.dimen.surveyform_dividerline_height);
                txtheader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerlineHeight));
                txtheader.setBackgroundColor(Color.parseColor("000000"));
                row.addView(txtheader);
            } else if (type.equalsIgnoreCase("qmtr")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmtr_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TableRow.LayoutParams qmtrparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                TextView readonlytext = new TextView(this);
                readonlytext.setTag(id);
                readonlytext.setId(id);
                readonlytext.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmtr_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmtr_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmtr_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmtr_paddingbottom);
                readonlytext.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                readonlytext.setLayoutParams(params);
                readonlytext.setBackgroundResource(R.drawable.rounded_border_readonly);
                readonlytext.setTextColor(Color.parseColor("#000000"));
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                readonlytext.setTextSize(size);
                JSONArray valuearray = new JSONArray(values);
                if (valuearray.length() > 0) {
                    for (int i = 0; i < valuearray.length(); i++) {
                        if (valuearray.getJSONObject(i).getString("code").toString().equalsIgnoreCase(retailerid)) {
                            readonlytext.setText(valuearray.getJSONObject(i).getString("name").toString());
                            questionobj.put("answer_id", valuearray.getJSONObject(i).getString("name").toString());
                            questionobj.put("answer", valuearray.getJSONObject(i).getString("name").toString());
                        }
                    }
                } else
                    readonlytext.setText("");


                row.addView(readonlytext);
            } else if (type.equalsIgnoreCase("qmte")) {
                JSONObject jobjvalidation = new JSONObject(validation);
                final String validationtype = jobjvalidation.has("type")?jobjvalidation.getString("type").toString():"";
                final String validationvalue = jobjvalidation.has("values")?jobjvalidation.getString("values").toString():"";
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmte_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmteparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 2, controlmarginright, 2);
                row.setTag(isgrid);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.alphtextbox, null, false);
                final EditText readonlytext = (EditText) view.findViewById(R.id.edittext);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingbottom);
                readonlytext.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                readonlytext.setLayoutParams(params);
                readonlytext.setTag(id);
                readonlytext.setId(id);
                readonlytext.setSingleLine(true);
                listedittext.add(readonlytext);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                readonlytext.setTextSize(size);
                readonlytext.setBackgroundResource(R.drawable.rounded_border_edittext);
                gridformcontrolheight = height;
                readonlytext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            TableRow vparent = (TableRow) readonlytext.getParent().getParent();
                            int id = vparent.getId();
                            int isformtype = Integer.parseInt(vparent.getTag().toString());
                            if(isformtype == 0) {
                                int titleid = getResources().getIdentifier(String.valueOf(id - 1), "id", "com.mapolbs.vizibeebritannia");
                                TableRow titletablerow = (TableRow) findViewById(titleid);
                                RelativeLayout rellay = (RelativeLayout) titletablerow.getChildAt(0);
                                LinearLayout lilay = (LinearLayout) rellay.getChildAt(0);
                                TextView txttitle = (TextView) lilay.getChildAt(0);
                                for (int k = 0; k < formarray.length(); k++) {
                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    for (int l = 0; l < questionsarray.length(); l++) {
                                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString())) {
                                            questionsarray.getJSONObject(l).put("answer_id", s.toString());
                                            questionsarray.getJSONObject(l).put("answer", s.toString());
                                            questionsarray.getJSONObject(l).put("is_other", "");
                                            if (s.toString().length() > 0) {
                                                questionsarray.getJSONObject(l).put("validationtype", validationtype);
                                                questionsarray.getJSONObject(l).put("validationvalue", validationvalue);
                                            } else {
                                                questionsarray.getJSONObject(l).put("validationtype", "");
                                                questionsarray.getJSONObject(l).put("validationvalue", "");
                                            }
                                            questionsarray.getJSONObject(l).put("title", txttitle.getText().toString());
                                        }
                                    }
                                    formarray.getJSONObject(k).put("questions", questionsarray);
                                }
                            }else
                            {   LinearLayout lihead = (LinearLayout) vparent.getParent().getParent();
                                TextView txthead = (TextView)lihead.getChildAt(1);
                                LinearLayout lilayout = (LinearLayout) vparent.getParent();
                                LinearLayout parentlilayout = (LinearLayout) lilayout.getParent().getParent();
                                LinearLayout child = null;
                                child = (LinearLayout) parentlilayout.getChildAt(0);

                                if(child.getChildCount() == 7)
                                    child = (LinearLayout) parentlilayout.getChildAt(1);
                                else
                                    child = (LinearLayout) parentlilayout.getChildAt(0);
                                LinearLayout child1 = (LinearLayout) child.getChildAt(3);
                                TextView txttitle = (TextView) child1.getChildAt(0);
                                for (int k = 0; k < formarray.length(); k++) {
                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    for (int l = 0; l < questionsarray.length(); l++) {
                                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString())) {
                                            questionsarray.getJSONObject(l).put("answer_id", s.toString());
                                            questionsarray.getJSONObject(l).put("answer", s.toString());
                                            questionsarray.getJSONObject(l).put("is_other", "");
                                            if (s.toString().length() > 0) {
                                                questionsarray.getJSONObject(l).put("validationtype", validationtype);
                                                questionsarray.getJSONObject(l).put("validationvalue", validationvalue);
                                            } else {
                                                questionsarray.getJSONObject(l).put("validationtype", "");
                                                questionsarray.getJSONObject(l).put("validationvalue", "");
                                            }
                                            questionsarray.getJSONObject(l).put("title", txthead.getText().toString()+" "+ txttitle.getText().toString());
                                        }
                                    }
                                    formarray.getJSONObject(k).put("questions", questionsarray);
                                }
                            }

                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                             for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int i=0;i<formrule_array.length();i++)
                                {
                                    String method = formrule_array.getJSONObject(i).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(i).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(i).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(i).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if(questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")){
                                                            if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                questionsarray.getJSONObject(n).put("is_required", "1");
                                                            else
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                            questionsarray.getJSONObject(n).put("answer_id", "");
                                                            questionsarray.getJSONObject(n).put("answer", "");
                                                            questionsarray.getJSONObject(n).put("is_other", "");
                                                            questionsarray.getJSONObject(n).put("visual", "1");
                                                            showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                        }
                                                        }
                                                    }
                                                }
                                            }
                                           // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }

                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                        if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                        if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Contains")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (questionsarray.getJSONObject(l).getString("answer").toString().trim().contains(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Not Contains")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().contains(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    }
                                                }
                                            }
                                        }
                                    }


                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if(questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                            }
                                                        }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                        }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }

                                }

                                formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }

                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }

                    }
                });
                row.addView(view);
            }else if (type.equalsIgnoreCase("remarks")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_remarks_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams remarksparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                final EditText readonlytext = new EditText(this);

                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingbottom);
                readonlytext.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                readonlytext.setLayoutParams(params);
                readonlytext.setTag(id);
                readonlytext.setGravity(Gravity.LEFT);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                readonlytext.setTextSize(size);
                readonlytext.setGravity(Gravity.TOP);
                readonlytext.setHint("Enter Text");
                readonlytext.setBackgroundResource(R.drawable.rounded_border_edittext);
                gridformcontrolheight = height;
                readonlytext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(Integer.parseInt(readonlytext.getTag().toString())))) {
                                        questionsarray.getJSONObject(l).put("others", s.toString());
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);
                            }
                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }

                    }
                });
                row.addView(readonlytext);
            }

            else if (type.equalsIgnoreCase("qmdmremarks")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_remarks_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmdmremarksparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                final EditText readonlytext = new EditText(this);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingbottom);
                readonlytext.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                readonlytext.setLayoutParams(params);
                readonlytext.setTag(id);
                readonlytext.setGravity(Gravity.LEFT);
                readonlytext.setGravity(Gravity.TOP);
                readonlytext.setHint("Enter Text");
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                readonlytext.setTextSize(size);
                readonlytext.setBackgroundResource(R.drawable.rounded_border_edittext);
                gridformcontrolheight = height;
                readonlytext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(Integer.parseInt(readonlytext.getTag().toString())))) {
                                        questionsarray.getJSONObject(l).put("others", s.toString());
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);
                            }
                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }

                    }
                });
                row.addView(readonlytext);

            }

            else if (type.equalsIgnoreCase("qmrbremarks")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_remarks_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmrbremarksparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                final EditText readonlytext = new EditText(this);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmte_paddingbottom);
                readonlytext.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                readonlytext.setLayoutParams(params);
                readonlytext.setTag(id);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                readonlytext.setTextSize(size);
                readonlytext.setGravity(Gravity.LEFT);
                readonlytext.setGravity(Gravity.TOP);
                readonlytext.setHint("Enter Text");
                readonlytext.setBackgroundResource(R.drawable.rounded_border_edittext);
                gridformcontrolheight = height;
                readonlytext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(Integer.parseInt(readonlytext.getTag().toString())))) {
                                        questionsarray.getJSONObject(l).put("others", s.toString());
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);
                            }
                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }

                    }
                });
                row.addView(readonlytext);

            }


            else if (type.equalsIgnoreCase("qmda")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmda_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmdaparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.datepicker, null, false);
                view.setLayoutParams(params);
                final TextView readonlytext = (TextView) view.findViewById(R.id.txtdatepicker);

                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingbottom);
                readonlytext.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                readonlytext.setTextSize(size);
                readonlytext.setTag(id);
                readonlytext.setId(id);
                readonlytext.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                readonlytext.setTextColor(Color.parseColor("#000000"));

                readonlytext.setSingleLine(true);

                final JSONObject validationobj = new JSONObject(validation);
                readonlytext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(readonlytext.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);
                        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                readonlytext.setText(dayOfMonth+"-"+(monthOfYear + 1) + "-"+ year);
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString())) {
                                                questionsarray.getJSONObject(l).put("answer_id", readonlytext.getText().toString());
                                                questionsarray.getJSONObject(l).put("answer", readonlytext.getText().toString());
                                                questionsarray.getJSONObject(l).put("is_other", "");
                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);
                                    }
                                    TableRow vparent = (TableRow) readonlytext.getParent().getParent();
                                    RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                                    TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                                    String itemid = txtrowtype.getTag().toString();
                                    //Page Rule
                                    JSONArray questionsarray = null;
                                    String questionid = "";
                                    int questionposition = 0;
                                    for (int k = 0; k < formarray.length(); k++) {
                                        if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                            questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                            questionposition = k;
                                        }
                                    }

                                    for(int l=0;l<questionsarray.length();l++)
                                    {
                                        if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString()))
                                            questionid = questionsarray.getJSONObject(l).getString("id").toString();
                                    }

                                    if(formrule_array.length()>0)
                                    {
                                        for(int p=0;p<formrule_array.length();p++)
                                        {
                                            String method = formrule_array.getJSONObject(p).getString("method").toString();
                                            JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                            JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                            JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                            int conditioncount=0;
                                            ArrayList<String> values=new ArrayList<String>();
                                            for(int j=0;j<condition_array.length();j++)
                                            {
                                                values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                                for(int m=0;m<hide_array.length();m++) {
                                                    for(int n=0;n<questionsarray.length();n++)
                                                    {
                                                        if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                        {
                                                            if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                                        else
                                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                   // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                }

                                                for(int l=0;l<questionsarray.length();l++) {
                                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString()))
                                                    {
                                                        if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                            if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                                if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                                if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            }else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Is")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                                Date selecteddate = sdf.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = sdf.parse(conditionvalueobj.getString("condition").toString());
                                                                if (selecteddate.equals(conditiondate)) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNot")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                                Date selecteddate = sdf.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = sdf.parse(conditionvalueobj.getString("condition").toString());
                                                                if (!selecteddate.equals(conditiondate)) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Less Than")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                                Date selecteddate = sdf.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = sdf.parse(conditionvalueobj.getString("condition").toString());
                                                                if (selecteddate.before(conditiondate)) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Greater Than")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                                Date selecteddate = sdf.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = sdf.parse(conditionvalueobj.getString("condition").toString());
                                                                if (selecteddate.after(conditiondate)) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Between")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                                Date selecteddate = sdf.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondatefrom = sdf.parse(conditionvalueobj.getString("condition").toString());
                                                                Date conditiondateto = sdf.parse(conditionvalueobj.getString("condition_between").toString());

                                                                if (selecteddate.after(conditiondatefrom)&&selecteddate.before(conditiondateto)) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            if(method.equalsIgnoreCase("1")){
                                                if(conditioncount >0)
                                                {
                                                    for(int m=0;m<show_array.length();m++) {
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {
                                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                                        else
                                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                                }
                                                        }
                                                     //   formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                    }
                                                    for(int m=0;m<hide_array.length();m++) {
                                                        String issubquestion = "";
                                                        String subqueids="";
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {

                                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                        }
                                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if(issubquestion.equalsIgnoreCase("1"))
                                                        {
                                                            String[] subquearray = subqueids.split(",");
                                                            for(int k=0;k<subquearray.length;k++)
                                                            {
                                                                for(int n=0;n<questionsarray.length();n++) {
                                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                     //   formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                    }
                                                }
                                            }else
                                            {
                                                HashSet<String> hashSet = new HashSet<String>();
                                                hashSet.addAll(values);
                                                values.clear();
                                                values.addAll(hashSet);
                                                int scuusscondition=0;
                                                for(int q =0;q<questionsarray.length();q++)
                                                {
                                                    if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                                    {
                                                        scuusscondition++;
                                                    }
                                                }
                                                if(values.size()==scuusscondition)
                                                {

                                                    for(int m=0;m<show_array.length();m++) {
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {
                                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                                        else
                                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                                }
                                                        }
                                                       // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                    }
                                                    for(int m=0;m<hide_array.length();m++) {
                                                        String issubquestion = "";
                                                        String subqueids="";
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {

                                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                        }
                                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if(issubquestion.equalsIgnoreCase("1"))
                                                        {
                                                            String[] subquearray = subqueids.split(",");
                                                            for(int k=0;k<subquearray.length;k++)
                                                            {
                                                                for(int n=0;n<questionsarray.length();n++) {
                                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                      //  formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                    }
                                                }else
                                                {
                                                    for(int m=0;m<hide_array.length();m++) {
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {
                                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                                        else
                                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                    }
                                                }
                                            }
                                        }
                                        formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }


                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }

                                view.setMaxDate(System.currentTimeMillis());
                            }
                        };
                        DatePickerDialog dpDialog = new DatePickerDialog(SurveyForm.this, listener, year,
                                month, day);
                        try {
                            if (validationobj.getString("values").toString().equalsIgnoreCase("0"))
                                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis()+ (1000 * 60 * 60));
                        } catch (Exception ex) {
                            Log.e("Datepicker EX", ex.getMessage().toString());
                        }
                        dpDialog.show();
                    }
                });
                row.addView(view);
            } else if (type.equalsIgnoreCase("qmti")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmti_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmtiparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.timepicker, null, false);
                view.setLayoutParams(params);
                final TextView readonlytext = (TextView) view.findViewById(R.id.txttimepicker);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmti_paddingbottom);
                readonlytext.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                readonlytext.setTextSize(size);
                readonlytext.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                readonlytext.setTextColor(Color.parseColor("#000000"));
                readonlytext.setTag(id);
                readonlytext.setId(id);
                readonlytext.setSingleLine(true);

                readonlytext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(readonlytext.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker = null;
                                mTimePicker = new TimePickerDialog(SurveyForm.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                Calendar mCalendar = Calendar.getInstance();
                                mCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                mCalendar.set(Calendar.MINUTE, selectedMinute);
                                SimpleDateFormat mSDF = new SimpleDateFormat("hh:mm a");
                                String time = mSDF.format(mCalendar.getTime());
                                readonlytext.setText(time);
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString())) {
                                                questionsarray.getJSONObject(l).put("answer_id", readonlytext.getText().toString());
                                                questionsarray.getJSONObject(l).put("answer", readonlytext.getText().toString());
                                                questionsarray.getJSONObject(l).put("is_other", "");
                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);
                                    }
                                    TableRow vparent = (TableRow) readonlytext.getParent().getParent();
                                    RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                                    TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                                    String itemid = txtrowtype.getTag().toString();
                                    //Page Rule
                                    JSONArray questionsarray = null;
                                    String questionid = "";
                                    int questionposition = 0;
                                    for (int k = 0; k < formarray.length(); k++) {
                                        if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                            questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                            questionposition = k;
                                        }
                                    }
                                    for(int l=0;l<questionsarray.length();l++)
                                    {
                                        if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString()))
                                            questionid = questionsarray.getJSONObject(l).getString("id").toString();
                                    }

                                    if(formrule_array.length()>0)
                                    {
                                        for(int p=0;p<formrule_array.length();p++)
                                        {
                                            String method = formrule_array.getJSONObject(p).getString("method").toString();
                                            JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                            JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                            JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                            int conditioncount=0;
                                            ArrayList<String> values=new ArrayList<String>();
                                            for(int j=0;j<condition_array.length();j++)
                                            {
                                                values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                                for(int m=0;m<hide_array.length();m++) {
                                                    for(int n=0;n<questionsarray.length();n++)
                                                    {
                                                        if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                        {
                                                            if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                                        else
                                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                   // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                }
                                                for(int l=0;l<questionsarray.length();l++) {
                                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(readonlytext.getTag().toString()))
                                                    {
                                                        if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                            if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                                if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                                if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            }else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Is")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                                                                Date selecteddate = sdf.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = sdf.parse(conditionvalueobj.getString("condition").toString());
                                                                if (selecteddate.equals(conditiondate)) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNot")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                                                                Date selecteddate = sdf.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = sdf.parse(conditionvalueobj.getString("condition").toString());
                                                                if (!selecteddate.equals(conditiondate)) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Less Than")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
                                                                SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
                                                                Date selecteddate = parseFormat.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = parseFormat.parse(conditionvalueobj.getString("condition").toString());
                                                                String outselecteddate = displayFormat.format(selecteddate);
                                                                String outconditiondate = displayFormat.format(conditiondate);
                                                                Date d1 = displayFormat.parse(outselecteddate);
                                                                Date d2 = displayFormat.parse(outconditiondate);
                                                                long difference = d2.getTime() - d1.getTime();
                                                                int days = (int) (difference / (1000*60*60*24));
                                                                int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                                                                int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                                                                if (days>0 || hours>0||min>0) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Greater Than")) {
                                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
                                                                SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
                                                                Date selecteddate = parseFormat.parse(questionsarray.getJSONObject(l).getString("answer").toString().trim());
                                                                Date conditiondate = parseFormat.parse(conditionvalueobj.getString("condition").toString());
                                                                String outselecteddate = displayFormat.format(selecteddate);
                                                                String outconditiondate = displayFormat.format(conditiondate);
                                                                Date d1 = displayFormat.parse(outselecteddate);
                                                                Date d2 = displayFormat.parse(outconditiondate);
                                                                long difference = d2.getTime() - d1.getTime();
                                                                int days = (int) (difference / (1000*60*60*24));
                                                                int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                                                                int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                                                                if (days<0 || hours<0||min<0) {
                                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                                    conditioncount++;
                                                                }else
                                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                                            }
                                                        }
                                                    }
                                                }
                                            }


                                            if(method.equalsIgnoreCase("1")){
                                                if(conditioncount >0)
                                                {
                                                    for(int m=0;m<show_array.length();m++) {
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {
                                                            if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                    if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                        questionsarray.getJSONObject(n).put("is_required", "1");
                                                                    else
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("answered", "yes");
                                                                    questionsarray.getJSONObject(n).put("visual", "1");
                                                                    showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                            }
                                                        }
                                                        //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                    }

                                                    for(int m=0;m<hide_array.length();m++) {
                                                        String issubquestion = "";
                                                        String subqueids="";
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {

                                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                        }
                                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if(issubquestion.equalsIgnoreCase("1"))
                                                        {
                                                            String[] subquearray = subqueids.split(",");
                                                            for(int k=0;k<subquearray.length;k++)
                                                            {
                                                                for(int n=0;n<questionsarray.length();n++) {
                                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                       // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                    }
                                                }
                                            }else
                                            {
                                                HashSet<String> hashSet = new HashSet<String>();
                                                hashSet.addAll(values);
                                                values.clear();
                                                values.addAll(hashSet);
                                                int scuusscondition=0;
                                                for(int q =0;q<questionsarray.length();q++)
                                                {
                                                    if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                                    {
                                                        scuusscondition++;
                                                    }
                                                }
                                                if(values.size()==scuusscondition)
                                                {
                                                    for(int m=0;m<show_array.length();m++) {
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {
                                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                                        else
                                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                    }
                                                    for(int m=0;m<hide_array.length();m++) {
                                                        String issubquestion = "";
                                                        String subqueids="";
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {

                                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                        }
                                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if(issubquestion.equalsIgnoreCase("1"))
                                                        {
                                                            String[] subquearray = subqueids.split(",");
                                                            for(int k=0;k<subquearray.length;k++)
                                                            {
                                                                for(int n=0;n<questionsarray.length();n++) {
                                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                    }
                                                }else
                                                {
                                                    for(int m=0;m<hide_array.length();m++) {
                                                        for(int n=0;n<questionsarray.length();n++)
                                                        {
                                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                            {
                                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                                        else
                                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                    }
                                                }
                                            }
                                        }
                                        formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }

                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }

                            }
                        }, hour, minute, false);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });
                row.addView(view);
            } else if (type.equalsIgnoreCase("qmrb")) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                row.setTag(isother);
                JSONArray valuearray = new JSONArray(values);
                final RadioGroup radiogroup = new RadioGroup(this);
                radiogroup.setOrientation(RadioGroup.VERTICAL);
                radiogroup.setBackgroundResource(R.drawable.rounded_border_transparent);
                radiogroup.setLayoutParams(params);
                radiogroup.setTag(id);
                radiogroup.setId(id);
                radiogroup.requestFocus();
                for (int i = 0; i < valuearray.length(); i++) {
                    String subids="";
                    JSONArray subquearray = new JSONArray(valuearray.getJSONObject(i).has("sub_questions")?valuearray.getJSONObject(i).getString("sub_questions").toString():"[]");
                    for(int t=0;t<subquearray.length();t++) {
                        subqueuniqueid++;
                        subids += subqueuniqueid+",";
                    }
                    RadioButton radiobtn = new RadioButton(this);
                    radiobtn.setText(valuearray.getJSONObject(i).getString("title").toString());
                    int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingtop);
                    int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingleft);
                    int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingright);
                    int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingbottom);
                    radiobtn.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                    radiobtn.setId(radioid);
                    TypedValue outxValue = new TypedValue();
                    getResources().getValue(R.dimen.radiobuttonscalex, outxValue, true);
                    float xvalue = outxValue.getFloat();
                    TypedValue outyValue = new TypedValue();
                    getResources().getValue(R.dimen.radiobuttonscaley, outyValue, true);
                    float yvalue = outyValue.getFloat();
                    radiobtn.setTag(valuearray.getJSONObject(i).getString("code").toString()+"~"+subids);
                    int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                    float size = px / getResources().getDisplayMetrics().density;
                    radiobtn.setTextSize(size);
                    radiobtn.setButtonDrawable(R.drawable.radiobtn_icon);
                    radiobtn.setCompoundDrawablePadding(50);
                    radiobtn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    radiogroup.addView(radiobtn);
                    radioid++;
                }
                if(isother.equalsIgnoreCase("1"))
                {

                    RadioButton radiobtn = new RadioButton(this);
                    radiobtn.setText("Others");
                    int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingtop);
                    int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingleft);
                    int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingright);
                    int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingbottom);
                    radiobtn.setPadding(paddingleft,paddingtop,paddingbottom,paddingright);
                    radiobtn.setId(radioid);
                    radiobtn.setTag("");
                    TypedValue outxValue = new TypedValue();
                    getResources().getValue(R.dimen.radiobuttonscalex, outxValue, true);
                    float xvalue = outxValue.getFloat();
                    TypedValue outyValue = new TypedValue();
                    getResources().getValue(R.dimen.radiobuttonscaley, outyValue, true);
                    float yvalue = outyValue.getFloat();
                    int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                    float size = px / getResources().getDisplayMetrics().density;
                    radiobtn.setTextSize(size);
                    radiobtn.setButtonDrawable(R.drawable.radiobtn_icon);
                    radiobtn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    radiogroup.addView(radiobtn);
                    radioid++;
                }

                radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup arg0, int selectedId) {
                        selectedId = radiogroup.getCheckedRadioButtonId();
                        RadioButton radiobutton = (RadioButton) findViewById(selectedId);
                        if (radiobutton != null)
                        {
                            String[] valuess = radiobutton.getTag().toString().split("~");
                        String[] ids = valuess.length > 1 ? valuess[1].toString().split(",") : null;
                        String[] dependids;
                        String strdependids = "";

                        for (int i = 0; i < radiogroup.getChildCount(); i++) {
                            View v = radiogroup.getChildAt(i);
                            if (v instanceof RadioButton) {
                                String[] childid = ((RadioButton) v).getTag().toString().split("~");
                                if (childid.length > 1)
                                    strdependids += childid[1].substring(0, childid[1].length() - 1) + ",";

                            }
                        }

                        strdependids = strdependids.substring(0, strdependids.length() > 0 ? strdependids.length() - 1 : 0);
                        dependids = strdependids.equalsIgnoreCase("") ? null : strdependids.split(",");
                        if (dependids != null) {
                            for (int s = 0; s < dependids.length; s++) {
                                resID = getResources().getIdentifier(dependids[s], "id", "com.mapolbs.vizibeebritannia");
                                String questiontype = "";
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                questionsarray.getJSONObject(l).put("dummy_is_required", questionsarray.getJSONObject(l).has("dummy_is_required") ? questionsarray.getJSONObject(l).getString("dummy_is_required").toString() : questionsarray.getJSONObject(l).getString("is_required").toString());
                                                questionsarray.getJSONObject(l).put("is_required", "0");
                                                questionsarray.getJSONObject(l).put("answer_id", "");
                                                questionsarray.getJSONObject(l).put("answer", "");
                                                questionsarray.getJSONObject(l).put("is_other", "");
                                                questionsarray.getJSONObject(l).put("visual", "0");


                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);

                                    }
                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }
                                hiddencontrol(String.valueOf(resID),questiontype);

                            }
                        }
                        if (ids != null) {
                            for (int o = 0; o < ids.length; o++) {
                                resID = getResources().getIdentifier(ids[o], "id", "com.mapolbs.vizibeebritannia");
                                String questiontype = "";
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                if (questionsarray.getJSONObject(l).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                    questionsarray.getJSONObject(l).put("is_required", "1");
                                                else
                                                    questionsarray.getJSONObject(l).put("is_required", "0");
                                                questionsarray.getJSONObject(l).put("answered", "yes");
                                                questionsarray.getJSONObject(l).put("visual", "1");

                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);

                                    }
                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }
                                TableRow currentrow = (TableRow) radiogroup.getParent();
                                int currentrowid = currentrow.getId();
                                int currentemptyid = getResources().getIdentifier(String.valueOf(currentrowid + 1), "id", "com.mapolbs.vizibeebritannia");
                                TableRow currentemptyrow = (TableRow) findViewById(currentemptyid);
                                currentemptyrow.setBackgroundColor(Color.parseColor("#EFEFEF"));


                                showcontrol(String.valueOf(resID),questiontype);

                            }
                        }

                        try {
                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(radiogroup.getTag().toString())) {
                                        questionsarray.getJSONObject(l).put("answer_id", radiobutton.getText().toString().equalsIgnoreCase("Others") ? radiobutton.getText().toString() : valuess[0]);
                                        questionsarray.getJSONObject(l).put("answer", radiobutton.getText().toString());
                                        questionsarray.getJSONObject(l).put("form_id", "");
                                        questionsarray.getJSONObject(l).put("others", "");
                                        if(radiobutton.getText().toString().equalsIgnoreCase("Others")) {
                                            questionsarray.getJSONObject(l).put("is_other", "1");
                                        }else{
                                            questionsarray.getJSONObject(l).put("is_other", "");
                                        }
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);
                            }
                        } catch (Exception ex) {
                            Log.e("Json exception", ex.getMessage().toString());
                        }

                        if (row.getTag().toString().equalsIgnoreCase("1")) {
                            TableRow vparent = (TableRow) radiogroup.getParent();
                            int id = vparent.getId();
                            int emptyid = getResources().getIdentifier(String.valueOf(id + 1), "id", "com.mapolbs.vizibeebritannia");
                            TableRow emptyrow = (TableRow) findViewById(emptyid);
                            int remarksid = getResources().getIdentifier(String.valueOf(id + 2), "id", "com.mapolbs.vizibeebritannia");
                            TableRow remarksrow = (TableRow) findViewById(remarksid);
                            EditText edt = (EditText) remarksrow.getChildAt(0);

                            if (edt != null) {
                                if (radiobutton.getText().toString().equalsIgnoreCase("Others")) {

                                    emptyrow.setVisibility(View.VISIBLE);
                                    emptyrow.setBackgroundColor(Color.parseColor("#EFEFEF"));
                                    remarksrow.setVisibility(View.VISIBLE);
                                    EditText edttext = (EditText) remarksrow.getChildAt(0);
                                    edttext.setText("");
                                } else {
                                    emptyrow.setVisibility(View.GONE);
                                    emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    remarksrow.setVisibility(View.GONE);
                                    EditText edttext = (EditText) remarksrow.getChildAt(0);
                                    edttext.setText("");
                                }
                            }

                        }

                            try {

                                TableRow vparent = (TableRow) radiogroup.getParent();
                                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                                String itemid = txtrowtype.getTag().toString();

                                //Page Rule
                                JSONArray questionsarray = null;
                                String questionid = "";
                                int questionposition = 0;
                                for (int k = 0; k < formarray.length(); k++) {
                                    if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                        questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        questionposition = k;
                                    }
                                }
                                for(int l=0;l<questionsarray.length();l++)
                                {
                                    if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(radiogroup.getTag().toString()))
                                        questionid = questionsarray.getJSONObject(l).getString("id").toString();
                                }

                                if(formrule_array.length()>0)
                                {
                                    for(int p=0;p<formrule_array.length();p++)
                                    {
                                        String method = formrule_array.getJSONObject(p).getString("method").toString();
                                        JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                        JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                        JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                        int conditioncount=0;
                                        ArrayList<String> values=new ArrayList<String>();
                                        for(int j=0;j<condition_array.length();j++)
                                        {
                                            values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                    {
                                                        if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                    if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                        questionsarray.getJSONObject(n).put("is_required", "1");
                                                                    else
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("is_other", "");
                                                                    questionsarray.getJSONObject(n).put("visual", "1");
                                                                    showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int l=0;l<questionsarray.length();l++) {
                                                if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(radiogroup.getTag().toString()))
                                                {
                                                    if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                        if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Is")) {
                                                            JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                            if (questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                                questionsarray.getJSONObject(l).put("isrule", true);
                                                                conditioncount++;
                                                            }else
                                                                questionsarray.getJSONObject(l).put("isrule", false);
                                                        } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNot")) {
                                                            JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                            if (!questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                                questionsarray.getJSONObject(l).put("isrule", true);
                                                                conditioncount++;
                                                            }else
                                                                questionsarray.getJSONObject(l).put("isrule", false);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if(method.equalsIgnoreCase("1")){
                                            if(conditioncount >0)
                                            {
                                                for(int m=0;m<show_array.length();m++) {
                                                    for(int n=0;n<questionsarray.length();n++)
                                                    {
                                                        if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                    if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                        questionsarray.getJSONObject(n).put("is_required", "1");
                                                                    else
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("answered", "yes");
                                                                    questionsarray.getJSONObject(n).put("visual", "1");
                                                                    showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                  //  formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                }
                                                for(int m=0;m<hide_array.length();m++) {
                                                    String issubquestion = "";
                                                    String subqueids="";
                                                    for(int n=0;n<questionsarray.length();n++)
                                                    {

                                                        if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                        {
                                                            if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                    questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("is_other", "");
                                                                    questionsarray.getJSONObject(n).put("visual", "0");
                                                                    if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                        questionsarray.getJSONObject(n).put("validationtype", "");
                                                                        questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                    }
                                                                    issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                    subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                    hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if(issubquestion.equalsIgnoreCase("1"))
                                                    {
                                                        String[] subquearray = subqueids.split(",");
                                                        for(int k=0;k<subquearray.length;k++)
                                                        {
                                                            for(int n=0;n<questionsarray.length();n++) {
                                                                if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("is_other", "");
                                                                    questionsarray.getJSONObject(n).put("visual", "0");
                                                                    hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                   // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                }
                                            }
                                        }else
                                        {
                                            HashSet<String> hashSet = new HashSet<String>();
                                            hashSet.addAll(values);
                                            values.clear();
                                            values.addAll(hashSet);
                                            int scuusscondition=0;
                                            for(int q =0;q<questionsarray.length();q++)
                                            {
                                                if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                                {
                                                    scuusscondition++;
                                                }
                                            }
                                            if(values.size()==scuusscondition)
                                            {
                                                for(int m=0;m<show_array.length();m++) {
                                                    for(int n=0;n<questionsarray.length();n++)
                                                    {
                                                        if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                    if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                        questionsarray.getJSONObject(n).put("is_required", "1");
                                                                    else
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("answered", "yes");
                                                                    questionsarray.getJSONObject(n).put("visual", "1");
                                                                    showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                   // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                }
                                                for(int m=0;m<hide_array.length();m++) {
                                                    String issubquestion = "";
                                                    String subqueids="";
                                                    for(int n=0;n<questionsarray.length();n++)
                                                    {

                                                        if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                        {
                                                            if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                    questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("is_other", "");
                                                                    questionsarray.getJSONObject(n).put("visual", "0");
                                                                    if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                        questionsarray.getJSONObject(n).put("validationtype", "");
                                                                        questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                    }
                                                                    issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                    subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                    hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if(issubquestion.equalsIgnoreCase("1"))
                                                    {
                                                        String[] subquearray = subqueids.split(",");
                                                        for(int k=0;k<subquearray.length;k++)
                                                        {
                                                            for(int n=0;n<questionsarray.length();n++) {
                                                                if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("is_other", "");
                                                                    questionsarray.getJSONObject(n).put("visual", "0");
                                                                    hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                    //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                }
                                            }else
                                            {
                                                for(int m=0;m<hide_array.length();m++) {
                                                    for(int n=0;n<questionsarray.length();n++)
                                                    {
                                                        if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                        {
                                                            if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                    if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                        questionsarray.getJSONObject(n).put("is_required", "1");
                                                                    else
                                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("answered", "yes");
                                                                    questionsarray.getJSONObject(n).put("visual", "1");
                                                                    showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                    //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                }
                                            }
                                        }
                                    }
                                    formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                }
                            }catch(Exception ex)
                            {
                                Log.e("Json Exception",ex.getMessage().toString());
                            }
                    }
                    }



                });
                row.addView(radiogroup);
            } else if (type.equalsIgnoreCase("qmpi")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmpi_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                row.setGravity(Gravity.CENTER);
                row.setTag(validation);
                row.setLayoutParams(params);
                int imgwidth = (int) getResources().getDimension(R.dimen.surveyform_qmpi_pic_width);
                int imgheight = (int) getResources().getDimension(R.dimen.surveyform_qmpi_pic_height);
                int imgbtnwidth = (int) getResources().getDimension(R.dimen.surveyform_qmpi_pic_button_width);
                int imgbtnheight = (int) getResources().getDimension(R.dimen.surveyform_qmpi_pic_button_height);
                TableRow.LayoutParams imgparams = new TableRow.LayoutParams(imgwidth, imgheight);
                TableRow.LayoutParams btnparams = new TableRow.LayoutParams(imgbtnwidth, imgbtnheight);
                final Button btntakepic = new Button(this);
                final ImageView img = new ImageView(this);
                try {
                    final JSONObject validationobj = new JSONObject(row.getTag().toString());
                    if (validationobj.getString("values").toString().equalsIgnoreCase("1")) {
                        btntakepic.setBackgroundResource(R.drawable.takepicture);
                    }else{
                        btntakepic.setBackgroundResource(R.drawable.selectpicture);
                    }
                }catch (Exception ex)
                {

                }
                img.setLayoutParams(imgparams);
                img.setTag(id);
                img.setId(id);
                img.setVisibility(View.GONE);

                btntakepic.setLayoutParams(btnparams);
                btntakepic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(startaudio.size() == stopaudio.size()) {
                            for(int j=0;j<listedittext.size();j++)
                            {
                                listedittext.get(j).clearFocus();
                            }
                            btntakepic.requestFocus();

                        TAKE_PHOTO_CODE = id;
                        imgsource = img;
                        MyApplication.getInstance().setImgsource(img);
                        MyApplication.getInstance().setCamerabar(1);
                        ids = id;

                        try {
                            final JSONObject validationobj = new JSONObject(row.getTag().toString());
                            MyApplication.getInstance().setImagetype(validationobj.getString("values").toString());
                            boolean result = CameraUtility.checkPermission(SurveyForm.this);
                            if (validationobj.getString("values").toString().equalsIgnoreCase("1")) {
                                if (result) {

                                    Calendar c1 = Calendar.getInstance();
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
                                    devicedate1 = sdf1.format(c1.getTime());
                                    File destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root"+File.separator+"CaptureImages");
                                    captureimgfile = new File(destination, "/IMG_"+devicedate1+"_"+quessid+"_"+Userid+".jpg");

                                    if(!destination.exists())
                                    {
                                        destination.mkdirs();
                                    }

                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    fileUri = Uri.fromFile(captureimgfile);
                                    Log.i("fileUri : ", String.valueOf(fileUri));
                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                    startActivityForResult(cameraIntent, id);

                                }

                            } else {
                                if (result) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "Select File"), id);
                                }

                            }

                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }
                    }else
                    {
                        Toast.makeText(SurveyForm.this,"Please Stop the Recording",Toast.LENGTH_SHORT).show();
                    }


                    }
                });
                gridformcontrolheight = height;
                row.addView(btntakepic);
                row.addView(img);

            }
            else if (type.equalsIgnoreCase("qmir")) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(marginleft, 0, marginright, 0);
                row.setGravity(Gravity.CENTER);
                row.setTag(validation);
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmpi_height);
                int imgwidth = (int) getResources().getDimension(R.dimen.surveyform_qmpi_pic_width);
                int imgheight = (int) getResources().getDimension(R.dimen.surveyform_qmpi_pic_height);
                TableRow.LayoutParams imgparams = new TableRow.LayoutParams(imgwidth, imgheight);

                final ImageView img = new ImageView(this);
                img.setLayoutParams(imgparams);
                img.setTag(id);
                img.setId(id);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                img.setBackground(getResources().getDrawable(R.drawable.no_image_available));
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        img.requestFocus();
                        String imgurl =  ((TableRow)view.getParent()).getTag().toString();
                        if(img.getDrawable() != null) {
                            final Dialog ViewDialog = new Dialog(SurveyForm.this);
                            ViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            ViewDialog.setContentView(R.layout.alert_image_dialoge);
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            ViewDialog.setCanceledOnTouchOutside(false);
                            lp.copyFrom(ViewDialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                            ViewDialog.show();
                            ViewDialog.getWindow().setAttributes(lp);
                            ImageView imgs = (ImageView) ViewDialog.findViewById(R.id.img);

                            imgs.setScaleType(ImageView.ScaleType.FIT_XY);
                            imgs.setImageDrawable(img.getDrawable());

                            Button close = (Button) ViewDialog.findViewById(R.id.btnclose);
                            close.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    ViewDialog.dismiss();
                                }
                            });


                            ViewDialog.show();
                        }
                    }
                });
                File destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root"+File.separator+"DoNotDelete_Vizibee");
                File file = new File(destination, "/IMG_"+subquestionid+".jpg");
                if(file.exists())
                {
                    img.setBackground(null);
                    Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath()
                    );
                    img.setImageBitmap(bmp);
                }
             gridformcontrolheight = height;
                row.addView(img);
            }
            else if (type.equalsIgnoreCase("qmtn")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmtn_height);
                JSONObject jobjvalidation = new JSONObject(validation);
                final String validationtype = jobjvalidation.has("type")?jobjvalidation.getString("type").toString():"";
                final String validationvalue = jobjvalidation.has("values")?jobjvalidation.getString("values").toString():"";
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmtnparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 2, controlmarginright, 2);
                row.setTag(isgrid);
                final JSONObject validationobj = new JSONObject(validation);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View qmtnview = headinflater.inflate(R.layout.alphtextbox, null, false);
                final EditText numbertext = (EditText) qmtnview.findViewById(R.id.edittext);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingtop);
                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingright);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmrb_paddingbottom);
                numbertext.setPadding(paddingleft, paddingtop, paddingbottom, paddingright);
                numbertext.setLayoutParams(params);
                numbertext.setTag(id);
                numbertext.setId(id);
                numbertext.setSingleLine(true);
                listedittext.add(numbertext);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                numbertext.setTextSize(size);
                if (decimallength.equalsIgnoreCase("0"))
                    numbertext.setInputType(InputType.TYPE_CLASS_NUMBER);
                else
                    numbertext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                numbertext.setBackgroundResource(R.drawable.rounded_border_edittext);

                numbertext.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            try {
                                if (Integer.parseInt(decimallength) > 0) {
                                    String userInput = numbertext.getText().toString();

                                    if (TextUtils.isEmpty(userInput)) {
                                        userInput = "";
                                    } else {
                                        String floatstring = "%."+decimallength+"f";
                                        float floatValue = Float.parseFloat(userInput);
                                        userInput = String.format(floatstring, floatValue);
                                    }

                                    numbertext.setText(userInput);
                                }
                            }catch(Exception ex){

                            }
                        }
                        return false;
                    }
                });
                numbertext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            TableRow vparent = (TableRow) numbertext.getParent().getParent();
                            int id = vparent.getId();
                            int isformtype = Integer.parseInt(vparent.getTag().toString());
                            if(isformtype == 0) {
                                int titleid = getResources().getIdentifier(String.valueOf(id - 1), "id", "com.mapolbs.vizibeebritannia");
                                TableRow titletablerow = (TableRow) findViewById(titleid);
                                RelativeLayout rellay = (RelativeLayout) titletablerow.getChildAt(0);
                                LinearLayout lilay = (LinearLayout) rellay.getChildAt(0);
                                TextView txttitle = (TextView) lilay.getChildAt(0);
                                for (int k = 0; k < formarray.length(); k++) {
                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    for (int l = 0; l < questionsarray.length(); l++) {
                                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(numbertext.getTag().toString())) {
                                            questionsarray.getJSONObject(l).put("answer_id", s.toString());
                                            questionsarray.getJSONObject(l).put("answer", s.toString());
                                            questionsarray.getJSONObject(l).put("is_other", "");
                                            if (s.toString().length() > 0) {
                                                questionsarray.getJSONObject(l).put("validationtype", validationtype);
                                                questionsarray.getJSONObject(l).put("validationvalue", validationvalue);
                                            } else {
                                                questionsarray.getJSONObject(l).put("validationtype", "");
                                                questionsarray.getJSONObject(l).put("validationvalue", "");
                                            }
                                            questionsarray.getJSONObject(l).put("title", txttitle.getText().toString());
                                        }
                                    }
                                    formarray.getJSONObject(k).put("questions", questionsarray);
                                }
                            }else
                            {
                                LinearLayout lihead = (LinearLayout) vparent.getParent().getParent();
                                TextView txthead = (TextView)lihead.getChildAt(1);
                                LinearLayout lilayout = (LinearLayout) vparent.getParent();
                                LinearLayout parentlilayout = (LinearLayout) lilayout.getParent().getParent();

                                LinearLayout child = (LinearLayout) parentlilayout.getChildAt(0);

                                if(child.getChildCount() == 7)
                                    child = (LinearLayout) parentlilayout.getChildAt(1);
                                else
                                    child = (LinearLayout) parentlilayout.getChildAt(0);
                                LinearLayout child1 = (LinearLayout) child.getChildAt(3);
                                TextView txttitle = (TextView) child1.getChildAt(0);
                               for (int k = 0; k < formarray.length(); k++) {
                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    for (int l = 0; l < questionsarray.length(); l++) {
                                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(numbertext.getTag().toString())) {
                                            questionsarray.getJSONObject(l).put("answer_id", s.toString());
                                            questionsarray.getJSONObject(l).put("answer", s.toString());
                                            questionsarray.getJSONObject(l).put("is_other", "");
                                            if (s.toString().length() > 0) {
                                                questionsarray.getJSONObject(l).put("validationtype", validationtype);
                                                questionsarray.getJSONObject(l).put("validationvalue", validationvalue);
                                            } else {
                                                questionsarray.getJSONObject(l).put("validationtype", "");
                                                questionsarray.getJSONObject(l).put("validationvalue", "");
                                            }
                                            questionsarray.getJSONObject(l).put("title", txthead.getText().toString()+" "+txttitle.getText().toString());
                                        }
                                    }
                                    formarray.getJSONObject(k).put("questions", questionsarray);
                                }
                            }

                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                            for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(numbertext.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int p=0;p<formrule_array.length();p++)
                                {
                                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                           // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }

                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(numbertext.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                        if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                        if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Less Than")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) < Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Less than or Equal To")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) <= Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Greater Than")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) > Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Greater than or Equal to")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) >= Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    }else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Between")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) >= Float.parseFloat(conditionvalueobj.getString("condition").toString())&&Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) <= Float.parseFloat(conditionvalueobj.getString("condition_between").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }
                                }
                                formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }

                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }
                    }
                });
                row.addView(qmtnview);
            } else if (type.equalsIgnoreCase("qmds")) {
                ArrayList<SimpleDropDownData>  SimpleDropdownlist = new ArrayList<SimpleDropDownData>();
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmds_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmdsparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                row.setGravity(Gravity.CENTER);
                JSONArray valuearray = new JSONArray(values);
                SimpleDropdownlist.add(new SimpleDropDownData("Select", "Select", questionid, "", ""));
                for (int i = 0; i < valuearray.length(); i++) {
                    String subids="";
                        JSONArray subquearray = new JSONArray(valuearray.getJSONObject(i).has("sub_questions")?valuearray.getJSONObject(i).getString("sub_questions").toString():"[]");
                        for(int t=0;t<subquearray.length();t++) {
                            subqueuniqueid++;
                            subids += subqueuniqueid+",";
                        }
                    SimpleDropdownlist.add(new SimpleDropDownData(valuearray.getJSONObject(i).getString("code"), valuearray.getJSONObject(i).getString("title"), questionid, valuearray.getJSONObject(i).has("form_id")?valuearray.getJSONObject(i).getString("form_id"):"",String.valueOf(subids.substring(0,subids.length()>0?subids.length()-1:subids.length()))));
                }
                if(isother.equalsIgnoreCase("1"))
                {
                    SimpleDropdownlist.add(new SimpleDropDownData("Others", "Others",questionid, "",""));
                }
                final SimpleDropdownCustomAdapter adapter = new SimpleDropdownCustomAdapter(SurveyForm.this, SimpleDropdownlist);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.spinner, null, false);
                final Spinner spn = (Spinner) view.findViewById(R.id.spinner);

                    view.setLayoutParams(params);

                int paddingleft = (int) getResources().getDimension(R.dimen.surveyform_qmds_paddingleft);
                int paddingtop = (int) getResources().getDimension(R.dimen.surveyform_qmds_paddingtop);
                spn.setPadding(paddingleft,0,0,0);
                spn.setTag(id);
                spn.setId(id);
                spn.setAdapter(adapter);



                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View arg1,
                                               int pos, long arg3) {
                        try {
                            if(arg1!=null) {
                                arg1.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                                spn.requestFocus();
                                View view = (View) arg1.getParent();
                                if (view != null) {
                                    TextView txtid = (TextView) view.findViewById(R.id.txt_id);
                                    TextView txtname = (TextView) view.findViewById(R.id.txt_name);
                                    TextView txt_queid = (TextView) view.findViewById(R.id.txt_queid);
                                    TextView txt_formid = (TextView) view.findViewById(R.id.txt_formid);
                                    TextView txt_childid = (TextView) view.findViewById(R.id.txt_childid);
                                    String[] ids = txt_childid.getText().toString().equalsIgnoreCase("") ? null : txt_childid.getText().toString().split(",");
                                    String[] dependids;
                                    String strdependids = "";
                                    for (int i = 0; i < adapter.getCount(); i++) {
                                        if (!adapter.getItem(i).getChildid().toString().equalsIgnoreCase(""))
                                            strdependids += adapter.getItem(i).getChildid() + ",";
                                    }
                                    strdependids = strdependids.substring(0, strdependids.length() > 0 ? strdependids.length() - 1 : 0);
                                    dependids = strdependids.equalsIgnoreCase("") ? null : strdependids.split(",");
                                    if (dependids != null) {
                                        for (int s = 0; s < dependids.length; s++) {
                                            resID = getResources().getIdentifier(dependids[s], "id", "com.mapolbs.vizibeebritannia");
                                            String questiontype = "";
                                            try {
                                                for (int k = 0; k < formarray.length(); k++) {
                                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                                    for (int l = 0; l < questionsarray.length(); l++) {
                                                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                            questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                            questionsarray.getJSONObject(l).put("dummy_is_required", questionsarray.getJSONObject(l).has("dummy_is_required") ? questionsarray.getJSONObject(l).getString("dummy_is_required").toString() : questionsarray.getJSONObject(l).getString("is_required").toString());
                                                            questionsarray.getJSONObject(l).put("is_required", "0");
                                                            questionsarray.getJSONObject(l).put("answer_id", "");
                                                            questionsarray.getJSONObject(l).put("answer", "");
                                                            questionsarray.getJSONObject(l).put("is_other", "");
                                                            questionsarray.getJSONObject(l).put("visual", "0");

                                                        }
                                                    }
                                                    formarray.getJSONObject(k).put("questions", questionsarray);

                                                }
                                            } catch (Exception ex) {
                                                Log.e("Json exception", ex.getMessage().toString());
                                            }
                                            hiddencontrol(String.valueOf(resID),questiontype);

                                        }
                                    }
                                    if (ids != null) {
                                        for (int o = 0; o < ids.length; o++) {
                                            resID = getResources().getIdentifier(ids[o], "id", "com.mapolbs.vizibeebritannia");
                                            String questiontype = "";
                                            try {
                                                for (int k = 0; k < formarray.length(); k++) {
                                                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                                    for (int l = 0; l < questionsarray.length(); l++) {
                                                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                            questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                            if (questionsarray.getJSONObject(l).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                questionsarray.getJSONObject(l).put("is_required", "1");
                                                            else
                                                                questionsarray.getJSONObject(l).put("is_required", "0");
                                                            questionsarray.getJSONObject(l).put("answered", "yes");
                                                            questionsarray.getJSONObject(l).put("visual", "1");

                                                        }
                                                    }
                                                    formarray.getJSONObject(k).put("questions", questionsarray);

                                                }
                                            } catch (Exception ex) {
                                                Log.e("Json exception", ex.getMessage().toString());
                                            }
                                            showcontrol(String.valueOf(resID),questiontype);

                                        }
                                    }

                                    try {
                                        for (int k = 0; k < formarray.length(); k++) {
                                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                            for (int l = 0; l < questionsarray.length(); l++) {
                                                if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(spn.getTag().toString())) {
                                                    questionsarray.getJSONObject(l).put("answer_id", txtid.getText().toString());
                                                    questionsarray.getJSONObject(l).put("answer", txtname.getText().toString());
                                                    questionsarray.getJSONObject(l).put("form_id", "");
                                                    if (txtname.getText().toString().equalsIgnoreCase("Others")) {
                                                        questionsarray.getJSONObject(l).put("is_other", "1");
                                                        questionsarray.getJSONObject(l).put("others", "");
                                                    } else {
                                                        questionsarray.getJSONObject(l).put("is_other", "");
                                                        questionsarray.getJSONObject(l).put("others", "");
                                                    }
                                                }
                                            }
                                            formarray.getJSONObject(k).put("questions", questionsarray);
                                        }
                                    } catch (Exception ex) {
                                        Log.e("Json exception", ex.getMessage().toString());
                                    }

                                    TableRow vparent = (TableRow) spn.getParent().getParent();
                                    int id = vparent.getId();
                                    int emptyid = getResources().getIdentifier(String.valueOf(id + 1), "id", "com.mapolbs.vizibeebritannia");
                                    TableRow emptyrow = (TableRow) findViewById(emptyid);
                                    int remarksid = getResources().getIdentifier(String.valueOf(id + 2), "id", "com.mapolbs.vizibeebritannia");
                                    TableRow remarksrow = (TableRow) findViewById(remarksid);
                                    EditText edt = (EditText) remarksrow.getChildAt(0);
                                    emptyrow.setVisibility(View.GONE);
                                    remarksrow.setVisibility(View.GONE);


                                    if (edt != null) {
                                        if (txtname.getText().toString().equalsIgnoreCase("Others") || txtname.getText().toString().equalsIgnoreCase("Other")) {

                                            emptyrow.setVisibility(View.VISIBLE);
                                            emptyrow.setBackgroundColor(Color.parseColor("#EFEFEF"));
                                            remarksrow.setVisibility(View.VISIBLE);
                                            EditText edttext = (EditText) remarksrow.getChildAt(0);
                                            edttext.setText("");
                                        } else {
                                            emptyrow.setVisibility(View.GONE);
                                            emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                            remarksrow.setVisibility(View.GONE);
                                            EditText edttext = (EditText) remarksrow.getChildAt(0);
                                            edttext.setText("");
                                        }
                                    }


                                }
                            }
                        } catch (Exception ex) {
                            Log.e("Default Form", ex.getMessage().toString());
                        }

                        try {
                            TableRow vparent = (TableRow) spn.getParent().getParent();
                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                            for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(spn.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int p=0;p<formrule_array.length();p++)
                                {
                                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                           // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }
                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(spn.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Is")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNot")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (!questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                              //  formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }
                                }
                                formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }
                        }catch(Exception ex)
                        {
                            Log.e("Json Exception",ex.getMessage().toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                row.addView(view);



            } else if (type.equalsIgnoreCase("qmdm")) {
                String[] itemscode;
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmdm_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                TableRow.LayoutParams qmdmparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                //row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                JSONArray valuearray = new JSONArray(values);

                final ArrayList<SimpleDropDownData>  SimpleDropdownlist = new ArrayList<SimpleDropDownData>();
                strdependids="";
                String subqueids="";
                for (int i = 0; i < valuearray.length(); i++) {
                    String subids="";
                    SimpleDropdownlist.add(new SimpleDropDownData(valuearray.getJSONObject(i).getString("code"), valuearray.getJSONObject(i).getString("title"), questionid, "",String.valueOf(subids.substring(0,subids.length()>0?subids.length()-1:subids.length()))));
                }
                if(isother.equalsIgnoreCase("1"))
                {
                    SimpleDropdownlist.add(new SimpleDropDownData("Others", "Others",questionid, "",""));
                }
                strdependids = subqueids.substring(0,subqueids.length()>0?subqueids.length()-1:subqueids.length());
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                final View view = headinflater.inflate(R.layout.multi_spinner, null, false);
                    view.setLayoutParams(params);
                final boolean[] checkSelected = new boolean[SimpleDropdownlist.size()];
                for (int i = 0; i < checkSelected.length; i++) {
                    checkSelected[i] = false;
                }
                final TextView txt = new TextView(this);
                txt.setId(id);
                txt.setVisibility(View.GONE);
                final TextView tv = (TextView) view.findViewById(R.id.SelectBox);
                tv.setTag(id);
                final TextView txtid = (TextView) view.findViewById(R.id.txtid);
                final TextView txtdeptid = (TextView) view.findViewById(R.id.txtdeptid);
                final TextView txtselectcount = (TextView) view.findViewById(R.id.txtselectcount);
                final TextView txtothers = (TextView) view.findViewById(R.id.txtothers);
                txtothers.setText(isother);

                view.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                       try {
                           view.requestFocus();
                           final Dialog dialog = new Dialog(SurveyForm.this);
                           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                           dialog.setContentView(R.layout.multispinner_popup_window);
                           dialog.setTitle("Please Select");
                           WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                           lp.copyFrom(dialog.getWindow().getAttributes());
                           lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                           lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                           lp.gravity = Gravity.CENTER;
                           dialog.getWindow().setAttributes(lp);
                           dialog.setCanceledOnTouchOutside(false);
                           final ListView list = (ListView) dialog.findViewById(R.id.dropDownList);
                           final DropDownListAdapter adapter = new DropDownListAdapter(SurveyForm.this, SimpleDropdownlist, tv, checkSelected, txtid, txtdeptid, txtselectcount);
                           list.setAdapter(adapter);

                           Button dialogButton = (Button) dialog.findViewById(R.id.btnok);
                           dialogButton.setBackground(getResources().getDrawable(R.drawable.multicheck_button));
                           dialogButton.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   dialog.dismiss();

                                   String[] ids = txtdeptid.getText().toString().equalsIgnoreCase("") ? null : txtdeptid.getText().toString().split(",");
                                   String[] dependids;


                                   dependids = strdependids.equalsIgnoreCase("") ? null : strdependids.split(",");
                                   if (dependids != null) {
                                       for (int s = 0; s < dependids.length; s++) {
                                           resID = getResources().getIdentifier(dependids[s], "id", "com.mapolbs.vizibeebritannia");
                                           String questiontype = "";
                                           try {
                                               for (int k = 0; k < formarray.length(); k++) {
                                                   JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                                   for (int l = 0; l < questionsarray.length(); l++) {
                                                       if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                           questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                           questionsarray.getJSONObject(l).put("dummy_is_required", questionsarray.getJSONObject(l).has("dummy_is_required") ? questionsarray.getJSONObject(l).getString("dummy_is_required").toString() : questionsarray.getJSONObject(l).getString("is_required").toString());
                                                           questionsarray.getJSONObject(l).put("is_required", "0");
                                                           questionsarray.getJSONObject(l).put("answer_id", "");
                                                           questionsarray.getJSONObject(l).put("answer", "");
                                                           questionsarray.getJSONObject(l).put("is_other", "");
                                                           questionsarray.getJSONObject(l).put("visual", "0");

                                                       }
                                                   }
                                                   formarray.getJSONObject(k).put("questions", questionsarray);

                                               }
                                           } catch (Exception ex) {
                                               Log.e("Json exception", ex.getMessage().toString());
                                           }

                                       }
                                   }
                                   if (ids != null) {
                                       for (int o = 0; o < ids.length; o++) {
                                           resID = getResources().getIdentifier(ids[o], "id", "com.mapolbs.vizibeebritannia");
                                           String questiontype = "";
                                           try {
                                               for (int k = 0; k < formarray.length(); k++) {
                                                   JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                                   for (int l = 0; l < questionsarray.length(); l++) {
                                                       if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                           questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                           if (questionsarray.getJSONObject(l).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                               questionsarray.getJSONObject(l).put("is_required", "1");
                                                           else
                                                               questionsarray.getJSONObject(l).put("is_required", "0");
                                                           questionsarray.getJSONObject(l).put("answered", "yes");
                                                           questionsarray.getJSONObject(l).put("visual", "1");
                                                       }
                                                   }
                                                   formarray.getJSONObject(k).put("questions", questionsarray);

                                               }
                                           } catch (Exception ex) {
                                               Log.e("Json exception", ex.getMessage().toString());
                                           }

                                       }
                                   }
                                   if (txtothers.getText().toString().equalsIgnoreCase("1")) {
                                       TableRow vparent = (TableRow) view.getParent();
                                       int id = vparent.getId();
                                       int emptyid = getResources().getIdentifier(String.valueOf(id + 1), "id", "com.mapolbs.vizibeebritannia");
                                       TableRow emptyrow = (TableRow) findViewById(emptyid);
                                       int remarksid = getResources().getIdentifier(String.valueOf(id + 2), "id", "com.mapolbs.vizibeebritannia");
                                       TableRow remarksrow = (TableRow) findViewById(remarksid);
                                       EditText edt = (EditText) remarksrow.getChildAt(0);

                                       String idss = tv.getTag().toString();
                                       String answer = "";
                                       try {
                                           for (int k = 0; k < formarray.length(); k++) {
                                               JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                               for (int l = 0; l < questionsarray.length(); l++) {
                                                   if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(idss))) {
                                                       JSONArray ansJarray = new JSONArray(questionsarray.getJSONObject(l).getString("answer").toString());
                                                       for (int j = 0; j < ansJarray.length(); j++) {
                                                           answer = ansJarray.getJSONObject(j).getString("answer");
                                                           if (edt != null) {
                                                               if (answer.equalsIgnoreCase("Others")) {

                                                                   emptyrow.setVisibility(View.VISIBLE);
                                                                   remarksrow.setVisibility(View.VISIBLE);
                                                                   EditText edttext = (EditText) remarksrow.getChildAt(0);
                                                                   edttext.setText("");
                                                                   questionsarray.getJSONObject(l).put("is_other", "1");
                                                               } else {
                                                                   emptyrow.setVisibility(View.GONE);
                                                                   remarksrow.setVisibility(View.GONE);
                                                                   EditText edttext = (EditText) remarksrow.getChildAt(0);
                                                                   edttext.setText("");
                                                                   questionsarray.getJSONObject(l).put("is_other", "");
                                                               }
                                                           }

                                                       }
                                                   }
                                               }
                                               formarray.getJSONObject(k).put("questions", questionsarray);
                                           }
                                       } catch (Exception ex) {
                                           Log.e("Json exception", ex.getMessage().toString());
                                       }


                                       emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                       if (edt != null) {
                                           if (answer.equalsIgnoreCase("Others")) {
                                               emptyrow.setBackgroundColor(Color.parseColor("#EFEFEF"));
                                               emptyrow.setVisibility(View.VISIBLE);
                                               remarksrow.setVisibility(View.VISIBLE);
                                               EditText edttext = (EditText) remarksrow.getChildAt(0);
                                               edttext.setText("");
                                           } else {
                                               emptyrow.setVisibility(View.GONE);
                                               remarksrow.setVisibility(View.GONE);
                                               EditText edttext = (EditText) remarksrow.getChildAt(0);
                                               edttext.setText("");
                                           }
                                       }

                                   }
                                   String idss = tv.getTag().toString();
                                   try {
                                       TableRow vparent = (TableRow) txt.getParent();
                                       RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                                       TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                                       String itemid = txtrowtype.getTag().toString();
                                       //Page Rule
                                       JSONArray questionsarray = null;
                                       String questionid = "";
                                       int questionposition = 0;
                                       for (int k = 0; k < formarray.length(); k++) {
                                           if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                               questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                               questionposition = k;
                                           }
                                       }
                                       for(int l=0;l<questionsarray.length();l++)
                                       {
                                           if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(idss))
                                               questionid = questionsarray.getJSONObject(l).getString("id").toString();
                                       }

                                       if(formrule_array.length()>0)
                                       {
                                           for(int i=0;i<formrule_array.length();i++)
                                           {
                                               String method = formrule_array.getJSONObject(i).getString("method").toString();
                                               JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(i).getString("condition").toString());
                                               JSONArray show_array = new JSONArray(formrule_array.getJSONObject(i).getString("show").toString());
                                               JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(i).getString("hide").toString());
                                               int conditioncount=0;
                                               ArrayList<String> values=new ArrayList<String>();
                                               for(int j=0;j<condition_array.length();j++)
                                               {
                                                   values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                                   for(int m=0;m<hide_array.length();m++) {
                                                       for(int n=0;n<questionsarray.length();n++)
                                                       {
                                                           if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                           {
                                                               if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                   if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                       if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                           if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                               questionsarray.getJSONObject(n).put("is_required", "1");
                                                                           else
                                                                               questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("is_other", "");
                                                                           questionsarray.getJSONObject(n).put("visual", "1");
                                                                           showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                       }
                                                    //   formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                   }
                                                   for(int l=0;l<questionsarray.length();l++) {
                                                       if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(idss))
                                                       {
                                                           if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                               if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                                   if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                                       questionsarray.getJSONObject(l).put("isrule", true);
                                                                       conditioncount++;
                                                                   }else
                                                                       questionsarray.getJSONObject(l).put("isrule", false);
                                                               } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                                   if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                                       questionsarray.getJSONObject(l).put("isrule", true);
                                                                       conditioncount++;
                                                                   }else
                                                                       questionsarray.getJSONObject(l).put("isrule", false);
                                                               } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Contains")) {
                                                                   JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                   if (questionsarray.getJSONObject(l).getString("answer").toString().trim().contains(conditionvalueobj.getString("condition").toString())) {
                                                                       questionsarray.getJSONObject(l).put("isrule", true);
                                                                       conditioncount++;
                                                                   }else
                                                                       questionsarray.getJSONObject(l).put("isrule", false);
                                                               } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Not Contains")) {
                                                                   JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                                   if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().contains(conditionvalueobj.getString("condition").toString())) {
                                                                       questionsarray.getJSONObject(l).put("isrule", true);
                                                                       conditioncount++;
                                                                   }else
                                                                       questionsarray.getJSONObject(l).put("isrule", false);
                                                               }
                                                           }
                                                       }
                                                   }
                                               }


                                               if(method.equalsIgnoreCase("1")){
                                                   if(conditioncount >0)
                                                   {
                                                       for(int m=0;m<show_array.length();m++) {
                                                           for(int n=0;n<questionsarray.length();n++)
                                                           {
                                                               if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                   if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                       if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                           if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                               questionsarray.getJSONObject(n).put("is_required", "1");
                                                                           else
                                                                               questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("answered", "yes");
                                                                           questionsarray.getJSONObject(n).put("visual", "1");
                                                                           showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                         //  formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                       }
                                                       for(int m=0;m<hide_array.length();m++) {
                                                           String issubquestion = "";
                                                           String subqueids="";
                                                           for(int n=0;n<questionsarray.length();n++)
                                                           {

                                                               if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                               {
                                                                   if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                       if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                           questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                           questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("is_other", "");
                                                                           questionsarray.getJSONObject(n).put("visual", "0");
                                                                           if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                               questionsarray.getJSONObject(n).put("validationtype", "");
                                                                               questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                           }
                                                                           issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                           subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                           hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                           if(issubquestion.equalsIgnoreCase("1"))
                                                           {
                                                               String[] subquearray = subqueids.split(",");
                                                               for(int k=0;k<subquearray.length;k++)
                                                               {
                                                                   for(int n=0;n<questionsarray.length();n++) {
                                                                       if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                           questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                           questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("is_other", "");
                                                                           questionsarray.getJSONObject(n).put("visual", "0");
                                                                           hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                         //  formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                       }
                                                   }
                                               }else
                                               {
                                                   HashSet<String> hashSet = new HashSet<String>();
                                                   hashSet.addAll(values);
                                                   values.clear();
                                                   values.addAll(hashSet);
                                                   int scuusscondition=0;
                                                   for(int q =0;q<questionsarray.length();q++)
                                                   {
                                                       if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                                       {
                                                           scuusscondition++;
                                                       }
                                                   }
                                                   if(values.size()==scuusscondition)
                                                   {
                                                       for(int m=0;m<show_array.length();m++) {
                                                           for(int n=0;n<questionsarray.length();n++)
                                                           {
                                                               if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                   if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                       if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                           if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                               questionsarray.getJSONObject(n).put("is_required", "1");
                                                                           else
                                                                               questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("answered", "yes");
                                                                           questionsarray.getJSONObject(n).put("visual", "1");
                                                                           showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                           //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                       }
                                                       for(int m=0;m<hide_array.length();m++) {
                                                           String issubquestion = "";
                                                           String subqueids="";
                                                           for(int n=0;n<questionsarray.length();n++)
                                                           {

                                                               if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                               {
                                                                   if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                       if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                           questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                           questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("is_other", "");
                                                                           questionsarray.getJSONObject(n).put("visual", "0");
                                                                           if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                               questionsarray.getJSONObject(n).put("validationtype", "");
                                                                               questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                           }
                                                                           issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                           subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                           hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                           if(issubquestion.equalsIgnoreCase("1"))
                                                           {
                                                               String[] subquearray = subqueids.split(",");
                                                               for(int k=0;k<subquearray.length;k++)
                                                               {
                                                                   for(int n=0;n<questionsarray.length();n++) {
                                                                       if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                           questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                           questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("is_other", "");
                                                                           questionsarray.getJSONObject(n).put("visual", "0");
                                                                           hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                          // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                                       }
                                                   }else
                                                   {
                                                       for(int m=0;m<hide_array.length();m++) {
                                                           for(int n=0;n<questionsarray.length();n++)
                                                           {
                                                               if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                               {
                                                                   if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                       if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                           if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                               questionsarray.getJSONObject(n).put("is_required", "1");
                                                                           else
                                                                               questionsarray.getJSONObject(n).put("is_required", "0");
                                                                           questionsarray.getJSONObject(n).put("answer_id", "");
                                                                           questionsarray.getJSONObject(n).put("answer", "");
                                                                           questionsarray.getJSONObject(n).put("answered", "yes");
                                                                           questionsarray.getJSONObject(n).put("visual", "1");
                                                                           showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                          // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                                       }
                                                   }
                                               }

                                           }

                                           formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                       }
                                   }catch(Exception ex)
                                   {
                                       Log.e("Json Exception",ex.getMessage().toString());
                                   }

                               }
                           });

                           dialog.show();

                       }catch(Exception ex)
                       {
                           Log.e("Dialoge log",ex.getMessage().toString());
                       }
                    }
                });


                row.addView(txt);
                row.addView(view);

            } else if (type.equalsIgnoreCase("qmls")) {
                int qmlsmargintop = (int) getResources().getDimension(R.dimen.surveyform_qmls_seekbar_margintop);
                int qmlsmarginbottom = (int) getResources().getDimension(R.dimen.surveyform_qmls_seekbar_marginbottom);
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmls_height);
                int width = (int) getResources().getDimension(R.dimen.surveyform_qmls_width);
                int fromwidth = (int) getResources().getDimension(R.dimen.surveyform_qmls_fromwidth);
                int towidth = (int) getResources().getDimension(R.dimen.surveyform_qmls_towidth);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TableRow.LayoutParams qmlsparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                gridformcontrolheight = height;
                int thumbbheightpx =  getResources().getDimensionPixelSize(R.dimen.surveyform_qmls_seekbar_thumbheight);
                int thumbbheight = (int)(thumbbheightpx / getResources().getDisplayMetrics().density);
                int thumbwidthpx =  getResources().getDimensionPixelSize(R.dimen.surveyform_qmls_seekbar_thumbhwidth);
                int thumbwidth = (int)(thumbwidthpx / getResources().getDisplayMetrics().density);
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                int paddingleft =(int) getResources().getDimension(R.dimen.surveyform_qmls_marginleft);
                int paddingright = (int) getResources().getDimension(R.dimen.surveyform_qmls_marginright);
                row.setGravity(Gravity.CENTER);
                LinearLayout li = new LinearLayout(this);
                li.setOrientation(LinearLayout.HORIZONTAL);
                li.setLayoutParams(params);
                li.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                TableRow.LayoutParams txtfromparams = new TableRow.LayoutParams(fromwidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                txtfromparams.setMargins(0, qmlsmargintop, 0, qmlsmarginbottom);
                TextView txtfromlabel = new TextView(this);
                txtfromlabel.setText(fromlabel == "null" ? "" : fromlabel);
                txtfromlabel.setTextSize(size);
                txtfromlabel.setLayoutParams(txtfromparams);
                txtfromlabel.setGravity(Gravity.CENTER);
                li.addView(txtfromlabel);

                TableRow.LayoutParams seekbarparams = new TableRow.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                seekbarparams.setMargins(20,20,0,20);
                final SeekBar seekbar = new SeekBar(this);
                final String[] fromtonum = fromtonumber.split(",");
                seekbar.setMax(Integer.parseInt(fromtonum[1]));
                seekbar.setLayoutParams(seekbarparams);
                seekbar.setTag(id);
                seekbar.setId(id);
                ShapeDrawable thumb = new ShapeDrawable(new OvalShape());
                thumb.getPaint().setColor(Color.parseColor("#FC6749"));
                thumb.setIntrinsicHeight(thumbbheight);
                thumb.setIntrinsicWidth(thumbwidth);
                seekbar.setThumb(thumb);


                final TextView txtlabel = new TextView(this);
                txtlabel.setTextSize(size);
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // TODO Auto-generated method stub
                        txtlabel.setText("(" + progress + "/" + fromtonum[1] + ")");
                        try {
                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(seekbar.getTag().toString())) {
                                        questionsarray.getJSONObject(l).put("answer_id", progress);
                                        questionsarray.getJSONObject(l).put("answer", progress);
                                        questionsarray.getJSONObject(l).put("is_other", "");
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);
                            }
                            LinearLayout seekli = (LinearLayout) seekbar.getParent();
                            TableRow vparent = (TableRow) seekli.getParent();
                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                            for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(seekbar.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int p=0;p<formrule_array.length();p++)
                                {
                                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }
                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(seekbar.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Is")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNot")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Less Than")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) < Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Less than or Equal To")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) <= Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Greater Than")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) > Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Greater than or Equal to")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) >= Float.parseFloat(conditionvalueobj.getString("condition").toString())) {
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Between")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) >= Float.parseFloat(conditionvalueobj.getString("condition").toString())&&Float.parseFloat(questionsarray.getJSONObject(l).getString("answer").toString().trim()) <= Float.parseFloat(conditionvalueobj.getString("condition_between").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }
                                }
                                formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }

                        } catch (Exception ex) {
                            Log.e("Json exception", ex.getMessage().toString());
                        }
                    }
                });
                li.addView(seekbar);
                txtlabel.setText("(" + seekbar.getProgress() + "/" + fromtonum[1] + ")");
                li.addView(txtlabel);
                TableRow.LayoutParams tolabelparams = new TableRow.LayoutParams(towidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                int paramsmarginleft = (int) getResources().getDimension(R.dimen.surveyform_qmls_paramsmarginleft);
                int paramsmarginright = (int) getResources().getDimension(R.dimen.surveyform_qmls_paramsmarginright);
                tolabelparams.setMargins(paramsmarginleft, qmlsmargintop, paramsmarginright, qmlsmarginbottom);
                TextView txttolabel = new TextView(this);
                txttolabel.setTextSize(size);
                txttolabel.setLayoutParams(tolabelparams);
                txttolabel.setText(tolabel == "null" ? "" : tolabel);
                txttolabel.setGravity(Gravity.CENTER);
                li.addView(txttolabel);
                row.addView(li);
            }
            else if (type.equalsIgnoreCase("qmar")) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int margintoppx =  getResources().getDimensionPixelSize(R.dimen.surveyform_qmar_marginright);
                int margintop = (int)(margintoppx / getResources().getDisplayMetrics().density);
                int marginbottompx =  getResources().getDimensionPixelSize(R.dimen.surveyform_qmar_marginright);
                int marginbottom = (int)(marginbottompx / getResources().getDisplayMetrics().density);

                int heightpx =  getResources().getDimensionPixelSize(R.dimen.surveyform_qmar_height);
                int heights = (int)(heightpx / getResources().getDisplayMetrics().density);
                int widthpx =  getResources().getDimensionPixelSize(R.dimen.surveyform_qmar_width);
                int widths = (int)(widthpx / getResources().getDisplayMetrics().density);
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                row.setGravity(Gravity.CENTER);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                final View view = headinflater.inflate(R.layout.audio_record, null, false);
                final Button btnstart = (Button)view.findViewById(R.id.btnstart);
                final Button btnstop = (Button)view.findViewById(R.id.btnstop);
                btnstart.setTag(id);
                btnstart.setId(id);
                btnstart.setBackground(getResources().getDrawable(R.drawable.startrecord));
                btnstart.setAllCaps(false);
                Calendar c1 = Calendar.getInstance();
                SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
                devicedate1 = sdf1.format(c1.getTime());
                myRecorder = new MediaRecorder();
                btnstart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ContextCompat.checkSelfPermission(SurveyForm.this,
                                Manifest.permission.RECORD_AUDIO)
                                != PackageManager.PERMISSION_GRANTED) {

                            //When permission is not granted by user, show them message why this permission is needed.
                            if (ActivityCompat.shouldShowRequestPermissionRationale(SurveyForm.this,
                                    Manifest.permission.RECORD_AUDIO)) {
                                Toast.makeText(SurveyForm.this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                                //Give user option to still opt-in the permissions
                                ActivityCompat.requestPermissions(SurveyForm.this,
                                        new String[]{Manifest.permission.RECORD_AUDIO},
                                        MY_PERMISSIONS_RECORD_AUDIO);

                            } else {
                                // Show user dialog to grant permission to record audio
                                ActivityCompat.requestPermissions(SurveyForm.this,
                                        new String[]{Manifest.permission.RECORD_AUDIO},
                                        MY_PERMISSIONS_RECORD_AUDIO);
                            }
                        }
                        //If permission is granted, then go ahead recording audio
                        else if (ContextCompat.checkSelfPermission(SurveyForm.this,
                                Manifest.permission.RECORD_AUDIO)
                                == PackageManager.PERMISSION_GRANTED) {
                            try {

                                startaudio.add("start");
                                File  destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root/"+AUDIO_RECORDER_FOLDER);

                                if(!destination.exists())
                                {
                                    destination.mkdirs();
                                }
                                final File file = new File(destination,"Aud_"+devicedate1+"_"+questionid+"_"+Userid+".3gpp");
                                absfilepath = file.getAbsolutePath();
                                outputFile = file.getAbsolutePath();
                                if (ContextCompat.checkSelfPermission(SurveyForm.this,
                                        Manifest.permission.RECORD_AUDIO)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    //When permission is not granted by user, show them message why this permission is needed.
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(SurveyForm.this,
                                            Manifest.permission.RECORD_AUDIO)) {
                                        Toast.makeText(SurveyForm.this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                                        //Give user option to still opt-in the permissions
                                        ActivityCompat.requestPermissions(SurveyForm.this,
                                                new String[]{Manifest.permission.RECORD_AUDIO},
                                                MY_PERMISSIONS_RECORD_AUDIO);

                                    } else {
                                        // Show user dialog to grant permission to record audio
                                        ActivityCompat.requestPermissions(SurveyForm.this,
                                                new String[]{Manifest.permission.RECORD_AUDIO},
                                                MY_PERMISSIONS_RECORD_AUDIO);
                                    }
                                }
                                //If permission is granted, then go ahead recording audio
                                else if (ContextCompat.checkSelfPermission(SurveyForm.this,
                                        Manifest.permission.RECORD_AUDIO)
                                        == PackageManager.PERMISSION_GRANTED) {

                                    myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                    myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                                    myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                                    myRecorder.setOutputFile(outputFile);

                                    myRecorder.prepare();
                                    myRecorder.start();

                                }

                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if(click == true) {
                                btnstart.setEnabled(false);
                                btnstart.setBackground(getResources().getDrawable(R.drawable.startrecord_disable));
                            }
                            btnstop.setEnabled(true);
                            btnstop.setBackground(getResources().getDrawable(R.drawable.stoprecord));
                            Toast.makeText(getApplicationContext(), "Start recording...",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }

                });

                btnstop.setTag(id);
                btnstop.setId(id);
                btnstop.setBackground(getResources().getDrawable(R.drawable.stoprecord_disable));
                btnstop.setAllCaps(false);
                btnstop.setEnabled(false);

                btnstop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            stopaudio.add("stop");;
                            myRecorder.stop();
                        } catch (IllegalStateException e) {

                            e.printStackTrace();
                        }

                        if(click == true) {
                            btnstop.setEnabled(false);
                            btnstop.setBackground(getResources().getDrawable(R.drawable.stoprecord_disable));
                        }
                        btnstart.setBackground(getResources().getDrawable(R.drawable.startrecord));
                        btnstart.setEnabled(true);
                        try {
                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(btnstop.getTag().toString())) {
                                        questionsarray.getJSONObject(l).put("answer_id", true);
                                        questionsarray.getJSONObject(l).put("answer", true);
                                        questionsarray.getJSONObject(l).put("is_other", "");
                                        questionsarray.getJSONObject(l).put("imagename", "Aud_"+devicedate1+"_"+questionid+"_"+Userid+".3gpp");
                                        questionsarray.getJSONObject(l).put("imagepath", absfilepath);
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);
                            }
                        } catch (Exception ex) {
                            Log.e("Json exception", ex.getMessage().toString());
                        }

                        try {
                            TableRow vparent = (TableRow) btnstop.getParent().getParent();
                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                            for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(btnstop.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int p=0;p<formrule_array.length();p++)
                                {
                                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                           // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }
                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(btnstop.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                        if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                        if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                              //  formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }
                                }
                                formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }
                        }catch(Exception ex)
                        {
                            Log.e("Json Exception",ex.getMessage().toString());
                        }

                        Toast.makeText(getApplicationContext(), "Stop recording...",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                row.addView(view);
            }

            else if (type.equalsIgnoreCase("qmbc")) {
                int height = (int) getResources().getDimension(R.dimen.surveyform_qmbc_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                row.setGravity(Gravity.CENTER);
                row.setLayoutParams(params);
                LinearLayout li = new LinearLayout(this);
                li.setOrientation(LinearLayout.VERTICAL);
                li.setGravity(Gravity.CENTER);
                int imgwidth = (int) getResources().getDimension(R.dimen.surveyform_qmbc_img_width);
                int imgheight = (int) getResources().getDimension(R.dimen.surveyform_qmbc_img_height);
                TableRow.LayoutParams imgparams = new TableRow.LayoutParams(imgwidth, imgheight);
                final TextView txtbarcode = new TextView(this);
                txtbarcode.setTypeface(Typeface.DEFAULT_BOLD);
                int imgmargintop = (int) getResources().getDimension(R.dimen.surveyform_qmgt_imgmargintop);
                int imgmarginbottom = (int) getResources().getDimension(R.dimen.surveyform_qmgt_imgmarginbottom);
                imgparams.setMargins(0, imgmargintop, 0,imgmarginbottom);
                txtbarcode.setGravity(Gravity.CENTER);
                int paddingbottom = (int) getResources().getDimension(R.dimen.surveyform_qmbc_paddingbottom);
                txtbarcode.setPadding(0, 0, 0, paddingbottom);
                txtbarcode.setTag(id);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                txtbarcode.setTextSize(size);
                final ImageView img = new ImageView(this);
                img.setBackgroundResource(R.drawable.barcode);
                img.setLayoutParams(imgparams);
                img.setTag(id);
                img.setId(id);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(startaudio.size() == stopaudio.size()) {
                            for(int j=0;j<listedittext.size();j++)
                            {
                                listedittext.get(j).clearFocus();
                            }
                        img.requestFocus();
                        TAKE_PHOTO_CODE = id;
                        MyApplication.getInstance().setTxtsource(txtbarcode);
                        MyApplication.getInstance().setCamerabar(2);
                        try {
                            IntentIntegrator integrator = new IntentIntegrator(SurveyForm.this);
                            integrator.setPrompt("Scan a barcode");
                            integrator.setCameraId(0);  // Use a specific camera of the device
                            integrator.setOrientationLocked(true);
                            integrator.setBeepEnabled(true);
                            integrator.setCaptureActivity(CaptureActivityPortrait.class);
                            integrator.initiateScan();
                            /*Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                            intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,RSS_14,UPC_A,UPC_E,QR_CODE");

                            startActivityForResult(intent, 0);    //Barcode Scanner to scan for us*/
                        } catch (ActivityNotFoundException e) {
                            // TODO: handle exception
                            showDialog(SurveyForm.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                        }

                    }else
                    {
                        Toast.makeText(SurveyForm.this,"Please Stop the Recording",Toast.LENGTH_SHORT).show();
                    }
                    }
                });
                gridformcontrolheight = height;
                li.addView(img);
                li.addView(txtbarcode);
                row.addView(li);

            } else if (type.equalsIgnoreCase("qmgt")) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(marginleft, 0, marginright, 0);
                row.setGravity(Gravity.CENTER);
                LinearLayout li = new LinearLayout(this);
                li.setOrientation(LinearLayout.VERTICAL);
                li.setGravity(Gravity.CENTER);
                int imgwidth = (int) getResources().getDimension(R.dimen.surveyform_qmbc_img_width);
                int imgheight = (int) getResources().getDimension(R.dimen.surveyform_qmbc_img_height);
                TableRow.LayoutParams imgparams = new TableRow.LayoutParams(imgwidth, imgheight);
                int imgmargintop = (int) getResources().getDimension(R.dimen.surveyform_qmgt_imgmargintop);
                int imgmarginbottom = (int) getResources().getDimension(R.dimen.surveyform_qmgt_imgmarginbottom);
                imgparams.setMargins(0, imgmargintop, 0,imgmarginbottom);
                TableRow.LayoutParams txtparams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int btnmargintop = (int) getResources().getDimension(R.dimen.surveyform_qmgt_btnmargintop);
                int btnmarginbottom = (int) getResources().getDimension(R.dimen.surveyform_qmgt_btnmarginbottom);
                txtparams.setMargins(0, btnmargintop, 0,btnmarginbottom);
                final TextView txtbarcode = new TextView(this);
                txtbarcode.setTypeface(Typeface.DEFAULT_BOLD);
                txtbarcode.setGravity(Gravity.CENTER);
                txtbarcode.setLayoutParams(txtparams);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                txtbarcode.setTextSize(size);
                final Button btnsetlocation = new Button(this);
                final ImageView img = new ImageView(this);

                img.setTag(id);
                img.setId(id);
                img.setBackgroundResource(R.drawable.map);
                img.setLayoutParams(imgparams);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean islocation = false;
                        gps = new GPSTracker(SurveyForm.this);
                        // check if GPS enabled
                        if(checkLocationPermission()) {
                            if (gps.canGetLocation()) {
                                islocation = true;
                            } else {

                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(SurveyForm.this, R.style.MyAlertDialogStyle);
                                // Setting Dialog Title
                                alertDialog.setTitle("GPS is settings");
                                // Setting Dialog Message
                                alertDialog.setMessage(Html.fromHtml("<font color='#ffffff'>GPS is not enabled. Do you want to go to settings menu?</font>"));
                                // On pressing Settings button
                                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(intent);
                                    }
                                });
                                // on pressing cancel button
                                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                // Showing Alert Message
                                alertDialog.show();
                            }
                        }else
                        {
                            Toast.makeText(SurveyForm.this, "Please Allow Location access permission to Proceed.", Toast.LENGTH_LONG).show();
                            requestPermission();
                        }
                        if (islocation == true){
                            if(startaudio.size() == stopaudio.size()) {
                                img.requestFocus();
                                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                    buildAlertMessageNoGps();

                                } else {
                                    MyApplication.getInstance().setImgmapsrc(img);
                                    MyApplication.getInstance().setLocalformid(locformid);
                                    MyApplication.getInstance().setFormarray(formarray);
                                    MyApplication.getInstance().setFormrulearray(formrule_array);

                                    maptextview = txtbarcode;
                                    MyApplication.getInstance().setTxtlatlong(txtbarcode);
                                    MyApplication.getInstance().setMapuniqueid(btnsetlocation.getTag().toString());
                                    MyApplication.getInstance().setMapformarray(formarray);
                                    Intent i = new Intent(SurveyForm.this, MapScreen.class);
                                    startActivity(i);
                                }

                            } else {
                                Toast.makeText(SurveyForm.this, "Please Stop the Recording", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                int btnwidth = (int) getResources().getDimension(R.dimen.surveyform_qmgt_btn_width);
                int btnheight = (int) getResources().getDimension(R.dimen.surveyform_qmgt_btn_height);
                TableRow.LayoutParams btnparams = new TableRow.LayoutParams(btnwidth, btnheight);
                btnsetlocation.setLayoutParams(btnparams);
                btnparams.setMargins(0, btnmargintop, 0,btnmarginbottom);
                btnsetlocation.setGravity(Gravity.CENTER);
                btnsetlocation.setText("Set Location");
                int btnsize = getResources().getDimensionPixelSize(R.dimen.surveyform_qmgt_btntextsize);
                btnsetlocation.setTextSize(btnsize);
                btnsetlocation.setTag(id);
                btnsetlocation.setBackgroundColor(Color.parseColor("#FF5722"));
                btnsetlocation.setTextColor(Color.parseColor("#ffffff"));
                btnsetlocation.setAllCaps(false);

                btnsetlocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.getInstance().getMapaddress() != null) {
                            String[] latlang = MyApplication.getInstance().getMapaddress().toString().split(",");
                            maptextview.setText("Latitude -"+latlang[0]+"\nLongitude -"+latlang[1]);
                        }
                        try {
                            for (int k = 0; k < formarray.length(); k++) {
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                            for (int l = 0; l < questionsarray.length(); l++) {
                                if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(img.getTag().toString())) {
                                    questionsarray.getJSONObject(l).put("answer_id", MyApplication.getInstance().getMapaddress());
                                    questionsarray.getJSONObject(l).put("answer", MyApplication.getInstance().getMapaddress());
                                    questionsarray.getJSONObject(l).put("is_other", "");
                                }
                            }
                            formarray.getJSONObject(k).put("questions", questionsarray);
                        }
                    } catch (Exception ex) {
                        Log.e("Json exception", ex.getMessage().toString());
                    }

                        try {
                            TableRow vparent = (TableRow) img.getParent().getParent();
                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                            for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(img.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int p=0;p<formrule_array.length();p++)
                                {
                                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("is_required").toString().equalsIgnoreCase("1"))
                                                                questionsarray.getJSONObject(n).put("is_required", "1");
                                                            else
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                            questionsarray.getJSONObject(n).put("answer_id", "");
                                                            questionsarray.getJSONObject(n).put("answer", "");
                                                            questionsarray.getJSONObject(n).put("is_other", "");
                                                            questionsarray.getJSONObject(n).put("visual", "1");
                                                            showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                        }
                                                    }
                                                }
                                            }
                                            //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }
                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(img.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                        if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                        if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                        }else
                                                            questionsarray.getJSONObject(l).put("isrule", false);
                                                    }
                                                }
                                            }
                                        }
                                    }



                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("is_required").toString().equalsIgnoreCase("1"))
                                                                questionsarray.getJSONObject(n).put("is_required", "1");
                                                            else
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                            questionsarray.getJSONObject(n).put("answer_id", "");
                                                            questionsarray.getJSONObject(n).put("answer", "");
                                                            questionsarray.getJSONObject(n).put("answered", "yes");
                                                            questionsarray.getJSONObject(n).put("visual", "1");
                                                            showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("is_required").toString().equalsIgnoreCase("1"))
                                                                questionsarray.getJSONObject(n).put("is_required", "1");
                                                            else
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                            questionsarray.getJSONObject(n).put("answer_id", "");
                                                            questionsarray.getJSONObject(n).put("answer", "");
                                                            questionsarray.getJSONObject(n).put("answered", "yes");
                                                            questionsarray.getJSONObject(n).put("visual", "1");
                                                            showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }
                                }
                                formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }
                        }catch(Exception ex)
                        {
                            Log.e("Json Exception",ex.getMessage().toString());
                        }
                    }
                });
                li.addView(img);
                li.addView(txtbarcode);
                //li.addView(btnsetlocation);
                row.addView(li);
            } else if (type.equalsIgnoreCase("qmgc")) {
                TableRow.LayoutParams paramss = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                paramss.setMargins(controlmarginleft, 0, controlmarginright, 0);
                row.setGravity(Gravity.CENTER);


                ListView lstv = new ListView(this);
                if(bgcolour.equalsIgnoreCase("#FFFFFF"))
                    lstv.setBackgroundResource(R.drawable.rounded_border_edittext);
                else
                    lstv.setBackgroundResource(R.drawable.rounded_border_edittext_grey);
                lstv.setLayoutParams(paramss);
                //lstv.setNestedScrollingEnabled(true);
                lstv.setTag(id);
                lstv.setId(id);
                ArrayList<ListClass> rowvaluelist = new ArrayList<ListClass>();
                ArrayList<SimpleListClass> columnvaluelist = new ArrayList<SimpleListClass>();
                JSONArray colvalarray = new JSONArray(columnvalues);
                for (int i = 0; i < colvalarray.length(); i++) {
                    columnvaluelist.add(new SimpleListClass(colvalarray.getJSONObject(i).getString("code"), colvalarray.getJSONObject(i).getString("title")));
                }
                JSONArray rowvalarray = new JSONArray(rowvalues);
                for (int i = 0; i < rowvalarray.length(); i++) {
                    rowvaluelist.add(new ListClass(rowvalarray.getJSONObject(i).getString("code"), rowvalarray.getJSONObject(i).getString("title"), columnvaluelist, lstv.getTag().toString()));
                }
                ListCustomAdapter adapter = new ListCustomAdapter(SurveyForm.this, R.layout.check_list_row, rowvaluelist,bgcolour);
                lstv.setAdapter(adapter);
                setListViewHeightBasedOnItems(row, adapter);
                row.addView(lstv);
            } else if (type.equalsIgnoreCase("qmgm")) {
                TableRow.LayoutParams paramss = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                paramss.setMargins(controlmarginleft, 0, controlmarginright, 0);
                row.setGravity(Gravity.CENTER);

                ListView lstv = new ListView(this);
                lstv.setLayoutParams(paramss);
                if(bgcolour.equalsIgnoreCase("#FFFFFF"))
                    lstv.setBackgroundResource(R.drawable.rounded_border_edittext);
                else
                    lstv.setBackgroundResource(R.drawable.rounded_border_edittext_grey);

                lstv.setTag(id);
                lstv.setId(id);
                ArrayList<ListClass> rowvaluelist = new ArrayList<ListClass>();
                ArrayList<SimpleListClass> columnvaluelist = new ArrayList<SimpleListClass>();
                JSONArray colvalarray = new JSONArray(columnvalues);
                for (int i = 0; i < colvalarray.length(); i++) {
                    columnvaluelist.add(new SimpleListClass(colvalarray.getJSONObject(i).getString("code"), colvalarray.getJSONObject(i).getString("title")));
                }
                JSONArray rowvalarray = new JSONArray(rowvalues);
                for (int i = 0; i < rowvalarray.length(); i++) {
                    rowvaluelist.add(new ListClass(rowvalarray.getJSONObject(i).getString("code"), rowvalarray.getJSONObject(i).getString("title"), columnvaluelist, lstv.getTag().toString()));
                }
                MultiCheckListCustomAdapter adapter = new MultiCheckListCustomAdapter(SurveyForm.this, R.layout.multicheck_list_row, rowvaluelist,bgcolour);
                lstv.setAdapter(adapter);

                setMultiListViewHeightBasedOnItems(row, adapter);
                row.addView(lstv);
            }


            else if (type.equalsIgnoreCase("qmyn")) {
                int height = (int) getResources().getDimension(R.dimen.defaultform_qmyn_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(controlmarginleft, 0, controlmarginright, 0);
                LinearLayout li = new LinearLayout(this);
                li.setOrientation(LinearLayout.HORIZONTAL);
                li.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                LayoutInflater headinflater = SurveyForm.this.getLayoutInflater();
                View view = null;
                if(issinglegrid == 1)
                    view = headinflater.inflate(R.layout.grid_toggle_button, null, false);
                else
                    view = headinflater.inflate(R.layout.custom_toggle_button, null, false);
                view.setLayoutParams(params);
                view.setId(id);
                final Button btnyes = (Button)view.findViewById(R.id.btn_yes);
                final Button btnno = (Button)view.findViewById(R.id.btn_no);
                final TextView txttitle = (TextView)view.findViewById(R.id.txttitle);
                final TextView txtyesformid = (TextView)view.findViewById(R.id.txtyesformid);
                final TextView txtnoformid = (TextView)view.findViewById(R.id.txtnoformid);
                final TextView txtmandatory = (TextView)view.findViewById(R.id.txtmandatory);
                txttitle.setText(title);
                txtmandatory.setVisibility(View.INVISIBLE);
                txttitle.setText(title);
                txttitle.setGravity(Gravity.LEFT);
                txttitle.setTypeface(Typeface.DEFAULT_BOLD);
                txtmandatory.setGravity(Gravity.LEFT);
                txtmandatory.setTypeface(Typeface.DEFAULT_BOLD);
                if(grid != 1){
                if (isrequired.equalsIgnoreCase("1")) {
                    txtmandatory.setVisibility(View.VISIBLE);
                }

            }
                JSONArray valuearray = new JSONArray(values);
                txtyesformid.setText("");
                txtnoformid.setText("");
                String tonglevalue = "";
                final String[] optionarr = new String[2];
                for (int i = 0; i < valuearray.length(); i++) {
                    String subids="";
                    optionarr[i] = valuearray.getJSONObject(i).getString("code");
                    JSONArray subquearray = new JSONArray(valuearray.getJSONObject(i).has("sub_questions")?valuearray.getJSONObject(i).getString("sub_questions").toString():"[]");
                    for(int t=0;t<subquearray.length();t++) {
                            subqueuniqueid++;
                            subids += subqueuniqueid + ",";
                    }
                    if(subids.length()>0)
                    subids = subids.substring(0,subids.length()-1);
                    tonglevalue += subids+"&";
                }
                if(tonglevalue.length()>0)
                tonglevalue = tonglevalue.substring(0,tonglevalue.length()-1);



                btnyes.setTag(tonglevalue);
                btnno.setTag(id);

                btnyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnyes.requestFocus();
                        btnyes.setBackgroundResource(R.drawable.yes_select_selector);
                        btnno.setBackgroundResource(R.drawable.no_normal_selector);
                        String allchild="";
                        String[] yesnochildid = btnyes.getTag().toString().split("&");
                        if(yesnochildid.length ==2)
                        {
                            allchild = btnyes.getTag().toString().replace("&", ",");
                        }else {
                             allchild = btnyes.getTag().toString().replace("&", "");
                        }


                        String[] allchildarray = allchild.split(",");

                        String[] yeschildidarray = null;
                        String[] nochildidarray = null;
                        if(yesnochildid.length > 0) {
                            if(yesnochildid.length > 0 && !yesnochildid[0].equalsIgnoreCase(""))
                                yeschildidarray = yesnochildid[0].split(",");
                            if(yesnochildid.length > 1 && !yesnochildid[1].equalsIgnoreCase(""))
                                nochildidarray = yesnochildid[1].split(",");
                        }

                        String[] childid = null;
                        String[] ids = null;
                        childid =  yeschildidarray;
                        if(childid != null)
                            ids = childid.length>0 ? childid : null;
                        String[] dependids;
                        String strdependids = "";
                        for (int i = 0; i < allchildarray.length; i++) {
                            if (!allchildarray[i].equalsIgnoreCase(""))
                                strdependids += allchildarray[i] + ",";
                        }
                        strdependids = strdependids.substring(0, strdependids.length() > 0 ? strdependids.length() - 1 : 0);
                        dependids = strdependids.equalsIgnoreCase("") ? null : strdependids.split(",");
                        if(dependids!= null){
                            for (int s = 0; s < dependids.length; s++) {
                                resID = getResources().getIdentifier(dependids[s], "id", "com.mapolbs.vizibeebritannia");
                                String questiontype = "";
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                questionsarray.getJSONObject(l).put("dummy_is_required", questionsarray.getJSONObject(l).has("dummy_is_required") ? questionsarray.getJSONObject(l).getString("dummy_is_required").toString() : questionsarray.getJSONObject(l).getString("is_required").toString());
                                                questionsarray.getJSONObject(l).put("is_required", "0");
                                                questionsarray.getJSONObject(l).put("answer_id", "");
                                                questionsarray.getJSONObject(l).put("answer", "");
                                                questionsarray.getJSONObject(l).put("is_other", "");
                                                questionsarray.getJSONObject(l).put("visual", "0");

                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);

                                    }
                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }
                                hiddencontrol(String.valueOf(resID),questiontype);
                            }
                        }
                        if (ids != null){
                            for (int o = 0; o < ids.length; o++) {
                                resID = getResources().getIdentifier(ids[o], "id", "com.mapolbs.vizibeebritannia");
                                String questiontype = "";
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                if (questionsarray.getJSONObject(l).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                    questionsarray.getJSONObject(l).put("is_required", "1");
                                                else
                                                    questionsarray.getJSONObject(l).put("is_required", "0");
                                                questionsarray.getJSONObject(l).put("answered", true);
                                                questionsarray.getJSONObject(l).put("visual", "1");
                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);

                                    }
                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }
                                showcontrol(String.valueOf(resID),questiontype);

                            }
                        }

                        try {

                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(btnno.getTag().toString()))) {
                                        questionsarray.getJSONObject(l).put("answer_id", optionarr[0]);
                                        questionsarray.getJSONObject(l).put("answer", true);
                                        questionsarray.getJSONObject(l).put("is_other", "");
                                        questionsarray.getJSONObject(l).put("form_id", "");
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);

                            }
                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }

                        try {
                            TableRow vparent = (TableRow) btnyes.getParent().getParent().getParent();
                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                            for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(btnno.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int p=0;p<formrule_array.length();p++)
                                {
                                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }

                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(btnno.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Is")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                            if(conditionvalueobj.has("form_id"))
                                                            {
                                                                if(!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase("")||!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase(null))
                                                                {
                                                                    questionsarray.getJSONObject(l).put("form_id", conditionvalueobj.getString("form_id").toString());
                                                                }else
                                                                {
                                                                    MyApplication.getInstance().setNextformid("");

                                                                }
                                                            }else
                                                            {

                                                                MyApplication.getInstance().setNextformid("");

                                                            }
                                                        }else {
                                                            questionsarray.getJSONObject(l).put("isrule", false);

                                                        }



                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNot")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (!questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                            if(conditionvalueobj.has("form_id"))
                                                            {
                                                                if(!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase("")||!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase(null))
                                                                {

                                                                    MyApplication.getInstance().setNextformid(nextformid);
                                                                    questionsarray.getJSONObject(l).put("form_id", conditionvalueobj.getString("form_id").toString());
                                                                }else
                                                                {
                                                                    nextformid = "";
                                                                    MyApplication.getInstance().setNextformid("");

                                                                }
                                                            }else
                                                            {
                                                                nextformid = "";
                                                                MyApplication.getInstance().setNextformid("");

                                                            }
                                                        }else {
                                                            questionsarray.getJSONObject(l).put("isrule", false);

                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }
                                }
                                formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }
                        }catch(Exception ex)
                        {
                            Log.e("Json Exception",ex.getMessage().toString());
                        }
                    }
                });

                btnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btnno.requestFocus();
                        btnyes.setBackgroundResource(R.drawable.yes_normal_selector);
                        btnno.setBackgroundResource(R.drawable.no_select_selector);

                        String allchild="";
                        String[] yesnochildid = btnyes.getTag().toString().split("&");
                        if(yesnochildid.length ==2)
                        {
                            allchild = btnyes.getTag().toString().replace("&", ",");
                        }else {
                            allchild = btnyes.getTag().toString().replace("&", "");
                        }

                        String[] allchildarray = allchild.split(",");

                        String[] yeschildidarray = null;
                        String[] nochildidarray = null;
                        if(yesnochildid.length > 0) {
                            if(yesnochildid.length > 0 && !yesnochildid[0].equalsIgnoreCase(""))
                                yeschildidarray = yesnochildid[0].split(",");
                            if(yesnochildid.length > 1 && !yesnochildid[1].equalsIgnoreCase(""))
                                nochildidarray = yesnochildid[1].split(",");
                        }

                        String[] childid = null;
                        String[] ids = null;
                        childid =  nochildidarray;
                        if(childid != null)
                            ids = childid.length>0 ? childid : null;
                        String[] dependids;
                        String strdependids = "";
                        for (int i = 0; i < allchildarray.length; i++) {
                            if (!allchildarray[i].equalsIgnoreCase(""))
                                strdependids += allchildarray[i] + ",";
                        }
                        strdependids = strdependids.substring(0, strdependids.length() > 0 ? strdependids.length() - 1 : 0);
                        dependids = strdependids.equalsIgnoreCase("") ? null : strdependids.split(",");
                        if(dependids!= null){
                            for (int s = 0; s < dependids.length; s++) {
                                resID = getResources().getIdentifier(dependids[s], "id", "com.mapolbs.vizibeebritannia");
                                String questiontype = "";
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                questionsarray.getJSONObject(l).put("dummy_is_required", questionsarray.getJSONObject(l).has("dummy_is_required") ? questionsarray.getJSONObject(l).getString("dummy_is_required").toString() : questionsarray.getJSONObject(l).getString("is_required").toString());
                                                questionsarray.getJSONObject(l).put("is_required", "0");
                                                questionsarray.getJSONObject(l).put("answer_id", "");
                                                questionsarray.getJSONObject(l).put("answer", "");
                                                questionsarray.getJSONObject(l).put("is_other", "");
                                                questionsarray.getJSONObject(l).put("visual", "0");
                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);

                                    }
                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }
                                hiddencontrol(String.valueOf(resID),questiontype);

                            }
                        }
                        if (ids != null){
                            for (int o = 0; o < ids.length; o++) {
                                resID = getResources().getIdentifier(ids[o], "id", "com.mapolbs.vizibeebritannia");
                                String questiontype = "";
                                try {
                                    for (int k = 0; k < formarray.length(); k++) {
                                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                        for (int l = 0; l < questionsarray.length(); l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(resID))) {
                                                questiontype = questionsarray.getJSONObject(l).getString("question_type").toString();
                                                if (questionsarray.getJSONObject(l).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                    questionsarray.getJSONObject(l).put("is_required", "1");
                                                else
                                                    questionsarray.getJSONObject(l).put("is_required", "0");
                                                questionsarray.getJSONObject(l).put("answered", true);
                                                questionsarray.getJSONObject(l).put("visual", "1");
                                            }
                                        }
                                        formarray.getJSONObject(k).put("questions", questionsarray);

                                    }
                                } catch (Exception ex) {
                                    Log.e("Json exception", ex.getMessage().toString());
                                }
                                showcontrol(String.valueOf(resID),questiontype);

                            }
                        }

                        try {

                            for (int k = 0; k < formarray.length(); k++) {
                                JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                for (int l = 0; l < questionsarray.length(); l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(String.valueOf(btnno.getTag()))) {
                                        questionsarray.getJSONObject(l).put("answer_id", optionarr[1]);
                                        questionsarray.getJSONObject(l).put("answer", false);
                                        questionsarray.getJSONObject(l).put("is_other", "");
                                        questionsarray.getJSONObject(l).put("form_id", "");
                                    }
                                }
                                formarray.getJSONObject(k).put("questions", questionsarray);

                            }
                        } catch (Exception ex) {
                            Log.e("Json Exception", ex.getMessage().toString());
                        }

                        try {
                            TableRow vparent = (TableRow) btnno.getParent().getParent().getParent();
                            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                            String itemid = txtrowtype.getTag().toString();
                            //Page Rule
                            JSONArray questionsarray = null;
                            String questionid = "";
                            int questionposition = 0;
                            for (int k = 0; k < formarray.length(); k++) {
                                if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                                    questionposition = k;
                                }
                            }
                            for(int l=0;l<questionsarray.length();l++)
                            {
                                if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(btnno.getTag().toString()))
                                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
                            }

                            if(formrule_array.length()>0)
                            {
                                for(int p=0;p<formrule_array.length();p++)
                                {
                                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                                    int conditioncount=0;
                                    ArrayList<String> values=new ArrayList<String>();
                                    for(int j=0;j<condition_array.length();j++)
                                    {
                                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                        for(int m=0;m<hide_array.length();m++) {
                                            for(int n=0;n<questionsarray.length();n++)
                                            {
                                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                                {
                                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                        }

                                        for(int l=0;l<questionsarray.length();l++) {
                                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(btnno.getTag().toString()))
                                            {
                                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Is")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                            if(conditionvalueobj.has("form_id"))
                                                            {
                                                                if(!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase("")||!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase(null))
                                                                {
                                                                    MyApplication.getInstance().setNextformid(nextformid);
                                                                    questionsarray.getJSONObject(l).put("form_id", conditionvalueobj.getString("form_id").toString());
                                                                }else
                                                                {
                                                                    nextformid = "";
                                                                    MyApplication.getInstance().setNextformid("");

                                                                }
                                                            }else
                                                            {
                                                                nextformid = "";
                                                                MyApplication.getInstance().setNextformid("");

                                                            }
                                                        }else {
                                                            questionsarray.getJSONObject(l).put("isrule", false);


                                                        }



                                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNot")) {
                                                        JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                        if (!questionsarray.getJSONObject(l).getString("answer_id").toString().trim().equalsIgnoreCase(conditionvalueobj.getString("condition").toString())) {
                                                            questionsarray.getJSONObject(l).put("isrule", true);
                                                            conditioncount++;
                                                            if(conditionvalueobj.has("form_id"))
                                                            {
                                                                if(!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase("")||!conditionvalueobj.getString("form_id").toString().equalsIgnoreCase(null))
                                                                {

                                                                    MyApplication.getInstance().setNextformid(nextformid);
                                                                    questionsarray.getJSONObject(l).put("form_id", conditionvalueobj.getString("form_id").toString());
                                                                }else
                                                                {
                                                                    nextformid = "";
                                                                    MyApplication.getInstance().setNextformid("");

                                                                }
                                                            }else
                                                            {
                                                                nextformid = "";
                                                                MyApplication.getInstance().setNextformid("");

                                                            }
                                                        }else {
                                                            questionsarray.getJSONObject(l).put("isrule", false);

                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(method.equalsIgnoreCase("1")){
                                        if(conditioncount >0)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                    questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                    questionsarray.getJSONObject(n).put("answer_id", "");
                                                                    questionsarray.getJSONObject(n).put("answer", "");
                                                                    questionsarray.getJSONObject(n).put("is_other", "");
                                                                    questionsarray.getJSONObject(n).put("visual", "0");
                                                                    hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }
                                    }else
                                    {
                                        HashSet<String> hashSet = new HashSet<String>();
                                        hashSet.addAll(values);
                                        values.clear();
                                        values.addAll(hashSet);
                                        int scuusscondition=0;
                                        for(int q =0;q<questionsarray.length();q++)
                                        {
                                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                            {
                                                scuusscondition++;
                                            }
                                        }
                                        if(values.size()==scuusscondition)
                                        {
                                            for(int m=0;m<show_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                            for(int m=0;m<hide_array.length();m++) {
                                                String issubquestion = "";
                                                String subqueids="";
                                                for(int n=0;n<questionsarray.length();n++)
                                                {

                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                                }
                                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                if(issubquestion.equalsIgnoreCase("1"))
                                                {
                                                    String[] subquearray = subqueids.split(",");
                                                    for(int k=0;k<subquearray.length;k++)
                                                    {
                                                        for(int n=0;n<questionsarray.length();n++) {
                                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                ///formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                            }
                                        }else
                                        {
                                            for(int m=0;m<hide_array.length();m++) {
                                                for(int n=0;n<questionsarray.length();n++)
                                                {
                                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                                    {
                                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                                else
                                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                                questionsarray.getJSONObject(n).put("answer", "");
                                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                            }
                                        }
                                    }
                                }
                                formarray.getJSONObject(questionposition).put("questions", questionsarray);

                            }
                        }catch(Exception ex)
                        {
                            Log.e("Json Exception",ex.getMessage().toString());
                        }
                    }
                });
                gridformcontrolheight = height;
                li.addView(view);
                row.addView(li);
            }
            if(gridfilter == 1) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(1, 1);
                LayoutInflater filterheadinflater = SurveyForm.this.getLayoutInflater();
                View view = filterheadinflater.inflate(R.layout.filterid, null, false);
                view.setLayoutParams(params);
                final TextView txtfilter1 = (TextView) view.findViewById(R.id.txtfilter1);
                final TextView txtfilter2 = (TextView) view.findViewById(R.id.txtfilter2);
                    if (!filter1.equalsIgnoreCase(",")) {
                        String[] filer1array = filter1.split(",");
                        txtfilter1.setTag(filer1array[0]);
                        txtfilter1.setText(filer1array[1]);
                    }
                    if (!filter1.equalsIgnoreCase(",")) {
                        String[] filer2array = filter2.split(",");
                        txtfilter2.setTag(filer2array[0]);
                        txtfilter2.setText(filer2array[1]);
                    }

                row.addView(view);
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(1, 1);
            LayoutInflater filterheadinflater = SurveyForm.this.getLayoutInflater();
            View views = filterheadinflater.inflate(R.layout.rowtype, null, false);
            views.setLayoutParams(params);
            final TextView txtrowtype = (TextView) views.findViewById(R.id.txttablerowtype);
            txtrowtype.setText(String.valueOf(rowtype));
            txtrowtype.setTag(String.valueOf(item_id));
            row.addView(views);
            tl.addView(row);
            if (type.equalsIgnoreCase("qmdmremarks")||type.equalsIgnoreCase("qmrbremarks"))
            {
                row.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage().toString());
        }
    }

    static boolean setListViewHeightBasedOnItems(TableRow row, ListCustomAdapter adapter) {
        if (adapter != null) {
            int numberOfItems = adapter.getCount();
            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = adapter.getView(itemPos, null, row);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }
            // Get total height of all item dividers.
            int totalDividersHeight = row.getMeasuredHeight() * (numberOfItems - 1);
            // Set list height.
            TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, totalItemsHeight + totalDividersHeight);

            row.setLayoutParams(params);
            row.requestLayout();
            return true;
        } else {
            return false;
        }
    }

    static boolean setMultiListViewHeightBasedOnItems(TableRow row, MultiCheckListCustomAdapter adapter) {
        if (adapter != null) {
            int numberOfItems = adapter.getCount();
            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = adapter.getView(itemPos, null, row);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }
            // Get total height of all item dividers.
            int totalDividersHeight = row.getMeasuredHeight() * (numberOfItems - 1);
            // Set list height.
            TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, totalItemsHeight + totalDividersHeight);

            row.setLayoutParams(params);
            row.requestLayout();
            return true;
        } else {
            return false;
        }
    }

    private Dialog showDialog(final Activity act, CharSequence title,
                              CharSequence message, CharSequence Yes, CharSequence No) {
        // TODO Auto-generated method stub
        AlertDialog.Builder download = new AlertDialog.Builder(act);
        download.setTitle(title);
        download.setMessage(message);
        download.setPositiveButton(Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent in = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(in);
                } catch (ActivityNotFoundException anfe) {
                }
            }
        });
        download.setNegativeButton(No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
            }
        });
        return download.show();
    }

    @Override
    public void onActivityResult(int req, int res, Intent i) {
        super.onActivityResult(req, res, i);

        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
        devicedate1 = sdf1.format(c1.getTime());

        if (res == RESULT_OK) {
            File destination = null;
            String imagename = "";
            String imagepath = "";
            camerabar = MyApplication.getInstance().getCamerabar();

            if (camerabar == 1) {
                try {
                    String imagetype = MyApplication.getInstance().getImagetype();
                    imgsource = MyApplication.getInstance().getImgsource();
                    imgsource.setVisibility(View.VISIBLE);
                    imgsource.setBackground(null);

                    if (imagetype.equalsIgnoreCase("1")) {

                        Bitmap bitmap = imagePathUtil.getImageBitmap(fileUri.getPath());
                        imagepath = fileUri.getPath().replace("/file:","");
                        File tempFile = new File("",imagepath);
                        imagename = tempFile.getName();

                        imagename = captureimgfile.getName();
                        imagepath = captureimgfile.getAbsolutePath().replace("/file:","");
                        //boolean success = deleteLastFromDCIM();
                        imgsource.setImageBitmap(bitmap);


                    } else {
                        if (i != null) {
                            try {
                            final Uri imageUri = i.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            imgsource.setImageBitmap(selectedImage);

                                 destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root"+File.separator+"CaptureImages");

                                captureimgfile = new File(destination, "/IMG_"+devicedate1+"_"+quessid+"_"+Userid+".jpg");

                                if(!destination.exists())
                                {
                                    destination.mkdirs();
                                }

                                FileOutputStream out = new FileOutputStream(captureimgfile);
                                selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                out.flush();
                                out.close();

                                imagepath = captureimgfile.getAbsolutePath();
                                imagename = imagepath.substring(imagepath.lastIndexOf("/") + 1);




                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }


                } catch (Exception ex) {
                    Log.e("Json Exception", ex.getMessage().toString());
                }

                try {
                    for (int k = 0; k < formarray.length(); k++) {
                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                        for (int l = 0; l < questionsarray.length(); l++) {
                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(imgsource.getTag().toString())) {
                                questionsarray.getJSONObject(l).put("answer_id", "");
                                questionsarray.getJSONObject(l).put("answer", imagename);
                                questionsarray.getJSONObject(l).put("is_other", "");
                                questionsarray.getJSONObject(l).put("imagename", imagename);
                                questionsarray.getJSONObject(l).put("imagepath", imagepath);
                            }
                        }
                        formarray.getJSONObject(k).put("questions", questionsarray);
                    }
                } catch (Exception ex) {
                    Log.e("Json exception", ex.getMessage().toString());
                }

                try {
                    //Page Rule
                    TableRow vparent = (TableRow) imgsource.getParent();
                    RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                    TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                    String itemid = txtrowtype.getTag().toString();
                    JSONArray questionsarray = null;
                    String questionid = "";
                    int questionposition = 0;
                    for (int k = 0; k < formarray.length(); k++) {
                        if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                            questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                            questionposition = k;
                        }
                    }
                    for(int l=0;l<questionsarray.length();l++)
                    {
                        if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(imgsource.getTag().toString()))
                            questionid = questionsarray.getJSONObject(l).getString("id").toString();
                    }

                    if(formrule_array.length()>0)
                    {
                        for(int p=0;p<formrule_array.length();p++)
                        {
                            String method = formrule_array.getJSONObject(p).getString("method").toString();
                            JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                            JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                            JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                            int conditioncount=0;
                            ArrayList<String> values=new ArrayList<String>();
                            for(int j=0;j<condition_array.length();j++)
                            {
                                values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                for(int m=0;m<hide_array.length();m++) {
                                    for(int n=0;n<questionsarray.length();n++)
                                    {
                                        if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                        {
                                            if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                   // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                }

                                for(int l=0;l<questionsarray.length();l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(imgsource.getTag().toString()))
                                    {
                                        if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                            if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                    conditioncount++;
                                                }else
                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                    conditioncount++;
                                                }else
                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                            }
                                        }
                                    }
                                }
                            }

                            if(method.equalsIgnoreCase("1")){
                                if(conditioncount >0)
                                {
                                    for(int m=0;m<show_array.length();m++) {
                                        for(int n=0;n<questionsarray.length();n++)
                                        {
                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                      //  formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }
                                    for(int m=0;m<hide_array.length();m++) {
                                        String issubquestion = "";
                                        String subqueids="";
                                        for(int n=0;n<questionsarray.length();n++)
                                        {

                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                            {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                        }
                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                        if(issubquestion.equalsIgnoreCase("1"))
                                        {
                                            String[] subquearray = subqueids.split(",");
                                            for(int k=0;k<subquearray.length;k++)
                                            {
                                                for(int n=0;n<questionsarray.length();n++) {
                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                      //  formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                    }
                                }
                            }else
                            {
                                HashSet<String> hashSet = new HashSet<String>();
                                hashSet.addAll(values);
                                values.clear();
                                values.addAll(hashSet);
                                int scuusscondition=0;
                                for(int q =0;q<questionsarray.length();q++)
                                {
                                    if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                    {
                                        scuusscondition++;
                                    }
                                }
                                if(values.size()==scuusscondition)
                                {
                                    for(int m=0;m<show_array.length();m++) {
                                        for(int n=0;n<questionsarray.length();n++)
                                        {
                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                      //  formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }
                                    for(int m=0;m<hide_array.length();m++) {
                                        String issubquestion = "";
                                        String subqueids="";
                                        for(int n=0;n<questionsarray.length();n++)
                                        {

                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                            {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                        }
                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                        if(issubquestion.equalsIgnoreCase("1"))
                                        {
                                            String[] subquearray = subqueids.split(",");
                                            for(int k=0;k<subquearray.length;k++)
                                            {
                                                for(int n=0;n<questionsarray.length();n++) {
                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                      //  formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                    }
                                }else
                                {
                                    for(int m=0;m<hide_array.length();m++) {
                                        for(int n=0;n<questionsarray.length();n++)
                                        {
                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                            {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                       // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }
                                }
                            }
                        }
                        formarray.getJSONObject(questionposition).put("questions", questionsarray);
                    }
                }catch(Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }

                //System.out.println("byte array:"+image);
                Log.e("log_tag", "byte array:" + "");
            } else if (camerabar == 2) {
                String result="";
                IntentResult results = IntentIntegrator.parseActivityResult(req, res, i);
                if(results != null) {
                    if(results.getContents() == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("MainActivity", "Scanned");
                        result = results.getContents();


                    }
                }
                //String result = i.getStringExtra("SCAN_RESULT");
                MyApplication.getInstance().getTxtsource().setText(result);
                try {
                    for (int k = 0; k < formarray.length(); k++) {
                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                        for (int l = 0; l < questionsarray.length(); l++) {
                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getTxtsource().getTag().toString())) {
                                questionsarray.getJSONObject(l).put("answer_id", result);
                                questionsarray.getJSONObject(l).put("answer", result);
                                questionsarray.getJSONObject(l).put("is_other", "");
                            }
                        }
                        formarray.getJSONObject(k).put("questions", questionsarray);
                    }
                } catch (Exception ex) {
                    Log.e("Json exception", ex.getMessage().toString());
                }

                try {
                    //Page Rule
                    TableRow vparent = (TableRow) MyApplication.getInstance().getTxtsource().getParent().getParent();
                    RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                    TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                    String itemid = txtrowtype.getTag().toString();
                    JSONArray questionsarray = null;
                    String questionid = "";
                    int questionposition = 0;
                    for (int k = 0; k < formarray.length(); k++) {
                        if(formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid))
                            questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                        questionposition = k;
                    }
                    for(int l=0;l<questionsarray.length();l++)
                    {
                        if(questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getTxtsource().getTag().toString()))
                            questionid = questionsarray.getJSONObject(l).getString("id").toString();
                    }

                    if(formrule_array.length()>0)
                    {
                        for(int p=0;p<formrule_array.length();p++)
                        {
                            String method = formrule_array.getJSONObject(p).getString("method").toString();
                            JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                            JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                            JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                            int conditioncount=0;
                            ArrayList<String> values=new ArrayList<String>();
                            for(int j=0;j<condition_array.length();j++)
                            {
                                values.add(condition_array.getJSONObject(j).getString("question_id").toString());
                                for(int m=0;m<hide_array.length();m++) {
                                    for(int n=0;n<questionsarray.length();n++)
                                    {
                                        if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                        {
                                            if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                   // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                }

                                for(int l=0;l<questionsarray.length();l++) {
                                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getTxtsource().getTag().toString()))
                                    {
                                        if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                            if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                                if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                    conditioncount++;
                                                }else
                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                                if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                    conditioncount++;
                                                }else
                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Contains")) {
                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                if (questionsarray.getJSONObject(l).getString("answer").toString().trim().contains(conditionvalueobj.getString("condition").toString())) {
                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                    conditioncount++;
                                                }else
                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                            } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("Not Contains")) {
                                                JSONObject conditionvalueobj = new JSONObject(condition_array.getJSONObject(j).getString("condition_value").toString());
                                                if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().contains(conditionvalueobj.getString("condition").toString())) {
                                                    questionsarray.getJSONObject(l).put("isrule", true);
                                                    conditioncount++;
                                                }else
                                                    questionsarray.getJSONObject(l).put("isrule", false);
                                            }
                                        }
                                    }
                                }
                            }

                            if(method.equalsIgnoreCase("1")){
                                if(conditioncount >0)
                                {
                                    for(int m=0;m<show_array.length();m++) {
                                        for(int n=0;n<questionsarray.length();n++)
                                        {
                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                       // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }
                                    for(int m=0;m<hide_array.length();m++) {
                                        String issubquestion = "";
                                        String subqueids="";
                                        for(int n=0;n<questionsarray.length();n++)
                                        {

                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                            {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                        }
                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                        if(issubquestion.equalsIgnoreCase("1"))
                                        {
                                            String[] subquearray = subqueids.split(",");
                                            for(int k=0;k<subquearray.length;k++)
                                            {
                                                for(int n=0;n<questionsarray.length();n++) {
                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                     //   formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                    }
                                }
                            }else
                            {
                                HashSet<String> hashSet = new HashSet<String>();
                                hashSet.addAll(values);
                                values.clear();
                                values.addAll(hashSet);
                                int scuusscondition=0;
                                for(int q =0;q<questionsarray.length();q++)
                                {
                                    if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                                    {
                                        scuusscondition++;
                                    }
                                }
                                if(values.size()==scuusscondition)
                                {
                                    for(int m=0;m<show_array.length();m++) {
                                        for(int n=0;n<questionsarray.length();n++)
                                        {
                                            if(show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                       // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }
                                    for(int m=0;m<hide_array.length();m++) {
                                        String issubquestion = "";
                                        String subqueids="";
                                        for(int n=0;n<questionsarray.length();n++)
                                        {

                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                            {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                            questionsarray.getJSONObject(n).put("validationtype", "");
                                                            questionsarray.getJSONObject(n).put("validationvalue", "");
                                                        }
                                                        issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                        subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                        if(issubquestion.equalsIgnoreCase("1"))
                                        {
                                            String[] subquearray = subqueids.split(",");
                                            for(int k=0;k<subquearray.length;k++)
                                            {
                                                for(int n=0;n<questionsarray.length();n++) {
                                                    if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                        questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                        questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("is_other", "");
                                                        questionsarray.getJSONObject(n).put("visual", "0");
                                                        hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                     //   formarray.getJSONObject(questionposition).put("questions", questionsarray);

                                    }
                                }else
                                {
                                    for(int m=0;m<hide_array.length();m++) {
                                        for(int n=0;n<questionsarray.length();n++)
                                        {
                                            if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                            {
                                                if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                    if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                        if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                            questionsarray.getJSONObject(n).put("is_required", "1");
                                                        else
                                                            questionsarray.getJSONObject(n).put("is_required", "0");
                                                        questionsarray.getJSONObject(n).put("answer_id", "");
                                                        questionsarray.getJSONObject(n).put("answer", "");
                                                        questionsarray.getJSONObject(n).put("answered", "yes");
                                                        questionsarray.getJSONObject(n).put("visual", "1");
                                                        showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                                    }
                                                }
                                            }
                                        }
                                      //  formarray.getJSONObject(questionposition).put("questions", questionsarray);
                                    }
                                }
                            }
                        }
                        formarray.getJSONObject(questionposition).put("questions", questionsarray);
                    }
                }catch(Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathsFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(SurveyForm.this, "Please Click Previous Button to Proceed.", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case CameraUtility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                String imagetype = MyApplication.getInstance().getImagetype();
                if (imagetype.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, ids);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), ids);
                }
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
// can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    @Override
    public void onStart() {
        super.onStart();

    }



    @Override
    public void onStop() {
        super.onStop();


    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int mappermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }


    }

    public static void verifylocationPermissions(Activity activity) {
        // Check if we have write permission
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 105);
        }


    }

    private class downloadimage extends AsyncTask<String , Void,Bitmap>
    {
        ImageView imgs;
        String error;

        public downloadimage(ImageView img)
        {
            this.imgs = img;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String disurl = url[0];
            Bitmap btma = null;

            try
            {
                InputStream in = new java.net.URL(disurl).openStream();
                btma = BitmapFactory.decodeStream(in);
            }
            catch(Exception ex)
            {
                error=ex.getMessage().toString();

            }


            return btma;
        }

        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                imgs.setBackground(null);
                imgs.setImageBitmap(result);
            }
            else
                Log.e("Image Error",error);
        }

    }

    private class PostDataTask extends AsyncTask<String, Integer, String> {
        int statusCode=0;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SurveyForm.this,R.style.MyAlertDialogStyle);
            pDialog.setMessage("Uploading Survey Data Please Wait...");
            pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.vizilogo));
            pDialog.setIndeterminate(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
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

            String jsonStr = uploadFile(params[0]);

            if (jsonStr != null) {
                // try {
                Log.e("PostDataTask Status: ", jsonStr);

            } else {
                Log.e("PostDataTask Status: ", "Not Yet Received");
            }

            return jsonStr;
        }

        @SuppressWarnings("rawtypes")
        private String uploadFile(String jsonString) {
            String charset = "UTF-8";
            String responseString = null;
            try {
                MultipartUtility client = new MultipartUtility(ipaddress + "post_data", charset);


                //publishProgress((int) ((num / (float) totalSize) * 100));
               /* AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });*/

                imagefiles = new ArrayList<String>();
                try {
                    for (int k = 0; k < formsarray.length(); k++) {
                        JSONArray questionsarray = new JSONArray(formsarray.getJSONObject(k).getString("questions"));
                        for (int l = 0; l < questionsarray.length(); l++) {



                            if (questionsarray.getJSONObject(l).getString("question_type").toString().equalsIgnoreCase("qmpi")||questionsarray.getJSONObject(l).getString("question_type").toString().equalsIgnoreCase("qmar")) {
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
                                                    InputStream in = new ByteArrayInputStream(bos.toByteArray());

                                                    client.addFilePart(fname, file);
                                                    imagefiles.add(questionsarray.getJSONObject(l).getString("imagepath").toString());
                                                }else{
                                                    client.addFilePart( fname, file);
                                                    imagefiles.add(questionsarray.getJSONObject(l).getString("imagepath").toString());
                                                }

                                            } else {
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

                String aaa = MyApplication.getInstance().getLogeduserjsonstring();
                // Extra parameters if you want to pass to server

            client.addFormField("forms", jsonString);
            client.addFormField("loggedUser",MyApplication.getInstance().getLogeduserjsonstring());
            client.addFormField("surveyDate",devicedate);
                // entity.addPart("email", new StringBody("abc@gmail.com"));
            client.addFormField("settings",settings_tag.toString());

                List<String> response = client.finish();
                statusCode = Integer.parseInt(response.get(0).toString());
                responseString = response.get(1).toString();


            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            pDialog.isShowing();
            pDialog.dismiss();
            SurveyForm.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try{
                        //JSONObject jobj = new JSONObject(result);

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
                            if(retlist.size() == 0) {
                                long xx = dbAdapter.insertretailer(retailerid, MyApplication.getInstance().getProjectid());
                            }
                            quit();
                        }else if(statusCode == 400)
                        {
                            try {
                                pDialog.setProgress(0);
                                JSONObject jObject = new JSONObject(result);
                                Toast.makeText(SurveyForm.this, jObject.getString("status").toString(), Toast.LENGTH_LONG).show();
                            } catch (Exception ex) {
                                Log.e("Login Log", ex.getMessage().toString());
                            }
                        }
                        else {
                            pDialog.setProgress(0);
                            Toast.makeText(SurveyForm.this, "Failed to upload "+result+" Status code="+statusCode,
                                    Toast.LENGTH_LONG).show();


                            JSONObject jobj = new JSONObject();
                            jobj.put("data", formsarray.toString());
                            jobj.put("loggedUser", MyApplication.getInstance().getLogeduserjsonstring());
                            jobj.put("surveydate", devicedate);
                            jobj.put("settings", settings_tag);


                            long x = dbAdapter.insertdata(jobj.toString());

                            if (x > 0) {
                                Toast.makeText(SurveyForm.this, "Data Inserted Successfully in Offline.", Toast.LENGTH_LONG).show();
                                if(retlist.size() == 0) {
                                    long xx = dbAdapter.insertretailer(retailerid, MyApplication.getInstance().getProjectid());
                                }
                                quit();
                            } else {
                                Toast.makeText(SurveyForm.this, "Offline data Failed to Insert.", Toast.LENGTH_LONG).show();
                            }


                        }
                    }catch(Exception ex){
                        Log.e("Json Exception",ex.getMessage().toString());
                    }
                }
            });

        }
    }



    public void quit() {
        Intent intent = new Intent(SurveyForm.this, AuditComplete.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {


        }
    }

    private boolean deleteLastFromDCIM() {

        boolean success = false;
        try {
            String CameraFolder="Camera";
            File CameraDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
            File[] files = CameraDirectory.listFiles();
            for (File CurFile : files) {
                if (CurFile.isDirectory()) {
                    CameraDirectory= CurFile;
                    break;
                }
            }
            final String CompleteCameraFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + CameraFolder;
            File[] images = new File(CompleteCameraFolder).listFiles();
            File latestSavedImage = images[0];
            for (int i = 1; i < images.length; ++i) {
                if (images[i].lastModified() > latestSavedImage.lastModified()) {
                    latestSavedImage = images[i];
                }
            }

            // OR JUST Use  success = latestSavedImage.delete();
            success = latestSavedImage.delete();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return success;
        }

    }



    public void hiddencontrol(String uniqueids,String questiontype)
    {

        try {

            if (questiontype.equalsIgnoreCase("qmds")) {

                Spinner spinner = (Spinner) findViewById(Integer.parseInt(uniqueids));
                spinner.setSelection(0);
                TableRow vparent = (TableRow) spinner.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);

                int emptysid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptyrow = (TableRow) findViewById(emptysid);
                emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                int remarksid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow remarksrow = (TableRow) findViewById(remarksid);
                EditText edttext = (EditText) remarksrow.getChildAt(0);
                edttext.setText("");
                emptyrow.setVisibility(View.GONE);
                remarksrow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmir")) {

                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) imgview.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmbc")) {

                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));
                LinearLayout li = (LinearLayout)imgview.getParent();
                TextView txtbarcode = (TextView)li.getChildAt(1);
                txtbarcode.setText("");
                TableRow vparent = (TableRow) imgview.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmgt")) {

                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));
                LinearLayout li = (LinearLayout)imgview.getParent();
                TextView txtlocation = (TextView)li.getChildAt(1);
                txtlocation.setText("");
                TableRow vparent = (TableRow) imgview.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmpi")) {
                //TableRow imgview = (TableRow) findViewById(Integer.parseInt(uniqueid));
                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));
                imgview.setImageBitmap(null);
                TableRow vparent = (TableRow) imgview.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmar")) {

                Button btn = (Button) findViewById(Integer.parseInt(uniqueids));
                final LinearLayout view = (LinearLayout)btn.getParent();
                final Button btnstart = (Button)view.getChildAt(0);
                final Button btnstop = (Button)view.getChildAt(1);
                btnstart.setBackgroundDrawable(getResources().getDrawable(R.drawable.startrecord));
                btnstop.setBackgroundDrawable(getResources().getDrawable(R.drawable.stoprecord));
                TableRow vparent = (TableRow) btn.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                };
            } else if (questiontype.equalsIgnoreCase("qmyn")) {
                View toggle = (View) findViewById(Integer.parseInt(uniqueids));
                final Button btnyes = (Button)toggle.findViewById(R.id.btn_yes);
                final Button btnno = (Button)toggle.findViewById(R.id.btn_no);
                btnyes.setBackground(getResources().getDrawable(R.drawable.yes_normal_selector));
                btnno.setBackground(getResources().getDrawable(R.drawable.no_normal_selector));
                TableRow vparent = (TableRow) toggle.getParent().getParent();
                int ids = vparent.getId();
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmte")) {
                EditText edttxt = (EditText) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);


            } else if (questiontype.equalsIgnoreCase("qmgc")) {
                ListView lstv = (ListView) findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) lstv.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmgm")) {
                ListView lstv = (ListView) findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) lstv.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmtn")) {
                EditText edttxt = (EditText) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmtr")) {
                TextView edttxt = (TextView) findViewById(Integer.parseInt(uniqueids));
                TableRow vparent = (TableRow) edttxt.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmti")) {
                TextView edttxt = (TextView) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmls")) {
                SeekBar edttxt = (SeekBar) findViewById(Integer.parseInt(uniqueids));
                edttxt.setProgress(0);
                LinearLayout seekli = (LinearLayout) edttxt.getParent();
                TableRow vparent = (TableRow) seekli.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmda")) {
                TextView edttxt = (TextView) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmdm")) {
                TextView txt = (TextView) findViewById(Integer.parseInt(uniqueids));

                txt.setText(R.string.select_string);
                TableRow vparent = (TableRow) txt.getParent();
                LinearLayout rel = (LinearLayout) vparent.getChildAt(1);
                TextView txtcount = (TextView) rel.getChildAt(3);
                //txtcount.setText("0");
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);

                int emptysid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptyrow = (TableRow) findViewById(emptysid);
                emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                int remarksid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow remarksrow = (TableRow) findViewById(remarksid);
                EditText edttext = (EditText) remarksrow.getChildAt(0);
                edttext.setText("");
                emptyrow.setVisibility(View.GONE);
                remarksrow.setVisibility(View.GONE);

            } else if (questiontype.equalsIgnoreCase("qmrb")) {
                RadioGroup radiogroup = (RadioGroup) findViewById(Integer.parseInt(uniqueids));
                radiogroup.clearCheck();
                TableRow vparent = (TableRow) radiogroup.getParent();
                int id = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(id - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(id - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);

                int emptysid = getResources().getIdentifier(String.valueOf(id + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptyrow = (TableRow) findViewById(emptysid);
                emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                int remarksid = getResources().getIdentifier(String.valueOf(id + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow remarksrow = (TableRow) findViewById(remarksid);
                EditText edttext = (EditText) remarksrow.getChildAt(0);
                edttext.setText("");
                emptyrow.setVisibility(View.GONE);
                remarksrow.setVisibility(View.GONE);
            }
        }catch (Exception ex)
        {
            Log.e("Hidden Exception",ex.getMessage().toString());
        }
    }

    public void showcontrol(String uniqueids,String questiontype)
    {
        try {
            View view = MyApplication.getInstance().getView();
            if (questiontype.equalsIgnoreCase("qmds")) {

                Spinner spinner = (Spinner) findViewById(Integer.parseInt(uniqueids));
                spinner.setSelection(0);
                TableRow vparent = (TableRow) spinner.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);

            }
            else if (questiontype.equalsIgnoreCase("qmir")) {

                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) imgview.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.VISIBLE);
                    divideidrow.setVisibility(View.VISIBLE);
                }
            }
            else if (questiontype.equalsIgnoreCase("qmbc")) {

                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));
                LinearLayout li = (LinearLayout)imgview.getParent();
                TextView txtbarcode = (TextView)li.getChildAt(1);
                txtbarcode.setText("");
                TableRow vparent = (TableRow) imgview.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.VISIBLE);
                    divideidrow.setVisibility(View.VISIBLE);
                }
            }
            else if (questiontype.equalsIgnoreCase("qmgt")) {

                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));
                LinearLayout li = (LinearLayout)imgview.getParent();
                TextView txtlocation = (TextView)li.getChildAt(1);
                txtlocation.setText("");
                TableRow vparent = (TableRow) imgview.getParent().getParent();
                int ids = vparent.getId();
                int titleid =getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.VISIBLE);
                    divideidrow.setVisibility(View.VISIBLE);
                }
            }
            else if (questiontype.equalsIgnoreCase("qmpi")) {

                ImageView imgview = (ImageView) findViewById(Integer.parseInt(uniqueids));
                imgview.setImageBitmap(null);
                TableRow vparent = (TableRow) imgview.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.VISIBLE);
                    divideidrow.setVisibility(View.VISIBLE);
                }

            }
            else if (questiontype.equalsIgnoreCase("qmar")) {

                Button btn = (Button) findViewById(Integer.parseInt(uniqueids));
                final LinearLayout arview = (LinearLayout)btn.getParent();
                final Button btnstart = (Button)arview.getChildAt(0);
                final Button btnstop = (Button)arview.getChildAt(1);
                btnstart.setBackgroundDrawable(getResources().getDrawable(R.drawable.startrecord));
                btnstop.setBackgroundDrawable(getResources().getDrawable(R.drawable.stoprecord));
                TableRow vparent = (TableRow) btn.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.VISIBLE);
                    divideidrow.setVisibility(View.VISIBLE);
                }
            }
            else if (questiontype.equalsIgnoreCase("qmyn")) {
                View toggle = (View) findViewById(Integer.parseInt(uniqueids));
                final Button btnyes = (Button)toggle.findViewById(R.id.btn_yes);
                final Button btnno = (Button)toggle.findViewById(R.id.btn_no);
                btnyes.setBackground(getResources().getDrawable(R.drawable.yes_normal_selector));
                btnno.setBackground(getResources().getDrawable(R.drawable.no_normal_selector));
                TableRow vparent = (TableRow) toggle.getParent().getParent();
                int ids = vparent.getId();
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);
            }
            else if (questiontype.equalsIgnoreCase("qmte")) {
                EditText edttxt = (EditText) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid =getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
            }
            else if (questiontype.equalsIgnoreCase("qmgc")) {
                ListView lstv = (ListView) findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) lstv.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
            }

            else if (questiontype.equalsIgnoreCase("qmgm")) {
                ListView lstv = (ListView) findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) lstv.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
            }
            else if (questiontype.equalsIgnoreCase("qmtn")) {
                EditText edttxt = (EditText) findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
            }
            else if (questiontype.equalsIgnoreCase("qmtr")) {
                TextView edttxt = (TextView) findViewById(Integer.parseInt(uniqueids));
                TableRow vparent = (TableRow) edttxt.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
            }
            else if (questiontype.equalsIgnoreCase("qmti")) {
                TextView edttxt = (TextView) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
            }
            else if (questiontype.equalsIgnoreCase("qmls")) {
                SeekBar edttxt = (SeekBar) findViewById(Integer.parseInt(uniqueids));
                edttxt.setProgress(0);
                LinearLayout seekli = (LinearLayout) edttxt.getParent();
                TableRow vparent = (TableRow) seekli.getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) findViewById(bottomemptyid);
                    int divideid = getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) findViewById(divideid);
                    bottomemptyrow.setVisibility(View.VISIBLE);
                    divideidrow.setVisibility(View.VISIBLE);
                }
            }
            else if (questiontype.equalsIgnoreCase("qmda")) {
                TextView edttxt = (TextView) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                emptytablerow.setVisibility(View.VISIBLE);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
            }
            else if (questiontype.equalsIgnoreCase("qmdm")) {
                TextView txt = (TextView) findViewById(Integer.parseInt(uniqueids));

                txt.setText(R.string.select_string);
                TableRow vparent = (TableRow) txt.getParent();
                LinearLayout rel = (LinearLayout)vparent.getChildAt(1);
                TextView txtcount = (TextView)rel.getChildAt(3);
                //txtcount.setText("0");
                int ids = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);


            }else if (questiontype.equalsIgnoreCase("qmrb")) {
                RadioGroup radiogroup = (RadioGroup) findViewById(Integer.parseInt(uniqueids));
                radiogroup.clearCheck();
                TableRow vparent = (TableRow) radiogroup.getParent();
                int id = vparent.getId();
                int titleid = getResources().getIdentifier(String.valueOf(id - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) findViewById(titleid);
                int emptyid = getResources().getIdentifier(String.valueOf(id - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) findViewById(emptyid);
                vparent.setVisibility(View.VISIBLE);
                titletablerow.setVisibility(View.VISIBLE);
                emptytablerow.setVisibility(View.VISIBLE);

            }
        }catch (Exception ex)
        {
            Log.e("Hidden Exception",ex.getMessage().toString());
        }
    }

    public void googlemaphiddencontrol(String uniqueids,String questiontype)
    {

        try {
            View view = MyApplication.getInstance().getView();
            if (questiontype.equalsIgnoreCase("qmds")) {

                Spinner spinner = (Spinner) view.findViewById(Integer.parseInt(uniqueids));
                spinner.setSelection(0);
                TableRow vparent = (TableRow) spinner.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);

                int emptysid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptyrow = (TableRow) view.findViewById(emptysid);
                emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                int remarksid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow remarksrow = (TableRow) view.findViewById(remarksid);
                EditText edttext = (EditText) remarksrow.getChildAt(0);
                edttext.setText("");
                emptyrow.setVisibility(View.GONE);
                remarksrow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmir")) {

                ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) imgview.getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                    int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) view.findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmbc")) {

                ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));
                LinearLayout li = (LinearLayout)imgview.getParent();
                TextView txtbarcode = (TextView)li.getChildAt(1);
                txtbarcode.setText("");
                TableRow vparent = (TableRow) imgview.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                    int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) view.findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmgt")) {

                ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));
                LinearLayout li = (LinearLayout)imgview.getParent();
                TextView txtlocation = (TextView)li.getChildAt(1);
                txtlocation.setText("");
                TableRow vparent = (TableRow) imgview.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                    int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) view.findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmpi")) {
                //TableRow imgview = (TableRow) findViewById(Integer.parseInt(uniqueid));
                ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));
                imgview.setImageBitmap(null);
                TableRow vparent = (TableRow) imgview.getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                    int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) view.findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmar")) {

                Button btn = (Button) view.findViewById(Integer.parseInt(uniqueids));
                final LinearLayout arview = (LinearLayout)btn.getParent();
                final Button btnstart = (Button)arview.getChildAt(0);
                final Button btnstop = (Button)arview.getChildAt(1);
                btnstart.setBackgroundDrawable(getResources().getDrawable(R.drawable.startrecord));
                btnstop.setBackgroundDrawable(getResources().getDrawable(R.drawable.stoprecord));
                TableRow vparent = (TableRow) btn.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                    int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) view.findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                };
            } else if (questiontype.equalsIgnoreCase("qmyn")) {
                View toggle = (View) view.findViewById(Integer.parseInt(uniqueids));
                final Button btnyes = (Button)toggle.findViewById(R.id.btn_yes);
                final Button btnno = (Button)toggle.findViewById(R.id.btn_no);
                btnyes.setBackground(getResources().getDrawable(R.drawable.yes_normal_selector));
                btnno.setBackground(getResources().getDrawable(R.drawable.no_normal_selector));
                TableRow vparent = (TableRow) toggle.getParent().getParent();
                int ids = vparent.getId();
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmte")) {
                EditText edttxt = (EditText) findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmgc")) {
                ListView lstv = (ListView) view.findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) lstv.getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmgm")) {
                ListView lstv = (ListView) view.findViewById(Integer.parseInt(uniqueids));

                TableRow vparent = (TableRow) lstv.getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmtn")) {
                EditText edttxt = (EditText) view.findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmtr")) {
                TextView edttxt = (TextView) view.findViewById(Integer.parseInt(uniqueids));
                TableRow vparent = (TableRow) edttxt.getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmti")) {
                TextView edttxt = (TextView) view.findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmls")) {
                SeekBar edttxt = (SeekBar) view.findViewById(Integer.parseInt(uniqueids));
                edttxt.setProgress(0);
                LinearLayout seekli = (LinearLayout) edttxt.getParent();
                TableRow vparent = (TableRow) seekli.getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
                TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
                if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                    int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                    TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                    int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                    TableRow divideidrow = (TableRow) view.findViewById(divideid);
                    bottomemptyrow.setVisibility(View.GONE);
                    divideidrow.setVisibility(View.GONE);
                }
            } else if (questiontype.equalsIgnoreCase("qmda")) {
                TextView edttxt = (TextView) view.findViewById(Integer.parseInt(uniqueids));
                edttxt.setText("");
                TableRow vparent = (TableRow) edttxt.getParent().getParent();
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                emptytablerow.setVisibility(View.GONE);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
            } else if (questiontype.equalsIgnoreCase("qmdm")) {
                TextView txt = (TextView) view.findViewById(Integer.parseInt(uniqueids));

                txt.setText(R.string.select_string);
                TableRow vparent = (TableRow) txt.getParent();
                LinearLayout rel = (LinearLayout) vparent.getChildAt(1);
                TextView txtcount = (TextView) rel.getChildAt(3);
                //txtcount.setText("0");
                int ids = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);

                int emptysid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptyrow = (TableRow) view.findViewById(emptysid);
                emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                int remarksid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow remarksrow = (TableRow) view.findViewById(remarksid);
                EditText edttext = (EditText) remarksrow.getChildAt(0);
                edttext.setText("");
                emptyrow.setVisibility(View.GONE);
                remarksrow.setVisibility(View.GONE);

            } else if (questiontype.equalsIgnoreCase("qmrb")) {
                RadioGroup radiogroup = (RadioGroup) view.findViewById(Integer.parseInt(uniqueids));
                radiogroup.clearCheck();
                TableRow vparent = (TableRow) radiogroup.getParent();
                int id = vparent.getId();
                int titleid = view.getResources().getIdentifier(String.valueOf(id - 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow titletablerow = (TableRow) view.findViewById(titleid);
                int emptyid = view.getResources().getIdentifier(String.valueOf(id - 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
                vparent.setVisibility(View.GONE);
                titletablerow.setVisibility(View.GONE);
                emptytablerow.setVisibility(View.GONE);

                int emptysid = view.getResources().getIdentifier(String.valueOf(id + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow emptyrow = (TableRow) view.findViewById(emptysid);
                emptyrow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                int remarksid = view.getResources().getIdentifier(String.valueOf(id + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow remarksrow = (TableRow) view.findViewById(remarksid);
                EditText edttext = (EditText) remarksrow.getChildAt(0);
                edttext.setText("");
                emptyrow.setVisibility(View.GONE);
                remarksrow.setVisibility(View.GONE);
            }
        }catch (Exception ex)
        {
            Log.e("Hidden Exception",ex.getMessage().toString());
        }
    }

    public void googlemapshowcontrol(String uniqueids,String questiontype)
    {
        try {
        View view = MyApplication.getInstance().getView();
        if (questiontype.equalsIgnoreCase("qmds")) {

            Spinner spinner = (Spinner) view.findViewById(Integer.parseInt(uniqueids));
            spinner.setSelection(0);
            TableRow vparent = (TableRow) spinner.getParent().getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);

        }
        else if (questiontype.equalsIgnoreCase("qmir")) {

            ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));

            TableRow vparent = (TableRow) imgview.getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);
            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
            if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow divideidrow = (TableRow) view.findViewById(divideid);
                bottomemptyrow.setVisibility(View.VISIBLE);
                divideidrow.setVisibility(View.VISIBLE);
            }
        }
        else if (questiontype.equalsIgnoreCase("qmbc")) {

            ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));
            LinearLayout li = (LinearLayout)imgview.getParent();
            TextView txtbarcode = (TextView)li.getChildAt(1);
            txtbarcode.setText("");
            TableRow vparent = (TableRow) imgview.getParent().getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);
            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
            if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow divideidrow = (TableRow) view.findViewById(divideid);
                bottomemptyrow.setVisibility(View.VISIBLE);
                divideidrow.setVisibility(View.VISIBLE);
            }
        }
        else if (questiontype.equalsIgnoreCase("qmgt")) {

            ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));
            LinearLayout li = (LinearLayout)imgview.getParent();
            TextView txtlocation = (TextView)li.getChildAt(1);
            txtlocation.setText("");
            TableRow vparent = (TableRow) imgview.getParent().getParent();
            int ids = vparent.getId();
            int titleid =view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);
            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
            if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow divideidrow = (TableRow) view.findViewById(divideid);
                bottomemptyrow.setVisibility(View.VISIBLE);
                divideidrow.setVisibility(View.VISIBLE);
            }
        }
        else if (questiontype.equalsIgnoreCase("qmpi")) {

            ImageView imgview = (ImageView) view.findViewById(Integer.parseInt(uniqueids));
            imgview.setImageBitmap(null);
            TableRow vparent = (TableRow) imgview.getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);
            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
            if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow divideidrow = (TableRow) view.findViewById(divideid);
                bottomemptyrow.setVisibility(View.VISIBLE);
                divideidrow.setVisibility(View.VISIBLE);
            }

        }
        else if (questiontype.equalsIgnoreCase("qmar")) {

            Button btn = (Button) view.findViewById(Integer.parseInt(uniqueids));
            final LinearLayout arview = (LinearLayout)btn.getParent();
            final Button btnstart = (Button)arview.getChildAt(0);
            final Button btnstop = (Button)arview.getChildAt(1);
            btnstart.setBackgroundDrawable(getResources().getDrawable(R.drawable.startrecord));
            btnstop.setBackgroundDrawable(getResources().getDrawable(R.drawable.stoprecord));
            TableRow vparent = (TableRow) btn.getParent().getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);
            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
            if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow divideidrow = (TableRow) view.findViewById(divideid);
                bottomemptyrow.setVisibility(View.VISIBLE);
                divideidrow.setVisibility(View.VISIBLE);
            }
        }
        else if (questiontype.equalsIgnoreCase("qmyn")) {
            View toggle = (View) view.findViewById(Integer.parseInt(uniqueids));
            final Button btnyes = (Button)toggle.findViewById(R.id.btn_yes);
            final Button btnno = (Button)toggle.findViewById(R.id.btn_no);
            btnyes.setBackground(getResources().getDrawable(R.drawable.yes_normal_selector));
            btnno.setBackground(getResources().getDrawable(R.drawable.no_normal_selector));
            TableRow vparent = (TableRow) toggle.getParent().getParent();
            int ids = vparent.getId();
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);
        }
        else if (questiontype.equalsIgnoreCase("qmte")) {
            EditText edttxt = (EditText) view.findViewById(Integer.parseInt(uniqueids));
            edttxt.setText("");
            TableRow vparent = (TableRow) edttxt.getParent().getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid =view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
        }
        else if (questiontype.equalsIgnoreCase("qmgc")) {
            ListView lstv = (ListView) view.findViewById(Integer.parseInt(uniqueids));

            TableRow vparent = (TableRow) lstv.getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
        }

        else if (questiontype.equalsIgnoreCase("qmgm")) {
            ListView lstv = (ListView) view.findViewById(Integer.parseInt(uniqueids));

            TableRow vparent = (TableRow) lstv.getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
        }
        else if (questiontype.equalsIgnoreCase("qmtn")) {
            EditText edttxt = (EditText) view.findViewById(Integer.parseInt(uniqueids));
            edttxt.setText("");
            TableRow vparent = (TableRow) edttxt.getParent().getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
        }
        else if (questiontype.equalsIgnoreCase("qmtr")) {
            TextView edttxt = (TextView) view.findViewById(Integer.parseInt(uniqueids));
            TableRow vparent = (TableRow) edttxt.getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
        }
        else if (questiontype.equalsIgnoreCase("qmti")) {
            TextView edttxt = (TextView) view.findViewById(Integer.parseInt(uniqueids));
            edttxt.setText("");
            TableRow vparent = (TableRow) edttxt.getParent().getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
        }
        else if (questiontype.equalsIgnoreCase("qmls")) {
            SeekBar edttxt = (SeekBar) view.findViewById(Integer.parseInt(uniqueids));
            edttxt.setProgress(0);
            LinearLayout seekli = (LinearLayout) edttxt.getParent();
            TableRow vparent = (TableRow) seekli.getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            RelativeLayout rellayout = (RelativeLayout)vparent.findViewById(R.id.rowtypelayout);
            TextView txtrowtype = (TextView)rellayout.findViewById(R.id.txttablerowtype);
            if(txtrowtype.getText().toString().equalsIgnoreCase("1")) {
                int bottomemptyid = view.getResources().getIdentifier(String.valueOf(ids + 1), "id", "com.mapolbs.vizibeebritannia");
                TableRow bottomemptyrow = (TableRow) view.findViewById(bottomemptyid);
                int divideid = view.getResources().getIdentifier(String.valueOf(ids + 2), "id", "com.mapolbs.vizibeebritannia");
                TableRow divideidrow = (TableRow) view.findViewById(divideid);
                bottomemptyrow.setVisibility(View.VISIBLE);
                divideidrow.setVisibility(View.VISIBLE);
            }
        }
        else if (questiontype.equalsIgnoreCase("qmda")) {
            TextView edttxt = (TextView) view.findViewById(Integer.parseInt(uniqueids));
            edttxt.setText("");
            TableRow vparent = (TableRow) edttxt.getParent().getParent();
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            emptytablerow.setVisibility(View.VISIBLE);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
        }
        else if (questiontype.equalsIgnoreCase("qmdm")) {
            TextView txt = (TextView) view.findViewById(Integer.parseInt(uniqueids));

            txt.setText(R.string.select_string);
            TableRow vparent = (TableRow) txt.getParent();
            LinearLayout rel = (LinearLayout)vparent.getChildAt(1);
            TextView txtcount = (TextView)rel.getChildAt(3);
            //txtcount.setText("0");
            int ids = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(ids - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(ids - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);


        }else if (questiontype.equalsIgnoreCase("qmrb")) {
            RadioGroup radiogroup = (RadioGroup) view.findViewById(Integer.parseInt(uniqueids));
            radiogroup.clearCheck();
            TableRow vparent = (TableRow) radiogroup.getParent();
            int id = vparent.getId();
            int titleid = view.getResources().getIdentifier(String.valueOf(id - 1), "id", "com.mapolbs.vizibeebritannia");
            TableRow titletablerow = (TableRow) view.findViewById(titleid);
            int emptyid = view.getResources().getIdentifier(String.valueOf(id - 2), "id", "com.mapolbs.vizibeebritannia");
            TableRow emptytablerow = (TableRow) view.findViewById(emptyid);
            vparent.setVisibility(View.VISIBLE);
            titletablerow.setVisibility(View.VISIBLE);
            emptytablerow.setVisibility(View.VISIBLE);

        }
    }catch (Exception ex)
    {
        Log.e("Hidden Exception",ex.getMessage().toString());
    }
    }

    public void mappagerule()
    {
        try {

            TableRow vparent = (TableRow) MyApplication.getInstance().getImgmapsrc().getParent().getParent();
            RelativeLayout rellayout = (RelativeLayout) vparent.findViewById(R.id.rowtypelayout);
            TextView txtrowtype = (TextView) rellayout.findViewById(R.id.txttablerowtype);
            String itemid = txtrowtype.getTag().toString();

            //Page Rule
            JSONArray questionsarray = null;
            String questionid = "";
            int questionposition = 0;
            formarray = MyApplication.getInstance().getFormarray();
            locformid = MyApplication.getInstance().getLocalformid();
            formrule_array = MyApplication.getInstance().getFormrulearray();
            for (int k = 0; k < formarray.length(); k++) {
                if (formarray.getJSONObject(k).getString("form_id").toString().equalsIgnoreCase(locformid)) {
                    questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                    questionposition = k;
                }
            }
            for (int l = 0; l < questionsarray.length(); l++) {
                if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getMapuniqueid()))
                    questionid = questionsarray.getJSONObject(l).getString("id").toString();
            }

            if (formrule_array.length() > 0) {
                for (int p = 0; p < formrule_array.length(); p++) {
                    String method = formrule_array.getJSONObject(p).getString("method").toString();
                    JSONArray condition_array = new JSONArray(formrule_array.getJSONObject(p).getString("condition").toString());
                    JSONArray show_array = new JSONArray(formrule_array.getJSONObject(p).getString("show").toString());
                    JSONArray hide_array = new JSONArray(formrule_array.getJSONObject(p).getString("hide").toString());
                    int conditioncount = 0;
                    ArrayList<String> values=new ArrayList<String>();
                    for(int j=0;j<condition_array.length();j++)
                    {
                        values.add(condition_array.getJSONObject(j).getString("question_id").toString());

                        for(int m=0;m<hide_array.length();m++) {
                            for(int n=0;n<questionsarray.length();n++)
                            {
                                if(condition_array.getJSONObject(j).getString("question_id").toString().equalsIgnoreCase(questionid))
                                {
                                    if (hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")&& questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0") && questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                else
                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                            }
                           // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                        }
                        for (int l = 0; l < questionsarray.length(); l++) {
                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getMapuniqueid())) {
                                if (condition_array.getJSONObject(j).getString("question_id").equalsIgnoreCase(questionid)) {
                                    if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isEmpty")) {
                                        if (questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                            questionsarray.getJSONObject(l).put("isrule", true);
                                            conditioncount++;
                                        }else
                                            questionsarray.getJSONObject(l).put("isrule", false);
                                    } else if (condition_array.getJSONObject(j).getString("condition_type").equalsIgnoreCase("isNotEmpty")) {
                                        if (!questionsarray.getJSONObject(l).getString("answer").toString().trim().equalsIgnoreCase("")) {
                                            questionsarray.getJSONObject(l).put("isrule", true);
                                            conditioncount++;
                                        }else
                                            questionsarray.getJSONObject(l).put("isrule", false);
                                    }
                                }
                            }
                        }
                    }

                    if (method.equalsIgnoreCase("1")) {
                        if (conditioncount > 0) {
                            for (int m = 0; m < show_array.length(); m++) {
                                for (int n = 0; n < questionsarray.length(); n++) {
                                    if (show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                else
                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                googlemapshowcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }
                            for(int m=0;m<hide_array.length();m++) {
                                String issubquestion = "";
                                String subqueids="";
                                for(int n=0;n<questionsarray.length();n++)
                                {

                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                    {
                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                }
                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                                if(issubquestion.equalsIgnoreCase("1"))
                                {
                                    String[] subquearray = subqueids.split(",");
                                    for(int k=0;k<subquearray.length;k++)
                                    {
                                        for(int n=0;n<questionsarray.length();n++) {
                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);

                            }
                        }
                    } else {
                        HashSet<String> hashSet = new HashSet<String>();
                        hashSet.addAll(values);
                        values.clear();
                        values.addAll(hashSet);
                        int scuusscondition=0;
                        for(int q =0;q<questionsarray.length();q++)
                        {
                            if(questionsarray.getJSONObject(q).getString("isrule").equalsIgnoreCase("true"))
                            {
                                scuusscondition++;
                            }
                        }
                        if(values.size()==scuusscondition)
                        {
                            for (int m = 0; m < show_array.length(); m++) {
                                for (int n = 0; n < questionsarray.length(); n++) {
                                    if (show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                        if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : show_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                else
                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                googlemapshowcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }
                            for(int m=0;m<hide_array.length();m++) {
                                String issubquestion = "";
                                String subqueids="";
                                for(int n=0;n<questionsarray.length();n++)
                                {

                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                    {
                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                            if (questionsarray.getJSONObject(n).getString("parent").toString().equalsIgnoreCase("1")) {
                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                if(questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmte")||questionsarray.getJSONObject(n).getString("question_type").toString().equalsIgnoreCase("qmtn")) {
                                                    questionsarray.getJSONObject(n).put("validationtype", "");
                                                    questionsarray.getJSONObject(n).put("validationvalue", "");
                                                }
                                                issubquestion = questionsarray.getJSONObject(n).getString("subquestion").toString();
                                                subqueids =questionsarray.getJSONObject(n).getString("subquestionid").toString();
                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                                if(issubquestion.equalsIgnoreCase("1"))
                                {
                                    String[] subquearray = subqueids.split(",");
                                    for(int k=0;k<subquearray.length;k++)
                                    {
                                        for(int n=0;n<questionsarray.length();n++) {
                                            if (!itemid.equalsIgnoreCase("0") ? questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid) : subquearray[k].equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                                questionsarray.getJSONObject(n).put("dummy_is_required", questionsarray.getJSONObject(n).has("dummy_is_required") ? questionsarray.getJSONObject(n).getString("dummy_is_required").toString() : questionsarray.getJSONObject(n).getString("is_required").toString());
                                                questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("is_other", "");
                                                questionsarray.getJSONObject(n).put("visual", "0");
                                                hiddencontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                               // formarray.getJSONObject(questionposition).put("questions", questionsarray);

                            }
                        }else
                        {
                            for(int m=0;m<hide_array.length();m++) {
                                for(int n=0;n<questionsarray.length();n++)
                                {
                                    if(hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString()))
                                    {
                                        if (!itemid.equalsIgnoreCase("0")?questionsarray.getJSONObject(n).getString("item_id").toString().equalsIgnoreCase(itemid):hide_array.getJSONObject(m).getString("question_id").toString().equalsIgnoreCase(questionsarray.getJSONObject(n).getString("id").toString())) {
                                            if (questionsarray.getJSONObject(n).getString("visual").toString().equalsIgnoreCase("0")) {
                                                if (questionsarray.getJSONObject(n).getString("dummy_is_required").toString().equalsIgnoreCase("1"))
                                                    questionsarray.getJSONObject(n).put("is_required", "1");
                                                else
                                                    questionsarray.getJSONObject(n).put("is_required", "0");
                                                questionsarray.getJSONObject(n).put("answer_id", "");
                                                questionsarray.getJSONObject(n).put("answer", "");
                                                questionsarray.getJSONObject(n).put("answered", "yes");
                                                questionsarray.getJSONObject(n).put("visual", "1");
                                                showcontrol(questionsarray.getJSONObject(n).getString("unique_id").toString(), questionsarray.getJSONObject(n).getString("question_type").toString());
                                            }
                                        }
                                    }
                                }
                                //formarray.getJSONObject(questionposition).put("questions", questionsarray);
                            }
                        }
                    }
                }
                formarray.getJSONObject(questionposition).put("questions", questionsarray);
            }
    }catch(Exception ex)
        {
            Log.e("Map Pagerule Exception",ex.getMessage().toString());
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


    }

    private boolean isGooglePlayServicesAvailable() {
        final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(SurveyForm.this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.d(TAG, "This device is not supported.");

            }
            return false;
        }
        Log.d(TAG, "This device is supported.");
        return true;
    }

}

