package com.mapolbs.vizibeebritannia.Utilities;

import android.os.Environment;

import com.mapolbs.vizibeebritannia.R;

public class Constants {



	public static final String ROOT_FOLDER = Environment
			.getExternalStorageDirectory().toString()
			+ MyApplication.getInstance().getResources()
					.getString(R.string.root_folder);

	public static final String VIZIBEE_IMAGE_FOLDER = MyApplication.getInstance().getString(R.string.vizibee_image_folder);

}
