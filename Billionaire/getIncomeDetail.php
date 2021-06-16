<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if(isset($_POST['id_income'])){

    $id_income = $_POST['id_income'];

    $result = array();
    $result['data'] = array();
    $select = "SELECT * from incomedetail WHERE ID_INCOME = $in_income ";
    $reponse = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($reponse)){
        $index['ID_INCOMEDETAIL'] = $row['0'];
        $index['MONEY'] = $row['2'];
        $index['ID_CATEGORY'] = $row['3'];
        $index['DESCRIPTION'] = $row['4'];
        $index['DATE'] = $row['5'];
        $index['IMAGE'] = $row['6'];
        $index['AUDIO'] = $row['7'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);
}

?>