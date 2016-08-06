package com.example.a13051_000.buffetmealsystem.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubin on 2016/8/6.
 */
public class OrderFormDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_NUM,MySQLiteHelper.COLUMN_DETAIL,MySQLiteHelper.COLUMN_ID_SERVRE,MySQLiteHelper.COLUMN_PRICE
    };
    public OrderFormDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void  close(){
        dbHelper.close();
    }
    public OrderForm create(OrderForm orderForm){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NUM,orderForm.getNum());
        values.put(MySQLiteHelper.COLUMN_DETAIL,orderForm.getDetail());
        values.put(MySQLiteHelper.COLUMN_PRICE,orderForm.getPrice());
        values.put(MySQLiteHelper.COLUMN_ID_SERVRE,orderForm.getId_server());
        long insertID = database.insert(MySQLiteHelper.TABLE_NAME,null,values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,allColumns,MySQLiteHelper.COLUMN_ID+"="+insertID,null,null,null,null);
        cursor.moveToFirst();
        cursor.close();
        return null;
    }
    public void deleteOrderform(String num){
        database.delete(MySQLiteHelper.TABLE_NAME,MySQLiteHelper.COLUMN_NUM + "=" +num ,null);
    }
    public List<OrderForm> getAllForm(){
        List<OrderForm> orderForms = new ArrayList<OrderForm>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            OrderForm orderForm = cusorToOrderForm(cursor);
            orderForms.add(orderForm);
            cursor.moveToNext();
        }
        cursor.close();
        return orderForms;
    }
    private OrderForm cusorToOrderForm(Cursor cursor){
        OrderForm orderForm = new OrderForm();
        orderForm.setId(cursor.getInt(0));
        orderForm.setNum(Long.valueOf(cursor.getString(1)));
        orderForm.setDetail(cursor.getString(2));
        orderForm.setId_server(cursor.getString(3));
        orderForm.setPrice(cursor.getString(4));
        return orderForm;
    }

}
