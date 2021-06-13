<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['idsaving']) && isset($_POST['totalsaving']) && isset($_POST['totalincome']) &&
    isset($_POST['totaloutcome']) && isset($_POST['totalday'])) {
    $idsaving = $_POST['idsaving'];
    $totalsaving = $_POST['totalsaving'];
    $totalincome = $_POST['totalincome'];
    $totaloutcome = $_POST['totaloutcome'];
    $totalday = $_POST['totalday'];

    $select = "UPDATE saving SET TOTAL_SAVING = '" .$totalsaving. "', TOTAL_INCOME = '" .$totalincome.
        "', TOTAL_OUTCOME ='" .$totaloutcome. "', TOTAL_DAY = '" .$totalday.
        "' WHERE ID_SAVING = '" .$idsaving."'";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update saving Success";
    } else{
        echo "Error: " . mysqli_error($connect). "\n Update saving Failed" ;
    }
} else echo "All fields are required";
?>
