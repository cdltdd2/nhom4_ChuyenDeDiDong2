<?php
	require "connect.php";
	
  $idUser = $_POST['idUser'];
	$idHouse = $_POST['idHouse'];


  $querySelect = "SELECT STATE FROM `tblhousers` WHERE FIND_IN_SET('$idHouse', IDHOUSE)";
  $result = mysqli_query($connect, $querySelect);
  $row = mysqli_fetch_assoc($result);

  if ($row['STATE'] == 1) {
    echo "booked";
  }else {
    if (strlen($idUser) > 0 && strlen($idHouse) > 0) {
      $query = "INSERT INTO `tblbooks` VALUES (null,'$idUser','$idHouse',0)";
      $data = mysqli_query($connect, $query);
      if ($data) {

        $query1 = "UPDATE `tblhousers` SET `STATE`= 1 WHERE `IDHOUSE` = '$idHouse'";
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
  }

?>
