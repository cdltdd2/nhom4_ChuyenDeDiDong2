<?php
	require "connect.php";

  $idUser = $_POST['idUser'];
	$idHouse = $_POST['idHouse'];
	

    if (strlen($idUser) > 0 && strlen($idHouse) > 0) {
      $query = "DELETE FROM `tblbooks` WHERE `IDUSER` = '$idUser' AND `IDHOUSE` = '$idHouse'";
      $data = mysqli_query($connect, $query);
      if ($data) {

        $query1 = "UPDATE `tblhousers` SET `STATE`= 0 WHERE `IDHOUSE` = '$idHouse'";
        $data1 = mysqli_query($connect, $query1);
        if ($data1) {
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


?>
