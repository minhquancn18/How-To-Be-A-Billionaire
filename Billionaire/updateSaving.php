<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser']) && isset($_POST['totalsaving']) && isset($_POST['totalincome']) && isset($_POST['totalspending'])) {
    if ($db->dbConnect()) {
        if ($db->updateSaving($_POST['iduser'], $_POST['totalsaving'], $_POST['totalincome'], $_POST['totalspending'])) {
            echo "Update saving Success";
        } else echo "Update saving Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
