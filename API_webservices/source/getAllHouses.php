<?php
	require "connect.php";
	
	class House{
		function House($id, $title, $address, $object, $image, $desc, $contact
		, $acreage, $price, $maxpeo, $created_at, $check_up, $state, $idunit
		, $idUser, $latlng){

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
		$query = "SELECT * FROM tblHousers";
		$data = mysqli_query($connect, $query);
		if ($data) {
			while ($row = mysqli_fetch_assoc($data)) {
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

?>
