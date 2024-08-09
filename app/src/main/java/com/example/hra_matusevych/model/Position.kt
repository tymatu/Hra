package com.example.hra_matusevych.model

data class Position (var x: Int, var y: Int) {
    fun isFree (gameObjects: ArrayList<GameObject>): Boolean {
        for (gameObject in gameObjects) {
            if (gameObject.position == this) {
                return false
            }
        }
        return true
    }

    constructor(position: Position, direction: Direction) : this(0,0) {
        var position= Position(position.x + direction.relativeX, position.y + direction.relativeY)
        this.x = position.x
        this.y = position.y
    }

}