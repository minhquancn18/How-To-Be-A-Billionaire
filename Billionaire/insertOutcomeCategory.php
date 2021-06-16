<?php
$connect = mysqli_connect("localhost", "id17019440_tester", "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['id_user']) && isset($_POST['name']) && isset($_POST['image'])){

    $target_dir = "ImagesCategory/";
    $image = $_POST['image'];
    $imageStore = rand(). "_" .time(). ".jpeg";
    $target_dir = $target_dir."/".$imageStore;
    file_put_contents($target_dir, base64_decode($image));

    $id_user = $_POST['id_user'];
    $name = $_POST['name'];

    $select = "SELECT * FROM outcomecategory WHERE (ID_USER = 0 or ID_USER = $id_user) and (NAME = '$name') ";
    $response = mysqli_query($connect, $select);

    if($response){
        $row = mysqli_fetch_row($response);
        if($row == 0){

            $select = "INSERT INTO outcomecategory (ID_USER, NAME, IMAGE) VALUES (
            '$id_user', '$name','$imageStore')";

            $response = mysqli_query($connect, $select);

            if($response){
                echo "Add new outcome category success";
                mysqli_close($connect);
            }
            else{
                echo "Error: " . mysqli_error($connect). "\n Add new outcome category failed" ;
            }
        }
        else{
            echo "This name has already exist.";
        }
    }
    else{
        echo "Error: " . mysqli_error($connect). "\n Add new income category failed" ;
    }
}
else {
    echo "All field required";
}
?>
