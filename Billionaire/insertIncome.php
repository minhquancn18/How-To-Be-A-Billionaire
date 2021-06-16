<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser']) && isset($_POST['total_income'])) {
    $iduser = $_POST['iduser'];
    $select = "SELECT * FROM income WHERE ID_USER = '" .$iduser. "'";
    $response = mysqli_query($connect, $select);
    if($response){
        $row = mysqli_fetch_assoc($response);
        if($row == 0){
            $total_income = $_POST['total_income'];
            $select = "INSERT INTO income (ID_USER, TOTAL_INCOME) VALUES ('" .$iduser. "', $total_income)'" ;
            $response = mysqli_query($connect, $select);
            if($response){
                echo "Add new income Success";
                mysqli_close($connect);
            }else{
                echo "Error: " . mysqli_error($connect). "\n Add new income Failed" ;
            }
        }else{
            echo "ID user has exist income.";
            mysqli_close($connect);
        }
    }else{
        echo "Error: " . mysqli_error($connect). "\n Insert income false" ;
    }
} else echo "All fields are required";
?>