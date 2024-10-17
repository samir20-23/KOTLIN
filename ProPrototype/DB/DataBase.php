<?php 

class DataBase{
  public $array = [];
  public $path = '../DB/Database.text';
  public function __construct()
  {
    $this->getDataTxt();
  }

  private function getDataTxt(){
if(file_exists($this->path)){
$data = file_get_contents($this->path);
$realData= unserialize($data);
$this->array=$realData;
}
  }
  private function setDataTxt(){
    $data = serialize($this->array);
    $data = file_put_contents($this->path,$data);
  }
  public function save(){
    $this->setDataTxt();
  }

}
?>