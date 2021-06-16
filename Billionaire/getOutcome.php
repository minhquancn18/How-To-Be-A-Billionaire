<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser'])) {

    $result = array();
    $result['data'] = array();

    $iduser = $_POST['iduser'];
    $select = "SELECT * FROM outcome WHERE ID_USER = '" .$iduser. "'";
    $response = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($response)){
        $index['ID_OUTCOME'] = $row['0'];
        $index['TOTAL_OUTCOME'] = $row['2'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);

} else echo "All fields are required";
?>