<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idsaving'])) {
    if ($db->dbConnect()) {
        $output = $db->getSavingDetail($_POST['idsaving']);
        header('Content-Type: application/json');
        echo json_encode($output);
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
