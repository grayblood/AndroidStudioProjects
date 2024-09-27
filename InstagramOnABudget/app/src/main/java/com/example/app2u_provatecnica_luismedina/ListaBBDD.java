package com.example.app2u_provatecnica_luismedina;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaBBDD extends SQLiteOpenHelper {
    Context context;

    String url = "https://inphototest.app2u.es/api/photographer/";
    String username = "test@gmail.com";
    String password = "1234";

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "photographerlist";
    private static final String TABLE_ITEMS = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_GUID = "guid";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_IS_REMOVED = "is_removed";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_FACEBOOK = "facebook";
    private static final String KEY_INSTAGRAM = "instagram";
    private static final String KEY_WEBPAGE = "webpage";

    private static ListaBBDD dbHelperInstance;

    public ListaBBDD(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static synchronized ListaBBDD getInstance(Context context) {
        if (dbHelperInstance == null) {
            return dbHelperInstance = new ListaBBDD(context.getApplicationContext());
        } else {
            return dbHelperInstance;
        }
    }

    public void ConnectToDatabaseOnline() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    ArrayList<HashMap<String, String>> itemList = new ArrayList<>();
                    try {
                        JSONArray resultsArray = response.getJSONArray("results");
                        if (resultsArray.length() > 0) {
                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject item = resultsArray.getJSONObject(i);

                                // Create a new HashMap for each item
                                HashMap<String, String> itemMap = new HashMap<>();
                                itemMap.put("id", item.getString("id"));
                                itemMap.put("guid", item.getString("guid"));
                                itemMap.put("email", item.getString("email"));
                                itemMap.put("first_name", item.getString("first_name"));
                                itemMap.put("last_name", item.getString("last_name"));
                                itemMap.put("is_removed", item.getString("is_removed"));
                                itemMap.put("description", item.getString("description"));
                                itemMap.put("avatar", item.isNull("avatar") ? null : item.getString("avatar"));
                                itemMap.put("image", item.isNull("image") ? null : item.getString("image"));
                                itemMap.put("facebook", item.isNull("facebook") ? null : item.getString("facebook"));
                                itemMap.put("instagram", item.isNull("instagram") ? null : item.getString("instagram"));
                                itemMap.put("webpage", item.isNull("webpage") ? null : item.getString("webpage"));

                                itemList.add(itemMap); // Add the new item to the list
                            }
                            insertItems(itemList);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = username + ":" + password;
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);


    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        if (db != null) {
            Createtable(db);
        }
    }

    public void Createtable(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_GUID + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_IS_REMOVED + " INTEGER,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_AVATAR + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_FACEBOOK + " TEXT,"
                + KEY_INSTAGRAM + " TEXT,"
                + KEY_WEBPAGE + " TEXT"
                + ")";
        Log.i("db", "Creando la base");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        // Create tables again
        onCreate(db);
    }

    // Adding new Item
    public void insertItems(ArrayList<HashMap<String, String>> itemlist) {
        Log.i("db", "Insertando informacion");
        boolean areTheSame = CompareDataBases(itemlist, GetItems());

        if (!areTheSame) {
            SQLiteDatabase db = this.getWritableDatabase();
            onUpgrade(db, 0, 1); // borramos la base de datos para actualizar de forma forzada
            db.beginTransaction();
            try {
                ContentValues cValues = new ContentValues();

                for (HashMap<String, String> item : itemlist) {
                    cValues.put(KEY_ID, item.get(KEY_ID));
                    cValues.put(KEY_GUID, item.get(KEY_GUID));
                    cValues.put(KEY_EMAIL, item.get(KEY_EMAIL));
                    cValues.put(KEY_FIRST_NAME, item.get(KEY_FIRST_NAME));
                    cValues.put(KEY_LAST_NAME, item.get(KEY_LAST_NAME));
                    cValues.put(KEY_IS_REMOVED, item.get(KEY_IS_REMOVED));
                    cValues.put(KEY_DESCRIPTION, item.get(KEY_DESCRIPTION));
                    cValues.put(KEY_AVATAR, item.get(KEY_AVATAR));
                    cValues.put(KEY_IMAGE, item.get(KEY_IMAGE));
                    cValues.put(KEY_FACEBOOK, item.get(KEY_FACEBOOK));
                    cValues.put(KEY_INSTAGRAM, item.get(KEY_INSTAGRAM));
                    cValues.put(KEY_WEBPAGE, item.get(KEY_WEBPAGE));

                    db.insert(TABLE_ITEMS, null, cValues);
                }

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }

    // Devolver la lista entera
    public ArrayList<HashMap<String, String>> GetItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> itemList = new ArrayList<>();
        String query = "SELECT " +
                KEY_ID + ", " +
                KEY_GUID + ", " +
                KEY_EMAIL + ", " +
                KEY_FIRST_NAME + ", " +
                KEY_LAST_NAME + ", " +
                KEY_IS_REMOVED + ", " +
                KEY_DESCRIPTION + ", " +
                KEY_AVATAR + ", " +
                KEY_IMAGE + ", " +
                KEY_FACEBOOK + ", " +
                KEY_INSTAGRAM + ", " +
                KEY_WEBPAGE +
                " FROM " + TABLE_ITEMS;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> item = new HashMap<>();
            item.put(KEY_ID, cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
            item.put(KEY_GUID, cursor.getString(cursor.getColumnIndexOrThrow(KEY_GUID)));
            item.put(KEY_EMAIL, cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)));
            item.put(KEY_FIRST_NAME, cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIRST_NAME)));
            item.put(KEY_LAST_NAME, cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME)));
            item.put(KEY_IS_REMOVED, cursor.getString(cursor.getColumnIndexOrThrow(KEY_IS_REMOVED)));
            item.put(KEY_DESCRIPTION, cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)));
            item.put(KEY_AVATAR, cursor.getString(cursor.getColumnIndexOrThrow(KEY_AVATAR)));
            item.put(KEY_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE)));
            item.put(KEY_FACEBOOK, cursor.getString(cursor.getColumnIndexOrThrow(KEY_FACEBOOK)));
            item.put(KEY_INSTAGRAM, cursor.getString(cursor.getColumnIndexOrThrow(KEY_INSTAGRAM)));
            item.put(KEY_WEBPAGE, cursor.getString(cursor.getColumnIndexOrThrow(KEY_WEBPAGE)));

            itemList.add(item);
        }
        return itemList;
    }

    public HashMap<String, String> GetItemById_Desc(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        HashMap<String, String> userList = new HashMap<>();
        String query = "SELECT " + KEY_ID + ", " + KEY_GUID + ", " + KEY_EMAIL + ", " + KEY_FIRST_NAME + ", " + KEY_LAST_NAME + ", " +
                KEY_IS_REMOVED + ", " + KEY_DESCRIPTION + ", " + KEY_AVATAR + ", " + KEY_IMAGE + ", " + KEY_FACEBOOK + ", " +
                KEY_INSTAGRAM + ", " + KEY_WEBPAGE + " FROM " + TABLE_ITEMS;

        Cursor cursor = db.query(TABLE_ITEMS, new String[]{
                KEY_ID, KEY_GUID, KEY_EMAIL, KEY_FIRST_NAME, KEY_LAST_NAME,
                KEY_IS_REMOVED, KEY_DESCRIPTION, KEY_AVATAR, KEY_IMAGE,
                KEY_FACEBOOK, KEY_INSTAGRAM, KEY_WEBPAGE
        }, KEY_ID + "=?", new String[]{String.valueOf(userid)}, null, null, null, null);

        if (cursor.moveToNext()) {
            userList.put(KEY_ID, cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
            userList.put(KEY_FIRST_NAME, cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIRST_NAME)));
            userList.put(KEY_LAST_NAME, cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME)));
            userList.put(KEY_DESCRIPTION, cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)));
            userList.put(KEY_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE)));
        }
        return userList;
    }

    public static boolean CompareDataBases(ArrayList<HashMap<String, String>> lista1, ArrayList<HashMap<String, String>> lista2) {
        // Verificar si los tamaños son iguales
        if (lista1.size() != lista2.size()) {
            return false;
        }

        // Iterar a través de los elementos y comparar los HashMap
        for (int i = 0; i < lista1.size(); i++) {
            HashMap<String, String> mapa1 = lista1.get(i);
            HashMap<String, String> mapa2 = lista2.get(i);

            if (!mapa1.equals(mapa2)) {
                return false;
            }
        }

        // Si todos los elementos son iguales, las listas son iguales
        return true;
    }
}
