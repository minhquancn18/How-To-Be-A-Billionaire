<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idgoal']) && isset($_POST['name']) && isset($_POST['description']) && isset($_POST['moneygoal'])
    && isset($_POST['imagegoal']) && isset($_POST['finish']) && isset($_POST['moneysaving'])) {
    if ($db->dbConnect()) {
        if ($db->updateGoal($_POST['idgoal'], $_POST['name'], $_POST['description'],$_POST['moneygoal'],
            $_POST['imagegoal'],$_POST['finish'],$_POST['moneysaving'])) {
            echo "Update goal Success";
        } else echo "Update goal Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
