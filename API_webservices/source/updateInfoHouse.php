<?php
	require "connect.php";
	
  $idHouse =$_POST['id'];
  $title = $_POST['title'];
	$address = $_POST['address'];
	$object = $_POST['object'];
  $desc= $_POST['desc'];
  $contact= $_POST['contact'];
  $acreage = $_POST['acreage'];
  $price = $_POST['price'];
  $maxpeo= $_POST['maxpeo'];
	$latlng = $_POST['latlng'];
	$state =$_POST['state'];

		$query = "UPDATE `tblhousers` SET `TITLE`='$title',`ADDRESS`='$address'
    ,`OBJECT`='$object',`DESC`='$desc',`CONTACT`='$contact',`ACREAGE`='$acreage'
    ,`PRICE`='$price',`MAXPEO`='$maxpeo',`LATLNG`='$latlng',`STATE`='$state' WHERE `IDHOUSE` = '$idHouse'";
		$data = mysqli_query($connect, $query);
		if ($data) {
      echo "success";
		}else {
      echo "fail";
    }

?>
