package com.mapolbs.vizibeebritannia.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by VENKATESAN on 3/28/2017.
 */
public class DBAdapter {
    public static final String loginTable="Logindetails";
    static final String collogindate="logindate";
    static final String colloginuser="loginuser";
    static final String StringTable="Stringdetails";
    static final String OfflinetableTable="Offlinedetails";
    static final String colStringdata="stringdata";
    static final String OfflineImageTable="OfflineImagedetails";
    static final String colsurveyid="surveyid";
    static final String RetailersTable="Retailersdetails";

    static final String colquestionid="imagequeid";
    static final String colhttplink="httplink";
    static final String collocalurl="localurl";

    static final String colretailercode="retailercode";
    static final String colprojectcode="projectcode";

    SQLiteDatabase mDb;
    Context mCtx;
    DBHelper mDbHelper;

    public DBAdapter(Context context)
    {
        this.mCtx = context;
    }

    public DBAdapter open() throws SQLException
    {
        mDbHelper = new DBHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public long logindate(String logindate,String username)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(collogindate, logindate);
        initialValues.put(colloginuser, username);
        return mDb.insert(loginTable, null, initialValues);
    }

    public long jsondata(String jsonstring)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(colStringdata, jsonstring);
        return mDb.insert(StringTable, null, initialValues);
    }

    public void deletelogindate()
    {
        mDb.delete(loginTable,null,null);
    }

    public void deletejsondata()
    {
        mDb.delete(StringTable,null,null);
    }

    public long insertdata(String data)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(colStringdata, data);
        return mDb.insert(OfflinetableTable, null, initialValues);
    }

    public long deletedata(String surveyid)
    {
        return mDb.delete(OfflinetableTable,"surveyid="+surveyid+"", null);
    }


    public void deletedofflineimagedata()
    {
        mDb.delete(OfflineImageTable,null,null);
    }


    public long insertimagedata(String questionid,String httplink,String localurl)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(colquestionid, questionid);
        initialValues.put(colhttplink, httplink);
        initialValues.put(collocalurl, localurl);
        return mDb.insert(OfflineImageTable, null, initialValues);
    }

    public String getimage(String uniqueid) {
        String url="";
        Cursor c = mDb.rawQuery("SELECT localurl FROM OfflineImagedetails WHERE imagequeid ='" + uniqueid + "'", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    url = c.getString(c.getColumnIndex("localurl"));
                } while (c.moveToNext());
            }


        }
        return url;
    }
    public String getloginusername() {
        String username="";
        try {
            Cursor c = mDb.rawQuery("SELECT loginuser FROM Logindetails ", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        username = c.getString(c.getColumnIndex("loginuser"));
                    } while (c.moveToNext());
                }


            }
            return username;
        }catch (Exception ex){
            Log.e("getloginusername",ex.getMessage());
            return username;
        }

    }

    public long insertretailer(String retailercode,String projectcode)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(colretailercode, retailercode);
        initialValues.put(colprojectcode, projectcode);
        return mDb.insert(RetailersTable, null, initialValues);
    }

    public long deleteretailer()
    {
        return mDb.delete(RetailersTable,null, null);
    }
}
