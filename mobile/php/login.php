<?php
    if (session_id() == "") {
        session_start();
    }
    error_reporting(E_ALL ^ E_NOTICE);

    include("connection.php");

    $user = $_POST["user"];
    $senha = $_POST["pass"];

    if ($user != "" && $senha != "") {
        $query = "SELECT * FROM usuario WHERE login='$user' AND senha='$senha'";
        $verific = mysql_query($query);
        if (mysql_num_rows($verific) > 0) {
            echo "retorno=true";
        } else {
            echo "retorno=false";
        }
    }
?>