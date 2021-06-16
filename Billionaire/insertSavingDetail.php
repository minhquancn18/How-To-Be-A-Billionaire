<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['idsaving']) && isset($_POST['moneyincomeperday']) &&
    isset($_POST['moneyoutcomeperday']) && isset($_POST['date'])) {
    $idsaving = $_POST['idsaving'];
    $moneyincomeperday = $_POST['moneyincomeperday'];
    $moneyoutcomeperday = $_POST['moneyoutcomeperday'];
    $moneysavingperday = $moneyincomeperday - $moneyoutcomeperday;
    $date = $_POST['date'];

    $select = "INSERT INTO savingdetail(ID_SAVING, SAVING_PER_DAY, INCOME_PER_DAY, OUTCOME_PER_DAY, DATE) VALUES ('"
        .$idsaving. "','" .$moneysavingperday. "','" .$moneyincomeperday. "','" .$moneyoutcomeperday. "','".$date."')";
    $response = mysqli_query($connect, $select);
    if($response){
        echo "Add new saving detail Success";
    } else{
        echo "Error: " . mysqli_error($connect). "\n Add new saving detail Failed" ;
    }
} else echo "All fields are required";
?>
