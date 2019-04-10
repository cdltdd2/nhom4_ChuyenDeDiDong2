<?php
	require "connect.php";

	$title = $_POST['title'];
	$address = $_POST['address'];
	$object = $_POST['object'];
	$image = $_POST['image'];
  $desc= $_POST['desc'];
  $contact= $_POST['contact'];
  $acreage = $_POST['acreage'];
  $price = $_POST['price'];
  $maxpeo= $_POST['maxpeo'];
  $created_at = $_POST['created_at'];
  $idUser = $_POST['idUser'];
	$latlng = $_POST['latlng'];


  class House{
		function House($id, $title, $address, $object, $image, $desc, $contact
    , $acreage, $price, $maxpeo, $created_at, $check_up, $state, $idunit, $idUser, $latlng){

			$this -> IDHOUSE = $id;
			$this -> TITLE = $title;
			$this -> ADDRESS = $address;
			$this -> OBJECT = $object;
			$this -> IMAGE = $image;
			$this -> DESC = $desc;
			$this -> CONTACT = $contact;
			$this -> ACREAGE = $acreage;
			$this -> PRICE = $price;
			$this -> MAXPEO = $maxpeo;
			$this -> CREATED_AT = $created_at;
			$this -> CHECK_UP = $check_up;
			$this -> STATE = $state;
			$this -> IDUNIT = $idunit;
			$this -> IDUSER = $idUser;
			$this -> LATLNG = $latlng;
		}
	}

		$arrHouse = array();
		$query = "INSERT INTO tblHousers VALUES(null, '$title', '$address', '$object'
      , '$image', '$desc', '$contact', '$acreage','$price','$maxpeo', '$created_at', 0, 0, 1, '$idUser', '$latlng')";
		$data = mysqli_query($connect, $query);
		if ($data) {
      $querySelect = "SELECT * FROM tblHousers WHERE FIND_IN_SET('$created_at', CREATED_AT) AND FIND_IN_SET('$idUser', IDUSER)";
      $dataSelect = mysqli_query($connect, $querySelect);
      if ($dataSelect) {
        while ($row = mysqli_fetch_assoc($dataSelect)) {
          array_push($arrHouse, new House($row['IDHOUSE'], $row['TITLE']
          , $row['ADDRESS'], $row['OBJECT'], $row['IMAGE'], $row['DESC']
          , $row['CONTACT'], $row['ACREAGE'], $row['PRICE'], $row['MAXPEO']
          , $row['CREATED_AT'], $row['CHECK_UP'], $row['STATE'], $row['IDUNIT']
          , $row['IDUSER'], $row['LATLNG']));
        }

        if (count($arrHouse) > 0) {
          echo json_encode($arrHouse);
        }else{
          echo "fail";
        }
      }

		}else {
      echo "fail";
    }


?>
