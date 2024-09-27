package com.example.comunicacionactividades;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button act1 = (Button) findViewById(R.id.Activity1);

        act1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Generem el Intent per cridar a la segona Activity (layoyt)
                Intent intent = new Intent(getApplicationContext(), actone.class); // (MainActivity.this,frmMensaje.class);

                Bundle bundle = new Bundle();
                //Afegim una dada al bundle.
                bundle.putString("Num", "1");
                TextView txtRes2 = findViewById(R.id.Resultado22);
                bundle.putString("REsultado2", txtRes2.getText().toString());
                TextView txtRes3 = findViewById(R.id.Resultado32);
                bundle.putString("REsultado3", txtRes3.getText().toString());
                intent.putExtras(bundle);


                startActivityForResult(intent, 1);

            }


        });
        final Button act2 = (Button) findViewById(R.id.Activity2);

        act2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Generem el Intent per cridar a la segona Activity (layoyt)
                Intent intent = new Intent(getApplicationContext(), actone.class); // (MainActivity.this,frmMensaje.class);
                // Generem un bundle.
                // Un Bundle serveis per contenir tipus primetius i objectes d'altres clases
                // Amb aquesta clase podem passar dades entre diferents activitys.
                Bundle bundle = new Bundle();
                //Afegim una dada al bundle.
                bundle.putString("Num", "2");
                TextView txtRes1 = findViewById(R.id.Resultado2);
                bundle.putString("REsultado1", txtRes1.getText().toString());
                TextView txtRes3 = findViewById(R.id.Resultado32);
                bundle.putString("REsultado3", txtRes3.getText().toString());
                intent.putExtras(bundle);


                startActivityForResult(intent, 1);

            }


        });
        final Button act3 = (Button) findViewById(R.id.Activity3);

        act3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Generem el Intent per cridar a la segona Activity (layoyt)
                Intent intent = new Intent(getApplicationContext(), actone.class); // (MainActivity.this,frmMensaje.class);
                // Generem un bundle.
                // Un Bundle serveis per contenir tipus primetius i objectes d'altres clases
                // Amb aquesta clase podem passar dades entre diferents activitys.
                Bundle bundle = new Bundle();
                //Afegim una dada al bundle.
                bundle.putString("Num", "3");
                TextView txtRes1 = findViewById(R.id.Resultado2);
                bundle.putString("REsultado1", txtRes1.getText().toString());
                TextView txtRes3 = findViewById(R.id.Resultado22);
                bundle.putString("REsultado2", txtRes3.getText().toString());
                intent.putExtras(bundle);


                startActivityForResult(intent, 1);

            }


        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView Resultado2 = (TextView) findViewById(R.id.Resultado2);
        TextView Resultado22 = (TextView) findViewById(R.id.Resultado22);
        TextView Resultado32 = (TextView) findViewById(R.id.Resultado32);
        // TODO Auto-generated method stub
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {


            Resultado2.setText(data.getStringExtra("Resultado1"));
            Resultado22.setText(data.getStringExtra("Resultado2"));
            Resultado32.setText(data.getStringExtra("Resultado3"));
        }

    }
}
