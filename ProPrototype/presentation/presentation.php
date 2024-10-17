<?php
require('../Entities/book.php');
require('../Servase/BookService.php');
class Presentation
{
  public function view()
  {
    $classdata = new DataDAO();
    $data = $classdata->getData();
    foreach ($data as $dt) {
      echo 'Id : ' . $dt->getId() . "\n";
      echo 'Title : ' . $dt->getTitle() . "\n";
      echo 'ISBN : ' . $dt->getISBN() . "\n";
    }
  }


  public function add()
  {
    $ISBN = ask('enter ISBN : ');
    $title = ask('enter title : ');
    if ($ISBN == 'back' || $title == "back") {
      return;
    }
    $classData = new Entities($ISBN, $title);
    $classDAO = new DataDAO;
    $classDAO->setData($classData);


    $animation = [
      "🔄 Connecting...",
      "✅ Connected Successfully!",
      "🎉 Operation Completed!",
      "⚙️ Processing..."
    ];

    $screenWidth = 80;
    $color = "\033[0;32m";

    foreach ($animation as $message) {
      echo $color . str_pad($message, $screenWidth, ' ', STR_PAD_BOTH) . "\033[0m\n";
      usleep(500000);
    }

    echo $color . str_pad("✨ Thank you for using our application! ✨", $screenWidth, ' ', STR_PAD_BOTH) . "\033[0m\n";
  }
}
