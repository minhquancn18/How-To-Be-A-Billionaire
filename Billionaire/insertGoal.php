<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser']) && isset($_POST['name']) && isset($_POST['description']) && isset($_POST['moneygoal'])
    && isset($_POST['imagegoal']) && isset($_POST['finish']) && isset($_POST['moneysaving'])) {
    if ($db->dbConnect()) {
            if ($db->insertGoal($_POST['iduser'], $_POST['name'], $_POST['description'],$_POST['moneygoal'],
                                $_POST['imagegoal'],$_POST['finish'],$_POST['moneysaving'])) {
                echo "Add new goal Success";
            } else echo "Add new goal Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>