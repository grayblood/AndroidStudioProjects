package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.*;


public class MainActivity extends AppCompatActivity {
    /*
      Variables globales
       */
    ListView lista;
    ArrayAdapter adaptador;
    HttpURLConnection con;

    // String DireccionServer = "http://192.168.58.129/profe/";
    String DireccionServer = "http://192.168.58.129/profe/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);


        // Accedemos a la lista de usuarios del layout
        lista= (ListView) findViewById(R.id.lvUsuarios);

         /*  **********************************
        // Inici   Suponemos que tenemos el JSON  en un fichero.

        String jsonString = "";
        try {
            InputStream stream = getAssets().open("usuarios.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            jsonString = new String(buffer);
        } catch (IOException e) {
            Log.i("nico", "Error al leer el archivo!, " + e.getMessage());
        }
        // YA tenemos leido el fichero

        List<Usuario> usuarios = new ArrayList<>();
        try {
            JSONArray users= new JSONArray(jsonString);
            for (int i=0; i<users.length(); i++) {
                JSONObject user=users.getJSONObject(i);
                Usuario newUser = new Usuario(user.getString("nombre").toString(),
                                              user.get("apellido").toString(),
                                               user.getDouble("latitud"),
                                                user.getDouble("longitud") )  ;
                usuarios.add(newUser);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (usuarios!=null) {
            adaptador = new AdaptadorDeUsuarios(getApplicationContext(),usuarios);
            lista.setAdapter(adaptador);
        }else {
            Toast.makeText(getApplicationContext(), "Error parseando datos", Toast.LENGTH_LONG).show();
        }

        // Final de tener el JSON en un fichero
         *************************  */




        // ************   Inicio de inserción de registros
        // Suponemos que queremos insertar estos datos. Los datos pueden provenir de la funcionalidad del sistema


        String nombreLocal="Eduardo";
        String apellidoLocal="MArtínez";
        Double latitudLocal=5.235;
        Double longitudLocal=-12.253;

        // Creamos el Listener que nos va a disparar cuando enviemos los datos al servidor.
        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Aqui ponemos lo que queremos que haga con la respuesta (response) del servidor a nuestra peticion
                // Pensemos que estamos dando de alta un nuevo registro.
                // El php envia un array en formato json, por lo tanto tenemos que tratar la información en json
                try {
                    JSONObject jsonRepuesta = new JSONObject(response);
                    boolean ok =jsonRepuesta.getBoolean("success"); // El php nos responde un array con un valor "success". Aquó lo rescatamos- Es un  boolea
                    if (ok) {
                        Toast.makeText(getApplicationContext(),"Inserción en la BBDD Correcta. Verificalo con el PhpAdmin.", LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
                        alerta.setMessage("Fallo en el registro")
                                .setNegativeButton("Reintentar", null)
                                .create()
                                .show();
                        Toast.makeText(getApplicationContext(),"ERROR.Algo ha fallado en la inserción!!!!", LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
        // Una vez tenemos el Listener ya podemos llamar a la peticion
        Log.i("nico","1");
        RegistroRequest r= new RegistroRequest(nombreLocal, apellidoLocal, latitudLocal,longitudLocal, respuesta );
        // El Volley, es quien hace la comunicación, y permite poenr las consultas en una cola. Tenemos que poner la peticion enla cola para que se ejecute
        Log.i("nico","2");
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(r);  // Aquí se ejectua.

        Log.i("nico","3");



        //*************** Fin de inserción de registros




        // **Inicio Leer Datos del servidor *****************


        // Comprobar la disponibilidad de la Red --> Ojo hay que dar permiso en Manifest.XML
        // Usamos un controlador de conectividad
        ConnectivityManager connMgr =  (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infored = connMgr.getActiveNetworkInfo();  // Depreciated a partir version 29

        if (infored !=null && infored.isConnected()){
            // Hay información y esta conectado
            // Ejecutamos la Tarea Asincrona creada abajo
            // Esta tarea no abre canal
            try {

                new JsonTask(this).execute( new URL (DireccionServer+"/get_usuarios.php"));  //http://localhost/niko/get_usuarios.php"));
                // OJO!!!! La dirección ha de ser donde tengamos el servidor web. eneste caso al ser local, es la dirección que en cada momento tenga el PC en la red. (ipConfig / All
                // y la dirección IPv4, en mi cado

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        } else {
            makeText(this,"Error red", LENGTH_LONG).show();
        }
        //*****************************



    }



    /* ***********************************  */

    // Creamos la tasca asincrona

    public  class JsonTask extends AsyncTask<URL, Void, List<Usuario>> {
        // Mostramos un ProgressDialog para hacer mas amena la esper (Esto es prescindible--> Hay que inicializar en el onPreExecute())
        private ProgressDialog pd;

        /* Constructor */
        public JsonTask (Activity activity) {

            this.pd=new ProgressDialog (activity); // Necesitamos el Activity en el constuctor para que funcione el ProgessDialog

        }


        @Override
        protected void onPreExecute() {
            // Barra de progreso

            this.pd.setMessage("Obteniendo datos....");
            this.pd.setIndeterminate(false);
            this.pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.pd.setCancelable(false);
            this.pd.show();
        }


        @Override
        protected List<Usuario> doInBackground(URL... urls) {
            // DEvolveremos una lista de usuarios que llemos del servicio web
            List<Usuario> usuarios= null;

            // Establecemos conexión sobre la variable global con
            // y sobre el primero de los valores de urls
            // Es obligado que este en un try/catch
            try {

                con = (HttpURLConnection) urls[0].openConnection();

                // Parmatreizamos los tiempos de espera y de lectura. En milisegundos
                con.setConnectTimeout(15000);
                con.setReadTimeout(10000);
                Log.i("nico",urls[0].toString());
                // Ejecturamos conexión y evaluamos resultado

                int codEstadoCon = con.getResponseCode();

                if (codEstadoCon==200) {

                    // Provocamos un retraso para que se vea efecto del ProgessDialog  ->> mejor no poner Sleeps ni toast  en tareas asincronas,
                    for (int j = 0; j < 9999; j++) {
                        int i = 0;
                        while (i < 9999) {
                            i = i + 1;
                        }

                    }
                    // Tenemos que parsear los datos JSON que nos legan a traves de la conexión
                    InputStream in= new BufferedInputStream(con.getInputStream());
                    // Vamos parseando la entrada usando Gson a usuarios
                    GsonUsuarioParser parser = new GsonUsuarioParser();
                    usuarios = parser.leerFlujoJson(in);

                } else {
                    // Generamos un usuario ficticio para mostrar ERRO
                    usuarios = new ArrayList<>();
                    usuarios.add (new Usuario("ERROR "+codEstadoCon," ", null));

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Desconectamos en cualquera de los casos la conneción
                con.disconnect();
            }
            return usuarios;
        }


        @Override
        protected void onPostExecute(List<Usuario> usuarios) {
            // super.onPostExecute(usuarios);
            // Una vez hemos ejecutado la lectura de los objetos json tenimos que parsearlos al adatador
            if (usuarios!=null) {
                adaptador = new AdaptadorDeUsuarios(getApplicationContext(),usuarios);
                lista.setAdapter(adaptador);

            }else {
                Toast.makeText(getApplicationContext(), "Error parseando datos", Toast.LENGTH_LONG).show();
            }


            // Quitamos progress
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }


}

