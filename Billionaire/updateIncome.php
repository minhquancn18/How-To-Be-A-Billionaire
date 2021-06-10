<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser']) && isset($_POST['totalmoney'])) {
    if ($db->dbConnect()) {
        if ($db->updateIncome($_POST['iduser'], $_POST['totalmoney'])) {
            echo "Update income Success";
        } else echo "Update income Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
