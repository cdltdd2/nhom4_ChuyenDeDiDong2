<?php
	require "connect.php";

	class RegisterRequest{
		function RegisterRequest($idUser, $user, $name, $phone, $permission, $idRegister, $confirm){

      $this -> IDUSER = $idUser;
			$this -> USER = $user;
			$this -> NAME = $name;
			$this -> PHONE = $phone;
			$this -> PERMISSION = $permission;
			$this -> IDREGISTER = $idRegister;
			$this -> CONFIRM = $confirm;
		}
	}

	
		$arrRR = array();
		$query = "SELECT tblusers.IDUSER, tblusers.USER, tblusers.NAME, tblusers.PHONE
    , tblusers.PERMISSION, tblregisterposter.IDREGISTER, tblregisterposter.CONFIRM
    FROM tblregisterposter INNER JOIN tblusers ON tblregisterposter.IDUSER = tblusers.IDUSER";
		$data = mysqli_query($connect, $query);
		if ($data) {
			while ($row = mysqli_fetch_assoc($data)) {
				array_push($arrRR, new RegisterRequest($row['IDUSER'], $row['USER']
        , $row['NAME'], $row['PHONE'], $row['PERMISSION'], $row['IDREGISTER'], $row['CONFIRM']));
			}

			if (count($arrRR) > 0) {
				echo json_encode($arrRR);
			}else{
				echo "fail";
			}
		}

?>
