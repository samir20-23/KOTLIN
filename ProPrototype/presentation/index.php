<?php

require('./presentation.php');
function ask($ask)
{
    echo $ask;
    return trim(fgets(STDIN));
}

function start()
{
    function LOGO()
    {

        $colors = [
            "\033[0;31m",
            "\033[0;32m",
            "\033[0;33m",
            "\033[0;34m",
            "\033[0;35m",
            "\033[0;36m",
            "\033[1;31m",
            "\033[1;32m",
            "\033[1;33m",
            "\033[1;34m",
            "\033[1;35m",
            "\033[1;36m",
        ];


        $reset = "\033[0m";

        function getRandomColor($colors)
        {
            return $colors[array_rand($colors)];
        }

        $textColor = getRandomColor($colors);

        $word = "
                ===========================================================================================
                  AAA     PPPPP     PPPPP    L       III     CCC     AAA    TTTTT    III     OOO     N    N
                 A   A    P    P    P    P   L        I     C       A   A     T       I     O   O    N N  N
                 AAAAA    PPPPP     PPPPP    L        I     C       AAAAA     T       I     O   O    N  N N
                 A   A    P         P        L        I     C       A   A     T       I     O   O    N   NN
                 A   A    P         P        LLLLL   III     CCC    A   A     T      III     OOO     N    N
                 ==========================================================================================
        ";

        echo $textColor  . $word . $reset;
    }
    LOGO();
    $ax = false;
    while (!$ax) {




        echo "++++++++++++++++++++++++++++++++++++++++\n";
        echo "enter [a]  [add book]" . "\n";
        echo "enter [v] [view books]" . "\n";
        echo "enter [d] [delete text]" . "\n";
        echo "enter [ex] [closs application]" . "\n";

        echo "++++++++++++++++++++++++++++++++++++++++\n";


        $question = ask('write  : ');
        switch ($question) {
            case 'v':
                $view = new Presentation();
                $view->view();
                break;
            case 'a':
                $view = new Presentation();
                $view->add();
                break;
            case 'd':

                $iddelete = ask('enter id delete : ');
                // $view = new Presentation();
                // $view->delete($iddelete);
                break;
            case 'exit':
                $ax = true;
                break;

            default:
                echo "error switch .\n";
                break;
        }
    }
}
start();
