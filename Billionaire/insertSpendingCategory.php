<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['name']) && isset($_POST['image']) && isset($_POST['child']) && isset($_POST['parent'])) {
    if ($db->dbConnect()) {
        if($db->isUniqueCategory("spendingcategory", $_POST['name'])){
            if ($db->insertCategory("spendingcategory", $_POST['name'], $_POST['image'], $_POST['child'], $_POST['parent'])) {
                echo "Add new spending category Success";
            } else echo "Add new spending category Failed";
        } else echo "Please choose another spending category";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
