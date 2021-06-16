<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if (isset($_POST['iduser'])) {

    $result = array();
    $result['data'] = array();

    $iduser = $_POST['iduser'];
    $select = "SELECT * from goal WHERE ID_USER = '" .$iduser. "'";
    $response = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($response)){
        $index['ID_GOAL'] = $row['0'];
        $index['NAME_GOAL'] = $row['2'];
        $index['DESCRIPTION_GOAL'] = $row['3'];
        $index['MONEY_GOAL'] = $row['4'];
        $index['IMAGE_GOAL'] = $row['5'];
        $index['FINISH'] = $row['6'];
        $index['MONEY_SAVING'] = $row['7'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);

} else echo "All fields are required";
?>