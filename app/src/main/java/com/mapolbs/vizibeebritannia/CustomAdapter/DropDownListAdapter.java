package com.mapolbs.vizibeebritannia.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mapolbs.vizibeebritannia.Model.SimpleDropDownData;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DropDownListAdapter extends BaseAdapter {

	private ArrayList<SimpleDropDownData> mListItems;
	private LayoutInflater mInflater;
	private TextView txtid;
	private TextView txtdeptid;
	private TextView txtselectcount;
	private TextView mSelectedItems;
	private static int selectedCount = 0;
	private static String firstSelected = "";
	private ViewHolder holder;
	boolean[] checkedposition;

	int uniqueid=0;
	private static String selected = "";	//shortened selected values representation
	JSONArray formarray = MyApplication.getInstance().getFinalobj();
	public static String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		DropDownListAdapter.selected = selected;
	}

	public DropDownListAdapter(Context context, ArrayList<SimpleDropDownData> items,
			TextView tv,boolean[] checked,TextView txtid,TextView txtdeptid,TextView txtselectcount) {
		mListItems = new ArrayList<SimpleDropDownData>();
		mListItems.addAll(items);
		mInflater = LayoutInflater.from(context);
		mSelectedItems = tv;
		checkedposition = checked;
		this.txtid = txtid;
		this.txtdeptid = txtdeptid;
		this.txtselectcount = txtselectcount;
		selectedCount = Integer.parseInt(this.txtselectcount.getText().toString());

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.multispinner_ddlist_row, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.SelectOption);
			holder.id = (TextView) convertView.findViewById(R.id.txtid);
			holder.deptid = (TextView) convertView.findViewById(R.id.txtdeptid);
			holder.chkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			holder.chkbox.setButtonDrawable(R.drawable.checkbtn_icon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv.setText(mListItems.get(position).getName());
		holder.id.setText(mListItems.get(position).getId());
		holder.deptid.setText(mListItems.get(position).getChildid());

		final int position1 = position;

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				CheckBox chk =  (CheckBox) view.findViewById(R.id.checkbox);
				if(chk.isChecked())
					chk.setChecked(false);
				else
				    chk.setChecked(true);
				setText(position1);
			}
		});
		
		//whenever the checkbox is clicked the selected values textview is updated with new selected values
		holder.chkbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setText(position1);
			}
		});

		if(checkedposition[position])
			holder.chkbox.setChecked(true);
		else
			holder.chkbox.setChecked(false);	 
		return convertView;
	}


	/*
	 * Function which updates the selected values display and information(checkSelected[])
	 * */
	private void setText(int position1){
		JSONArray ansarray = new JSONArray();
		if (!checkedposition[position1]) {
			checkedposition[position1] = true;
			selectedCount++;
		} else {
			checkedposition[position1] = false;
			selectedCount--;
		}

		if (selectedCount == 0) {
			mSelectedItems.setText(R.string.select_string);
		} else if (selectedCount == 1) {
			for (int i = 0; i < checkedposition.length; i++) {
				if (checkedposition[i] == true) {
					firstSelected = mListItems.get(i).getName();
					break;
				}
			}
			mSelectedItems.setText(firstSelected);
			setSelected(firstSelected);
		} else if (selectedCount > 1) {
			for (int i = 0; i < checkedposition.length; i++) {
				if (checkedposition[i] == true) {
					firstSelected = mListItems.get(i).getName();
					break;
				}
			}

			mSelectedItems.setText(firstSelected + " & "+ (selectedCount - 1) + " more");
			setSelected(firstSelected + " & "+ (selectedCount - 1) + " more");

		}
		txtselectcount.setText(String.valueOf(selectedCount));

		String ids="";
		String deptids="";
		for (int i = 0; i < checkedposition.length; i++) {
			if (checkedposition[i] == true ) {
				try {
					JSONObject jobj = new JSONObject();
					jobj.put("answer_id", mListItems.get(i).getId());
					jobj.put("answer", mListItems.get(i).getName());
					ansarray.put(jobj);
				}catch (Exception ex){

				}
				if(!mListItems.get(i).getId().equalsIgnoreCase(""))
				ids += mListItems.get(i).getId()+",";
				if(!mListItems.get(i).getChildid().equalsIgnoreCase(""))
				deptids += mListItems.get(i).getChildid()+",";
			}
		}
		txtid.setText(ids.length()>0 ? ids.substring(0,ids.length()-1):"");
		txtdeptid.setText(deptids.length()>0 ? deptids.substring(0,deptids.length()-1): "");

		String uniqueid =  mSelectedItems.getTag().toString();

		try {

			for (int k = 0; k < formarray.length(); k++) {
				JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
				for (int l = 0; l < questionsarray.length(); l++) {
					if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(uniqueid)) {

						questionsarray.getJSONObject(l).put("answer_id", "");
						if(!ansarray.toString().equalsIgnoreCase("[]"))
						questionsarray.getJSONObject(l).put("answer",ansarray);
						else
							questionsarray.getJSONObject(l).put("answer","");

					}
				}
				formarray.getJSONObject(k).put("questions", questionsarray);
			}
		} catch (Exception ex) {
			Log.e("Json exception", ex.getMessage().toString());
		}

	}

	private class ViewHolder {
		TextView tv;
		TextView id;
		TextView deptid;
		CheckBox chkbox;
		
	}
}
