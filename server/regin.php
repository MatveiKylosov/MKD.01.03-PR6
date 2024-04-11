<?php
	$mysqli = new mysqli('localhost', 'root', '', 'users');
	$mysqli->query("SET NAMES UTF8");
	
	$login = $_GET['login'];
	$password = $_GET['password'];
	
	// ищем пользователя
	$query_user = $mysqli->query("SELECT * FROM `users` WHERE `login`='".str_replace(' ', '', $login)."';");
	
	if($user_read = $query_user->fetch_row()) {
		echo "0";
	} else {
	    // создаём пользователя
	    $query_user = $mysqli->query("INSERT INTO `users`(`login`, `password`) VALUES ('".$login."','".$password."')");
	    echo "1";
	}

?>