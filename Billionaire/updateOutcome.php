<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser']) && isset($_POST['total_outcome'])) {
    $iduser = $_POST['iduser'];
    $total_outcome = $_POST['total_outcome'];
    $select = "UPDATE income set TOTAL_OUTCOME = '" .$total_outcome. "' WHERE ID_USER ='" .$iduser. "'";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update outcome success";
    }else{
        echo "Error: " . mysqli_error($connect). "\n Update outcome false" ;
    }
} else echo "All fields are required";
?>
