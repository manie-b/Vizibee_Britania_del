package com.mapolbs.vizibeebritannia.CustomAdapter;

/**
 * Created by RAMMURALI on 3/31/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mapolbs.vizibeebritannia.Model.SimpleDropDownData;
import com.mapolbs.vizibeebritannia.R;

import java.util.ArrayList;

public class SimpleDropdownCustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<SimpleDropDownData> Statelist;
    LayoutInflater inflter;

    public SimpleDropdownCustomAdapter(Context applicationContext, ArrayList<SimpleDropDownData> Statelist) {
        this.context = applicationContext;
        this.Statelist = Statelist;
        inflter = ((Activity) context).getLayoutInflater();


    }

    @Override
    public int getCount() {
        return Statelist.size();
    }

    @Override
    public SimpleDropDownData getItem(int i) {
        SimpleDropDownData data = Statelist.get(i);

        return data;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.simple_dropdown_row, viewGroup, false);
        TextView txtid = (TextView) view.findViewById(R.id.txt_id);
        TextView txtname = (TextView) view.findViewById(R.id.txt_name);
        TextView txt_queid = (TextView) view.findViewById(R.id.txt_queid);
        TextView txt_formid = (TextView) view.findViewById(R.id.txt_formid);
        TextView txt_childid = (TextView) view.findViewById(R.id.txt_childid);

        txtid.setText(Statelist.get(i).getId());
        txtname.setText(Statelist.get(i).getName());
        txt_queid.setText(Statelist.get(i).getQuestionid());
        txt_formid.setText(Statelist.get(i).getFormidid());
        txt_childid.setText(Statelist.get(i).getChildid());
        return view;
    }
}