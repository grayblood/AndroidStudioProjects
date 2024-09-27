package com.example.comunicacionactividades;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.BreakIterator;

public class actone extends Activity implements View.OnClickListener {
	String s;
	String R1;
	String R2;
	String R3;
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Mostrar el view frmmensaje creat a layout
	        setContentView(R.layout.actone);
		 TextView lblSaludo = (TextView) findViewById(R.id.Numero);
		 //
		 Bundle bundle = this.getIntent().getExtras();
		 s = bundle.getString("Num");
		 R1 = bundle.getString("Resultado1");
		 R2 = bundle.getString("Resultado2");
		 R3 = bundle.getString("Resultado3");
		 // També es pot fer directament:
		 lblSaludo.setText(bundle.getString("Num"));

	        
	        // Si no existeix el nomde la variable al bundle, retorna null

		 Button cmdOK = (Button) findViewById(R.id.btnAceptar);

		cmdOK.setOnClickListener(this);






	}


	@Override
	public void onClick(View v) {

		Intent inData = new Intent();


		EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
		int num = Integer.parseInt(s);
		if(num == 1) {
			inData.putExtra("Resultado1", txtNombre.getText().toString());
			inData.putExtra("Resultado2", R2);
			inData.putExtra("Resultado3", R3);
		}else if(num == 2) {
			inData.putExtra("Resultado2", txtNombre.getText().toString());
			inData.putExtra("Resultado1", R1);
			inData.putExtra("Resultado3", R3);
		}else if(num == 3) {
			inData.putExtra("Resultado3", txtNombre.getText().toString());
			inData.putExtra("Resultado2", R2);
			inData.putExtra("Resultado1", R1);
		}



				setResult(RESULT_OK, inData);
				// Cerramos la Activity
				finish();



	}
}

