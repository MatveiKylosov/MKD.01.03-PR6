<?php
	$mysqli = new mysqli('localhost', 'root', '', 'users');
	$mysqli->query("SET NAMES UTF8");
	
	$login = $_GET['login'];
	$password = $_GET['password'];
	
	// ищем пользователя
	$query_user = $mysqli->query("SELECT * FROM `users` WHERE `login`='".str_replace(' ', '', $login)."' AND `password`= '".str_replace(' ', '', $password)."';");
	
	while($user_read = $query_user->fetch_row()) {
		// создаём новый пароль
		
		$array = array(
        	'id' => $user_read[0], 
        	'login' => $user_read[1], 
        	'password' => $user_read[2]
        );
        echo '['.json_encode($array).']';
		
	}

?>