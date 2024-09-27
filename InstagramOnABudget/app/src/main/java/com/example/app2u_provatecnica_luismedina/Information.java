package com.example.app2u_provatecnica_luismedina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_desc);

        // Obtener vistas de la actividad
        TextView nameTextView = findViewById(R.id.name);
        TextView descriptionTextView = findViewById(R.id.description);
        ImageView imageView = findViewById(R.id.image);
        ImageView returnIcon = findViewById(R.id.returnimage);

        // Icono de regreso
        returnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtener información de la página MainActivity
        Intent intent = getIntent();
        String firstname = intent.getStringExtra("first_name");
        String lastname = intent.getStringExtra("last_name");
        String description = intent.getStringExtra("description");
        String image = intent.getStringExtra("image");

        // Cargar imagen
        Picasso.get().load(image).placeholder(R.drawable.artist_placeholder).into(imageView);

        // Cargar nombre
        String fullName = firstname + " " + lastname;
        nameTextView.setText(fullName);

        // Cargar descripción
        descriptionTextView.setText(description);
    }
}
