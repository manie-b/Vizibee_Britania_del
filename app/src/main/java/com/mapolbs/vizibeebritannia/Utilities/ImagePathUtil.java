package com.mapolbs.vizibeebritannia.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;

public class ImagePathUtil {

	private DisplayMetrics mDisplayMetrics;
	private Context mContext;
	private Bitmap mBitmap = null;
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera Demo";
	public static final int MEDIA_TYPE_IMAGE = 1;
	private String imagePath;
	private Uri fileUri;

	public ImagePathUtil(Context context) {
		mContext = context;
	}


	public Bitmap getImageBitmap(String imagePath, String quesId, JSONArray qmpistaticquestarray, Integer ImageSize) {
		try {
			ArrayList<String> qmpistaticquestIds = new ArrayList<String>();
			JSONArray jArray = (JSONArray)qmpistaticquestarray;
			if (jArray != null) {
				for (int i=0;i<jArray.length();i++){
					qmpistaticquestIds.add(jArray.getString(i));
				}
			}
			// Loading Large Bitmap
			LargeBitmap largeBitmap = new LargeBitmap();
			mDisplayMetrics = mContext.getResources().getDisplayMetrics();

			mBitmap = largeBitmap.decodeSampledBitmapFromFile(imagePath,
					mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels);


			mBitmap = rotateBitmap(imagePath, mBitmap);

			if(!qmpistaticquestIds.contains(quesId.trim()))
			{
				saveReducedSizeBitmap(imagePath, mBitmap);
			}
			else
			{
				saveReducedSizeBitmap1200(imagePath, mBitmap,ImageSize);
			}


			// if you want to reduce memory of image use this method.


		} catch (Exception e) {
			Log.e("Exception", String.valueOf(e));
			// Toast.makeText(mContext, "Support File Missing!!!",
			// Toast.LENGTH_LONG).show();
		}

		return mBitmap;
	}

	public Bitmap getImageBitmapForMAC(String imageFile) {
		try {
			fileUri = getOutputMediaFileUri(imageFile);
			imagePath = fileUri.getPath();
			Log.e("imagepath", imagePath);

			// Loading Large Bitmap
			LargeBitmap largeBitmap = new LargeBitmap();
			mDisplayMetrics = mContext.getResources().getDisplayMetrics();
			mBitmap = largeBitmap.decodeSampledBitmapFromFileForMAC(imagePath,
					mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels);

		} catch (Exception e) {
			Log.e("Exception", String.valueOf(e));
			// Toast.makeText(mContext, "Support File Missing!!!",
			// Toast.LENGTH_LONG).show();
		}

		return mBitmap;
	}

	private Uri getOutputMediaFileUri(String imageFile) {
		return Uri.fromFile(getOutputMediaFile(imageFile));
	}

	public static File getOutputMediaFile(String imageFile) {
		// External sdcard location
		File mediaStorageDir = new File(Constants.ROOT_FOLDER + File.separator
				+ Constants.VIZIBEE_IMAGE_FOLDER);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(Constants.VIZIBEE_IMAGE_FOLDER, "Oops! Failed create "
						+ Constants.VIZIBEE_IMAGE_FOLDER + " directory");
				return null;
			}
		}

		File mediaFile;

		//mediaFile = new File(mediaStorageDir.getPath() + File.separator + imageFile);
		mediaFile = new File(imageFile);

		Log.e("mediaFile", String.valueOf(mediaFile));

		if (!mediaFile.exists())
			return null;

		return mediaFile;
	}

	private Bitmap rotateBitmap(String imagePath, Bitmap bitmap) {
		int rotate = 0;
		int orientation;
		Bitmap cropped = null;
		try {
			// File imageFile = new File(imagePath);
			ExifInterface exif = new ExifInterface(imagePath);
			orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/****** Image rotation ****/
		Matrix matrix = new Matrix();
		matrix.postRotate(rotate);
		cropped = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		// bitmap.recycle();
		return cropped;

	}

	private void saveReducedSizeBitmap(String imagePath, Bitmap bitmap) {
		try {
			// Bitmap photo = (Bitmap) "your Bitmap image";
			// bitmap = Bitmap.createScaledBitmap(bitmap, 250, 320, false);
			bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
					bitmap.getHeight(), false);

			// Convert immutable to mutable bitmap
			Bitmap drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			drawableBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


			// mediaFile.createNewFile();
			FileOutputStream fo = new FileOutputStream(new File(imagePath));
			fo.write(bytes.toByteArray());
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void saveReducedSizeBitmap1200(String imagePath, Bitmap bitmap, Integer ImageSize) {
		try {

			final int maxSize = ImageSize;
			int outWidth;
			int outHeight;
			int inWidth = bitmap.getWidth();
			int inHeight = bitmap.getHeight();
			if(inWidth > inHeight){
				outWidth = maxSize;
				outHeight = (inHeight * maxSize) / inWidth;
			} else {
				outHeight = maxSize;
				outWidth = (inWidth * maxSize) / inHeight;
			}

			bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
			// Bitmap photo = (Bitmap) "your Bitmap image";
			// bitmap = Bitmap.createScaledBitmap(bitmap, 250, 320, false);
//			bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
//					bitmap.getHeight(), true);

			// Convert immutable to mutable bitmap
			Bitmap drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			drawableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

			// mediaFile.createNewFile();
			FileOutputStream fo = new FileOutputStream(new File(imagePath));
			fo.write(bytes.toByteArray());
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Uri getOutputMediaFileUri(int mediaTypeImage) {
		return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
	}

	public static File getOutputMediaFile(int type) {
		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}

		return mediaFile;
	}

}
