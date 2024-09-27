package com.example.listafinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {
    public static ListAdapter adapter;
    public ListView lv;

    int NightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    ListaBBDD dbHandler;
    SQLiteDatabase db;
    MenuInflater inflater;
    View voiew ;
    TextView textView;
    int iditem;
    public ArrayList<HashMap<String, String>> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        inflater = getMenuInflater();
        dbHandler = ListaBBDD.getInstance(DetailsActivity.this);
        db = dbHandler.getWritableDatabase();
        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        NightMode = sharedPreferences.getInt("NightModeInt", 1);
        AppCompatDelegate.setDefaultNightMode(NightMode);
        lv = (ListView) findViewById(R.id.item_list);
        itemList = dbHandler.GetItems();

        if(NightMode == 1){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        lv.setDivider(null);
        lv.setDividerHeight(0);
        adapter = new SimpleAdapter(DetailsActivity.this, itemList, R.layout.list_row,
                new String[]{"id","name","quantity"},
                new int[]{R.id.iditem,R.id.name, R.id.quant});
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView txt = (TextView) view.findViewById(R.id.name);
                if ((txt.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
                    txt.setPaintFlags( txt.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

                }else{
                    txt.setPaintFlags(txt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

            }
        });



        FloatingActionButton fab = findViewById(R.id.save_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiew = view;
                Intent intent = new Intent(getApplicationContext(), AddItem.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        NightMode = AppCompatDelegate.getDefaultNightMode();

        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putInt("NightModeInt", NightMode);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.help){
            //pasar a una pagina con video
            Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
            startActivityForResult(intent, 1);
        }
        //buscar en la configuracion
        if(id == R.id.daynight){
        Log.i("CDT","Click en la opcion");
            if(NightMode == 1){
                Log.i("CDT","Cambiando a Dia");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                NightMode = 0;

            } else{
                Log.i("CDT","Cambiando a Noche");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                NightMode = 1;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuindi, menu);
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.modi:
                Intent intent = new Intent(getApplicationContext(), AddItem.class); // (MainActivity.this,frmMensaje.class);
                textView = (TextView) info.targetView.findViewById(R.id.iditem);
                iditem = Integer.parseInt(textView.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putInt("id", iditem);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                return true;

            case R.id.eliminar:
                textView = (TextView) info.targetView.findViewById(R.id.iditem);
                iditem= Integer.parseInt(textView.getText().toString());
                dbHandler.DeleteItem(iditem);
                itemList = dbHandler.GetItems();
                lv.setDivider(null);
                lv.setDividerHeight(0);
                adapter = new SimpleAdapter(DetailsActivity.this, itemList, R.layout.list_row,
                        new String[]{"id","name","quantity"},
                        new int[]{R.id.iditem,R.id.name, R.id.quant});
                lv.setAdapter(adapter);
                registerForContextMenu(lv);

                return true;


            default:
                return super.onContextItemSelected(item);
        }
    }



}



