package com.mapolbs.vizibeebritannia.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mapolbs.vizibeebritannia.CustomAdapter.NotificationAdapter;
import com.mapolbs.vizibeebritannia.Model.NotificationClass;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.ConnectionDetector;
import com.mapolbs.vizibeebritannia.Utilities.EndlessScrollListener;
import com.mapolbs.vizibeebritannia.Utilities.FCMConfig;
import com.mapolbs.vizibeebritannia.Utilities.MultipartUtility;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;
import com.mapolbs.vizibeebritannia.Utilities.NotificationUtils;
import com.mapolbs.vizibeebritannia.Utilities.UserSessionManager;
import com.mapolbs.vizibeebritannia.Utilities.VerticalLineDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationScreen extends AppCompatActivity implements EndlessScrollListener.OnLoadMoreCallback{


    ConnectionDetector cd;
    ProgressDialog pDialog;
    int status =0;
    UserSessionManager session;
    final Context context = this;
    String ipaddress;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private int currentPage = 1;
    private int itemperpage = 0;
    ArrayList<NotificationClass> data;
    private static final int VISIBLE_THRESHOLD = 3;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BroadcastReceiver mRegReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_screen);
        init();
        mRegReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase(FCMConfig.PUSH_NOTIFICATION)) {
                    String count = intent.getStringExtra("count");
                    String datas = intent.getStringExtra("datas");
                    try {

                        JSONObject json =new JSONObject(datas);
                        JSONObject dataobj = json.getJSONObject("data");
                        ArrayList<NotificationClass> newdata = new ArrayList<>();
                        NotificationClass newdataset =new NotificationClass("1",dataobj.getString("title"),dataobj.getString("message"),dataobj.getString("timestamp"),dataobj.getString("image"));
                        newdata.add(new NotificationClass("id",dataobj.getString("title"),dataobj.getString("message"),dataobj.getString("timestamp"),dataobj.getString("image")));
                        mAdapter.setnewList(newdataset,data);
                        FCMConfig.NOTIFICATION_COUNT=0;
                        //mRecyclerView.scrollToPosition(0);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                }
            }

        };
    }

    private void init() {
        try {

            session = new UserSessionManager(getApplicationContext());
            cd = new ConnectionDetector(getApplicationContext());
            HashMap<String, String> ipadd = session.getipDetails();
            ipaddress = ipadd.get(UserSessionManager.IP_CONNECTIONSTRING);

            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            mAdapter = new NotificationAdapter(NotificationScreen.this);
            mRecyclerView.setAdapter(mAdapter);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    data.clear();
                    if (cd.isConnectingToInternet())
                    new PostDataTask().execute("1");
                    else
                        Toast.makeText(NotificationScreen.this, "No Internet Connection", Toast.LENGTH_SHORT).show();



                }
            });
            final GridLayoutManager lamanage = new GridLayoutManager(this,1);
            mRecyclerView.setLayoutManager(lamanage);
            mRecyclerView.addItemDecoration(new VerticalLineDecorator(2));

            data = new ArrayList<NotificationClass>();
            mRecyclerView.addOnScrollListener(new EndlessScrollListener(lamanage) {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    final int firstVisibleItem = lamanage.findFirstVisibleItemPosition();
                    final int lastVisibleItem = lamanage.findLastVisibleItemPosition()+1;
                    final int totalItemCount = lamanage.getItemCount();
                    if((itemperpage*currentPage) == lastVisibleItem) {
                        currentPage++;
                        if (cd.isConnectingToInternet())
                            new PostDataTask().execute(String.valueOf(currentPage));
                        else
                            Toast.makeText(NotificationScreen.this, "No Internet Connection", Toast.LENGTH_SHORT).show();


                    }
                }


            });
            /*mRecyclerView.setOnScrollListener(new EndlessScrollListener(VISIBLE_THRESHOLD) {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    currentPage++;
                    //new PostDataTask().execute(String.valueOf(currentPage));
                }


            });*/
            if (cd.isConnectingToInternet())
                new PostDataTask().execute(String.valueOf(currentPage));
            else
                Toast.makeText(NotificationScreen.this, "No Internet Connection", Toast.LENGTH_SHORT).show();





        }catch (Exception ex){
            Log.e("NotificationScreeninit",ex.getMessage());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegReceiver,
                new IntentFilter(FCMConfig.PUSH_NOTIFICATION));


        if (FCMConfig.NOTIFICATION_COUNT>0) {
            NotificationUtils.clearNotifications(getApplicationContext());
            FCMConfig.NOTIFICATION_COUNT=0;
        }
        if (MyApplication.getInstance().getProject_notificationcount()>0)
            MyApplication.getInstance().setProject_notificationcount(0);
        // clear the notification area when the app is opened

    }


    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegReceiver);
    }
    @Override
    public void onLoadMore(int page, int totalItemsCount) {

    }


    private class PostDataTask extends AsyncTask<String, Integer, String> {
        int statusCode=0;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(NotificationScreen.this,R.style.MyAlertDialogStyle);
            pDialog.setMessage("Loading... Please Wait...");
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
        protected String doInBackground(String... pageno) {
            Log.e("PostDataTask : ", "doInBackground");

            String jsonStr = uploadFile(pageno[0]);

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
                MultipartUtility client = new MultipartUtility(ipaddress + "message_details?page="+jsonString, charset);
                client.addFormField("project_id", MyApplication.getInstance().getProjectid());

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
            if(mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
            NotificationScreen.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try{
                        //JSONObject jobj = new JSONObject(result);

                        if (statusCode == 200) {
                            JSONObject jObject = new JSONObject(result);
                            String total =jObject.getString("total").toString();
                            itemperpage =Integer.parseInt(jObject.getString("per_page").toString());
                            currentPage =Integer.parseInt(jObject.getString("current_page").toString());
                            String last_page =jObject.getString("last_page").toString();
                            String next_page_url =jObject.getString("next_page_url").toString();
                            String prev_page_url =jObject.getString("prev_page_url").toString();
                            String from =jObject.getString("from").toString();
                            String to =jObject.getString("to").toString();

                            JSONArray dataarray = new JSONArray(jObject.getString("data"));
                            for (int i = 0; i < dataarray.length(); i++){
                                String id =dataarray.getJSONObject(i).get("id").toString();
                                String project_id =dataarray.getJSONObject(i).get("project_id").toString();
                                String title =dataarray.getJSONObject(i).get("title").toString();
                                String description =dataarray.getJSONObject(i).get("description").toString();
                                String attachment =dataarray.getJSONObject(i).get("image").toString();
                                String created_at =dataarray.getJSONObject(i).get("created_at").toString();

                                data.add(new NotificationClass(id,title,description,created_at,attachment));

                            }

                            mAdapter.setNotificationList(data);



                        }else if(statusCode == 400)
                        {
                            try {
                                pDialog.setProgress(0);
                                JSONObject jObject = new JSONObject(result);
                                Toast.makeText(NotificationScreen.this, jObject.getString("status").toString(), Toast.LENGTH_LONG).show();
                            } catch (Exception ex) {
                                Log.e("Login Log", ex.getMessage().toString());
                            }
                        }
                        else {
                            pDialog.setProgress(0);
                            Toast.makeText(NotificationScreen.this, "Failed to upload "+result+" Status code="+statusCode,
                                    Toast.LENGTH_LONG).show();



                        }
                    }catch(Exception ex){
                        Log.e(" Exception",ex.getMessage().toString());
                    }
                }
            });

        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static class Itemholder extends RecyclerView.ViewHolder {
        TextView txt_nottitle;
        TextView txt_notmessage;
        TextView txt_nottime;
        CardView card_notiimg;
        ImageView img_notibanner;
        public Itemholder(View itemView) {
            super(itemView);
            txt_nottitle = (TextView) itemView.findViewById(R.id.txt_nottitle);
            txt_notmessage = (TextView) itemView.findViewById(R.id.txt_notmessage);
            txt_nottime = (TextView) itemView.findViewById(R.id.txt_nottime);
            card_notiimg = (CardView) itemView.findViewById(R.id.card_notiimg);
            img_notibanner = (ImageView) itemView.findViewById(R.id.img_notibanner);
        }
    }
}

