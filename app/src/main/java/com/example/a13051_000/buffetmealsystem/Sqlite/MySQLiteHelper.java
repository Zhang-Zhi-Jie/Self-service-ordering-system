package com.example.a13051_000.buffetmealsystem.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shubin on 2016/8/6.
 * 创建数据库的表
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "order_form";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUM = "num";
    public static final String COLUMN_DETAIL = "detail";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_ARG1 = "arg1";
    public static final String COLUMN_ARG2 = "arg2";
    public static final String COLUMN_ARG3 = "arg3";
    public static final String TABLE_NAME_menu = "menu";
    public static final String COLUMN_ID_menu = "_id";
    public static final String COLUMN_NUM_menu = "num";
    public static final String COLUMN_NAME_menu = "dish_name";
    public static final String COLUMN_UNIT_menu = "unit";
    public static final String COLUMN_NUM_EXIST_menu = "num_exist";
    public static final String COLUMN_CLASSIFY_menu = "classify";
    public static final String COLUMN_PRICE_menu = "price";
    public static final String COLUMN_ID_SERVRE = "id_server";
    private static final String DATABASE_NAME = "diancan";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME +"("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NUM + " text but not null,"
            + COLUMN_DETAIL +" text but not null,"
            + COLUMN_ID_SERVRE +" text but not null,"
            + COLUMN_PRICE +" text but not null"
    +");";
    private static final String DATABASE_CREATE_menu = "create table " + TABLE_NAME_menu +"("
            + COLUMN_ID_menu + " integer primary key autoincrement,"
            + COLUMN_NUM_menu + " text but not null,"
            + COLUMN_NAME_menu +" text but not null,"
            + COLUMN_UNIT_menu +" text but not null,"
            + COLUMN_PRICE_menu+" text but not null,"
            + COLUMN_NUM_EXIST_menu +" text but not null, "
            + COLUMN_CLASSIFY_menu + " text but not null, "
            + COLUMN_ARG1+" text but not null,"
            + COLUMN_ARG2 +" text but not null,"
            + COLUMN_ARG3 + " text but not null"
            + ");";
    public MySQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE_menu);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("dbUpgrading","Updating database from version"+oldVersion+"to"+newVersion+",which will detory all the old data");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
