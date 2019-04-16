<?php
	require "connect.php";
	
	$id = $_POST['idUser'];
  $result = $_POST['result'];

  if ($result == 2) {
    $query = "UPDATE `tblregisterposter` SET `CONFIRM`= '$result' WHERE `IDUSER` = '$id'";
    $data = mysqli_query($connect, $query);
    if ($data) {
      echo "success";
    }else {
      echo "fail";
    }
  }elseif ($result == 1) {
    if (strlen($id) > 0 && strlen($result) > 0) {
      $query = "UPDATE `tblregisterposter` SET `CONFIRM`= '$result' WHERE `IDUSER` = '$id'";
      $data = mysqli_query($connect, $query);
      if ($data) {
        $queryUpdate = "UPDATE `tblUsers` SET `PERMISSION`= 2 WHERE `IDUSER` = '$id'";
        $dataUpdate = mysqli_query($connect, $queryUpdate);
        if ($dataUpdate) {
          echo "success";
        }else {
          echo "fail";
        }
      }else {
        echo "fail";
      }
    }else{
      echo "null";
    }
  }



?>
