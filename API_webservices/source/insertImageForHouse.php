<?php
	require "connect.php";

	$url = $_POST['url'];
	$idHouse = $_POST['idHouse'];
	
	if (strlen($url) > 0 && strlen($idHouse) > 0) {
		$query = "INSERT INTO tblinfoimages VALUES(null, '$url', '$idHouse')";
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
