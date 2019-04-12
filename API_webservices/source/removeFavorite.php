<?php
	require "connect.php";
	
	$idUser = $_POST['idUser'];
	$idHouse = $_POST['idHouse'];

	if (strlen($idUser) > 0 && strlen($idHouse) > 0) {
		$query = "DELETE FROM tblfavorites WHERE IDUSER = '$idUser' AND IDHOUSE = '$idHouse'";
		$data = mysqli_query($connect, $query);
		if ($data) {
      echo "success";
		}else {
      echo "fail";
    }
	}else{
		echo "null";
	}

?>
