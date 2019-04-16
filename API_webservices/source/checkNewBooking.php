<?php
	require "connect.php";
	
  $idUser =  $_POST['idUser'];

  class ID{
    function ID($id){
      $this-> IDBOOK = $id;
    }
  }

	if (strlen($idUser) > 0) {
		// $query = "SELECT tblbooks.IDBOOK, tblusers.NAME, tblhousers.TITLE FROM
    // tblbooks INNER JOIN tblusers ON tblbooks.IDUSER = tblusers.IDUSER INNER JOIN
    // tblhousers ON tblbooks.IDHOUSE = tblhousers.IDHOUSE WHERE tblhousers.IDUSER = '$idUser'
    // AND tblbooks.CHECK_SEEN = 0";
    $array = array();
    $query = "SELECT tblbooks.IDBOOK FROM tblbooks INNER JOIN tblhousers
    ON tblbooks.IDHOUSE = tblhousers.IDHOUSE WHERE tblhousers.IDUSER = '$idUser' AND tblbooks.CHECK_SEEN = 0";
		$data = mysqli_query($connect, $query);
		if ($data) {
      while ($row = mysqli_fetch_assoc($data)) {
        array_push($array, new ID($row['IDBOOK']));
      }
      if (count($array) > 0) {
        echo json_encode($array);
      }else {
        echo "none";
      }

		}else {
      echo "none";
    }
	}else{
		echo "none";
	}

?>
