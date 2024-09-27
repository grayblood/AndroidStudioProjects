package com.example.myapplication;


//import android.util.JsonReader;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class GsonUsuarioParser {

    public List<Usuario> leerFlujoJson (InputStream in) throws IOException {
        // OJO!!! PAra usar Gson hay que poner el implementation 'com.google.code.gson:gson:2.8.5' en  las dependencias del  gradle y hacer hacer Rebuid

        Gson gson =new Gson();
        // Creamos lector  por el canal de entrda con la codificacion UTF-8 para controlar acentos
        JsonReader reader = new JsonReader(new InputStreamReader( in, "UTF-8"));
        // leeemos un array de usuarios
        List<Usuario> usuarios = new ArrayList<>();

        reader.beginArray();

        while (reader.hasNext()){
            // leemos objeto a objeto
            Usuario user = gson.fromJson (reader, Usuario.class); // Se puede leer directamente un objeto o consturirlo (reader.beginObject() ,..... Ver documentacion oficial
            usuarios.add(user);
        }
        // Cerramos lector del canar.
        reader.endArray();
        reader.close();

        return usuarios;
    };

}
