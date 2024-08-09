package com.example.hra_matusevych.model

import kotlin.random.Random

data class GamePlan(val width: Int = 20, val height: Int = 10, val numForest: Int = 4) {

    private var gamePlan = Array(height) { Array(width) { GameField(Terrain.MEADOW) } }

    init {
        generateGamePlan()
    }

    private fun generateGamePlan() {
        generateBorder()
        generateRiver()
        repeat(numForest) {
            generateForest()
        }
    }

    private fun generateForest() {
        var centPos = generateRandomPositionOnMeadow()
        gamePlan[centPos.y][centPos.x] = GameField(Terrain.FOREST)

        var x = centPos.x
        var y = centPos.y
        var positionsAround = arrayOf(Position(x + 1, y), Position(x - 1, y),
            Position(x, y + 1), Position(x, y - 1))

        for (element in positionsAround) {
            var position = element
            if (getGameField(position).terrain == Terrain.MEADOW) {
                gamePlan[position.y][position.x].terrain = Terrain.FOREST
            }
        }
    }

    fun generateRandomPositionOnMeadow(): Position {
        var centPos = Position(0, 0)
        while (getGameField(centPos).terrain != Terrain.MEADOW) {
            centPos = generateRandomPosition()
        }
        return centPos
    }


    private fun generateBorder() {

        for (i in 0 until width) {
            gamePlan[0][i] = GameField(Terrain.BORDER)
            gamePlan[height - 1][i] = GameField(Terrain.BORDER)
        }
        for (i in 0 until height) {
            gamePlan[i][0] = GameField(Terrain.BORDER)
            gamePlan[i][width - 1] = GameField(Terrain.BORDER)
        }
    }

    private fun generateRiver() {
        val bridgePosition = generateRandomPosition()
        for (i in 1..height - 2) {
            gamePlan[i][bridgePosition.x] = GameField(Terrain.RIVER)
        }
        gamePlan[bridgePosition.y][bridgePosition.x] = GameField(Terrain.BRIDGE)
    }

    private fun generateRandomCoord(size: Int): Int {
        return Random.nextInt(1, size - 1)
    }

    private fun generateRandomPosition(): Position {
        return Position(generateRandomCoord(width), generateRandomCoord(height))
    }

    fun generateFreeRandomPositionOnMeadow(gameObjects: ArrayList<GameObject>): Position {
        var randomPosition: Position
        do {
            randomPosition = generateRandomPositionOnMeadow()
        } while (!randomPosition.isFree(gameObjects))
        return randomPosition
    }

    fun getGameField(position: Position): GameField {
        return gamePlan[position.y][position.x]
    }

    fun map(gameObjects: ArrayList<GameObject>) {
        for (i in 0 until height) {
            for (j in 0 until width) {
                var char = ""
                for (gameObject in gameObjects) {
                    if (i == gameObject.position.y && j == gameObject.position.x) {
                        if (gameObject is Hero)
                            char = "H "
                        else if (gameObject is Enemy && !gameObject.isDead())
                            char = "N "
                        else if (gameObject is Item && !gameObject.isCollected)
                            char = "P "
                        print(char)
                    }
                }
                if (char == "")
                    print(gamePlan[i][j].terrain.terrainChar + " ")
            }
            println()
        }
    }
}
