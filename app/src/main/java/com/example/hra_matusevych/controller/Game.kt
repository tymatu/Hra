package com.example.hra_matusevych.controller

import com.example.hra_matusevych.model.*
import kotlin.random.Random

class Game(val heroName:String) {

    private var width = 20
    private var height = 10
    private var numForests = 4
    private var gamePlan = GamePlan(width, height, numForests)
    var hero = Hero(name = heroName, position = gamePlan.generateRandomPositionOnMeadow())
    private var command: String = ""
    var numEnemies = 5
    var enemies = arrayListOf<GameObject>()
    var gameObjects = arrayListOf<GameObject>()
    var items = arrayListOf<Item>()
    var score: Int = 0

    init {
        gameObjects.add(hero)
        generateEnemies()
        generateItems()
    }


    private fun getSurroundingDescription(): String {
        val description = java.lang.StringBuilder("")
        val item = getItemOnGameField(hero.position,items)
        val enemy = getEnemyOnGameField(hero.position, enemies)
        if (enemy is Enemy && ! enemy.isDead()) description.append("\n Pozor ${enemy.name}.\n")
        if(item is Item && !item.isCollected) description.append("\n Hele, tady se válí ${item.name}, muzes ho sebrat!")

        enumValues<Direction>().forEach {
            description.append("Na ${it.description} je ${gamePlan.getGameField(
                Position(hero.position,it)
            ).terrain.description}.")
        }

