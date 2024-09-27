package com.example.listafinal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.HashMap;

public class AddItem extends AppCompatActivity {
    EditText name,quant;
    int iditem;
    Button saveBtn,ret;
    int NightMode;
    SharedPreferences sharedPreferences;
    Intent intent;
    ListaBBDD dbHandler;
    Bundle bundle;
    ArrayList<HashMap<String, String>> listas;
    HashMap<String, String> item;
    boolean update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        update = false;

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        dbHandler = ListaBBDD.getInstance(AddItem.this);
        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        NightMode = sharedPreferences.getInt("NightModeInt", 1);
        AppCompatDelegate.setDefaultNightMode(NightMode);


        name = (EditText)findViewById(R.id.txtName);
        quant = (EditText)findViewById(R.id.txtquantity);

        bundle = this.getIntent().getExtras();

        if(bundle != null){
            iditem = bundle.getInt("id");

            listas = dbHandler.GetItemById(iditem);
            item =  listas.get(0);
            name.setText(item.get("name"));
            quant.setText(item.get("quantity"));
            update = true;
        }

        saveBtn = (Button)findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String neme = name.getText().toString();
                String quantity = quant.getText().toString();
                if (!neme.matches("")) {
                if(update) {
                    dbHandler.UpdateItemDetails(neme, quantity,iditem);
                }else {
                    dbHandler.insertItem(neme, quantity);
                }
                intent = new Intent(AddItem.this,DetailsActivity.class);
                startActivity(intent);

                setResult(RESULT_OK);
                //---close the activity---
                finish();
                }else{
                    setResult(RESULT_OK);
                    //---close the activity---
                    finish();
                }
            }
        });

        ret = (Button)findViewById(R.id.btnRet);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                //---close the activity---
                finish();

            }
        });

    }
}
