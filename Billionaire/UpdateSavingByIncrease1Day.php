<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['idsaving'])) {
    $idsaving = $_POST['idsaving'];

    $select = "UPDATE saving SET TOTAL_DAY = TOTAL_DAY + 1 WHERE ID_SAVING = '" .$idsaving."'";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update saving Success";
    } else{
        echo "Error: " . mysqli_error($connect). "\n Update saving Failed" ;
    }
} else echo "All fields are required";
?>

