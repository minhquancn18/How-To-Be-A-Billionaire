<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['idgoal']) && isset($_POST['moneysaving'])) {

    $idgoal = $_POST['idgoal'];
    $moneysaving = $_POST['moneysaving'];

    $select =  "UPDATE goal SET MONEY_SAVING = MONEY_SAVING + '" .$moneysaving. "' WHERE ID_GOAL = '" .$idgoal. "'";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update goal Success";
    }else{
        echo "Error: " . mysqli_error($connect). "\n Update goal Failed" ;
    }
} else echo "All fields are required";
?>

