<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idsavingdetail']) && isset($_POST['moneyincomeperday']) &&
    isset($_POST['moneyspendingperday']) && isset($_POST['date'])) {
    if ($db->dbConnect()) {
        if ($db->updateSavingDetail($_POST['idsavingdetail'], $_POST['moneyincomeperday'], $_POST['moneyspendingperday'], $_POST['date'])) {
            echo "Update saving detail Success";
        } else echo "Update saving detail Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>