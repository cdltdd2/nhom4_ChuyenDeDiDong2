<?php
	require "connect.php";
	
  $idHouse =$_POST['id'];

		$query = "DELETE FROM `tblinfoimages` WHERE `IDHOUSE` = '$idHouse'";
		$data = mysqli_query($connect, $query);
		if ($data) {


      $query1 = "DELETE FROM `tblcomments` WHERE `IDHOUSE` =  '$idHouse'";
      $data1 = mysqli_query($connect, $query1);
      if ($data1) {


        $query2 = "DELETE FROM `tblfavorites` WHERE `IDHOUSE` =  '$idHouse'";
        $data2 = mysqli_query($connect, $query2);
        if ($data2) {


          $query3 = "DELETE FROM `tblhousers` WHERE `IDHOUSE` = '$idHouse'";
          $data3 = mysqli_query($connect, $query3);
          if ($data3) {
            echo "success";
          }else {
            echo "fail";
          }


        }else {
          echo "fail";
        }


      }else {
        echo "fail";
      }


		}else {
      echo "fail";
    }

?>
