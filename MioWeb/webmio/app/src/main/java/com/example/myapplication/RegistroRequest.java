package com.example.myapplication;

/* **************
ESta clase sirve para la inserción de registros en la BBDD remota usando el servicioweb ya creao en el servidor

Tenemos que usar la  libreria Volley -->        implementation 'com.android.volley:volley:1.1.1'  y tenemos que añadir esa line a build.grade
Esto es para poer usar la StringRequest.
Volley es una libreria desarrollada por Google para  optimizar el envio de peticiones Http desde Android a servidores extenos.
https://developer.android.com/training/volley


*************** */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegistroRequest extends StringRequest {
    // Definimos la ruta del archivo php para escribir en la BBDD

    private static final String ruta = "http://192.168.0.15/niko/put_usuario.php"; // "http://10.1.131.139/niko/put_usuario.php";
    private Map<String, String> parametros;  // parametros que contentdra los valores del registro

    // Constructor
    public RegistroRequest(String nombre, String apellido, Double latitud, Double longitud, Response.Listener<String> listener){
        // el listener es lo que tiene que pasar cuando le enviemos la informacion al servidor. PAra saber como ha ido.
        super(Request.Method.POST, ruta, listener, null); // LA peticion es post
        // Creamos un HaspMap para psar datos del registro
        parametros = new HashMap<>();
        parametros.put("nombre",nombre);
        parametros.put("apellido",apellido);
        parametros.put("latitud",latitud+""); // Han de ser String, por eso pasamos Doubles a Atring
        parametros.put("longitud",longitud+"");

    }

    // Sobreescribimos map  para asegurarnos que nos devuelve la información de uestros parametros
    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
