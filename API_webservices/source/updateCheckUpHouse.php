<?php
	require "connect.php";
	
	$id = $_POST['idHouse'];
  $result = $_POST['result'];



	if (strlen($id) > 0 ) {
		$query = "UPDATE `tblhousers` SET `CHECK_UP`= '$result' WHERE `IDHOUSE` = '$id'";
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
