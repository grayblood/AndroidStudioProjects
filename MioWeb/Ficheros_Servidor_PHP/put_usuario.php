<?php

$server = "localhost";
$user = "root";
$pass = "";
$bd = "webservice";
//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd)
or die("Ha sucedido un error inexperado en la conexion de la base de datos");

// recibimos los datos del usuario a insertar en  http_post_data
$nombre = $_POST["nombre"];
$apellido = $_POST["apellido"];
$latitud = $_POST["latitud"];
$longitud = $_POST["longitud"];

$sql =  mysqli_prepare($conexion,"INSERT INTO usuario (nombre, apellido, latitud, longitud) VALUES (?,?,?,?)");
mysqli_set_charset($conexion, "utf8"); //formato de datos utf8
mysqli_stmt_bind_param ($sql, "ssdd",$nombre,$apellido,$latitud,$longitud);
mysqli_stmt_execute($sql);
$response=array();
$response["success"] =true;
echo json_encode($response);

?>
