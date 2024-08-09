package com.example.hra_matusevych

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hra_matusevych.controller.*
import com.example.hra_matusevych.model.*
import com.example.hra_matusevych.view.*
import com.example.hra_matusevych.databinding.ActivityGameBinding
import java.util.LinkedList

class GameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding
    private lateinit var game: Game
    private var heroName:String= "Hrdina"
    private val logs = LinkedList(listOf(""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.extras != null) {
            heroName = intent.extras!!.getString("heroName").toString()
        }
        game = Game(heroName)
        refreshHeroStats()
        refreshGameFields()

        binding.imgNorth.setOnClickListener {runRound(Direction.NORTH)}
        binding.imgNorthEast.setOnClickListener {runRound(Direction.NORTHEAST)}
        binding.imgEast.setOnClickListener {runRound(Direction.EAST)}
        binding.imgSouthEast.setOnClickListener {runRound(Direction.SOUTHEAST)}
        binding.imgSouth.setOnClickListener {runRound(Direction.SOUTH)}
        binding.imgSouthWest.setOnClickListener {runRound(Direction.SOUTHWEST)}
        binding.imgWest.setOnClickListener {runRound(Direction.WEST)}
        binding.imgNorthWest.setOnClickListener {runRound(Direction.NORTHWEST)}
        binding.imgCenter.setOnClickListener {runRound(Direction.NOMOVE)}
        binding.logRecycler.layoutManager = LinearLayoutManager(this)
        binding.logRecycler.adapter = LogAdapter(logs)
        binding.itemsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.itemsRecycler.adapter = ItemAdapter (game.hero.items)
    }

    private fun runRound(direction: Direction) {
        var message = game.runRound(direction)
        if (message.isNotEmpty()) {
            logs.push (message)
            binding.logRecycler.adapter?.notifyDataSetChanged()
        }

        message = game.isGameFinished()
        if (message.isNotEmpty()) {
            AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Konec hry")
                .setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
        refreshHeroStats()
        refreshGameFields()
        binding.itemsRecycler.adapter?.notifyDataSetChanged()
    }

    private fun refreshHeroStats () {
        binding.txtHeroName.text = game.hero.name
        binding.txtScore.text = game.score.toString()
        binding.txtHealth.text= "%.2f".format(game.hero.health)
        binding.txtAttack.text= "%.2f".format(game.hero.attack)
        binding.txtDefence.text= "%.2f".format(game.hero.defense)
        binding.txtHealing.text= "%.2f".format(game.hero.healing)
        binding.txtKilled.text = game.hero.kills.toString()
    }

    private fun refreshGameFields() {
        binding.imgNorth.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.NORTH)).imgResource)

        binding.imgNorthEast.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.NORTHEAST)).imgResource)

        binding.imgEast.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.EAST)).imgResource)

        binding.imgSouthEast.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.SOUTHEAST)).imgResource)

        binding.imgSouth.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.SOUTH)).imgResource)

        binding.imgSouthWest.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.SOUTHWEST)).imgResource)

        binding.imgWest.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.WEST)).imgResource)

        binding.imgNorthWest.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.NORTHWEST)).imgResource)

        binding.imgCenter.setImageResource(
            Icons.valueOf(game.getObjectOnPosition(Direction.NOMOVE)).imgResource)
    }
}