        return description.toString()
    }

    fun getObjectOnPosition (direction: Direction): String {
        val newPosition = Position(hero.position, direction)
        val enemy = getEnemyOnGameField(newPosition, enemies)
        val item = getItemOnGameField(newPosition, items)

        if (enemy is Enemy) {
            return if (enemy.isDead()) {
                "Hrob"
            } else {
                enemy.name
            }
        } else if (item is Item && ! item.isCollected) {
            return item.name
        }

        return gamePlan.getGameField(newPosition).terrain.toString()
    }

    private fun runCommand(command: String): String {
        score += 1
        val enemy = getEnemyOnGameField(hero.position, enemies)
        val item = getItemOnGameField(hero.position, items)

        when (command) {
            "stav" -> return (hero.toString())
            "mapa" -> gamePlan.map(gameObjects)
            "utok" -> return if (enemy != null) {
                hero.attack(enemy)
            } else
                ""
            "seber" -> return if (item != null) {
                hero.pickUpItem(item)
            } else
                ""

            Direction.NORTH.command -> return hero.go(Direction.NORTH)
            Direction.EAST.command -> return hero.go(Direction.EAST)
            Direction.SOUTH.command -> return hero.go(Direction.SOUTH)
            Direction.WEST.command -> return hero.go(Direction.WEST)
            Direction.NORTHEAST.command -> return hero.go(Direction.NORTHEAST)
            Direction.NORTHWEST.command -> return hero.go(Direction.NORTHWEST)
            Direction.SOUTHEAST.command -> return hero.go(Direction.SOUTHEAST)
            Direction.SOUTHWEST.command -> return hero.go(Direction.SOUTHWEST)
            "kacejw" -> return hero.cutDown(Direction.NORTH, gamePlan)
            "kaceja" -> return hero.cutDown(Direction.WEST, gamePlan)
            "kacejs" -> return hero.cutDown(Direction.SOUTH, gamePlan)
            "kacejd" -> return hero.cutDown(Direction.EAST, gamePlan)
            "kacejwa" -> return hero.cutDown(Direction.NORTHWEST, gamePlan)
            "kacejwd" -> return hero.cutDown(Direction.NORTHEAST, gamePlan)
            "kacejsa" -> return hero.cutDown(Direction.SOUTHWEST, gamePlan)
            "kacejsd" -> return hero.cutDown(Direction.SOUTHEAST, gamePlan)

        }
        return ""
    }

    fun runRound (direction : Direction) : String {
        val message = StringBuilder()
        val command = getCommand (direction)

        if (command != "") {
            message.append(runCommand(command))
        }
        message.append(enemyAttack())
        hero.heroHealing()
        return message.toString()
    }

    private fun getCommand(direction: Direction) : String {
        if (direction == Direction.NOMOVE) {
            val enemy = getEnemyOnGameField(hero.position, enemies)
            val item = getItemOnGameField(hero.position, items)

            if (enemy is Enemy && !enemy.isDead()) {
                return "utok"
            } else if (item is Item && !item.isCollected) {
//                return item.name.lowercase()
                return "seber"
            }
        } else {
            when (gamePlan.getGameField(Position(hero.position, direction)).terrain) {
                Terrain.MEADOW, Terrain.BRIDGE -> return direction.command
                Terrain.FOREST -> return "kacej"+direction.command
                Terrain.RIVER, Terrain.BORDER -> return ""
            }
        }
        return ""
    }

    private fun generateEnemies() {
        var enemy: Enemy
        repeat(numEnemies) {
            enemy = generateEnemy()
            enemies.add(enemy)
            gameObjects.add(enemy)
        }
    }

    private fun generateEnemy(): Enemy {
        var health = Random.nextInt(from = 20, until = 45)
        var attack = Random.nextDouble(from = 4.0, until = 7.5)
        var defense = Random.nextDouble(from = 0.3, until = 0.7)
        return Enemy(
            name = "Skeleton",
            position = gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            health = health.toDouble(),
            attack = attack,
            defense = defense
        )
    }

    private fun getEnemyOnGameField (position: Position, gameObjects: ArrayList<GameObject>) : Enemy? {
        for (obj in gameObjects ) {
            if (obj is Enemy) {
                if (obj.position == position) {
                    return obj
                }
            }
        }
        return null
    }

    private fun enemyAttack() : String {
        val enemy = getEnemyOnGameField(hero.position, enemies)

        if (enemy is Enemy) {
            if (!enemy.isDead()) {
                return(enemy.attack(hero))
            }
        }
        return ""
    }

    private fun allEnemiesDead() : Boolean {
        for (enemy in enemies) {
            if (enemy is Enemy && ! enemy.isDead()) return false
        }
        return true
    }

    private fun getItemOnGameField (position: Position, gameObjects: ArrayList<Item>) : Item?{
        for (obj in gameObjects ) {
            if (obj is Item) {
                if (obj.position == position) {
                    return obj
                }
            }
        }
        return null
    }
    fun isGameFinished(): String {
        if (hero.isDead()) return "Jsi mrtvý."
        if (allEnemiesDead()) return "Všichni nepřátelé jsou mrtví. Vyhrál jsi. Potřeboval jsi $score tahů."
        if (command == "konec") return "Konec hry."
        return ""
    }

    private fun generateItems() {
        var item = Item(
            "Mec",
            gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            false,
            0.0,
            4.0,
            1.0,
            0.0
        )
        items.add(item)
        gameObjects.add(item)
        item = Item(
            "Dyka",
            gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            false,
            0.0,
            2.0,
            1.0,
            0.0,
        )
        items.add(item)
        gameObjects.add(item)
        item = Item(
            "Stit",
            gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            false,
            0.0,
            0.0,
            2.0,
            0.0,
        )
        items.add(item)
        gameObjects.add(item)
        item = Item(
            "Helma",
            gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            false,
            0.0,
            0.0,
            1.0,
            0.0,
        )
        items.add(item)
        gameObjects.add(item)
        item = Item(
            "Brneni",
            gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            false,
            0.0,
            0.0,
            3.0,
            0.0,
        )
        items.add(item)
        gameObjects.add(item)
        item = Item(
            "Lekarna",
            gamePlan.generateFreeRandomPositionOnMeadow(gameObjects),
            false,
            0.0,
            0.0,
            0.0,
            1.0
        )
        items.add(item)
        gameObjects.add(item)
    }

}
