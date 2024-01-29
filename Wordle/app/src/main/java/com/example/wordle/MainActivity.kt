package com.example.wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var wordToGuess: String = ""
    private var guessCounter: Int = 1
    private var correctGuess: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        val editTextGuess = findViewById<EditText>(R.id.editTextGuess)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val resetButton = findViewById<Button>(R.id.buttonReset)

        buttonSubmit.setOnClickListener {
            if (!correctGuess) {
                val userGuess = editTextGuess.text.toString().uppercase()

                if (userGuess.length == 4) {
                    val guessResult = checkGuess(userGuess)
                    displayGuessResult(textViewResult, userGuess, guessResult)
                    guessCounter++

                    if (guessResult == "OOOO") {
                        correctGuess = true
                        displayCorrectAnswer(textViewResult)
                        hideInputAndSubmit(editTextGuess, buttonSubmit, resetButton)
                    } else if (guessCounter > 3) {
                        displayCorrectAnswer(textViewResult)
                        hideInputAndSubmit(editTextGuess, buttonSubmit, resetButton)
                    }

                    editTextGuess.text.clear()
                } else {
                    showToast("Please enter a 4-letter word.")
                }
            }
        }

        resetButton.setOnClickListener {
            resetGame(editTextGuess, buttonSubmit, textViewResult, resetButton)
        }
    }

    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0 until wordToGuess.length) {
            if (guess.length > i && guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess.length > i && guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }

    private fun displayGuessResult(textViewResult: TextView, userGuess: String, guessResult: String) {
        val currentText = textViewResult.text.toString()
        val newText = "$currentText\nGuess $guessCounter: $userGuess\nGuess $guessCounter Check: $guessResult"
        textViewResult.text = newText
    }

    private fun displayCorrectAnswer(textViewResult: TextView) {
        val currentText = textViewResult.text.toString()

        if (correctGuess) {
            val newText = "$currentText\nCongrats, You Solved It!"
            textViewResult.text = newText
        } else {
            val newText = "$currentText\nSorry, Maybe Next Time!\nCorrect Answer: $wordToGuess"
            textViewResult.text = newText
        }
    }

    private fun hideInputAndSubmit(editTextGuess: EditText, buttonSubmit: Button, resetButton: Button) {
        editTextGuess.visibility = View.GONE
        buttonSubmit.visibility = View.GONE
        resetButton.visibility = View.VISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun resetGame(editTextGuess: EditText, buttonSubmit: Button, textViewResult: TextView, resetButton: Button) {
        editTextGuess.visibility = View.VISIBLE
        buttonSubmit.visibility = View.VISIBLE
        resetButton.visibility = View.GONE
        correctGuess = false
        guessCounter = 1
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        textViewResult.text = ""
    }
}
