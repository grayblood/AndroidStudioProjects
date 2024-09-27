package com.example.uf1_1_a9_contar_lletres;



import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	 String strTexteLabel;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        
	        strTexteLabel= getString(R.string.pulsaparasaber);
	        
	        
	        // Le ponemos una imagen de fondo a la pantalla
	        // getWindow().setBackgroundDrawableResource(android.R.drawable.arrow_down_float);
	        getWindow().setBackgroundDrawableResource(android.R.drawable.divider_horizontal_dark);
	        
	        
	        
	        
	        // Le cambiamos titulo a la pantalla aunque no hace falta... ya esta en 
	        // el Layout principal.
	        getWindow().setTitle(getResources().getText(R.string.title_activity_main));
	        
	        Button btnCuenta = (Button) findViewById(R.id.btnCuenta);
	        btnCuenta.setOnClickListener(new OnClickListener() {	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					EditText txtTexto = (EditText) findViewById(R.id.txtTexto);
					int numLetras = txtTexto.getText().length();
					
					TextView lblCuenta = (TextView) findViewById(R.id.lblLetras);
					// Això funciona ja que durant la concatenació la conversió directament.
					
					lblCuenta.setText("Hay "+numLetras + " letras.");
					// Pero de forma correcte:
					lblCuenta.setText("Hay "+String.valueOf(numLetras) + " letras.");
				}
			}); // btnCuenta.setOnClickListener
	        
	        
	        // Hasta aquui todo funciona, pero ¿que pasa si cambiamo el Texto?
	        // Que la TextView (label) no se actualiza..
	    	EditText txtTexto = (EditText) findViewById(R.id.txtTexto);
	    	// Controlamos cuando se clica el TextView
	    	txtTexto.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    TextView lblCuenta = (TextView) findViewById(R.id.lblLetras);
				  	lblCuenta.setText(strTexteLabel);
				}
			}); // txtTexto.setOnClickListener
	    	// Tambien controlamos cuando se pulsa una tecla sobre el TextView
	    	txtTexto.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					TextView lblCuenta = (TextView) findViewById(R.id.lblLetras);
					lblCuenta.setText(strTexteLabel);
					// o bé directamente.
					lblCuenta.setText(getString(R.string.pulsaparasaber));
					return false;
				}
			}); // txtTexto.setOnKeyListener
	        
	    }



    
}
