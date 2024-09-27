<?php

$server = "localhost";
$user = "root";
$pass = "";
$bd = "webservice";
//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd)
or die("Ha sucedido un error inexperado en la conexion de la base de datos");

//generamos la consulta
$sql = "SELECT id,nombre, apellido, latitud,longitud FROM usuario";
mysqli_set_charset($conexion, "utf8"); //formato de datos utf8

if(!$result = mysqli_query($conexion, $sql)) die();

$usuarios = array(); //creamos un array

while($row = mysqli_fetch_array($result))
{
    $id=$row['id'];
    $nombre=$row['nombre'];
    $apellido=$row['apellido'];
    $latitud=$row['latitud'];
    $longitud=$row['longitud'];

    $usuarios[] = array('id'=> $id, 'nombre'=> $nombre, 'apellido'=> $apellido,
                    'latitud'=> $latitud,
                     'longitud'=> $longitud);
}

//desconectamos la base de datos
$close = mysqli_close($conexion)
or die("Ha sucedido un error inexperado en la desconexion de la base de datos");

//Creamos el JSON
$json_string = json_encode($usuarios);
echo $json_string;

//Si queremos crear un archivo json, sería de esta forma:
/*
$file = 'clientes.json';
file_put_contents($file, $json_string);
*/


?>
