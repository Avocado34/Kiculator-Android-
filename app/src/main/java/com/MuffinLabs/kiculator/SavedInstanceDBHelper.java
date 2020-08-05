package com.MuffinLabs.kiculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SavedInstanceDBHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String DATABASE_NAME = "SavedInstanceDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Saved_Instance";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "app_name";
    private static final String COL_3 = "base_fee";
    private static final String COL_4 = "base_time";
    private static final String COL_5 = "fee_per_min";

    public SavedInstanceDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT, " +
                COL_5 + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String appName, String baseFee, String baseTime, String feePerMin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, appName);
        contentValues.put(COL_3, baseFee);
        contentValues.put(COL_4, baseTime);
        contentValues.put(COL_5, feePerMin);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean deleteData(String appName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL_2 + " = ?", new String[]{appName});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean updateData(String appName, String baseFee, String baseTime, String feePerMin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, appName);
        contentValues.put(COL_3, baseFee);
        contentValues.put(COL_4, baseTime);
        contentValues.put(COL_5, feePerMin);

        long result = db.update(TABLE_NAME, contentValues, COL_2 + " = ?", new String[]{appName});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isExists(String appName) {
        SQLiteDatabase db = this.getWritableDatabase();

        boolean isExists = false;

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
            while (cursor.moveToNext()){
                if (cursor.getString(1).equals(appName)){
                    isExists = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return isExists;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
    }

}