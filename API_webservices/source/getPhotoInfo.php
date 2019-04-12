<?php
	require "connect.php";

  $idHouse = $_POST['idHouse'];

	class Photo{
		function Photo($url){
			$this -> URL = $url;
		}
	}

	
		$arrPhoto = array();
		$query = "SELECT URL FROM tblinfoimages WHERE IDHOUSE = '$idHouse'";
		$data = mysqli_query($connect, $query);
		if ($data) {
			while ($row = mysqli_fetch_assoc($data)) {
				array_push($arrPhoto, new Photo($row['URL']));
			}

			if (count($arrPhoto) > 0) {
				echo json_encode($arrPhoto);
			}else{
				echo "fail";
			}
		}

?>
