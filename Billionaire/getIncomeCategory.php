<?php
require "DataBase.php";
$db = new DataBase();
if ($db->dbConnect()) {
     $output = $db->getCategory("incomecategory");
    header('Content-Type: application/json');
    echo json_encode($output);
} else echo "Error: Database connection";
?>