<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['iduser'])) {
    $iduser = $_POST['iduser'];
    $totalsaving = 0;
    $totalincome = 0;
    $totaloutcome = 0;
    $totalday = 0;

    $select = "INSERT INTO saving(ID_USER, TOTAL_SAVING, TOTAL_INCOME, TOTAL_OUTCOME, TOTAL_DAY) VALUES ('" .$iduser.
        "','" .$totalsaving. "','" .$totalincome. "','" .$totaloutcome. "','" .$totalday. "')";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Add new saving Success";
    } else{
        echo "Error: " . mysqli_error($connect). "\n Add new saving Failed" ;
    }
} else echo "All fields are required";
?>
