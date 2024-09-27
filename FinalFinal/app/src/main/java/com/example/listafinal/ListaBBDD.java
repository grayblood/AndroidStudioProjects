package com.example.listafinal;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class ListaBBDD extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "Lista";
    private static final String TABLE_ITEMS = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANT = "quantity";

    private static final String TABLE_CONFIG = "config";



    private static ListaBBDD dbHelperInstance;
    public ListaBBDD(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    public static synchronized ListaBBDD getInstance(Context context) {
        if (dbHelperInstance == null) {
            return dbHelperInstance = new ListaBBDD(context.getApplicationContext());
        } else {
            return dbHelperInstance;
        }

    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        if(db != null) {
            Createtable1(db);
        }
    }
    public void Createtable1(SQLiteDatabase db){
        String CREATE_TABLE = " CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_QUANT + " INTEGER"
                + ")";
        Log.i("db","Creando la base");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIG);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new Item
    void insertItem(String name, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_QUANT, quantity);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_ITEMS,null, cValues);
        db.close();
    }
    // Get Item Details
    public ArrayList<HashMap<String, String>> GetItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> itemList = new ArrayList<>();
        String query = "SELECT id, name, quantity FROM "+ TABLE_ITEMS;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> item = new HashMap<>();
            item.put("id",cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
            item.put("name",cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
            item.put("quantity",cursor.getString(cursor.getColumnIndexOrThrow(KEY_QUANT)));

            itemList.add(item);
        }
        return  itemList;
    }

    public ArrayList<HashMap<String, String>> GetItemById(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id, name, quantity FROM "+ TABLE_ITEMS;
        Cursor cursor = db.query(TABLE_ITEMS, new String[]{KEY_ID,KEY_NAME, KEY_QUANT}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
            user.put("name",cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
            user.put("quantity",cursor.getString(cursor.getColumnIndexOrThrow(KEY_QUANT)));

            userList.add(user);
        }
        return  userList;
    }
    public ArrayList<HashMap<String, String>> GetItemByName(String na){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id, name, quantity FROM "+ TABLE_ITEMS;
        Cursor cursor = db.query(TABLE_ITEMS, new String[]{KEY_ID,KEY_NAME,KEY_QUANT}, KEY_NAME+ "=?",new String[]{na},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
            user.put("name",cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
            user.put("quantity",cursor.getString(cursor.getColumnIndexOrThrow(KEY_QUANT)));

            userList.add(user);
        }
        return  userList;
    }

    // Delete User Details
    public void DeleteItem(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update Item Details
    public int UpdateItemDetails(String name,String quantity, int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cVals = new ContentValues();
        cVals.put(KEY_NAME, name);
        cVals.put(KEY_QUANT, Integer.parseInt(quantity));

        int count = db.update(TABLE_ITEMS, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}