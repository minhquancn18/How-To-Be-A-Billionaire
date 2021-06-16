<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['id_user'])){

    $result = array();
    $result['data'] = array();
    $select = "SELECT * from incomecategory WHERE (ID_USER = 0) or (ID_USER = ".$_POST['id_user']. ") ";
    $reponse = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($reponse)){
        $index['ID_CATEGORY'] = $row['0'];
        $index['NAME'] = $row['2'];
        $index['IMAGE'] = $row['3'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);
}

?>