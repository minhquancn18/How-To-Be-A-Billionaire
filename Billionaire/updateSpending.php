<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser']) && isset($_POST['totalmoney'])) {
    if ($db->dbConnect()) {
        if ($db->updateSpending($_POST['iduser'], $_POST['totalmoney'])) {
            echo "Update spending Success";
        } else echo "Update spending Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
