<?php
	require "connect.php";
	
  $idUser = $_POST['idUser'];
	$idHouse = $_POST['idHouse'];

	if (strlen($idUser) > 0 && strlen($idHouse) > 0) {
		$query = "SELECT IDUSER FROM `tblbooks` WHERE FIND_IN_SET('$idHouse', IDHOUSE)";
		$data = mysqli_query($connect, $query);
		if ($data) {
      $row = mysqli_fetch_assoc($data);
      if ($row['IDUSER'] == $idUser) {
        echo "true";
      }else {
        echo "false";
      }
		}else {
      echo "fail";
    }
	}else{
		echo "null";
	}

?>
