<?php
class Entities
{
    public $id;
    public $ISBN;
    public $title;

    public function __construct($ISBN, $title)
    {
        $this->id = time();
        $this->ISBN = $ISBN;
        $this->title = $title;
    }
    public function getId()
    {
        return $this->id;
    }
    public function setId($id)
    {
        $this->id = $id;
    }
    public function getISBN()
    {
        return $this->ISBN;
    }
    public function setISBN($ISBN)
    {
        $this->ISBN = $ISBN;
    }
    public function getTitle()
    {
        return $this->title;
    }
    public function settitle($title)
    {
        $this->title = $title;
    }
}
