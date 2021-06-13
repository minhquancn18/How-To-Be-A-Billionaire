<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser']) && isset($_POST['money'])) {
    $iduser = $_POST['iduser'];
    $income = $_POST['money'];
    $select = "UPDATE income set TOTAL_INCOME = TOTAL_INCOME + '" .$income. "' WHERE ID_USER ='" .$iduser. "'";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update income success";
    }else{
        echo "Error: " . mysqli_error($connect). "\n Update income false" ;
    }
} else echo "All fields are required";
?>
