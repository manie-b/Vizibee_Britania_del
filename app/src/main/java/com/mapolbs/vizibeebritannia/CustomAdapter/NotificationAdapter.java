package com.mapolbs.vizibeebritannia.CustomAdapter;

import android.app.Dialog;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.mapolbs.vizibeebritannia.Activity.NotificationScreen;
import com.mapolbs.vizibeebritannia.Model.NotificationClass;
import com.mapolbs.vizibeebritannia.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public  class NotificationAdapter extends RecyclerView.Adapter<NotificationScreen.Itemholder> {
    private List<NotificationClass> mnotificationList;
    private LayoutInflater mInflater;
    private Context mContext;

    public NotificationAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public NotificationScreen.Itemholder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.row_notification, parent, false);
        final NotificationScreen.Itemholder viewHolder = new NotificationScreen.Itemholder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NotificationScreen.Itemholder holder, int position) {
        final NotificationClass notifications = mnotificationList.get(position);


        ((TextView)holder.itemView.findViewById(R.id.txt_nottitle)).setText(notifications.getTitle());
        ((TextView)holder.itemView.findViewById(R.id.txt_notmessage)).setText(notifications.getMessage());
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date date = null;
        try {
            date = inputFormat.parse(notifications.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        ((TextView)holder.itemView.findViewById(R.id.txt_nottime)).setText(outputDateStr);
        String imgurl = notifications.getBannerurl();
        if (notifications.getBannerurl().equalsIgnoreCase(""))
            ((CardView)holder.itemView.findViewById(R.id.card_notiimg)).setVisibility(View.GONE);
        else {
            ((CardView) holder.itemView.findViewById(R.id.card_notiimg)).setVisibility(View.VISIBLE);
            final ImageView img =(ImageView) holder.itemView.findViewById(R.id.img_notibanner);
            img.setTag(notifications.getBannerurl());
            Picasso.with(mContext)
                    .load(notifications.getBannerurl())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(img);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    img.requestFocus();
                    String imgurl = img.getTag().toString();
                    if(img.getDrawable() != null) {
                        final Dialog ViewDialog = new Dialog(mContext);
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
                        Picasso.with(mContext)
                                .load(imgurl)
                                .rotate(90f)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(imgs);
                        Button close = (Button) ViewDialog.findViewById(R.id.btnclose);
                        close.setVisibility(View.GONE);

                        ViewDialog.show();
                    }
                }
            });
        }
       // holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.itemView.setTag(notifications.getId());
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setBackgroundColor(Color.parseColor("#e1e4e4"));


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return (mnotificationList == null) ? 0 : mnotificationList.size();
    }

    public void setNotificationList(List<NotificationClass> NotificationList) {
        this.mnotificationList = new ArrayList<>();
        this.mnotificationList.addAll(NotificationList);
        notifyDataSetChanged();
    }

    public void setnewList(NotificationClass newset,List<NotificationClass> NotificationList) {
        this.mnotificationList = new ArrayList<>();
        this.mnotificationList.add(newset);
        this.mnotificationList.addAll(NotificationList);
        notifyDataSetChanged();

    }
}

