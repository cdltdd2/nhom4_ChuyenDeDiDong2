<?php
	require "connect.php";

	$idUser = $_POST['idUser'];
	
	class Fav{
		function Fav($idFav, $idUser, $idHouse){

			$this -> IDFAV = $idFav;
			$this -> IDUSER = $idUser;
			$this -> IDHOUSE = $idHouse;
		}
	}
		$arrFav = array();
    $arrFav1 = array();
		$query = "SELECT * FROM tblfavorites WHERE IDUSER = '$idUser'";
		$data = mysqli_query($connect, $query);
		if ($data) {
			while ($row = mysqli_fetch_assoc($data)) {
				array_push($arrFav, new Fav($row['IDFAV'], $row['IDUSER']
				, $row['IDHOUSE']));
			}

			if (count($arrFav) > 0) {
				echo json_encode($arrFav);
			}else{
				echo json_encode($arrFav1);
			}
		}

?>
