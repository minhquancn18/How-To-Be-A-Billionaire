<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idsaving'])) {
    if ($db->dbConnect()) {
        if ($db->updateSavingInDayIncrease1($_POST['idsaving'])) {
            echo "Update saving in day increase 1 Success";
        } else echo "Update saving in day increase 1 Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
