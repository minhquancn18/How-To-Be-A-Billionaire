<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser'])) {
    if ($db->dbConnect()) {
        $output = $db->getGoal($_POST['iduser']);
        echo json_encode($output);
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
