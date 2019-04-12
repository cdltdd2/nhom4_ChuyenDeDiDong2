<?php
  // require_once('connect.php');
  // mysqli_set_charset($con,'utf8');
  // if($_SERVER['REQUEST_METHOD']=='POST') {	
    $imageName = $_POST['imageName'];

    $imageCode = $_POST['imageCode'];
    // Tạo một thư mục chứa ảnh
    // imaName là tên ảnh, để không trùng có thể add thêm ngày tháng cho nó
    $path = "images/$imageName";

    if (file_put_contents($path,base64_decode($imageCode))) {
      echo "success";
    }else {
      echo "fail";
    }
  //
  // }
?>
