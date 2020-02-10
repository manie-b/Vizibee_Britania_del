package com.mapolbs.vizibeebritannia.CustomAdapter;

/**
 * Created by RAMMURALI on 4/12/2017.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mapolbs.vizibeebritannia.Activity.MapScreen;
import com.mapolbs.vizibeebritannia.Model.DropDownData;
import com.mapolbs.vizibeebritannia.Model.GridFromLayoutdata;
import com.mapolbs.vizibeebritannia.Model.ListClass;
import com.mapolbs.vizibeebritannia.Model.SimpleDropDownData;
import com.mapolbs.vizibeebritannia.Model.SimpleListClass;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.MultiSelectionSpinner;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class GridFormLayoutAdapter extends ArrayAdapter<GridFromLayoutdata> {
    Context context;
    ViewHolder holder = null;
    int layoutResourceId;
    String srt;
    static SimpleListAdapter adapter = null;
    String id;
    AlertDialog alert;
    int count=0;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    String retailerid;
    JSONArray questionarray;
    private int TAKE_PHOTO_CODE = 0;
    ImageView imgsource;
    ArrayList<DropDownData> Dropdownlist;
    ArrayList<SimpleDropDownData> SimpleDropdownlist;
    ArrayList<GridFromLayoutdata> offlist = new ArrayList<GridFromLayoutdata>();
    JSONObject questionobj;
    int resID=0;
    int camerabar = 0;
    TextView maptextview;
    int rowcount = 0;

    public GridFormLayoutAdapter(Context context, int layoutResourceId,
                                 ArrayList<GridFromLayoutdata> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.offlist = data;
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        retailerid = MyApplication.getInstance().getRetailerid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final GridFromLayoutdata parameter = offlist.get(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.txtquestion = (TextView) row.findViewById(R.id.txtquestion);
            holder.txtquestionid = (TextView) row.findViewById(R.id.txtquestionid);
            holder.lilayout=(LinearLayout) row.findViewById(R.id.lilayout);
            try {
                JSONArray quejarray = parameter.getValues();
                questionarray = new JSONArray();
                for (int j = 0; j < quejarray.length(); j++) {
                    questionobj = new JSONObject();
                    questionobj.put("question_id", quejarray.getJSONObject(j).getString("id").toString());
                    questionobj.put("question_type", quejarray.getJSONObject(j).getString("type").toString());
                    questionobj.put("is_required", quejarray.getJSONObject(j).getString("is_required").toString());
                    controls("empty", holder.lilayout, "", "", 0, "", "", "", "", "", "", "", "");
                    controls(quejarray.getJSONObject(j).getString("type").toString(), holder.lilayout, "", quejarray.getJSONObject(j).has("values") ? quejarray.getJSONObject(j).getString("values").toString() : "", j + 1, quejarray.getJSONObject(j).has("target") ? quejarray.getJSONObject(j).getString("target").toString() : "", quejarray.getJSONObject(j).getString("id").toString(), quejarray.getJSONObject(j).has("validation") ? quejarray.getJSONObject(j).getString("validation").toString() : "", quejarray.getJSONObject(j).has("from_lable") ? quejarray.getJSONObject(j).getString("from_lable").toString() : "", quejarray.getJSONObject(j).has("to_lable") ? quejarray.getJSONObject(j).getString("to_lable").toString() : "", quejarray.getJSONObject(j).has("from_to_number") ? quejarray.getJSONObject(j).getString("from_to_number").toString() : "", quejarray.getJSONObject(j).has("row_values") ? quejarray.getJSONObject(j).getString("row_values").toString() : "", quejarray.getJSONObject(j).has("column_values") ? quejarray.getJSONObject(j).getString("column_values").toString() : "");
                }
            /*formobj.put("questions", questionarray);
            formarray.put(formobj);*/
                controls("empty", holder.lilayout, "", "", 0, "", "", "", "", "", "", "", "");


                setRadioViewHeightBasedOnItems(holder.lilayout);



            }catch (Exception ex)
            {
                Log.e("Grid form layout error",ex.getMessage().toString());
            }


            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        holder.txtquestion.setText(parameter.getQuestionname());
        holder.txtquestionid.setText(parameter.getQuestionid());


        return row;

    }



    static class ViewHolder {
        TextView txtquestion;
        TextView txtquestionid;
        LinearLayout lilayout;
    }

    static boolean setRadioViewHeightBasedOnItems(LinearLayout lilayout) {


        if (adapter != null) {

            int numberOfItems = adapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = adapter.getView(itemPos, null, lilayout);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = lilayout.getMeasuredHeight()*
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = lilayout.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            lilayout.setLayoutParams(params);
            lilayout.requestLayout();

            return true;

        } else {
            return false;
        }

    }


    public void controls(String type,final LinearLayout tl,String title,String values,final int id,final String targetid,String questionid,String validation,String fromlabel,String tolabel,String fromtonumber,String rowvalues,String columnvalues)
    {
        try {
            final TableRow row = new TableRow(context);
            Dropdownlist = new ArrayList<DropDownData>();
            SimpleDropdownlist = new ArrayList<SimpleDropDownData>();

            if (type.equalsIgnoreCase("title")) {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 90);
                params.setMargins(28, 0, 20, 0);
                row.setLayoutParams(params);
                LinearLayout li = new LinearLayout(context);
                li.setOrientation(LinearLayout.HORIZONTAL);
                TextView txtheader = new TextView(context);
                TextView txtmandatory = new TextView(context);
                txtheader.setGravity(Gravity.LEFT);
                txtheader.setPadding(10,0,0,0);
                txtheader.setTextSize(17);
                txtheader.setTypeface(Typeface.DEFAULT_BOLD);
                txtheader.setText(title);
                txtmandatory.setGravity(Gravity.LEFT);
                txtmandatory.setPadding(10,0,0,0);
                txtmandatory.setTextSize(17);
                txtmandatory.setTypeface(Typeface.DEFAULT_BOLD);
                li.addView(txtheader);
                if(questionid.equalsIgnoreCase("1")) {
                    txtmandatory.setText("*");
                    txtmandatory.setTextColor(Color.parseColor("#D50000"));
                    li.addView(txtmandatory);
                }



                row.addView(li);

            }
            else if (type.equalsIgnoreCase("empty")) {
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
                TextView txtheader = new TextView(context);
                row.addView(txtheader);

            }
            else if(type.equalsIgnoreCase("qmtr"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                TextView readonlytext = new TextView(context);
                readonlytext.setTextSize(18);
                readonlytext.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                readonlytext.setPadding(8,2,2,10);
                readonlytext.setLayoutParams(params);
                readonlytext.setBackgroundResource(R.drawable.rounded_border_edittext);
                JSONArray valuearray = new JSONArray(values);
                if(valuearray.length() > 0){
                    for(int i=0;i<valuearray.length();i++)
                    {
                        if(valuearray.getJSONObject(i).getString("code").toString().equalsIgnoreCase(retailerid))
                        {
                            readonlytext.setText(valuearray.getJSONObject(i).getString("name").toString());
                        }
                    }}else
                    readonlytext.setText("");
                row.addView(readonlytext);
            } else if(type.equalsIgnoreCase("qmte"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                EditText readonlytext = new EditText(context);
                readonlytext.setPadding(12,2,2,10);
                readonlytext.setLayoutParams(params);
                //readonlytext.setFocusableInTouchMode(false);
                //readonlytext.setFocusable(false);
                readonlytext.setBackgroundResource(R.drawable.rounded_border_edittext);
                row.addView(readonlytext);
            }else if(type.equalsIgnoreCase("qmda"))
            {

                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                final TextView readonlytext = new TextView(context);
                readonlytext.setPadding(12, 2, 2, 10);
                readonlytext.setTextSize(18);
                readonlytext.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                readonlytext.setLayoutParams(params);
                readonlytext.setBackgroundResource(R.drawable.datepicker_bg);
                final JSONObject validationobj = new JSONObject(validation);
                readonlytext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {

                                readonlytext.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                view.setMaxDate(System.currentTimeMillis() - 1000);

                            }
                        };

                        DatePickerDialog dpDialog = new DatePickerDialog(context, listener, year,
                                month, day);
                        try {
                            if (validationobj.getString("values").toString().equalsIgnoreCase("1"))
                                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                        }
                        catch (Exception ex)
                        {
                            Log.e("Datepicker EX",ex.getMessage().toString());
                        }
                        dpDialog.show();
                    }
                });
                row.addView(readonlytext);

            }
            else if(type.equalsIgnoreCase("qmti"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                final TextView readonlytext = new TextView(context);
                readonlytext.setPadding(12,2,2,10);
                readonlytext.setTextSize(18);
                readonlytext.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                readonlytext.setLayoutParams(params);
                readonlytext.setBackgroundResource(R.drawable.datepicker_bg);
                readonlytext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                readonlytext.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });
                row.addView(readonlytext);
            }
            else if(type.equalsIgnoreCase("qmrb"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(25, 0, 25, 0);
                row.setLayoutParams(params);
                row.setBackgroundResource(R.drawable.rounded_border_edittext);
                JSONArray valuearray = new JSONArray(values);
                RadioGroup radiogroup = new RadioGroup(context);
                radiogroup.setOrientation(RadioGroup.VERTICAL);
                for(int i=0;i<valuearray.length();i++)
                {

                    RadioButton radiobtn = new RadioButton(context);
                    radiobtn.setText(valuearray.getJSONObject(i).getString("title").toString());
                    radiobtn.setPadding(12,2,2,10);
                    radiobtn.setTextSize(18);
                    radiobtn.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                    radiogroup.addView(radiobtn);
                }

                row.addView(radiogroup);

            }
            else if(type.equalsIgnoreCase("qmtn"))
            {

                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                final JSONObject validationobj = new JSONObject(validation);
                EditText numbertext = new EditText(context);
                numbertext.setPadding(12,2,2,10);
                numbertext.setLayoutParams(params);
                if(validationobj.getString("values").toString().equalsIgnoreCase("0"))
                    numbertext.setInputType(InputType.TYPE_CLASS_NUMBER);
                else
                    numbertext.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

                numbertext.setBackgroundResource(R.drawable.rounded_border_edittext);
                row.addView(numbertext);
            }else if (type.equalsIgnoreCase("qmds")) {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                JSONArray valuearray = new JSONArray(values);
                SimpleDropdownlist.add(new SimpleDropDownData("Select","Select",questionid,"",""));
                for(int i=0;i<valuearray.length();i++)
                {
                    SimpleDropdownlist.add(new SimpleDropDownData(valuearray.getJSONObject(i).getString("code"),valuearray.getJSONObject(i).getString("title"),questionid,valuearray.getJSONObject(i).getString("form_id"),""));
                }
                final SimpleDropdownCustomAdapter adapter = new SimpleDropdownCustomAdapter(context, SimpleDropdownlist);
                final Spinner spn = new Spinner(context, Spinner.MODE_DROPDOWN);
                spn.setBackgroundResource(R.drawable.spinner);
                spn.setLayoutParams(params);
                spn.setId(id);
                spn.setAdapter(adapter);

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View arg1,
                                               int pos, long arg3) {
                        try
                        {
                            View view = (View) arg1.getParent();
                            if (view != null) {
                                TextView txtid = (TextView) view.findViewById(R.id.txt_id);
                                TextView txtname = (TextView) view.findViewById(R.id.txt_name);
                                TextView txt_queid = (TextView) view.findViewById(R.id.txt_queid);
                                TextView txt_formid = (TextView) view.findViewById(R.id.txt_formid);
                                for(int k=0;k<questionarray.length();k++)
                                {
                                    if(questionarray.getJSONObject(k).getString("question_id").toString().equalsIgnoreCase(txt_queid.getText().toString()))
                                    {
                                        questionarray.getJSONObject(k).put("answer_id",txtid.getText().toString());
                                        questionarray.getJSONObject(k).put("answer", txtname.getText().toString());
                                        questionarray.getJSONObject(k).put("is_other", "");
                                    }
                                }
                                questionobj.put("answer_id", txtid.getText().toString());
                                questionobj.put("answer", txtname.getText().toString());
                                questionobj.put("is_other", "");

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

                row.addView(spn);
                questionarray.put(questionobj);

            }
            else if (type.equalsIgnoreCase("qmdm")) {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                JSONArray valuearray = new JSONArray(values);
                SimpleDropdownlist.add(new SimpleDropDownData("Select","Select",questionid,"",""));
                ArrayList<String> list = new ArrayList<String>();
                for(int i=0;i<valuearray.length();i++)
                {
                    list.add(valuearray.getJSONObject(i).getString("title"));
                    SimpleDropdownlist.add(new SimpleDropDownData(valuearray.getJSONObject(i).getString("code"),valuearray.getJSONObject(i).getString("title"),questionid,"",""));
                }

                final MultiSelectionSpinner multispinner = new MultiSelectionSpinner(context);
                //multispinner.setBackgroundResource(R.drawable.spinner_default);
                multispinner.setLayoutParams(params);
                multispinner.setId(id);
               // multispinner.setItems(list);
                row.addView(multispinner);
                questionarray.put(questionobj);

            }
            else if(type.equalsIgnoreCase("qmls"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                row.setBackgroundResource(R.drawable.rounded_border_edittext);
                row.setGravity(Gravity.CENTER);
                LinearLayout li = new LinearLayout(context);
                li.setOrientation(LinearLayout.HORIZONTAL);
                li.setLayoutParams(params);
                li.setGravity(Gravity.CENTER);
                TextView txtfromlabel = new TextView(context);
                txtfromlabel.setText(fromlabel=="null"?"":fromlabel);
                li.addView(txtfromlabel);
                android.widget.TableRow.LayoutParams seekbarparams = new android.widget.TableRow.LayoutParams(500, ViewGroup.LayoutParams.WRAP_CONTENT);
                SeekBar seekbar = new SeekBar(context);
                final String[] fromtonum = fromtonumber.split(",");
                seekbar.setMax(Integer.parseInt(fromtonum[1]));
                seekbar.setLayoutParams(seekbarparams);
                final TextView txtlabel = new TextView(context);
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
                    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                        // TODO Auto-generated method stub

                        txtlabel.setText("("+progress+"/"+fromtonum[1]+")");

                    }
                });
                li.addView(seekbar);


                txtlabel.setText("("+seekbar.getProgress()+"/"+fromtonum[1]+")");
                li.addView(txtlabel);
                android.widget.TableRow.LayoutParams tolabelparams = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tolabelparams.setMargins(20, 0, 20, 0);
                TextView txttolabel = new TextView(context);
                txttolabel.setLayoutParams(tolabelparams);
                txttolabel.setText(tolabel=="null"?"":tolabel);
                li.addView(txttolabel);


                row.addView(li);
            }
            else if(type.equalsIgnoreCase("qmgc"))
            {

                android.widget.TableRow.LayoutParams paramss = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                paramss.setMargins(20, 10, 20,10);

                row.setGravity(Gravity.CENTER);
                row.setBackgroundResource(R.drawable.rounded_border_edittext);
                ListView lstv = new ListView(context);
                lstv.setLayoutParams(paramss);
                lstv.setNestedScrollingEnabled(true);
                lstv.setTag(id);
                ArrayList<ListClass> rowvaluelist = new ArrayList<ListClass>();

                ArrayList<SimpleListClass> columnvaluelist = new ArrayList<SimpleListClass>();
                JSONArray colvalarray = new JSONArray(columnvalues);
                for(int i=0;i<colvalarray.length();i++)
                {
                    columnvaluelist.add(new SimpleListClass(colvalarray.getJSONObject(i).getString("code"),colvalarray.getJSONObject(i).getString("title")));
                }

                JSONArray rowvalarray = new JSONArray(rowvalues);
                for(int i=0;i<rowvalarray.length();i++)
                {
                    rowvaluelist.add(new ListClass(rowvalarray.getJSONObject(i).getString("code"),rowvalarray.getJSONObject(i).getString("title"),columnvaluelist,lstv.getTag().toString()));
                }

                ListCustomAdapter adapter = new ListCustomAdapter(context,R.layout.check_list_row,rowvaluelist,"");
                lstv.setAdapter(adapter);
                setListViewHeightBasedOnItems(row,adapter);
                row.addView(lstv);
            }
            else if(type.equalsIgnoreCase("qmgm"))
            {

                android.widget.TableRow.LayoutParams paramss = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                paramss.setMargins(20, 10, 20,10);

                row.setGravity(Gravity.CENTER);
                row.setBackgroundResource(R.drawable.rounded_border_edittext);
                ListView lstv = new ListView(context);
                lstv.setLayoutParams(paramss);
                lstv.setTag(id);
                lstv.setNestedScrollingEnabled(true);

                ArrayList<ListClass> rowvaluelist = new ArrayList<ListClass>();

                ArrayList<SimpleListClass> columnvaluelist = new ArrayList<SimpleListClass>();
                JSONArray colvalarray = new JSONArray(columnvalues);
                for(int i=0;i<colvalarray.length();i++)
                {
                    columnvaluelist.add(new SimpleListClass(colvalarray.getJSONObject(i).getString("code"),colvalarray.getJSONObject(i).getString("title")));
                }

                JSONArray rowvalarray = new JSONArray(rowvalues);
                for(int i=0;i<rowvalarray.length();i++)
                {
                    rowvaluelist.add(new ListClass(rowvalarray.getJSONObject(i).getString("code"),rowvalarray.getJSONObject(i).getString("title"),columnvaluelist,lstv.getTag().toString()));
                }

                MultiCheckListCustomAdapter adapter = new MultiCheckListCustomAdapter(context,R.layout.multicheck_list_row,rowvaluelist,"");
                lstv.setAdapter(adapter);
                setMultiListViewHeightBasedOnItems(row,adapter);
                row.addView(lstv);
            }
            else if(type.equalsIgnoreCase("qmpi"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 0, 20, 0);
                row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                android.widget.TableRow.LayoutParams imgparams = new android.widget.TableRow.LayoutParams(500,500);
                final ImageView img = new ImageView(context);
                img.setBackgroundResource(R.drawable.no_image);
                img.setLayoutParams(imgparams);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAKE_PHOTO_CODE = id;
                        MyApplication.getInstance().setImgsource(img);
                        MyApplication.getInstance().setCamerabar(1);
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        ((Activity) context).startActivityForResult(cameraIntent, id);
                    }
                });

                row.addView(img);
            }
            else if(type.equalsIgnoreCase("qmbc"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 10, 20,10);
                row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundResource(R.drawable.rounded_border_edittext);
                LinearLayout li = new LinearLayout(context);
                li.setLayoutParams(params);
                li.setOrientation(LinearLayout.VERTICAL);
                li.setGravity(Gravity.CENTER);
                android.widget.TableRow.LayoutParams imgparams = new android.widget.TableRow.LayoutParams(400,400);
                final TextView txtbarcode = new TextView(context);
                txtbarcode.setTypeface(Typeface.DEFAULT_BOLD);
                txtbarcode.setLayoutParams(params);
                txtbarcode.setGravity(Gravity.CENTER);
                final ImageView img = new ImageView(context);
                img.setBackgroundResource(R.drawable.barcode);
                img.setLayoutParams(imgparams);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TAKE_PHOTO_CODE = id;
                        MyApplication.getInstance().setTxtsource(txtbarcode);
                        MyApplication.getInstance().setCamerabar(2);
                        try{
                            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                            intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,RSS_14,UPC_A,UPC_E,QR_CODE");
                            ((Activity)context).startActivityForResult(intent, 0);	//Barcode Scanner to scan for us
                        }catch (ActivityNotFoundException e) {
                            // TODO: handle exception
                            showDialog(((Activity)context), "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                        }


                    }
                });
                li.addView(img);
                li.addView(txtbarcode);
                row.addView(li);
            }
            else if(type.equalsIgnoreCase("qmgt"))
            {
                android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 10, 20,10);
                row.setLayoutParams(params);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundResource(R.drawable.rounded_border_edittext);
                LinearLayout li = new LinearLayout(context);
                li.setLayoutParams(params);
                li.setOrientation(LinearLayout.VERTICAL);
                li.setGravity(Gravity.CENTER);
                android.widget.TableRow.LayoutParams imgparams = new android.widget.TableRow.LayoutParams(200,200);
                final TextView txtbarcode = new TextView(context);
                txtbarcode.setTypeface(Typeface.DEFAULT_BOLD);
                txtbarcode.setLayoutParams(params);
                txtbarcode.setGravity(Gravity.CENTER);
                final ImageView img = new ImageView(context);
                img.setBackgroundResource(R.drawable.map);
                img.setLayoutParams(imgparams);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maptextview = txtbarcode;
                        Intent i = new Intent(context,MapScreen.class);
                        context.startActivity(i);

                    }
                });
                Button btnsetlocation = new Button(context);
                btnsetlocation.setLayoutParams(params);
                btnsetlocation.setGravity(Gravity.CENTER);
                btnsetlocation.setText("Set Location");
                btnsetlocation.setBackgroundColor(Color.parseColor("#FF5722"));
                btnsetlocation.setTextColor(Color.parseColor("#ffffff"));
                btnsetlocation.setAllCaps(false);
                btnsetlocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyApplication.getInstance().getMapaddress() != null)
                            maptextview.setText(MyApplication.getInstance().getMapaddress());
                    }
                });

                li.addView(img);
                li.addView(txtbarcode);
                li.addView(btnsetlocation);
                row.addView(li);
            }

            tl.addView(row);
        }
        catch (Exception ex)
        {
            Log.e("Error",ex.getMessage().toString());
        }
    }

    static boolean setListViewHeightBasedOnItems(TableRow row,ListCustomAdapter adapter) {


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
            int totalDividersHeight = row.getMeasuredHeight()*
                    (numberOfItems - 1);

            // Set list height.
            android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, totalItemsHeight + totalDividersHeight);
            params.setMargins(20, 10, 20,10);
            row.setLayoutParams(params);
            row.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    static boolean setMultiListViewHeightBasedOnItems(TableRow row,MultiCheckListCustomAdapter adapter) {


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
            int totalDividersHeight = row.getMeasuredHeight()*
                    (numberOfItems - 1);

            // Set list height.
            android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, totalItemsHeight + totalDividersHeight);
            params.setMargins(20, 10, 20,10);
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

        androidx.appcompat.app.AlertDialog.Builder download = new androidx.appcompat.app.AlertDialog.Builder(act);
        download.setTitle(title);
        download.setMessage(message);
        download.setPositiveButton(Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i){
                // TODO Auto-generated method stub
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent in = new Intent(Intent.ACTION_VIEW, uri);
                try{
                    act.startActivity(in);
                }catch(ActivityNotFoundException anfe){

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

}