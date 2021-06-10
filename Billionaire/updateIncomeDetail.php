<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idmoneydetail']) && isset($_POST['idmoney']) && isset($_POST['money']) && isset($_POST['idcategory']) &&
    isset($_POST['description']) && isset($_POST['date']) && isset($_POST['image']) && isset($_POST['audio'])) {
    if ($db->dbConnect()) {
        if ($db->updateIncomeDetail($_POST['idmoneydetail'],$_POST['idmoney'], $_POST['money'],
            $_POST['idcategory'], $_POST['description'], $_POST['date'], $_POST['image'], $_POST['audio'])) {
            echo "Update income detail Success";
        } else echo "Update income detail Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
