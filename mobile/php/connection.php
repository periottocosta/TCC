<?php
    $db = "basedadosimobiliaria";
    $link = mysql_connect("localhost", "root", "") or die("N�o conseguiu conectar ao servidor.");
    $db = mysql_select_db($db, $link);
?>