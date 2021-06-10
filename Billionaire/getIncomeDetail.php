<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idmoney']) && isset($_POST['idcategory']) && isset($_POST['month']) && isset($_POST['year'])) {
    if ($db->dbConnect()) {
        $output = $db->getIncomeDetail($_POST['idmoney'], $_POST['idcategory'], $_POST['month'], $_POST['year']);
        header('Content-Type: application/json');
        echo json_encode($output);
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>