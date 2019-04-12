<?php
	require "connect.php";

	$idHouse = $_POST['house'];
	
	class Comment{
		function Comment($id, $text, $idUser, $idHouse, $time){

			$this -> IDCOMMENT = $id;
			$this -> TEXT = $text;
			$this -> IDUSER = $idUser;
			$this -> IDHOUSE = $idHouse;
      $this -> CREATED_AT = $time;
		}
	}
		$arrComment = array();
		$query = "SELECT * FROM tblcomments WHERE IDHOUSE = '$idHouse'";
		$data = mysqli_query($connect, $query);
		if ($data) {
			while ($row = mysqli_fetch_assoc($data)) {
				array_push($arrComment, new Comment($row['IDCOMMENT'], $row['TEXT']
				, $row['IDUSER'], $row['IDHOUSE'], $row['CREATED_AT']));
			}

			if (count($arrComment) > 0) {
				echo json_encode($arrComment);
			}else{
				echo "fail";
			}
		}

?>
