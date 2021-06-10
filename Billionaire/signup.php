<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['email']) && isset($_POST['fullname']) &&
    isset($_POST['datestart']) && isset($_POST['income']) && isset($_POST['userimage'])) {
    if ($db->dbConnect()) {
        if($db->isUniqueUser($_POST['username'], $_POST['email'])){
            if ($db->signUp("user", $_POST['username'], $_POST['password'], $_POST['email'], $_POST['fullname'],
                $_POST['datestart'], $_POST['income'], $_POST['userimage'])) {
                echo "Sign Up Success";
            } else echo "Sign up Failed";
        }else echo "Please choose another username or email";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
