<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idgoal']) && isset($_POST['moneysaving'])) {
    if ($db->dbConnect()) {
        if ($db->updateSavingGoal($_POST['idgoal'],$_POST['moneysaving'])) {
            echo "Update saving goal Success";
        } else echo "Update saving goal Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
