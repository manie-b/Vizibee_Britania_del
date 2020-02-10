package com.mapolbs.vizibeebritannia.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by VENKATESAN on 3/28/2017.
 */
public class DBHelper extends SQLiteOpenHelper {


     static final String DATABASE_NAME="checklogin.db";
     static final String loginTable="Logindetails";
    static final String colloginuser="loginuser";
     static final String collogindate="logindate";
    static final String StringTable="Stringdetails";
    static final String OfflinetableTable="Offlinedetails";
    static final String OfflineImageTable="OfflineImagedetails";
    static final String colStringdata="stringdata";
    static final String colsurveyid="surveyid";
    static final String RetailersTable="Retailersdetails";

    static final String colquestionid="imagequeid";
    static final String colhttplink="httplink";
    static final String collocalurl="localurl";

    static final String colretailercode="retailercode";
    static final String colprojectcode="projectcode";

    private static final int DATABASE_VERSION = 2;

    private static final String STRING_CREATE = "CREATE TABLE "+loginTable+" ("+collogindate+" TEXT,"+colloginuser+" TEXT)";
    private static final String DATA_CREATE = "CREATE TABLE "+StringTable+" ("+colStringdata+" TEXT)";
    private static final String OFFLINE_DATA_CREATE = "CREATE TABLE "+OfflinetableTable+" ("+colsurveyid+" INTEGER PRIMARY KEY AUTOINCREMENT,"+colStringdata+" TEXT)";

    private static final String OFFLINE_IMAGE_CREATE = "CREATE TABLE "+OfflineImageTable+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            +colquestionid+" TEXT, "+colhttplink+" TEXT,"+collocalurl+" TEXT);";

    private static final String OFFLINE_RETAILER_CREATE = "CREATE TABLE "+RetailersTable+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            +colretailercode+" TEXT, "+colprojectcode+" TEXT);";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(STRING_CREATE);
        db.execSQL(DATA_CREATE);
        db.execSQL(OFFLINE_DATA_CREATE);
        db.execSQL(OFFLINE_IMAGE_CREATE);
        db.execSQL(OFFLINE_RETAILER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Logindetails");
        db.execSQL("DROP TABLE IF EXISTS Stringdetails");
        db.execSQL("DROP TABLE IF EXISTS Offlinedetails");
        db.execSQL("DROP TABLE IF EXISTS OfflineImagedetails");
        db.execSQL("DROP TABLE IF EXISTS Retailersdetails");
        onCreate(db);

        if (newVersion>oldVersion){
            Log.e("db upgrade",String.valueOf(oldVersion)+String.valueOf(newVersion));
            db.execSQL("DROP TABLE IF EXISTS Logindetails");
            db.execSQL("DROP TABLE IF EXISTS Stringdetails");
            db.execSQL("DROP TABLE IF EXISTS Offlinedetails");
            db.execSQL("DROP TABLE IF EXISTS OfflineImagedetails");
            db.execSQL("DROP TABLE IF EXISTS Retailersdetails");
            onCreate(db);
        }
    }
}
