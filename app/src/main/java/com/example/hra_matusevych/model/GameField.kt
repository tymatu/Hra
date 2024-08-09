package com.example.hra_matusevych.model

class GameField(var terrain: Terrain) {

    fun isWalkable():Boolean{
        return when (this.terrain){
            Terrain.BRIDGE, Terrain.MEADOW -> true;
            Terrain.BORDER, Terrain.RIVER, Terrain.FOREST -> false;
        }
    }

}