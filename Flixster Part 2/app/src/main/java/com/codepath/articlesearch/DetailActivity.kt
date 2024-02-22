package com.codepath.articlesearch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.articlesearch.Movie
import com.codepath.articlesearch.R


class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bylineTextView: TextView
    private lateinit var abstractTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        bylineTextView = findViewById(R.id.mediaByline)
        abstractTextView = findViewById(R.id.mediaAbstract)

        // TODO: Get the extra from the Intent
        val movie = intent.getSerializableExtra("item") as Movie
        val posterUrl = "https://image.tmdb.org/t/p/w200" + movie.poster_path

        // TODO: Set the title, byline, and abstract information from the article
        titleTextView.text = movie.title
        bylineTextView.text = movie.overview
        abstractTextView.text =
            "Total Votes: " + movie.vote_count.toString() + "   Vote Avg: " + movie.vote_average

        // TODO: Load the media image
        Glide.with(this)
            .load(posterUrl)
            .into(mediaImageView)
    }
}