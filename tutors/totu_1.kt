package com.example.fundamentals_KOTLIN.tutors

fun main (){
    val num: Int = 22
    if(num == 22) {
        println("true");
    }else if(num == 10){
        println("false");
    }else{
  println("false");
    }

    val  num2 = "string";
    val wheen = when(num2){
        "string" -> "true"
        else->"false"
    }
    println(wheen);

    }

