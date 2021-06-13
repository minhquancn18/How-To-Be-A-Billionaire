<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser']) && isset($_POST['name']) && isset($_POST['description']) &&
    isset($_POST['moneygoal']) && isset($_POST['finish']) && isset($_POST['moneysaving'])) {

    $iduser = $_POST['iduser'];
    $name = $_POST['name'];
    $description = $_POST['description'];
    $moneygoal = $_POST['moneygoal'];
    $finish = $_POST['finish'];
    $moneysaving = $_POST['moneysaving'];

    $select =  "INSERT INTO goal(ID_USER, NAME_GOAL, DESCRIPTION_GOAL, MONEY_GOAL,  FINISH, MONEY_SAVING) VALUES ('"
        .$iduser. "','" .$name. "','" .$description. "','" .$moneygoal. "','"  .$finish. "','" .$moneysaving. "')";

    $response = mysqli_query($connect, $select);
    if($response){
        echo "Add new goal Success";
    }else{
        echo "Error: " . mysqli_error($connect). "\n Add new goal Failed" ;
    }
} else echo "All fields are required";
?>
