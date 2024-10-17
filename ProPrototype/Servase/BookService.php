<?php 
require('../DB/DataBase.php');
class DataDAO{
    public function getData(){
$databaseClass = new DataBase();
return $databaseClass->array;
    }
    public function setData($book){
        $databaseClass = new DataBase();
        $databaseClass->array[]=$book;
        $databaseClass->save();
    }
}
 ?>