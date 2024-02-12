package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flixster.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, FlixsterFragment())
        fragmentTransaction.commit()
    }
}
