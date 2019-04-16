package com.example.quang.studenthousing.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.quang.studenthousing.object.City;
import com.example.quang.studenthousing.object.District;
import com.example.quang.studenthousing.object.Ward;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseUtils {
    private static final String PATH = Environment.getDataDirectory().getPath()
            + "/data/com.example.quang.studenthousing/databases/";

    private static final String DB_NAME = "data.sqlite";
    private static final String TABLE_CITY = "devvn_tinhthanhpho";
    private static final String TABLE_DISTRICT = "devvn_quanhuyen";
    private static final String TABLE_WARD = "devvn_xaphuongthitran";


    private static final String MA_TP = "matp";
    private static final String NAME_TP = "name";
    private static final String TYPE_TP = "type";


    private static final String MA_QH = "maqh";
    private static final String NAME_QH = "name";
    private static final String TYPE_QH = "type";
    private static final String MA_TP_QH = "matp";


    private static final String XA_ID = "xaid";
    private static final String NAME_XA = "name";
    private static final String TYPE_XA = "type";
    private static final String MA_QH_XA = "maqh";



    private Context context;
    private SQLiteDatabase database;

    public DatabaseUtils(Context context) {
        this.context = context;
        copyFileToDevice();
    }

    private void copyFileToDevice() {
        File file = new File(PATH + DB_NAME);
        if (!file.exists()) {
            File parent = file.getParentFile();
            parent.mkdirs();
            try {
                InputStream inputStream = context.getAssets().open(DB_NAME);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int count = inputStream.read(b);
                while (count != -1) {
                    outputStream.write(b, 0, count);
                    count = inputStream.read(b);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    private void closeDatabase() {
        database.close();
    }

    public ArrayList<City> getCity() {
        ArrayList<City> arr = new ArrayList<>();
        openDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_CITY, null, null, null, null, null, null);
        cursor.moveToFirst();

        int indexMa = cursor.getColumnIndex(MA_TP);
        int indexName = cursor.getColumnIndex(NAME_TP);
        int indexType = cursor.getColumnIndex(TYPE_TP);

        while (!cursor.isAfterLast()) {
            String ma = cursor.getString(indexMa);
            String name = cursor.getString(indexName);
            String type = cursor.getString(indexType);
            City city = new City(ma,name,type);
            arr.add(city);
            cursor.moveToNext();
        }
        closeDatabase();
        return arr;
    }

    public ArrayList<District> getDistrict() {
        ArrayList<District> arr = new ArrayList<>();
        openDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_DISTRICT, null, null, null, null, null, null);
        cursor.moveToFirst();

        int indexMa = cursor.getColumnIndex(MA_QH);
        int indexName = cursor.getColumnIndex(NAME_QH);
        int indexType = cursor.getColumnIndex(TYPE_QH);
        int indexMaCity = cursor.getColumnIndex(MA_TP_QH);

        while (!cursor.isAfterLast()) {
            String ma = cursor.getString(indexMa);
            String name = cursor.getString(indexName);
            String type = cursor.getString(indexType);
            String maCity = cursor.getString(indexMaCity);
            District d = new District(ma,name,type,maCity);
            arr.add(d);
            cursor.moveToNext();
        }
        closeDatabase();
        return arr;
    }

    public ArrayList<Ward> getWard() {
        ArrayList<Ward> arr = new ArrayList<>();
        openDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_WARD, null, null, null, null, null, null);
        cursor.moveToFirst();

        int indexMa = cursor.getColumnIndex(XA_ID);
        int indexName = cursor.getColumnIndex(NAME_XA);
        int indexType = cursor.getColumnIndex(TYPE_XA);
        int indexMaDistrict = cursor.getColumnIndex(MA_QH_XA);

        while (!cursor.isAfterLast()) {
            String ma = cursor.getString(indexMa);
            String name = cursor.getString(indexName);
            String type = cursor.getString(indexType);
            String maDistrict = cursor.getString(indexMaDistrict);
            Ward d = new Ward(ma,name,type,maDistrict);
            arr.add(d);
            cursor.moveToNext();
        }
        closeDatabase();
        return arr;
    }


}