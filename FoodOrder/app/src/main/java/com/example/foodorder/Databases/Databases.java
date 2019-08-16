package com.example.foodorder.Databases;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.foodorder.models.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Databases extends SQLiteAssetHelper {
    private static final String DB_NAME = "EatDB.db";
    private static final int DB_VER = 1;

    public Databases(Context context) {
        super(context, DB_NAME, null, DB_VER);


    }

    public List<Order> getCart() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"Productname", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderUser";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {

            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("Productname")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")
                        )));

            }
            while (c.moveToNext());


        }
        return result;

    }

    public void addCart(Order order){
SQLiteDatabase db = getReadableDatabase();
String query = String.format("INSERT INTO OrderUser (ProductId,Productname,Quantity,Price,Discount)VALUES('%s' , '%s' , '%s' , '%s' , '%s');",

        order.getProductId(),order.getProductname(),order.getQuantity(),order.getPrice(),order.getDiscount());
db.execSQL(query);


    }

    public  void clearCart(){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderUser");
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

    }
}
