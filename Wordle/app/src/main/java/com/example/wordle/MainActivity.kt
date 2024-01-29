package com.example.wordle

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan

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

                    if (guessResult.toString() == wordToGuess) {
                        correctGuess = true
                        displayCorrectAnswer(textViewResult)
                        hideInputAndSubmit(editTextGuess, buttonSubmit, resetButton)
                    } else if (guessCounter > 3) {
                        displayCorrectAnswer(textViewResult)
                        hideInputAndSubmit(editTextGuess, buttonSubmit, resetButton)
                    }

                    editTextGuess.text.clear()
                    hideKeyboard()
                } else {
                    showToast("Please enter a 4-letter word.")
                }
            }
        }

        resetButton.setOnClickListener {
            resetGame(editTextGuess, buttonSubmit, textViewResult, resetButton)
        }
    }

    private fun checkGuess(guess: String): SpannableString {
        val spannableStringBuilder = SpannableStringBuilder()

        for (i in 0 until guess.length) {
            val color = when {
                guess.length > i && guess[i] == wordToGuess[i] -> Color.GREEN
                guess.length > i && guess[i] in wordToGuess -> Color.rgb(255, 165, 0)
                else -> Color.RED
            }

            val spannableString = SpannableString(guess[i].toString())
            spannableString.setSpan(
                ForegroundColorSpan(color),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableStringBuilder.append(spannableString)
        }

        return SpannableString.valueOf(spannableStringBuilder)
    }

    private fun displayGuessResult(
        textViewResult: TextView,
        userGuess: String,
        guessResult: SpannableString
    ) {
        val currentText = textViewResult.text.toString()
        val newText = "\nGuess $guessCounter: $userGuess\nGuess $guessCounter Check: "

        if (!currentText.contains(newText)) {
            textViewResult.append(newText)
        }

        textViewResult.append(guessResult)
    }


    private fun displayCorrectAnswer(textViewResult: TextView) {
        hideKeyboard()
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

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
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
