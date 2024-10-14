package com.example.fundamentals_KOTLIN.tutors


class Class1 {
    init{
        println("class2");
    }
}
class Class2{
    fun play(){
        println("class1");
    }
}

//
fun main () {
    Class1();
    val  myclass = Class2();
    myclass.play();
}
