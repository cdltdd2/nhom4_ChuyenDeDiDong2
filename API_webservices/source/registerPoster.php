<?php
	require "connect.php";
	
	$id = $_POST['id'];

  class RegisterPoster{
    function RegisterPoster($id, $idUser, $confirm){
      $this->IDREGISTER = $id;
      $this->IDUSER = $idUser;
      $this->CONFIRM = $confirm;
    }
  }


  if (strlen($id) > 0) {
    $arrRegister = array();
    $query = "SELECT * FROM tblRegisterPoster WHERE FIND_IN_SET('$id', IDUSER)";
    $data = mysqli_query($connect, $query);
    if ($data) {
      while ($row = mysqli_fetch_assoc($data)) {
        array_push($arrRegister, new RegisterPoster($row['IDREGISTER'], $row['IDUSER']
        , $row['CONFIRM']));
      }

      if (count($arrRegister) > 0) {
        echo "exists";
      }else{
        $insert = "INSERT INTO tblRegisterPoster VALUES(null, '$id',0)";
        $dataInsert = mysqli_query($connect, $insert);
        if ($dataInsert) {
          echo "success";
        }else{
          echo "fail";
        }
      }
    }
  }else{
    echo "null";
  }

?>
