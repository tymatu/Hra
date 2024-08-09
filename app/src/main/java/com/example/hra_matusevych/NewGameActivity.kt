package com.example.hra_matusevych

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hra_matusevych.databinding.ActivityNewGameBinding

class NewGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {
            if (binding.heroNameEdit.text.isEmpty()) {
                Toast.makeText(this, "Zadejte jméno hrdiny.", Toast.LENGTH_LONG).show()
            } else {
                val i = Intent(this, GameActivity::class.java)
                i.putExtra("heroName", binding.heroNameEdit.text.toString())
                startActivity(i)
            }
        }
    }
}