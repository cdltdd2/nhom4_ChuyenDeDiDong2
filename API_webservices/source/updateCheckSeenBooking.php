<?php
	require "connect.php";
	
  $idBooking = $_POST['idBooking'];

	if (strlen($idBooking) > 0) {

    $query = "UPDATE `tblbooks` SET `CHECK_SEEN`= 1 WHERE `IDBOOK` = '$idBooking'";
		$data = mysqli_query($connect, $query);
		if ($data) {
      echo "true";
		}else {
      echo "false";
    }
	}else{
		echo "none";
	}

?>
