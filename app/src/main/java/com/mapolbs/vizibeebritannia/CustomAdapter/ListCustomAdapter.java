package com.mapolbs.vizibeebritannia.CustomAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mapolbs.vizibeebritannia.Model.ListClass;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListCustomAdapter extends ArrayAdapter<ListClass> {
    Context context;
    ViewHolder holder = null;
    int layoutResourceId;
    String srt;
    String colour;
    static SimpleListAdapter adapter = null;
    String id;
    AlertDialog alert;
    int count=0;
    JSONArray valuesarray;
    ArrayList<ListClass> offlist = new ArrayList<ListClass>();

    JSONArray  formarray = MyApplication.getInstance().getFinalobj();

    public ListCustomAdapter(Context context, int layoutResourceId,
                             ArrayList<ListClass> data,String colour) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.offlist = data;
        this.colour = colour;
        valuesarray = new JSONArray();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          View row = convertView;


        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.txtquestion = (TextView) row.findViewById(R.id.txtquestion);
            holder.txtquestionid = (TextView) row.findViewById(R.id.txtquestionid);
            holder.radiogroup=(RadioGroup) row.findViewById(R.id.radiogroup);


            final ListClass parameter = offlist.get(position);
            holder.txtquestion.setText(parameter.getQuestion());
            holder.txtquestionid.setText(parameter.getQuestionid());
            holder.radiogroup.setTag(Integer.parseInt(parameter.getUniqueid()));

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("row_id", parameter.getQuestionid());
                jobj.put("answer_id", "");
                jobj.put("answer","");
                valuesarray.put(jobj);

            }catch(Exception ex){
                Log.e("Json Exception",ex.getMessage().toString());
            }

            for(int i = 0;i<parameter.getQuestionlist().size();i++) {
                RadioButton radiobtn = new RadioButton(context);
                radiobtn.setText(parameter.getQuestionlist().get(i).getQuestion());
                radiobtn.setId(Integer.parseInt(parameter.getQuestionlist().get(i).getQuestionid()));
                radiobtn.setPadding(12,2,2,10);
                int px = context.getResources().getDimensionPixelSize(R.dimen.visitdetail_answer_textsize);
                float size = px / context.getResources().getDisplayMetrics().density;
                radiobtn.setTextSize(size);
                TypedValue outxValue = new TypedValue();
                context.getResources().getValue(R.dimen.radiobuttonscalex, outxValue, true);
                float xvalue = outxValue.getFloat();
                TypedValue outyValue = new TypedValue();
                context.getResources().getValue(R.dimen.radiobuttonscaley, outyValue, true);
                float yvalue = outyValue.getFloat();
                /*radiobtn.setScaleX(xvalue);
                radiobtn.setScaleY(yvalue);*/
                radiobtn.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                radiobtn.setButtonDrawable(R.drawable.radiobtn_icon);
                holder.radiogroup.addView(radiobtn);
            }
            for(int i=offlist.size();i<valuesarray.length();i++)
            {
                valuesarray.remove(i);
            }

            try {
                for (int k = 0; k < formarray.length(); k++) {
                    JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                    for (int l = 0; l < questionsarray.length(); l++) {
                        if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(holder.radiogroup.getTag().toString())) {
                            questionsarray.getJSONObject(l).put("answer_id", "");
                            questionsarray.getJSONObject(l).put("answer", valuesarray);
                        }
                    }
                    formarray.getJSONObject(k).put("questions",questionsarray);
                }
            }catch(Exception ex){
                Log.e("Json exception",ex.getMessage().toString());
            }

            holder.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()            {

                @Override
                public void onCheckedChanged(RadioGroup arg0, int selectedId) {
                    LinearLayout li = (LinearLayout)arg0.getParent();
                    TextView txtquestionid = (TextView) li.findViewById(R.id.txtquestionid);
                    RadioGroup radiogroup=(RadioGroup) li.findViewById(R.id.radiogroup);
                    selectedId=radiogroup.getCheckedRadioButtonId();
                    RadioButton radiobutton = (RadioButton) li.findViewById(selectedId);
                    try {
                        for (int k = 0; k < formarray.length(); k++) {
                            JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                            for (int l = 0; l < questionsarray.length(); l++) {
                                if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(radiogroup.getTag().toString())) {
                                    JSONArray valuesarray = new JSONArray(questionsarray.getJSONObject(l).getString("answer").toString());
                                    for(int m=0;m<valuesarray.length();m++) {
                                        if (valuesarray.getJSONObject(m).getString("row_id").toString().equalsIgnoreCase(txtquestionid.getText().toString())) {
                                            valuesarray.getJSONObject(m).put("answer_id", selectedId);
                                            valuesarray.getJSONObject(m).put("answer", radiobutton.getText().toString());
                                        }
                                    }
                                    questionsarray.getJSONObject(l).put("answer",valuesarray);
                                }
                            }
                            formarray.getJSONObject(k).put("questions",questionsarray);
                        }
                    }catch(Exception ex){
                        Log.e("Json exception",ex.getMessage().toString());
                    }

                }
            });
            setRadioViewHeightBasedOnItems(holder.radiogroup);



            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }




        return row;

    }



    static class ViewHolder {
        TextView txtquestion;
        TextView txtquestionid;
        RadioGroup radiogroup;
    }

     static boolean setRadioViewHeightBasedOnItems(RadioGroup radiogroup) {


        if (adapter != null) {

            int numberOfItems = adapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = adapter.getView(itemPos, null, radiogroup);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = radiogroup.getMeasuredHeight()*
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = radiogroup.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            radiogroup.setLayoutParams(params);
            radiogroup.requestLayout();

            return true;

        } else {
            return false;
        }

    }


}