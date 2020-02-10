package com.mapolbs.vizibeebritannia.Activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mapolbs.vizibeebritannia.DB.DBAdapter;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.ConnectionDetector;
import com.mapolbs.vizibeebritannia.Utilities.FCMConfig;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;
import com.mapolbs.vizibeebritannia.Utilities.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VisitDetails extends AppCompatActivity {

    TextView txt_reasonfornotmerchandising;
    TextView txt_manditory;
    Spinner  spn_reasonfornotmerchandising;
    EditText edt_Otherremarks;
    Button btnnext;
    Button btnyes;
    Button btnno;
    LinearLayout lispinner;
    List<String> notmerchandisinglist = new ArrayList<String>();
    JSONArray formarray;
    JSONObject formobj;
    JSONObject questionobj;
    JSONArray questionarray;
    String questionid="";
    boolean redirect;
    DBAdapter dbadapter;
    ConnectionDetector cd;
    ImageView imghome;
    public static VisitDetails instance = null;
    ImageView img_notifcation;
    TextView txtnotifications_count;
    private BroadcastReceiver mRegReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_details);
        instance = this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dbadapter = new DBAdapter(this);
        dbadapter.open();
        init();
        initcallback();
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
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
        formarray = MyApplication.getInstance().getFinalobj();
        formobj = new JSONObject();
        imghome = (ImageView)findViewById(R.id.imghome);
        img_notifcation = (ImageView)findViewById(R.id.img_notifcation);
        txtnotifications_count = (TextView) findViewById(R.id.txtnotifications_count);
        img_notifcation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VisitDetails.this,NotificationScreen.class);
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

        txt_reasonfornotmerchandising =(TextView) findViewById(R.id.txt_reasonfornotmerchandising);
        txt_manditory =(TextView) findViewById(R.id.textView6);
        spn_reasonfornotmerchandising =(Spinner) findViewById(R.id.spn_reasonfornotmerchandising);
        int paddingleft = (int) getResources().getDimension(R.dimen.defaultform_qmdt_paddingleft);
        int paddingtop = (int) getResources().getDimension(R.dimen.defaultform_qmdts_paddingtop);
        spn_reasonfornotmerchandising.setPadding(paddingleft,paddingtop,0,0);
        edt_Otherremarks =(EditText)findViewById(R.id.edt_Otherremarks);
        btnyes = (Button)findViewById(R.id.btn_yes);
        btnno = (Button)findViewById(R.id.btn_no);
        btnnext = (Button)findViewById(R.id.btnnext);
        edt_Otherremarks.setVisibility(View.GONE);
        edt_Otherremarks.setText("");
        lispinner = (LinearLayout)findViewById(R.id.spn_lilayout) ;
        spn_reasonfornotmerchandising.setVisibility(View.GONE);
        txt_reasonfornotmerchandising.setVisibility(View.GONE);
        txt_manditory.setVisibility(View.GONE);
        spn_reasonfornotmerchandising.setSelection(0);
        edt_Otherremarks.setVisibility(View.GONE);
        edt_Otherremarks.setText("");
        lispinner.setVisibility(View.GONE);
    }

    public void initcallback(){
        try {
        formobj.put("form_id", "");
            questionarray = new JSONArray();
        for(int i=1;i<= 3;i++)
        {
                questionobj = new JSONObject();
                questionobj.put("unique_id",i+5);
                questionobj.put("question_id", i);
                questionobj.put("question_type", "");
            if(i==1)
                questionobj.put("is_required", "1");
            else
                questionobj.put("is_required", "");
                    questionobj.put("answer_id", "");
                    questionobj.put("answer", "");
                questionobj.put("is_other", "");
                questionarray.put(questionobj);
        }
            formobj.put("questions", questionarray);
            formarray.put(formobj);

        }catch (Exception ex){

        }

        notmerchandisinglist.add("Select");
        notmerchandisinglist.add("Store Closed");
        notmerchandisinglist.add("Stored Removed from Program");
        notmerchandisinglist.add("No Permission");
        notmerchandisinglist.add("Payout Issue");
        notmerchandisinglist.add("Others");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(VisitDetails.this,R.layout.support_simple_spinner_dropdown_item,notmerchandisinglist);
        spn_reasonfornotmerchandising.setAdapter(adapter);


        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnyes.setBackgroundResource(R.drawable.yes_select_selector);
                btnno.setBackgroundResource(R.drawable.no_normal_selector);
                questionid = "1";
                redirect = true;
                try {
                    for (int s = 0; s < formarray.length(); s++) {
                        if (formarray.getJSONObject(s).getString("form_id").equalsIgnoreCase("")) {
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(s).getString("questions"));
                            for (int k = 0; k < questionsarray.length(); k++) {
                                if (questionsarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(questionid)) {
                                    questionsarray.getJSONObject(k).put("answer_id", true);
                                    questionsarray.getJSONObject(k).put("answer", true);
                                    questionsarray.getJSONObject(k).put("is_other", "0");


                                }
                            }
                            formarray.getJSONObject(s).put("questions", questionsarray);
                        }
                    }
                }catch(Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }

                    spn_reasonfornotmerchandising.setVisibility(View.GONE);
                    txt_reasonfornotmerchandising.setVisibility(View.GONE);
                    txt_manditory.setVisibility(View.GONE);
                    spn_reasonfornotmerchandising.setSelection(0);
                    edt_Otherremarks.setVisibility(View.GONE);
                    edt_Otherremarks.setText("");
                    lispinner.setVisibility(View.GONE);

            }


        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnyes.setBackgroundResource(R.drawable.yes_normal_selector);
                btnno.setBackgroundResource(R.drawable.no_select_selector);
                questionid = "1";
                redirect = false;
                try {

                    for (int s = 0; s < formarray.length(); s++) {
                        if (formarray.getJSONObject(s).getString("form_id").equalsIgnoreCase("")) {
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(s).getString("questions"));
                            for (int k = 0; k < questionsarray.length(); k++) {
                                if (questionsarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(questionid)) {
                                    questionsarray.getJSONObject(k).put("answer_id", false);
                                    questionsarray.getJSONObject(k).put("answer", false);
                                    questionsarray.getJSONObject(k).put("is_other", "0");


                                }
                            }
                            formarray.getJSONObject(s).put("questions", questionsarray);
                        }
                    }

                }catch(Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }



                    spn_reasonfornotmerchandising.setVisibility(View.VISIBLE);
                    txt_reasonfornotmerchandising.setVisibility(View.VISIBLE);
                    lispinner.setVisibility(View.VISIBLE);
                    txt_manditory.setVisibility(View.VISIBLE);


            }


        });



        spn_reasonfornotmerchandising.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String strReasonforNotMerchandisingVlaue = spn_reasonfornotmerchandising.getSelectedItem().toString();
                questionid = "2";
                try {

                    for (int s = 0; s < formarray.length(); s++) {
                        if (formarray.getJSONObject(s).getString("form_id").equalsIgnoreCase("")) {
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(s).getString("questions"));
                            for (int k = 0; k < questionsarray.length(); k++) {
                                if (questionsarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(questionid)) {
                                    questionsarray.getJSONObject(k).put("answer_id", strReasonforNotMerchandisingVlaue);
                                    questionsarray.getJSONObject(k).put("answer", strReasonforNotMerchandisingVlaue);
                                    if(strReasonforNotMerchandisingVlaue.equalsIgnoreCase("Others")){
                                        questionsarray.getJSONObject(k).put("is_other", "1");
                                    }else {
                                        questionsarray.getJSONObject(k).put("is_other", "0");
                                    }

                                }
                            }
                            formarray.getJSONObject(s).put("questions", questionsarray);
                        }
                    }

                }catch(Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }
                if(strReasonforNotMerchandisingVlaue.equalsIgnoreCase("Others")){
                    edt_Otherremarks.setVisibility(View.VISIBLE);
                    edt_Otherremarks.setText("");
                }else {
                    edt_Otherremarks.setVisibility(View.GONE);
                    edt_Otherremarks.setText("");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    VisitDetails.this.finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(VisitDetails.this,R.style.ConfirmDialogStyle);
                    builder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure Want to Exit to Home?</font>")).setPositiveButton("Yes", dialogClickListener)
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
                questionid = "3";
                try {
                    for (int s = 0; s < formarray.length(); s++) {
                        if (formarray.getJSONObject(s).getString("form_id").equalsIgnoreCase("")) {
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(s).getString("questions"));
                            for (int k = 0; k < questionsarray.length(); k++) {
                                if (questionsarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(questionid)) {
                                    questionsarray.getJSONObject(k).put("answer_id", edt_Otherremarks.getText().toString());
                                    questionsarray.getJSONObject(k).put("answer", edt_Otherremarks.getText().toString());
                                    questionsarray.getJSONObject(k).put("is_other", "");

                                }
                            }
                            formarray.getJSONObject(s).put("questions", questionsarray);
                        }
                    }
                }catch(Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }

                int errorcount = 0;

                try {
                    if(formarray.length()>2) {
                        JSONArray jarray = new JSONArray();
                        for (int k = 0; k < 2; k++) {
                            jarray.put(formarray.getJSONObject(k));
                        }
                        formarray = new JSONArray();
                        formarray = jarray;
                    }
                for (int k = 0; k < formarray.length(); k++) {
                    if (formarray.getJSONObject(k).getString("form_id").equalsIgnoreCase("")) {
                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                        for (int i = 0; i < questionsarray.length(); i++) {

                            if (questionsarray.getJSONObject(i).getString("is_required").toString().equalsIgnoreCase("1")) {
                                if (questionsarray.getJSONObject(i).getString("answer").toString().equalsIgnoreCase("") || questionsarray.getJSONObject(i).getString("answer").toString().equalsIgnoreCase("Select")) {
                                    errorcount++;
                                }
                            }
                        }
                    }
                }

                if (errorcount > 0)
                    Toast.makeText(VisitDetails.this, "Please Select/Fill All Mandatory Fields.", Toast.LENGTH_LONG).show();
                else {
                    MyApplication.getInstance().setFinalobj(formarray);
                    if (redirect == true) {
                        Intent i = new Intent(VisitDetails.this, SurveyForm.class);
                        startActivityForResult(i, 500);
                        MyApplication.getInstance().setDonetype(String.valueOf(redirect));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        if(spn_reasonfornotmerchandising.getSelectedItem().toString().equalsIgnoreCase("Select")|| spn_reasonfornotmerchandising.getSelectedItem().toString().equalsIgnoreCase(""))
                            Toast.makeText(VisitDetails.this, "Please Select/Fill All Mandatory Fields.", Toast.LENGTH_LONG).show();
                        else {
                            Intent i = new Intent(VisitDetails.this, SurveyForm.class);
                            startActivityForResult(i, 500);
                            MyApplication.getInstance().setDonetype(String.valueOf(redirect));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }
                }
                }catch(Exception ex)
                {
                    Log.e("Json Exception",ex.getMessage().toString());
                }
            }
        });


    }

}