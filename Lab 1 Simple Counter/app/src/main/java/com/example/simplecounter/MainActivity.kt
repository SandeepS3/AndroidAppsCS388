package com.example.simplecounter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View

var counter = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = counter.toString()

        val upgradeButton = findViewById<Button>(R.id.upgradeBtn)
        val resetButton = findViewById<Button>(R.id.resetBtn)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            counter++
            textView.text = counter.toString()
            if (counter >= 100) {
                upgradeButton.visibility = View.VISIBLE
            }
            if (counter >= 200) {
                resetButton.visibility = View.VISIBLE
            }
        }


        upgradeButton.setOnClickListener {
            upgradeButton.text = "Add 2"
            button.visibility = View.INVISIBLE
            counter += 20
            textView.text = counter.toString()
            if (counter >= 200) {
                resetButton.visibility = View.VISIBLE
            }
        }



        resetButton.setOnClickListener {
            counter = 0
            textView.text = counter.toString()
            upgradeButton.visibility = View.INVISIBLE
            button.visibility = View.VISIBLE
            resetButton.visibility = View.INVISIBLE
        }
    }
}
