<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['idgoal']) && isset($_POST['name']) && isset($_POST['description']) && isset($_POST['moneygoal'])
    && isset($_POST['imagegoal']) && isset($_POST['finish']) && isset($_POST['moneysaving'])) {

    $target_dir = "ImagesUser/";
    $image = $_POST['imagegoal'];
    $imageStore = rand() . "_" . time() . ".jpeg";
    $target_dir = $target_dir . "/" . $imageStore;
    file_put_contents($target_dir, base64_decode($image));

    $idgoal = $_POST['idgoal'];
    $name = $_POST['name'];
    $description = $_POST['description'];
    $moneygoal = $_POST['moneygoal'];
    $finish = $_POST['finish'];
    $moneysaving = $_POST['moneysaving'];

    $select =  "UPDATE goal SET NAME_GOAL = '" .$name. "', DESCRIPTION_GOAL = '" .$description. "', MONEY_GOAL = '" .$moneygoal.
        "', IMAGE_GOAL = '" .$imageStore. "', FINISH = '" .$finish. "', MONEY_SAVING = '" .$moneysaving.
        "', WHERE ID_GOAL = '" .$idgoal. "'";

    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update goal Success";
    }else{
        echo "Error: " . mysqli_error($connect). "\n Update goal Failed" ;
    }
} else echo "All fields are required";
?>
