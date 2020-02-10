package com.mapolbs.vizibeebritannia.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mapolbs.vizibeebritannia.Utilities.Privacypolicy;
import com.mapolbs.vizibeebritannia.Utilities.UserSessionManager;
import com.tooltip.Tooltip;
import com.mapolbs.vizibeebritannia.CustomAdapter.Default_DropdownCustomAdapter;
import com.mapolbs.vizibeebritannia.CustomAdapter.RetailerListAdapter;
import com.mapolbs.vizibeebritannia.DB.DBAdapter;
import com.mapolbs.vizibeebritannia.DB.DBHelper;
import com.mapolbs.vizibeebritannia.Model.DropDownData;
import com.mapolbs.vizibeebritannia.Model.RetailerListClass;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.ConnectionDetector;
import com.mapolbs.vizibeebritannia.Utilities.FCMConfig;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;
import com.mapolbs.vizibeebritannia.Utilities.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DefaultForm extends AppCompatActivity implements AdapterView.OnItemClickListener{


    LinearLayout linlayout;
    ArrayList<DropDownData> Dropdownlist;
    JSONArray quejarray;
    JSONArray formarray;
    JSONObject formobj;
    JSONObject questionobj;
    JSONArray questionarray;
    String retailerid;
    String[] retailername;
    String[] retailercode;
    String strretailers="";
    ImageView imglogout;
    ImageView imginfo;
    Button btnnext;
    ImageView projectlogo;
    TextView projectname;
    TextView txttitle;
    int resID=0;
    DBAdapter dbadapter;
    ConnectionDetector cd;
    Button autocompletetxt;
    SimpleAdapter simpleadapter;
    RetailerListAdapter retailerlistadapter;
    boolean doubleBackToExitPressedOnce = false;
    int marginleftpx =  0;
    int marginleft = 0;
    int marginrightpx =  0;
    int marginright = 0;
    private SQLiteDatabase mDb;
    ArrayList<String> retailerlist;
    String[] questionidarray;
    ImageView img_notifcation;
    TextView txtnotifications_count;
    private BroadcastReceiver mRegReceiver;
    Bundle bundle =null;
    String msgcount ="";
    String msgdatas ="";
    final String PREFS_NAME = "MyPrefsFile";
    UserSessionManager session;
    String ipaddress="";
    String in_username="";
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_form);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dbadapter = new DBAdapter(this);
        dbadapter.open();
        float scale = getResources().getConfiguration().fontScale;
        bundle = getIntent().getExtras();
        if (null!=bundle) {
            msgcount = bundle.containsKey("count")?  bundle.getString("count"):"";
            msgdatas = bundle.containsKey("datas")?  bundle.getString("datas"):"";
            if (!msgcount.equalsIgnoreCase("")||!msgdatas.equalsIgnoreCase("")) {
                gotonotificationscreen();
            }

        }else {
            if (MyApplication.getInstance().getProject_notificationcount()>0){
                gotonotificationscreen();
            }
        }
        init();
        initcallback();
    }


    private  void gotonotificationscreen(){
        Intent i = new Intent(DefaultForm.this, NotificationScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegReceiver,
                new IntentFilter(FCMConfig.PUSH_NOTIFICATION));
        if (FCMConfig.NOTIFICATION_COUNT>0) {
            txtnotifications_count.setVisibility(View.VISIBLE);
            txtnotifications_count.setText(String.valueOf(FCMConfig.NOTIFICATION_COUNT));
        }else {
            txtnotifications_count.setVisibility(View.GONE);

        }

        // clear the notification area when the app is opened

    }


    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegReceiver);
    }


    public void init(){
        try {
            btnnext = (Button)findViewById(R.id.btnnext);
            imginfo = (ImageView)findViewById(R.id.imginfo);
            imglogout = (ImageView)findViewById(R.id.imglogout);
            txttitle = (TextView)findViewById(R.id.txt_visitdetails);
            img_notifcation = (ImageView)findViewById(R.id.img_notifcation);
            txtnotifications_count = (TextView) findViewById(R.id.txtnotifications_count);
            img_notifcation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DefaultForm.this,NotificationScreen.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            mRegReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(FCMConfig.PUSH_NOTIFICATION)) {
                        String count = intent.getStringExtra("count");
                        txtnotifications_count.setVisibility(View.VISIBLE);
                        txtnotifications_count.setText(count);
                        NotificationUtils.clearNotifications(getApplicationContext());

                    }
                }

            };

            marginleftpx =  getResources().getDimensionPixelSize(R.dimen.marginleft);
            marginleft = (int)(marginleftpx / getResources().getDisplayMetrics().density);
            marginrightpx =  getResources().getDimensionPixelSize(R.dimen.marginright);
            marginright = (int)(marginrightpx / getResources().getDisplayMetrics().density);
            projectlogo = (ImageView)findViewById(R.id.imgprojectlogo);
            projectname = (TextView)findViewById(R.id.txtprojectname);
            formarray = new JSONArray();
            formobj = new JSONObject();

            linlayout = (LinearLayout)findViewById(R.id.relativelayout);
            JSONObject jobj = new JSONObject(MyApplication.getInstance().getjsonstring());
            JSONObject project_settingsObject = jobj.getJSONObject("project_settings");
            JSONObject loggedobj =jobj.getJSONObject("logged_user");

            MyApplication.getInstance().setProjectname(loggedobj.getString("project_name"));
            MyApplication.getInstance().setProjectlogo(loggedobj.getString("logo"));
            MyApplication.getInstance().setAuto_geo_location(project_settingsObject.getString("AUTOMATIC_GEO_LOCIATON"));
            MyApplication.getInstance().setProjectid(loggedobj.getString("project_id"));
            JSONArray formsjarray = new JSONArray(jobj.getString("forms"));
            for(int i=0;i<formsjarray.length();i++)
            {
                if(formsjarray.getJSONObject(i).getString("type").toString().equalsIgnoreCase("DEFAULT"))
                {
                    formobj.put("form_id", formsjarray.getJSONObject(i).getString("form_id").toString());
                    //controls("header",linlayout,formsjarray.getJSONObject(i).getString("title").toString(),"",0,"","");
                    controls("empty",linlayout,"","",0,"","","");
                    txttitle.setText(formsjarray.getJSONObject(i).getString("title").toString());
                    txttitle.setTag(formsjarray.getJSONObject(i).getString("description").toString());
                    quejarray = new JSONArray(formsjarray.getJSONObject(i).getString("questions").toString());
                    questionarray = new JSONArray();
                    for(int j=0;j<quejarray.length();j++)
                    {
                        questionobj = new JSONObject();
                        questionobj.put("unique_id",j+1);
                        questionobj.put("question_id",quejarray.getJSONObject(j).getString("id").toString());
                        questionobj.put("question_type", quejarray.getJSONObject(j).getString("type").toString());
                        questionobj.put("is_required", quejarray.getJSONObject(j).getString("is_required").toString());
                        controls("title",linlayout,quejarray .getJSONObject(j).getString("title").toString(),"",0,"",quejarray.getJSONObject(j).getString("is_required").toString(),quejarray .getJSONObject(j).has("description")?quejarray .getJSONObject(j).getString("description").toString():"");
                        controls(quejarray.getJSONObject(j).getString("type").toString(),linlayout,"",quejarray.getJSONObject(j).getString("values").toString(),j+1,quejarray.getJSONObject(j).getString("target").toString(),quejarray.getJSONObject(j).getString("id").toString(),"");
                    }
                    formobj.put("questions", questionarray);
                    formarray.put(formobj);
                    MyApplication.getInstance().setFinalobj(formarray);
                    controls("empty",linlayout,"","",0,"","","");
                    controls("empty",linlayout,"","",0,"","","");
                    controls("empty",linlayout,"","",0,"","","");

                    controls("empty",linlayout,"","",0,"","","");
                    controls("empty",linlayout,"","",0,"","","");
                    controls("empty",linlayout,"","",0,"","","");
                }
            }
            projectname.setText(MyApplication.getInstance().getProjectname());
            File destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + ".Vizibeekat_Root"+File.separator+"DoNotDelete_Vizibee");
            File file = new File(destination, "/IMG_logo.jpg");
            if(file.exists())
            {
                projectlogo.setBackground(null);
                Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath()
                );
                projectlogo.setImageBitmap(bmp);
            }

        }
        catch (Exception ex)
        {
            Log.e("Default Form",ex.getMessage().toString());
        }
    }


    public void initcallback()
    {
        try{
            txttitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = v.getTag().toString();
                if (!desc.equalsIgnoreCase("")&&desc!=null)
                {

                    Tooltip.Builder builder = new Tooltip.Builder(v, R.style.Tooltip)
                            .setCancelable(true)
                            .setDismissOnClick(false)
                            .setCornerRadius(5f)
                            .setText(desc);
                    builder.show();


                }
            }
        });

            imginfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Privacypolicy(DefaultForm.this);


                }
            });
            imginfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        final Dialog dialog = new Dialog(DefaultForm.this,
                                R.style.MyAlert);
                        dialog.setContentView(R.layout.info_dialoge);
                        Rect displayRectangle = new Rect();
                        Window window = DefaultForm.this.getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = (int)(displayRectangle.width() * 0.5f);
                        lp.height = (int)(displayRectangle.height() * 0.5f);
                        lp.gravity = Gravity.CENTER;
                        final ViewFlipper viewflipper = (ViewFlipper)dialog.findViewById(R.id.viewflipper);
                        TextView underlinetxt = (TextView) dialog.findViewById(R.id.txtaboutus);
                        TextView underlinetxtprivacypolicy = (TextView) dialog.findViewById(R.id.txtprivacypolicy);
                        underlinetxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewflipper.showPrevious();
                            }
                        });
                        underlinetxtprivacypolicy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewflipper.showNext();
                            }
                        });
                        String udata="About Us";
                        SpannableString content = new SpannableString(udata);
                        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
                        underlinetxt.setText(content);
                        String updata="Privacy Policy";
                        SpannableString contentp = new SpannableString(updata);
                        contentp.setSpan(new UnderlineSpan(), 0, updata.length(), 0);
                        underlinetxtprivacypolicy.setText(contentp);
                        TextView txt = (TextView) dialog.findViewById(R.id.textView9);
                        txt.setMovementMethod(new ScrollingMovementMethod());
                        TextView textView19 = (TextView) dialog.findViewById(R.id.textView19);
                        textView19.setMovementMethod(new ScrollingMovementMethod());
                        txt.setText(Html.fromHtml("<div class=\"entry-content clearfix\">\n" +
                                "<p><strong>And Marketing</strong> (<a target=\"_blank\" title=\"andmarketing\" href=\"http://andmarketing.in/\">www.andmarketing.in</a>) offers “<strong>Vizibee</strong>” app as a Commercial app. Vizibee – is a mobile app based field reporting tool used by individual users/merchandisers/promoters/field personnel for submitting their field/market/sales/availability/visibility/implementation reports and is used to monitor the field force across Pan India for various brands. And Marketing is a partnership firm incorporated in India and is committed to the privacy and security of all data. This SERVICE is provided by And Marketing and is intended for use as is. Vizibee is currently developed and maintained by Mapol Business Solutions Pvt. Ltd (<a title=\"Mapol\" target=\"_blank\" href=\"http://mapolbs.com/\">www.mapolbs.com</a>)<br>\n" +
                                "This page is used to inform website/app visitors/users regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.<br>\n" +
                                "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\n" +
                                "<p><strong>Information Collection and Use</strong></p>\n" +
                                "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request is will be retained by us and used as described in this privacy policy.<br>\n" +
                                "The app does use third party services that may collect information used to identify you.<br>\n" +
                                "Link to privacy policy of third party service providers used by the app<br>\n" +
                                "<a title=\"Google Play Services\" href=\"https://www.google.com/policies/privacy/\">Google Play Services</a></p>\n" +
                                "<p><strong>Log Data</strong></p>\n" +
                                "<p>We want to inform you that whenever you use our Service, both during regular usage or in a case of an error in the app we collect data and information on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\n" +
                                "<p><strong>Location information</strong></p>\n" +
                                "<p>We collect the IP address you use to connect to the Service, and your location information from a mobile device. This helps us allows us to show you relevant content accessible from your account and also to capture the location of visit to use it for marking the visit/site address for future use while you/other user intend to visit the same location and/or make the route/beat plans and/or attendance for the field force based on the locations visited. Location access may include: Approximate location (network-based), Precise location (GPS and network-based), Access extra location provider commands and GPS access</p>\n" +
                                "<p><strong>Camera</strong></p>\n" +
                                "<p>We use your camera only to capture the pictures/videos you voluntarily submit in the app as a proof of work completion and/or as part of your reporting norms as agreed in your employment. Any unsolicited/unethical/inappropriate pictures/videos if any taken/submitted shall be deleted and may lead to blocking of the user id. We access and sync the data/pictures/images/videos which are stored in a designated app folder during offline usage, and do not access other images/pictures stored in your device.</p>\n" +
                                "<p><strong>Storage – Photos/Media/Files</strong></p>\n" +
                                "<p>We use files or data stored on your device. Photos/Media/Files access may include the ability to: Read the contents of your USB storage (example: SD card), Modify or delete the contents of your USB storage, Format external storage and Mount or unmount external storage.</p>\n" +
                                "<p><strong>Cookies</strong></p>\n" +
                                "<p>Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your device internal memory.<br>\n" +
                                "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collection information and to improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\n" +
                                "<p><strong>Service Providers</strong></p>\n" +
                                "<p>We may employ third-party companies and individuals due to the following reasons:</p>\n" +
                                "<ul>\n" +
                                "<li>To facilitate our Service;</li>\n" +
                                "<li>To provide the Service on our behalf;</li>\n" +
                                "<li>To perform Service-related services; or</li>\n" +
                                "<li>To assist us in analyzing how our Service is used.</li>\n" +
                                "</ul>\n" +
                                "<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\n" +
                                "<p><strong>Security</strong></p>\n" +
                                "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it.<br>\n" +
                                "We only provide articles and information. We never ask for credit card numbers. We use regular Malware Scanning.<br>\n" +
                                "But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\n" +
                                "<p><strong>Links to Other Sites</strong></p>\n" +
                                "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\n" +
                                "<p><strong>Children’s Privacy</strong></p>\n" +
                                "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\n" +
                                "<p><strong>Changes to This Privacy Policy</strong></p>\n" +
                                "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page.</p>\n" +
                                "<p><strong>Contact Us</strong></p>\n" +
                                "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.<br>\n" +
                                "<strong>And Marketing</strong>, No. 272-A, Ground Floor, Dr. Selvi Jayakumar Street, Golden George Nagar, Nerkundram, Chennai – 600107. Tamil Nadu, India.<br>\n" +
                                "<strong>Email</strong>: <a title=\"andmarketing\" href=\"http://andmarketing.in/privacy-policy/info@andmarketing.in\">info@andmarketing.in</a><br>\n" +
                                "<strong>Phone</strong>: +91 44 64622444</p>\n" +
                                "  </div> <!-- end .entry-content -->"));

                        textView19.setText(Html.fromHtml("<div class=\"entry-content clearfix\">\n" +
                                "<p><strong>And Marketing</strong> (<a target=\"_blank\" title=\"andmarketing\" href=\"http://andmarketing.in/\">www.andmarketing.in</a>) offers “<strong>Vizibee</strong>” app as a Commercial app. Vizibee – is a mobile app based field reporting tool used by individual users/merchandisers/promoters/field personnel for submitting their field/market/sales/availability/visibility/implementation reports and is used to monitor the field force across Pan India for various brands. And Marketing is a partnership firm incorporated in India and is committed to the privacy and security of all data. This SERVICE is provided by And Marketing and is intended for use as is. Vizibee is currently developed and maintained by Mapol Business Solutions Pvt. Ltd (<a title=\"Mapol\" target=\"_blank\" href=\"http://mapolbs.com/\">www.mapolbs.com</a>)<br>\n" +
                                "This page is used to inform website/app visitors/users regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.<br>\n" +
                                "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\n" +
                                "<p><strong>Information Collection and Use</strong></p>\n" +
                                "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request is will be retained by us and used as described in this privacy policy.<br>\n" +
                                "The app does use third party services that may collect information used to identify you.<br>\n" +
                                "Link to privacy policy of third party service providers used by the app<br>\n" +
                                "<a title=\"Google Play Services\" href=\"https://www.google.com/policies/privacy/\">Google Play Services</a></p>\n" +
                                "<p><strong>Log Data</strong></p>\n" +
                                "<p>We want to inform you that whenever you use our Service, both during regular usage or in a case of an error in the app we collect data and information on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\n" +
                                "<p><strong>Location information</strong></p>\n" +
                                "<p>We collect the IP address you use to connect to the Service, and your location information from a mobile device. This helps us allows us to show you relevant content accessible from your account and also to capture the location of visit to use it for marking the visit/site address for future use while you/other user intend to visit the same location and/or make the route/beat plans and/or attendance for the field force based on the locations visited. Location access may include: Approximate location (network-based), Precise location (GPS and network-based), Access extra location provider commands and GPS access</p>\n" +
                                "<p><strong>Camera</strong></p>\n" +
                                "<p>We use your camera only to capture the pictures/videos you voluntarily submit in the app as a proof of work completion and/or as part of your reporting norms as agreed in your employment. Any unsolicited/unethical/inappropriate pictures/videos if any taken/submitted shall be deleted and may lead to blocking of the user id. We access and sync the data/pictures/images/videos which are stored in a designated app folder during offline usage, and do not access other images/pictures stored in your device.</p>\n" +
                                "<p><strong>Storage – Photos/Media/Files</strong></p>\n" +
                                "<p>We use files or data stored on your device. Photos/Media/Files access may include the ability to: Read the contents of your USB storage (example: SD card), Modify or delete the contents of your USB storage, Format external storage and Mount or unmount external storage.</p>\n" +
                                "<p><strong>Cookies</strong></p>\n" +
                                "<p>Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your device internal memory.<br>\n" +
                                "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collection information and to improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\n" +
                                "<p><strong>Service Providers</strong></p>\n" +
                                "<p>We may employ third-party companies and individuals due to the following reasons:</p>\n" +
                                "<ul>\n" +
                                "<li>To facilitate our Service;</li>\n" +
                                "<li>To provide the Service on our behalf;</li>\n" +
                                "<li>To perform Service-related services; or</li>\n" +
                                "<li>To assist us in analyzing how our Service is used.</li>\n" +
                                "</ul>\n" +
                                "<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\n" +
                                "<p><strong>Security</strong></p>\n" +
                                "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it.<br>\n" +
                                "We only provide articles and information. We never ask for credit card numbers. We use regular Malware Scanning.<br>\n" +
                                "But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\n" +
                                "<p><strong>Links to Other Sites</strong></p>\n" +
                                "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\n" +
                                "<p><strong>Children’s Privacy</strong></p>\n" +
                                "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\n" +
                                "<p><strong>Changes to This Privacy Policy</strong></p>\n" +
                                "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page.</p>\n" +
                                "<p><strong>Contact Us</strong></p>\n" +
                                "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.<br>\n" +
                                "<strong>And Marketing</strong>, No. 272-A, Ground Floor, Dr. Selvi Jayakumar Street, Golden George Nagar, Nerkundram, Chennai – 600107. Tamil Nadu, India.<br>\n" +
                                "<strong>Email</strong>: <a title=\"andmarketing\" href=\"http://andmarketing.in/privacy-policy/info@andmarketing.in\">info@andmarketing.in</a><br>\n" +
                                "<strong>Phone</strong>: +91 44 64622444</p>\n" +
                                "  </div> <!-- end .entry-content -->"));



                        dialog.show();


                    } catch (Exception ex) {
                        Log.e("Info", ex.getMessage().toString());
                    }
                }
            });
            imglogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        dbadapter.deletelogindate();
                                        dbadapter.deletejsondata();
                                        dbadapter.deletedofflineimagedata();
                                        File destination = new File(Environment.getExternalStorageDirectory().getPath(),  File.separator + "Vizibeekat_Root"+File.separator+"DoNotDelete_Vizibee");
                                        if (destination.isDirectory())
                                        {
                                            String[] children = destination.list();
                                            for (int i = 0; i < children.length; i++)
                                            {
                                                new File(destination, children[i]).delete();
                                            }
                                        }
                                        DefaultForm.this.finish();
                                        Intent i = new Intent(DefaultForm.this,LoginScreen.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivityForResult(i,500);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DefaultForm.this,R.style.MyAlertDialogStyle);
                        builder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure Want to Logout?</font>")).setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener);

                        AlertDialog dialog = builder.create();
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
                    } catch (ActivityNotFoundException e) {
                        // TODO: handle exception

                    }
                }
            });

            btnnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int errorcount = 0;
                        int retailererrorcount = 0;
                        if(formarray.length()>1) {
                            JSONArray jarray = new JSONArray();
                            for (int k = 0; k < 1; k++) {
                                jarray.put(formarray.getJSONObject(k));
                            }
                            formarray = new JSONArray();
                            formarray = jarray;
                        }

                        for (int i = 0; i < questionarray.length(); i++) {
                            if (questionarray.getJSONObject(i).getString("is_required").toString().equalsIgnoreCase("1")) {
                                if(questionarray.getJSONObject(i).getString("answer").toString().equalsIgnoreCase("Select")||questionarray.getJSONObject(i).getString("answer").toString().equalsIgnoreCase(""))
                                {
                                    errorcount++;
                                }
                                if(questionarray.getJSONObject(i).getString("question_type").toString().equalsIgnoreCase("qmdts"))
                                {
                                    if(!questionarray.getJSONObject(i).getString("retailers").toString().equalsIgnoreCase(strretailers))
                                        retailererrorcount++;
                                }

                            }
                        }

                        if(errorcount > 0)
                            Toast.makeText(DefaultForm.this, "Please Select All Mandatory Fields.", Toast.LENGTH_LONG).show();
                        else if(retailererrorcount > 0)
                            Toast.makeText(DefaultForm.this, "Please Remove unwanted text from Retailer.", Toast.LENGTH_LONG).show();
                        else {

                            MyApplication.getInstance().setRetailerid(retailerid);
                            Intent i = new Intent(DefaultForm.this,VisitDetails.class);
                            startActivityForResult(i, 500);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        Log.e("Submit", formarray.toString());
                    }catch(Exception ex)
                    {
                        Log.e("Subit Exception",ex.getMessage().toString());
                    }
                }
            });


        }catch (Exception ex)
        {
            Log.e("Default Form",ex.getMessage().toString());
        }
    }


    //onBackKeyPressed
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            DefaultForm.this.finish();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public static String StreamToString(InputStream in) throws IOException {
        if(in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }catch (Exception ex ){
            Log.e("StreamToString",ex.getMessage());
        }
        return writer.toString();
    }

    @SuppressLint("NewApi")
    public void controls(String type, LinearLayout tl, String title, String values, final int id, final String targetid, final String questionid, final String description)
    {
        try {
            TableRow row = new TableRow(this);
            Dropdownlist = new ArrayList<DropDownData>();


            if (type.equalsIgnoreCase("qmdt")) {
                int height = (int) getResources().getDimension(R.dimen.defaultform_qmdt_height);
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                android.widget.TableRow.LayoutParams qmdtparams = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                params.setMargins(marginleft, 0, marginright, 0);
                row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                JSONArray valuearray = new JSONArray(values);
                Dropdownlist.add(new DropDownData("Select","Select",questionid));
                for(int i=0;i<valuearray.length();i++)
                {
                    Dropdownlist.add(new DropDownData(valuearray.getJSONObject(i).getString("code"),valuearray.getJSONObject(i).getString("name"),questionid));
                }
                final Default_DropdownCustomAdapter adapter = new Default_DropdownCustomAdapter(DefaultForm.this, Dropdownlist);
                LayoutInflater headinflater = DefaultForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.spinner, null, false);
                final Spinner spn = (Spinner) view.findViewById(R.id.spinner);
                int paddingleft = (int) getResources().getDimension(R.dimen.defaultform_qmdt_paddingleft);
                int paddingtop = (int) getResources().getDimension(R.dimen.defaultform_qmdts_paddingtop);
                spn.setPadding(paddingleft,paddingtop,0,0);
                view.setLayoutParams(qmdtparams);
                spn.setId(id);
                spn.setAdapter(adapter);

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View arg1,
                                               int pos, long arg3) {
                        try
                        {
                            arg1.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                            if(autocompletetxt != null) {
                                autocompletetxt.setText("");
                                try
                                {

                                    for(int k=0;k<questionarray.length();k++)
                                    {
                                        if(questionarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(questionidarray[0]))
                                        {
                                            questionarray.getJSONObject(k).put("answer_id","");
                                            questionarray.getJSONObject(k).put("answer", "");
                                            questionarray.getJSONObject(k).put("retailers", "");
                                            questionarray.getJSONObject(k).put("is_other", "");

                                        }
                                    }
                                    questionobj.put("answer_id", "");
                                    questionobj.put("answer",  "");
                                    questionobj.put("retailers", "");
                                    questionobj.put("is_other", "");


                                }
                                catch (Exception ex)
                                {
                                    Log.e("Default Form",ex.getMessage().toString());
                                }
                            }
                            if(arg1!= null) {
                                View view = (View) arg1.getParent();
                                if (view != null) {
                                    TextView txtid = (TextView) view.findViewById(R.id.txt_id);
                                    TextView txtname = (TextView) view.findViewById(R.id.txt_name);
                                    TextView txt_queid = (TextView) view.findViewById(R.id.txt_queid);

                                    for (int k = 0; k < questionarray.length(); k++) {
                                        if (questionarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(txt_queid.getText().toString())) {
                                            questionarray.getJSONObject(k).put("answer_id", txtid.getText().toString());
                                            questionarray.getJSONObject(k).put("answer", txtname.getText().toString());
                                            questionarray.getJSONObject(k).put("is_other", "");

                                        }
                                    }
                                    questionobj.put("answer_id", "");
                                    questionobj.put("answer", "");
                                    questionobj.put("is_other", "");
                                    if (quejarray.length() - 1 == id) {
                                        ArrayAdapter<String> txtadapter = null;
                                        ArrayList<RetailerListClass> list = null;
                                        //List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
                                        for (int i = 0; i < quejarray.length(); i++) {
                                            if (quejarray.getJSONObject(i).getString("hierarchy_code").toString().equalsIgnoreCase(targetid)) {
                                                JSONArray valuesarray = new JSONArray(quejarray.getJSONObject(i).getString("values").toString());

                                                retailername = new String[valuesarray.length()];
                                                retailercode = new String[valuesarray.length()];
                                                final String[] questionidarray = new String[valuesarray.length()];
                                                int idcount = 0;
                                                list = new ArrayList<RetailerListClass>();
                                                for (int j = 0; j < valuesarray.length(); j++) {
                                                    if (valuesarray.getJSONObject(j).getString(targetid).toString().equalsIgnoreCase(txtid.getText().toString())) {
                                                        retailername[idcount] = valuesarray.getJSONObject(j).getString("name");
                                                        retailercode[idcount] = valuesarray.getJSONObject(j).getString("code");
                                                        questionidarray[idcount] = questionid;

                                                        list.add(new RetailerListClass(valuesarray.getJSONObject(j).getString("code"),valuesarray.getJSONObject(j).getString("name")));
                                                        idcount++;
                                                    }
                                                }

                                            }

                                        }
                                        retailerlist = new ArrayList<String>();
                                        DBHelper dbHelper = new DBHelper(DefaultForm.this);
                                        mDb = dbHelper.getReadableDatabase();
                                        String jsonstring = "";
                                        Cursor c1 = mDb.rawQuery("SELECT retailercode FROM Retailersdetails where projectcode='"+MyApplication.getInstance().getProjectid()+"'", null);
                                        if (c1.moveToFirst()) {
                                            do {

                                                retailerlist.add(c1.getString(c1.getColumnIndex("retailercode")));

                                            } while (c1.moveToNext());
                                        }
                                        c1.close();
                                        retailerlistadapter = new RetailerListAdapter(DefaultForm.this,R.layout.retailerlist_layout,list,retailerlist);

                                    } else {
                                        Default_DropdownCustomAdapter adapters = null;
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
                                                adapters = new Default_DropdownCustomAdapter(DefaultForm.this, Dropdownlist);
                                            }

                                        }
                                        resID = getResources().getIdentifier(String.valueOf(spn.getId() + 1), "id", "com.mapolbs.vizibeebritannia");
                                        Spinner spnnext = (Spinner) findViewById(resID);
                                        spnnext.setAdapter(adapters);
                                    }
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            Log.e("Default Form",ex.getMessage().toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });

                row.addView(view);
                questionarray.put(questionobj);

            }else if (type.equalsIgnoreCase("qmdts")) {
                int height = (int) getResources().getDimension(R.dimen.defaultform_qmdts_height);
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                android.widget.TableRow.LayoutParams qmdtsparams = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                params.setMargins(marginleft, 0, marginright, 0);
                row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                JSONArray valuearray = new JSONArray(values);

                questionidarray = new String[valuearray.length()];

                for(int i=0;i<valuearray.length();i++)
                {
                    questionidarray[i] = questionid;
                }
                LayoutInflater headinflater = DefaultForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.retailerspinner, null, false);
                autocompletetxt = (Button) view.findViewById(R.id.spinner);
                view.setLayoutParams(qmdtsparams);
                int paddingleft = (int) getResources().getDimension(R.dimen.defaultform_qmdts_paddingleft);
                autocompletetxt.setPadding(paddingleft,0,0,0);
                autocompletetxt.setId(id);
                autocompletetxt.setText("");
                autocompletetxt.setSingleLine(true);
                int px = getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / getResources().getDisplayMetrics().density;
                autocompletetxt.setTextSize(size);
                strretailers = "";
                DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                dateFormatter.setLenient(false);
                Date today = new Date();
                final String devicedate = dateFormatter.format(today);
                autocompletetxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        androidx.appcompat.app.AlertDialog.Builder builderSingle = new androidx.appcompat.app.AlertDialog.Builder(
                                DefaultForm.this,R.style.MyAlert);
                        LayoutInflater headinflater = DefaultForm.this.getLayoutInflater();
                        View view = headinflater.inflate(R.layout.retailertitle, null, false);
                        TextView txtdate = (TextView)view.findViewById(R.id.txtdate);
                        TextView txttotal = (TextView)view.findViewById(R.id.txttotal);

                        TextView txtachived = (TextView)view.findViewById(R.id.txtachived);
                        txtdate.setText(devicedate);
                        txttotal.setText("Tgt-("+String.valueOf(retailerlistadapter.getCount()+")"));
                        if(retailerlistadapter.getCount()>0)
                            txtachived.setText("Ach-("+String.valueOf(retailerlist.size()+")"));
                        else
                            txtachived.setText("A-(0)");
                        builderSingle.setCustomTitle(view);
                        builderSingle.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        autocompletetxt.setText("");

                                    }
                                });

                        builderSingle.setAdapter(retailerlistadapter,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        autocompletetxt.setText(retailerlistadapter.getItem(which).getRetailername());
                                        autocompletetxt.setTag(retailerlistadapter.getItem(which).getRetailerid());
                                        try
                                        {
                                            String[] retailers = autocompletetxt.getText().toString().split(" > ");
                                            strretailers = autocompletetxt.getText().toString();
                                            if (v != null) {

                                                for(int k=0;k<questionarray.length();k++)
                                                {
                                                    if(questionarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(questionidarray[which]))
                                                    {
                                                        questionarray.getJSONObject(k).put("answer_id",autocompletetxt.getTag().toString());
                                                        questionarray.getJSONObject(k).put("answer", retailers[0]);
                                                        questionarray.getJSONObject(k).put("retailers", autocompletetxt.getText().toString());
                                                        questionarray.getJSONObject(k).put("is_other", "");
                                                        retailerid = autocompletetxt.getTag().toString();
                                                    }
                                                }
                                                questionobj.put("answer_id", autocompletetxt.getTag().toString());
                                                questionobj.put("answer",  retailers[0]);
                                                questionobj.put("retailers", autocompletetxt.getText().toString());
                                                questionobj.put("is_other", "");

                                            }
                                        }
                                        catch (Exception ex)
                                        {
                                            Log.e("Default Form",ex.getMessage().toString());
                                        }


                                    }
                                });
                        androidx.appcompat.app.AlertDialog alert = builderSingle.create();
                        ListView listView=alert.getListView();
                        listView.setDivider(new ColorDrawable(Color.LTGRAY)); // set color
                        listView.setDividerHeight(1); // set height
                        alert.show();

                        alert.setCanceledOnTouchOutside(false);


                    }
                });
                if(quejarray.length() == 1)
                {
                    ArrayList<RetailerListClass> list = null;

                    JSONArray valuesarray = new JSONArray(values);

                    retailername = new String[valuesarray.length()];
                    retailercode = new String[valuesarray.length()];
                    int idcount = 0;

                    list = new ArrayList<RetailerListClass>();
                    for (int j = 0; j < valuesarray.length(); j++) {
                        retailername[idcount] = valuesarray.getJSONObject(j).getString("name");
                        retailercode[idcount] = valuesarray.getJSONObject(j).getString("code");
                        questionidarray[idcount] = questionid;

                        list.add(new RetailerListClass(valuesarray.getJSONObject(j).getString("code"),valuesarray.getJSONObject(j).getString("name")));
                        idcount++;

                    }

                    retailerlist = new ArrayList<String>();
                    DBHelper dbHelper = new DBHelper(DefaultForm.this);
                    mDb = dbHelper.getReadableDatabase();
                    String jsonstring = "";
                    Cursor c1 = mDb.rawQuery("SELECT retailercode FROM Retailersdetails where projectcode='"+MyApplication.getInstance().getProjectid()+"'", null);
                    if (c1.moveToFirst()) {
                        do {

                            retailerlist.add(c1.getString(c1.getColumnIndex("retailercode")));

                        } while (c1.moveToNext());
                    }
                    c1.close();
                    retailerlistadapter = new RetailerListAdapter(DefaultForm.this,R.layout.retailerlist_layout,list,retailerlist);

                }

                row.addView(view);
                questionarray.put(questionobj);

            }
            else if (type.equalsIgnoreCase("title")) {
                int height = (int) getResources().getDimension(R.dimen.title_height);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                int margintoptpx =  getResources().getDimensionPixelSize(R.dimen.title_margintop);
                int margintop = (int)(margintoptpx / getResources().getDisplayMetrics().density);

                params.setMargins(marginleft, margintop, marginright, 0);
                row.setLayoutParams(params);
                LayoutInflater headinflater = DefaultForm.this.getLayoutInflater();
                View view = headinflater.inflate(R.layout.title, null, false);


                final TextView txtheader = (TextView) view.findViewById(R.id.txttitle);
                final ImageView imgdescription = (ImageView) view.findViewById(R.id.imgdescription);
                int paddingleft = (int) getResources().getDimension(R.dimen.title_paddingleft);
                imgdescription.setPadding(paddingleft, 0, 0, 0);
                txtheader.setText(title);
                txtheader.setTag(description);
                String text="";
                if (questionid.equalsIgnoreCase("1")) {
                    text = "<font>"+title+"</font> <font color=#D50000>*</font>";
                }else{
                    text = "<font>"+title+"</font> <font color=#D50000></font>";
                }

                String desc = txtheader.getTag().toString();
                if (!desc.equalsIgnoreCase("")&&desc!=null)
                    imgdescription.setVisibility(View.VISIBLE);
                else
                    imgdescription.setVisibility(View.GONE);

                txtheader.setText(Html.fromHtml(text));
                txtheader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String desc = v.getTag().toString();
                        if (!desc.equalsIgnoreCase("")&&desc!=null)
                       {

                            Tooltip.Builder builder = new Tooltip.Builder(v, R.style.Tooltip2)
                                    .setCancelable(true)
                                    .setDismissOnClick(false)
                                    .setCornerRadius(20f)
                                    .setText(desc);
                            builder.show();


                        }
                    }
                });
                row.addView(view);

            }

            /*29-01-2020 mani try*/
            else if (type.equalsIgnoreCase("empty"))
            {
                int height = (int) getResources().getDimension(R.dimen.survey_btn_height);
                final Button surveySubmit_btn=new Button(this);
                TableRow.LayoutParams params=new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height);
                surveySubmit_btn.setElevation(R.dimen.survey_btn_elevation);
                surveySubmit_btn.setPadding(50,0,50,0);
                surveySubmit_btn.setText("Upload Offilne Survey");
                surveySubmit_btn.setTextColor(Color.parseColor("#fff"));
                surveySubmit_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_button));
                row.setGravity(Gravity.CENTER);
                row.setTag(values); //doubt
                row.addView(row); //doubt

                surveySubmit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            /*29-01-2020 mani try*/

            else if (type.equalsIgnoreCase("empty")) {
                int height = (int) getResources().getDimension(R.dimen.defaultform_empty_height);
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
                TextView txtheader = new TextView(this);
                row.addView(txtheader);

            }
            tl.addView(row);

        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage().toString());
        }


    }
    class Logout extends AsyncTask<String, String, String> {
        String responsestr = "";
        String exceptionerrors="";
        URL url;
        HttpURLConnection urlConnection = null;
        int status = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {

            try {
                url = new URL(ipaddress+ "user_logout?username=" + in_username );
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
                exceptionerrors = e.getMessage().toString();
            }
            return responsestr;
        }

        protected void onPostExecute(String result) {

            if(result != ""){
                if (status==200){
                    Log.d("User Logged Out", in_username+"success");
                    Toast.makeText(DefaultForm.this, in_username+" Logged Out Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("User Logged Out", "fail");

                }

            } else{

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
}
