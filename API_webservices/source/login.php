<?php

	require "connect.php";

	$u = $_POST['user'];
	$p = $_POST['pass'];

    $u = '"'. $u . '"';
    $p = '"' . $p . '"';
    
    
	class User{
		function User($id, $user, $pass, $name, $phone, $permission){
			$this -> IDUSER = $id;
			$this -> USER = $user;
			$this -> PASSWORD = $pass;
			$this -> NAME = $name;
			$this -> PHONE = $phone;
			$this -> PERMISSION = $permission;
		}
	}

	if (strlen($u) > 0 && strlen($p) > 0 ) {
		$arrUser = array();
		$query = "SELECT * FROM tblusers where user = $u and password = $p";
		$data = mysqli_query($connect, $query);
		if ($data) {
			while ($row = mysqli_fetch_assoc($data)) {
				array_push($arrUser, new User($row['IDUSER'], $row['USER'], $row['PASSWORD'], $row['NAME'], $row['PHONE'], $row['PERMISSION']));
			}

			if (count($arrUser) > 0) {
				echo json_encode($arrUser);
			}else{
				echo "fail";
			}
		}
	}else{
		echo "null";
	}

?>
