<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['iduser'])) {
    $iduser = $_POST['iduser'];

    $result = array();
    $result['data'] = array();

    $select = "SELECT * FROM saving WHERE ID_USER = '" .$iduser. "'";
    $response = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($response)){
        $index['ID_SAVING'] = $row['0'];
        $index['TOTAL_SAVING'] = $row['2'];
        $index['TOTAL_INCOME'] = $row['3'];
        $index['TOTAL_OUTCOME'] = $row['4'];
        $index['TOTAL_DAY'] = $row['5'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);
} else echo "All fields are required";
?>
