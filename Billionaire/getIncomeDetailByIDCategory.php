<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

if(isset($_POST['id_category']) && isset($_POST['id_income'])){

    $id_category = $_POST['id_category'];
    $id_income = $_POST['id_income'];

    $result = array();
    $result['data'] = array();
    $select = "SELECT * from incomedetail WHERE ID_CATEGORY = $id_category  and ID_INCOME = $id_income";
    $reponse = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($reponse)){
        $index['ID_INCOMEDETAIL'] = $row['0'];
        $index['MONEY'] = $row['2'];
        $index['DATE'] = $row['5'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);
}

?>
