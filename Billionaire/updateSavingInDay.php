<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idsaving'])) {
    if ($db->dbConnect()) {
        if ($db->updateSavingInResetDay($_POST['idsaving'])) {
            echo "Update saving in reset day Success";
        } else echo "Update saving in reset day Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
