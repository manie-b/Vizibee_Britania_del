package com.mapolbs.vizibeebritannia.CustomAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mapolbs.vizibeebritannia.Model.GridFilterClass;
import com.mapolbs.vizibeebritannia.R;

import java.util.ArrayList;

public class GridFilterCustomAdapter extends ArrayAdapter<GridFilterClass> {
    Context context;
    ViewHolder holder = null;
    int layoutResourceId;

    ArrayList<GridFilterClass> offlist = new ArrayList<GridFilterClass>();



    public GridFilterCustomAdapter(Context context, int layoutResourceId,
                                   ArrayList<GridFilterClass> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.offlist = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          View row = convertView;


        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.txtname = (TextView) row.findViewById(R.id.txt_name);
            holder.txtcode = (TextView) row.findViewById(R.id.txt_code);


            final GridFilterClass parameter = offlist.get(position);
            holder.txtname.setText(parameter.getCode());
            holder.txtcode.setText(parameter.getName());






            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        if(position%2 == 0)
            row.setBackgroundColor(Color.parseColor("#FFFFFF"));
        else
            row.setBackgroundColor(Color.parseColor("#FBE9E7"));




        return row;

    }



    static class ViewHolder {
        TextView txtname;
        TextView txtcode;
    }



}