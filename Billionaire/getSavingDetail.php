<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");
if (isset($_POST['idsaving'])) {
    $idsaving = $_POST['idsaving'];

    $result = array();
    $result['data'] = array();

    $select = "SELECT * FROM savingdetail WHERE ID_SAVING = '" .$idsaving. "'";
    $response = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($response)){
        $index['ID_SAVINGDETAIL'] = $row['0'];
        $index['SAVING_PER_DAY'] = $row['2'];
        $index['INCOME_PER_DAY'] = $row['3'];
        $index['OUTCOME_PER_DAY'] = $row['4'];
        $index['DATE'] = $row['5'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);

} else echo "All fields are required";
?>