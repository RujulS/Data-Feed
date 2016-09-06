package com.example.rujuls.datafeed;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

 class DBHandler extends SQLiteOpenHelper
{
    private static String TAG = "com.example.rujuls.datafeed";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DataField.db";
    public static final String TABLE_PRODUCTS = "DataFields";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_MAIL = "MAIL";
    public static final String COLUMN_ADDRESS = "ADDRESS";

    private static final String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " + COLUMN_PHONE + " INTEGER, " +
            COLUMN_MAIL + " TEXT, " + COLUMN_ADDRESS + " TEXT " +
            ");";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query);
    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(DataFields f){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, f.get_name());
        values.put(COLUMN_PHONE, f.get_phone());
        values.put(COLUMN_MAIL, f.get_mail());
        values.put(COLUMN_ADDRESS, f.get_address());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_NAME + "=\"" + productName + "\";");
    }

    public Cursor fetch() {

        //String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        if (recordSet != null) {
            recordSet.moveToFirst();
        }
        return recordSet;
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("NAME")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("NAME"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

}
