package com.mapolbs.vizibeebritannia.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mapolbs.vizibeebritannia.Model.DropDownData;
import com.mapolbs.vizibeebritannia.R;

import java.util.ArrayList;

/**
 * Created by Mapol on 9/12/2017.
 */

public class Default_DropdownCustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<DropDownData> Statelist;
    LayoutInflater inflter;

    public Default_DropdownCustomAdapter(Context applicationContext, ArrayList<DropDownData> Statelist) {
        this.context = applicationContext;
        this.Statelist = Statelist;
        inflter = ((Activity) context).getLayoutInflater();


    }

    @Override
    public int getCount() {
        return Statelist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.default_dropdown_row, viewGroup, false);
        TextView txtid = (TextView) view.findViewById(R.id.txt_id);
        TextView txtname = (TextView) view.findViewById(R.id.txt_name);
        TextView txt_queid = (TextView) view.findViewById(R.id.txt_queid);
        txtid.setText(Statelist.get(i).getId());
        txtname.setText(Statelist.get(i).getName());
        txt_queid.setText(Statelist.get(i).getQuestionid());
        return view;
    }
}