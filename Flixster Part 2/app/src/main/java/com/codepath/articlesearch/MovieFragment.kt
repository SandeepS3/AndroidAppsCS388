package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import okhttp3.Headers


class MoviesFragment : Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.item_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()

        client[
            "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=vote_average.desc&without_genres=99,10755&vote_count.gte=200&api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
            object : JsonHttpResponseHandler()
            { /* * The onSuccess function gets called when * HTTP response status is "200 OK" */
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    // The wait for a response is over
                    progressBar.hide()
                    val gson = Gson()
                    val resultsJSON = json.jsonObject.get("results").toString()
                    val moviesList = gson.fromJson(resultsJSON, Array<Movie>::class.java).toList()

                    // Log.d("Results JSON",resultsJSON)
                    val models: List<Movie> = moviesList.map { movie ->
                        Movie( movie.title, movie.overview, movie.poster_path, movie.id,movie.release_date, movie.vote_average, movie.vote_count)
                    }
                    recyclerView.adapter = MovieRecyclerViewAdapter(models, this@MoviesFragment)
                }

                override fun onFailure( statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable? ) {
                    progressBar.hide()
                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("ERROR", errorResponse)
                    }
                }
            }]

    }

    override fun onItemClick(item: Movie) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("item", item)
        context?.startActivity(intent)
    }

}