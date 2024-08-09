package com.example.hra_matusevych.model


data class Hero(override var name: String = "Hrdina",
                override var position: Position) : Character() {

    var kills: Int = 0
    var items = arrayListOf<Item>()

    override var health: Double = 100.0
        set(value) {
            field = if (value > 100.0) {
                100.0
            } else {
                value
            }
        }
        get(){
            var h = field
            for (item in items) h += item.health
            return h
        }
    override var attack: Double = 7.0
        get(){
            var a = field
            for (item in items) a += item.attack
            return a
        }
    override var defense: Double = 0.0
        get(){
            var d = field
            for (item in items) d += item.defense
            return d
        }
    var healing: Double = 0.5
        get(){
            var h = field
            for (item in items) h += item.healing
            return h
        }


    constructor(name: String, position: Position, health: Double, attack: Double, defense: Double, healing: Double) : this(name, position) {
        this.health = health
        this.attack = attack
        this.defense = defense
        this.healing = healing
    }

    fun go(direction: Direction): String {
        position.x += direction.relativeX
        position.y += direction.relativeY
        return "Jdu na ${direction.description}"
    }

    fun heroHealing() {
        health += healing
    }

    override fun attack(enemy: Character): String {
        val result = super.attack(enemy)
        if (enemy.isDead()) kills += 1
        return result
    }

    fun cutDown(direction: Direction, gamePlan: GamePlan): String {
        val gameField = gamePlan.getGameField(Position(this.position, direction))
        gameField.terrain = Terrain.MEADOW
        return "Les na ${direction.description} je nyní vykácen."
    }

    fun pickUpItem(item: Item) : String{
        items.add(item)
        item.isCollected = true
        return "Předmět ${item.name} je nyní ve vašem inventáři!"
    }

    override fun toString(): String {
        val description = StringBuilder("")
        description.append("Stav hrdiny\n")
        description.append("===========\n")
        description.append("Jméno        $name \n")
        description.append("Zdraví:      " + "%.2f".format(health) + "\n")
        description.append("Útok:        " + "%.2f".format(attack) + "\n")
        description.append("Obrana:      " + "%.2f".format(defense) + "\n")
        description.append("Uzdravování: " + "%.2f".format(healing) + "\n")
        description.append("Zabití: $kills\n")

        return description.toString()
    }
}
