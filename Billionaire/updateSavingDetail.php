<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['idsavingdetail']) && isset($_POST['moneyincomeperday']) &&
    isset($_POST['moneyoutcomeperday']) && isset($_POST['date'])) {
    $idsavingdetail = $_POST['idsavingdetail'];
    $moneyincomeperday = $_POST['moneyincomeperday'];
    $moneyoutcomeperday = $_POST['moneyoutcomeperday'];
    $moneysavingperday = $moneyincomeperday - $moneyoutcomeperday;
    $date = $_POST['date'];

    $select = "UPDATE savingdetail SET SAVING_PER_DAY = '" .$moneysavingperday. "', INCOME_PER_DAY ='" .$moneyincomeperday.
        "', SPENDING_PER_DAY = '" .$moneyoutcomeperday. "', DATE = '" .$date.
        "' WHERE ID_SAVINGDETAIL = '" .$idsavingdetail. "'";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Update saving detail Success";
    } else{
        echo "Error: " . mysqli_error($connect). "\n Update saving detail Failed" ;
    }
} else echo "All fields are required";
?>
