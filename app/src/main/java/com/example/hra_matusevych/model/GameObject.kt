package com.example.hra_matusevych.model

abstract class GameObject {
    open lateinit var name : String
    abstract var position: Position

}