package com.example.app2u_provatecnica_luismedina;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ListaBBDD dbHandler;
    SQLiteDatabase db;
    public ListView lv;
    ArrayList<HashMap<String, String>> itemList = new ArrayList<>();
    View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = ListaBBDD.getInstance(MainActivity.this);
        db = dbHandler.getWritableDatabase();
        dbHandler.ConnectToDatabaseOnline();
        lv = findViewById(R.id.item_list);
        itemList = dbHandler.GetItems();

        // Inicializar adaptador personalizado
        CustomAdapter customAdapter = new CustomAdapter(this, itemList);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Manejar clic en el elemento aquí

                Intent intent = new Intent(MainActivity.this, Information.class);

                // Obtener ID del elemento
                HashMap<String, String> selectedinfo = dbHandler.GetItemById_Desc(Integer.parseInt(Objects.requireNonNull(itemList.get(position).get("id"))));

                intent.putExtra("first_name", selectedinfo.get("first_name"));
                intent.putExtra("last_name", selectedinfo.get("last_name"));
                intent.putExtra("description", selectedinfo.get("description"));
                intent.putExtra("image", selectedinfo.get("image"));
                startActivity(intent);
            }
        });
        lv.setAdapter(customAdapter);
    }
}
