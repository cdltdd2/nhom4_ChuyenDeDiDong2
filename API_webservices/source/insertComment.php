<?php
	require "connect.php";

	$idUser = $_POST['user'];
	$idHouse = $_POST['house'];
	$text = $_POST['text'];
  $time = $_POST['time'];

	
	if (strlen($idUser) > 0 && strlen($idHouse) > 0 && strlen($text) > 0) {
    $insert = "INSERT INTO tblcomments VALUES(null, '$text', '$idUser', '$idHouse', '$time')";
    $dataInsert = mysqli_query($connect, $insert);
    if ($dataInsert) {
      echo "success";
    }else{
      echo "fail";
    }
	}else{
		echo "null";
	}

?>
