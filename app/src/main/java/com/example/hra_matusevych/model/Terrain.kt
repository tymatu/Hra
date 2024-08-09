package com.example.hra_matusevych.model

enum class Terrain (val description: String, val terrainChar: Char) {
    BORDER ("hranice", '#'),
    MEADOW ("louka", ' '),
    FOREST ("les", '|'),
    RIVER ("řeka", '*'),
    BRIDGE ("most", '=')
}