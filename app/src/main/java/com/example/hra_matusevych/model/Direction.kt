package com.example.hra_matusevych.model

enum class Direction(val relativeY: Int, val relativeX: Int, val description: String, val command: String) {
    NORTH(-1, 0, "sever", "w"),
    SOUTH(1, 0, "jih", "s"),
    EAST(0, 1, "východ", "d"),
    WEST(0, -1, "západ", "a"),
    NORTHWEST(-1, -1, "severozápad", "wa"),
    NORTHEAST(-1, 1, "severovýchod", "wd"),
    SOUTHWEST(1, -1, "jihozápad", "sa"),
    SOUTHEAST(1, 1, "jihovýchod", "sd"),
    NOMOVE (0, 0, "nop", "nop")

}