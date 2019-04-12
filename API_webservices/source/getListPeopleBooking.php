<?php
	require "connect.php";

    $idUser =  $_POST['idUser'];

	class PeopleBooking{
		function PeopleBooking($idUser, $name, $phone, $idHouse, $title){

			$this -> IDUSER = $idUser;
			$this -> NAME = $name;
			$this -> PHONE = $phone;
			$this -> IDHOUSE = $idHouse;
			$this -> TITLE = $title;
		}
	}

	
		$arr = array();
    $arr1 = array();
		$query = "SELECT tblusers.IDUSER, tblusers.NAME, tblusers.PHONE
    , tblbooks.IDHOUSE, tblhousers.TITLE FROM tblbooks INNER JOIN
    tblusers ON tblbooks.IDUSER = tblusers.IDUSER INNER JOIN tblhousers
    ON tblbooks.IDHOUSE = tblhousers.IDHOUSE WHERE tblusers.IDUSER = '$idUser' OR tblhousers.IDUSER = '$idUser'";
		$data = mysqli_query($connect, $query);
		if ($data) {
			while ($row = mysqli_fetch_assoc($data)) {
				array_push($arr, new PeopleBooking($row['IDUSER'], $row['NAME']
				, $row['PHONE'], $row['IDHOUSE'], $row['TITLE']));
			}

			if (count($arr) > 0) {
				echo json_encode($arr);
			}else{
				echo json_encode($arr1);
			}
		}

?>
