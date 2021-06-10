<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['iduser'])) {
    if ($db->dbConnect()) {
        $output = $db->getSaving($_POST['iduser']);
        header('Content-Type: application/json');
        echo json_encode($output);
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
