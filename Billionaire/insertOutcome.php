<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser'])) {
    $iduser = $_POST['iduser'];
    $select = "SELECT * FROM outcome WHERE ID_USER = '" .$iduser. "'";
    $response = mysqli_query($connect, $select);
    if($response){
        $row = mysqli_fetch_assoc($response);
        if($row == 0){
            $select = "INSERT INTO outcome (ID_USER, TOTAL_OUTCOME) VALUES ('" .$iduser. "', 0)'" ;
            $response = mysqli_query($connect, $select);
            if($response){
                echo "Add new outcome Success";
                mysqli_close($connect);
            }else{
                echo "Error: " . mysqli_error($connect). "\n Add new outcome Failed" ;
            }
        }else{
            echo "ID user has exist outcome.";
            mysqli_close($connect);
        }
    }else{
        echo "Error: " . mysqli_error($connect). "\n Insert outcome false" ;
    }
} else echo "All fields are required";
?>
