<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser']) && isset($_POST['money'])) {
    $iduser = $_POST['iduser'];
    $outcome = $_POST['money'];
    $select = "UPDATE outcome set TOTAL_OUTCOME = TOTAL_OUTCOME + '" .$outcome. "' WHERE ID_USER ='" .$iduser. "'";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update outcome success";
    }else{
        echo "Error: " . mysqli_error($connect). "\n Update outcome false" ;
    }
} else echo "All fields are required";
?>
