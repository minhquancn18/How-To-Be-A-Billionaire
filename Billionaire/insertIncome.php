<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser'])) {
    if ($db->dbConnect()) {
        if($db->isInsert("income", $_POST['iduser'])){
            if ($db->insertIncome($_POST['iduser'])) {
                echo "Add new income Success";
            } else echo "Add new income Failed";
        }else echo "ID user has exist";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
