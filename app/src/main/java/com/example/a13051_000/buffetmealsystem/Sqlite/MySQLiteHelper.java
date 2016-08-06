package com.example.a13051_000.buffetmealsystem.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shubin on 2016/8/6.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "order_form";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUM = "num";
    public static final String COLUMN_DETAIL = "detail";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_ID_SERVRE = "id_server";
    private static final String DATABASE_NAME = "diancan";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME +"("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NUM + " text but not null,"
            + COLUMN_DETAIL +" text but not null,"
            + COLUMN_ID_SERVRE +" text but not null,"
            + COLUMN_PRICE +" text but not null);";
    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("dbUpgrading","Updating database from version"+oldVersion+"to"+newVersion+",which will detory all the old data");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
