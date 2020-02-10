package com.mapolbs.vizibeebritannia.CustomAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mapolbs.vizibeebritannia.Model.RetailerListClass;
import com.mapolbs.vizibeebritannia.R;

import java.util.ArrayList;

public class RetailerListAdapter extends ArrayAdapter<RetailerListClass> {
    Context context;
    ViewHolder holder = null;
    int layoutResourceId;
    String srt;
    String id;
    AlertDialog alert;
    int count=0;
    ArrayList<RetailerListClass> offlist = new ArrayList<RetailerListClass>();
    ArrayList<String> retailerlist = new ArrayList<String>();

    public RetailerListAdapter(Context context, int layoutResourceId,
                               ArrayList<RetailerListClass> data,ArrayList<String> retailerlst) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.offlist = data;
        this.retailerlist = retailerlst;
    }

    @Override
    public RetailerListClass getItem(int position) {
        // TODO Auto-generated method stub
        return offlist.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.txtretailercode = (TextView) row.findViewById(R.id.txtretailercode);
            holder.txtretailername = (TextView) row.findViewById(R.id.txtretailername);



            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        if (position%2 == 0) {
            row.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            row.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        final RetailerListClass parameter = offlist.get(position);
        holder.txtretailercode.setText(parameter.getRetailerid());
        holder.txtretailername.setText(parameter.getRetailername());

        if(retailerlist.contains(parameter.getRetailerid()))
        {
            row.setBackgroundColor(Color.parseColor("#C8E6C9"));

        }



        return row;

    }



    static class ViewHolder {
        TextView txtretailercode;
        TextView txtretailername;

    }



}