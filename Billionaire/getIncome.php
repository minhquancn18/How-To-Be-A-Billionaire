<?php
$connect = mysqli_connect("localhost", "id17019440_tester",
    "V=9Gx|_ASsl?&TD#", "id17019440_billionaire");

$result = array();
$select = "SELECT MONEY, NAME, DATE
FROM incomedetail INNER JOIN incomecategory ON incomedetail.ID_CATEGORY = incomecategory.ID_CATEGORY";
$res = mysqli_query($connect,$select);
while($row = mysqli_fetch_array($res)){
    array_push($result, array( 'money' => $row[0],
        'name' => $row[1],
        'date' => $row[2]));}
echo json_encode(array("result"=>$result));
mysqli_close($connect);

/*if (isset($_POST['iduser'])) {

    $result = array();
    $result['data'] = array();

    $iduser = $_POST['iduser'];
    $select = "SELECT * FROM income WHERE ID_USER = '" .$iduser. "'";
    $response = mysqli_query($connect, $select);

    while($row = mysqli_fetch_row($response)){
        $index['ID_INCOME'] = $row['0'];
        $index['TOTAL_INCOME'] = $row['2'];

        array_push($result['data'], $index);
    }

    $result["success"] = "1";
    echo json_encode($result);
    mysqli_close($connect);

} else echo "All fields are required";*/
?>
