<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idsaving']) && isset($_POST['moneyincomeperday']) &&
    isset($_POST['moneyspendingperday']) && isset($_POST['date'])) {
    if ($db->dbConnect()) {
        if ($db->insertSavingDetail($_POST['idsaving'], $_POST['moneyincomeperday'], $_POST['moneyspendingperday'], $_POST['date'])) {
            echo "Add new saving detail Success";
        } else echo "Add new saving detail Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
