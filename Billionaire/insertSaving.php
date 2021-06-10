<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser'])) {
    if ($db->dbConnect()) {
        if ($db->insertSaving($_POST['iduser'])) {
            echo "Add new saving Success";
        } else echo "Add new saving Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
