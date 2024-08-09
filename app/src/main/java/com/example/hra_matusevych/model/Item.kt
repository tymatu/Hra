package com.example.hra_matusevych.model

data class Item( override var name: String,
                 override var position: Position,
                 var isCollected: Boolean,
                 override var health: Double,
                 override var attack: Double,
                 override var defense: Double,
                 var healing: Double
) : Character() {

}
