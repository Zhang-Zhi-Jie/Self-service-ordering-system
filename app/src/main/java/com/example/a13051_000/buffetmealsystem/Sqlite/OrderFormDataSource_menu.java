package com.example.a13051_000.buffetmealsystem.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.a13051_000.buffetmealsystem.Order.Result_Spoon_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubin on 2016/8/13.
 */
public class OrderFormDataSource_menu {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_menu, MySQLiteHelper.COLUMN_NUM_menu, MySQLiteHelper.COLUMN_NAME_menu, MySQLiteHelper.COLUMN_NUM_EXIST_menu, MySQLiteHelper.COLUMN_UNIT_menu, MySQLiteHelper.COLUMN_CLASSIFY_menu
    };

    public OrderFormDataSource_menu(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void create(Result_Spoon_detail result_spoon_detail) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CLASSIFY_menu, result_spoon_detail.getClassify());
        values.put(MySQLiteHelper.COLUMN_NUM, result_spoon_detail.getId());
        values.put(MySQLiteHelper.COLUMN_NAME_menu, result_spoon_detail.getDish_name());
        values.put(MySQLiteHelper.COLUMN_UNIT_menu, result_spoon_detail.getUnit());
        values.put(MySQLiteHelper.COLUMN_NUM_EXIST_menu, "");
        values.put(MySQLiteHelper.COLUMN_PRICE_menu,result_spoon_detail.getPrice());
        long insertID = database.insert(MySQLiteHelper.TABLE_NAME_menu, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_menu, allColumns, MySQLiteHelper.COLUMN_ID_menu+ "= " + insertID, null, null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return;
    }

    public void deleteAll() {
        database.delete(MySQLiteHelper.TABLE_NAME_menu, null, null);
        return;
    }

    public List<Result_Spoon_detail> getAllMenu() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME_menu,null,null,null,null,null,null);
        List<Result_Spoon_detail> results = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                Result_Spoon_detail result_spoon_detail = new Result_Spoon_detail();
                result_spoon_detail.setId(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_NUM_menu)));
                result_spoon_detail.setPrice(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_PRICE_menu)));
                result_spoon_detail.setClassify(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_CLASSIFY_menu)));
                result_spoon_detail.setDish_name(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME_menu)));
                result_spoon_detail.setUnit(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_UNIT_menu)));
                result_spoon_detail.setUser_name(null);
                results.add(result_spoon_detail);
            }while (cursor.moveToNext());
        }
        return results;
    }
}
