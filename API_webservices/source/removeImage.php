<?php

    $imgPath = $_POST['imgPath'];
	
    if (unlink($imgPath)) {
      echo "success";
    }else {
      echo "fail";
    }

?>
