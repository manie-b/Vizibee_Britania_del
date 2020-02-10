package com.mapolbs.vizibeebritannia.Utilities;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Utility {

	// directory name to store captured images
	private static final String IMAGE_DIRECTORY_NAME = "Vizibee_Merchandise";



	/**
	 * ------------ Helper Methods ----------------------
	 * */

	/**
	 * Creating file uri to store image/video
	 */
	public static Uri getOutputMediaFileUri(String imagename) {
		return Uri.fromFile(getOutputMediaFile(imagename));
	}

	/**
	 * returning image
	 */
	public static File getOutputMediaFile(String imagename) {
		// External sdcard location
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + imagename + ".jpg");

		return mediaFile;
	}

	public static void deleteOutputMediaFiles() {
		File dir = new File(Environment.getExternalStorageDirectory(),
				IMAGE_DIRECTORY_NAME);
		if (dir.exists()) {
			if (dir.isDirectory()) {
				String[] children = dir.list();
				for (int i = 0; i < children.length; i++) {
					new File(dir, children[i]).delete();
				}
			}
		}
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(Calendar.getInstance().getTime());
		return date;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public static void deleteSpecificOutputMediaFiles(
			Map<String, String> imagePathMap) {
		File dir = new File(Environment.getExternalStorageDirectory(),
				IMAGE_DIRECTORY_NAME);
		if (dir.exists()) {
			if (dir.isDirectory()) {
				Iterator entries = imagePathMap.entrySet().iterator();

				while (entries.hasNext()) {
					Entry thisEntry = (Entry) entries.next();
					Object key = thisEntry.getKey();
					Object value = thisEntry.getValue();
					String filePath = (String) value;
					String imageKey = (String) key;

					// Adding file data to http body
					if (filePath == null || filePath.isEmpty()) {

					} else {
						new File(filePath).delete();
					}
				}
			}
		}
	}

}
