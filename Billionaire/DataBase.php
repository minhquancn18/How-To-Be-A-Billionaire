<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $hostname;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->hostname = $dbc->hostname;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->hostname, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    ////////////////////////////////////////////////////
    //User
    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where USERNAME = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $username, $password, $email, $fullname, $datestart, $income, $userimage)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $fullname = $this->prepareData($fullname);
        $datestart = $this->prepareData($datestart);
        $income = $this->prepareData($income);
        $userimage = $this->prepareData($userimage);
        $password = password_hash($password, PASSWORD_DEFAULT);

        $this->sql =
            "INSERT INTO " . $table . " (USERNAME, PASSWORD, EMAIL, FULLNAME, DATESTART, INCOME, USERIMAGE) VALUES ('" . $username . "','" . $password . "','" .$email. "','" . $fullname . "','" . $datestart . "','". $income ."','". $userimage ."')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function isUniqueUser($username, $email){
        $username = $this->prepareData($username);
        $email = $this->prepareData($email);
        $this->sql = "select * from user where USERNAME = '" . $username . "' and EMAIL = '" .$email. "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if($row == 0)
            return true;
        else return false;
    }

    function getUser(){
        $this->sql = "SELECT * FROM user ";

        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
             }
            return $output;
        }
        return null;
    }

    ////////////////////////////////////////////////////
    //Category (IncomeCategory && SpendingCategory)
    function insertCategory($table,$name,$image, $child, $parent){
        $name = $this->prepareData($name);
        $image = $this->prepareData($image);
        $child = $this->prepareData($child);
        $parent = $this->prepareData($parent);

        //$image = base64_encode($image);

        $this->sql =
            "INSERT INTO " . $table . " (NAME, IMAGE, CHILD, PARENT_NAME) VALUES ('" .$name. "','" . $image. "','" .$child."','" .$parent. "')";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function isUniqueCategory($table, $name){
        $name = $this->prepareData($name);
        $this->sql = "select * from " .$table. " where NAME = '" . $name . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if($row == 0)
            return true;
        else return false;
    }

    function getCategory($table){
        $this->sql = "select * from " . $table . "";
        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
            }
            return $output;
        }
        return null;
    }

    ////////////////////////////////////////////////////
    //Income && Spending
    function insertIncome($iduser){
        $iduser = $this->prepareData($iduser);

        $this->sql =
            "INSERT INTO income (ID_USER, TOTAL_INCOME) VALUES ('" .$iduser. "', 0)'" ;

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function insertSpending($iduser){
        $iduser = $this->prepareData($iduser);

        $this->sql =
            "INSERT INTO spending (ID_USER, TOTAL_SPENDING) VALUES ('" .$iduser. "', 0)'" ;

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function isInsert($table,$iduser){
        $iduser = $this->prepareData($iduser);

        $this->sql = "SELECT * FROM " .$table. " WHERE ID_USER = '" .$iduser. "'";

        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if($row == 0)
            return true;
        else return false;
    }

    function getMoney($table, $iduser){
        $iduser = $this->prepareData($iduser);
        $this->sql = "select * from " . $table. " WHERE ID_USER = '" .$iduser."'";
        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
            }
            return $output;
        }
        return null;
    }

    function updateIncome($iduser, $totalmoney){
        $iduser = $this->prepareData($iduser);
        $totalmoney = $this->prepareData($totalmoney);

        $this->sql = "UPDATE income set TOTAL_INCOME = '" .$totalmoney. "' WHERE ID_USER ='" .$iduser. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function updateSpending($iduser, $totalmoney){
        $iduser = $this->prepareData($iduser);
        $totalmoney = $this->prepareData($totalmoney);

        $this->sql = "UPDATE spending set TOTAL_SPENDING ='" .$totalmoney. "' WHERE ID_USER ='" .$iduser. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else return false;
    }

    function updateIncomeByValue($iduser, $money){
        $iduser = $this->prepareData($iduser);
        $money = $this->prepareData($money);

        $this->sql = "UPDATE income set TOTAL_INCOME = TOTAL_INCOME + '" .$money. "' WHERE ID_USER ='" .$iduser. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function updateSpendingByValue($iduser, $money){
        $iduser = $this->prepareData($iduser);
        $money = $this->prepareData($money);

        $this->sql = "UPDATE spending set TOTAL_SPENDING = TOTAL_SPENDING + '" .$money. "' WHERE ID_USER ='" .$iduser. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    ////////////////////////////////////////////////////
    //IncomeDetail && Spending Detail
    function insertIncomeDetail($idmoney, $money, $idcategory, $description, $date, $image, $audio){
        $idmoney = $this->prepareData($idmoney);
        $money = $this->prepareData($money);
        $idcategory = $this->prepareData($idcategory);
        $description = $this->prepareData($description);
        $date = $this->prepareData($date);
        $image = $this->prepareData($image);
        $audio = $this->prepareData($audio);

        $this->sql =
            "INSERT INTO incomedetail (ID_INCOME, MONEY, ID_INCOMECATEGORY, DESCRIPTION, DATE, IMAGE, AUDIO) VALUES ('"
                .$idmoney. "','" .$money. "','" .$idcategory. "','" .$description. "','" .$date. "','" .$image. "','" .$audio."')";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function insertSpendingDetail($idmoney, $money, $idcategory, $description, $date, $image, $audio){
        $idmoney = $this->prepareData($idmoney);
        $money = $this->prepareData($money);
        $idcategory = $this->prepareData($idcategory);
        $description = $this->prepareData($description);
        $date = $this->prepareData($date);
        $image = $this->prepareData($image);
        $audio = $this->prepareData($audio);

        $this->sql =
            "INSERT INTO spendingdetail (ID_SPENDING, MONEY, ID_SPENDINGCATEGORY, DESCRIPTION, DATE, IMAGE, AUDIO) VALUES ('"
            .$idmoney. "','" .$money. "','" .$idcategory. "','" .$description. "','" .$date. "','" .$image. "','" .$audio."')";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function updateIncomeDetail($idmoneydetail,$idmoney, $money, $idcategory, $description, $date, $image, $audio){
        $idmoneydetail = $this->prepareData($idmoneydetail);
        $idmoney = $this->prepareData($idmoney);
        $money = $this->prepareData($money);
        $idcategory = $this->prepareData($idcategory);
        $description = $this->prepareData($description);
        $date = $this->prepareData($date);
        $image = $this->prepareData($image);
        $audio = $this->prepareData($audio);

        $this->sql =
            "UPDATE incomedetail SET MONEY = '" .$money. "', ID_INCOMECATEGORY = '" .$idcategory. "', DESCRIPTION = '" .$description.
                                            "', DATE = '" .$date. "', IMAGE = '" .$image. "', AUDIO = '" .$audio.
                        "' WHERE ID_INCOMEDETAIL ='" .$idmoneydetail."'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function updateSpendingDetail($idmoneydetail,$idmoney, $money, $idcategory, $description, $date, $image, $audio){
        $idmoneydetail = $this->prepareData($idmoneydetail);
        $idmoney = $this->prepareData($idmoney);
        $money = $this->prepareData($money);
        $idcategory = $this->prepareData($idcategory);
        $description = $this->prepareData($description);
        $date = $this->prepareData($date);
        $image = $this->prepareData($image);
        $audio = $this->prepareData($audio);

        $this->sql =
            "UPDATE spendingdetail SET MONEY = '" .$money. "', ID_SPENDINGCATEGORY = '" .$idcategory. "', DESCRIPTION = '" .$description.
                "', DATE = '" .$date. "', IMAGE = '" .$image. "', AUDIO = '" .$audio.
            "' WHERE ID_SPENDINGDETAIL ='" .$idmoneydetail."'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function getIncomeDetail($idmoney, $idcategory, $month, $year){
        $idmoney = $this->prepareData($idmoney);
        $idcategory = $this->prepareData($idcategory);
        $month = $this->prepareData($month);
        $year = $this->prepareData($year);

        $this->sql = "SELECT * from incomedetail WHERE ID_INCOME = '" .$idmoney. "', ID_INCOMECATEGORY = '" .$idcategory.
            "', MONTH(DATE) ='" .$month. "', YEAR(DATE) = '" .$year. "'";

        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
            }
            return $output;
        }
        return null;
    }

    function getSpendingDetail($idmoney, $idcategory, $month, $year){
        $idmoney = $this->prepareData($idmoney);
        $idcategory = $this->prepareData($idcategory);
        $month = $this->prepareData($month);
        $year = $this->prepareData($year);

        $this->sql = "SELECT * FROM incomedetail WHERE ID_SPENDING = '" .$idmoney. "', ID_SPENDINGCATEGORY = '" .$idcategory.
            "', MONTH(DATE) ='" .$month. "', YEAR(DATE) = '" .$year. "'";

        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
            }
            return $output;
        }
        return null;
    }

    ////////////////////////////////////////////////////
    //Goal
    function insertGoal($iduser, $name, $description, $moneygoal, $imagegoal, $finish, $moneysaving){
        $iduser = $this->prepareData($iduser);
        $name = $this->prepareData($name);
        $description = $this->prepareData($description);
        $moneygoal = $this->prepareData($moneygoal);
        $imagegoal = $this->prepareData($imagegoal);
        $finish = $this->prepareData($finish);
        $moneysaving = $this->prepareData($moneysaving);

        $this->sql =
            "INSERT INTO goal(ID_USER, NAME_GOAL, DESCRIPTION_GOAL, MONEY_GOAL, IMAGE_GOAL, FINISH, MONEY_SAVING) VALUES ('"
                .$iduser. "','" .$name. "','" .$description. "','" .$moneygoal. "','" .$imagegoal. "','" .$finish. "','" .$moneysaving. "')";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function getGoal($iduser){
        $iduser = $this->prepareData($iduser);

        $this->sql = "SELECT * from goal WHERE ID_USER = '" .$iduser. "'";

        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
            }
            return $output;
        }
        return null;
    }

    function updateGoal($idgoal, $name, $description, $moneygoal, $imagegoal, $finish, $moneysaving){
        $idgoal = $this->prepareData($idgoal);
        $name = $this->prepareData($name);
        $description = $this->prepareData($description);
        $moneygoal = $this->prepareData($moneygoal);
        $imagegoal = $this->prepareData($imagegoal);
        $finish = $this->prepareData($finish);
        $moneysaving = $this->prepareData($moneysaving);

        $this->sql = "UPDATE goal SET NAME_GOAL = '" .$name. "', DESCRIPTION_GOAL = '" .$description. "', MONEY_GOAL = '" .$moneygoal.
            "', IMAGE_GOAL = '" .$imagegoal. "', FINISH = '" .$finish. "', MONEY_SAVING = '" .$moneysaving.
            "', WHERE ID_GOAL = '" .$idgoal. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function  updateSavingGoal($idgoal,$moneysaving){
        $idgoal = $this->prepareData($idgoal);
        $moneysaving = $this->prepareData($moneysaving);

        $this->sql = "UPDATE goal SET MONEY_SAVING = MONEY_SAVING + '" .$moneysaving. "' WHERE ID_GOAL = '" .$idgoal. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    ////////////////////////////////////////////////////
    //Saving
    function insertSaving($iduser){
        $iduser = $this->prepareData($iduser);
        $totalsaving = 0;
        $totalincome = 0;
        $totalspending = 0;
        $totalday = 0;

        $this->sql =
            "INSERT INTO saving(ID_USER, TOTAL_SAVING, TOTAL_INCOME, TOTAL_SPENDING, TOTAL_DAY) VALUES ('" .$iduser.
             "','" .$totalsaving. "','" .$totalincome. "','" .$totalspending. "','" .$totalday. "')";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function getSaving($iduser){
        $iduser = $this->prepareData($iduser);

        $this->sql = "SELECT * FROM saving WHERE ID_USER = '" .$iduser. "'";

        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
            }
            return $output;
        }
        return null;
    }

    function updateSaving($iduser, $totalsaving, $totalincome, $totalspending){
        $iduser = $this->prepareData($iduser);
        $totalsaving = $this->prepareData($totalsaving);
        $totalincome = $this->prepareData($totalincome);
        $totalspending = $this->prepareData($totalspending);

        $this->sql = "UPDATE saving SET TOTAL_SAVING = '" .$totalsaving.
            "', TOTAL_INCOME = '" .$totalincome. "', TOTAL_SPENDING ='" .$totalspending.
            "' WHERE ID_USER = '" .$iduser."'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function updateSavingInResetDay($idsaving){
        $idsaving = $this->prepareData($idsaving);

        $this->sql = "UPDATE saving SET TOTAL_DAY = 1 WHERE ID_SAVING = '" .$idsaving. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function updateSavingInDayIncrease1($idsaving){
        $idsaving = $this->prepareData($idsaving);

        $this->sql = "UPDATE saving SET TOTAL_DAY = TOTAL_DAY + 1 WHERE ID_SAVING = '" .$idsaving. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    ////////////////////////////////////////////////////
    //Saving Detail
    function insertSavingDetail($idsaving, $moneyincomeperday, $moneyspendingperday, $date){
        $idsaving = $this->prepareData($idsaving);
        $moneyincomeperday = $this->prepareData($moneyincomeperday);
        $moneyspendingperday = $this->prepareData($moneyspendingperday);
        $moneysavingperday = $moneyincomeperday - $moneyspendingperday;
        $date = $this->prepareData($date);

        $this->sql =
            "INSERT INTO savingdetail(ID_SAVING, SAVING_PER_DAY, INCOME_PER_DAY, SPENDING_PER_DAY, 	DATE) VALUES ('"
                .$idsaving. "','" .$moneysavingperday. "','" .$moneyincomeperday. "','" .$moneyspendingperday. "','".$date."')";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }

    function getSavingDetail($idsaving){
        $idsaving = $this->prepareData($idsaving);

        $this->sql = "SELECT * FROM savingdetail WHERE ID_SAVING = '" .$idsaving. "'";

        $result = mysqli_query($this->connect, $this->sql);
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $output[] = $row;
            }
            return $output;
        }
        return null;
    }

    function updateSavingDetail($idsavingdetail, $moneyincomeperday, $moneyspendingperday, $date){
        $idsavingdetail = $this->prepareData($idsavingdetail);
        $moneyincomeperday = $this->prepareData($moneyincomeperday);
        $moneyspendingperday = $this->prepareData($moneyspendingperday);
        $moneysavingperday = $moneyincomeperday - $moneyspendingperday;
        $date = $this->prepareData($date);

        $this->sql = "UPDATE savingdetail SET SAVING_PER_DAY = '" .$moneysavingperday. "', INCOME_PER_DAY ='" .$moneyincomeperday.
            "', SPENDING_PER_DAY = '" .$moneyspendingperday. "', DATE = '" .$date.
            "' WHERE ID_SAVINGDETAIL = '" .$idsavingdetail. "'";

        if(mysqli_query($this->connect, $this->sql)){
            return true;
        }else {
            echo "Error: " . mysqli_error($this->connect). "<br>" ;
            return false;
        }
    }
}
?>
