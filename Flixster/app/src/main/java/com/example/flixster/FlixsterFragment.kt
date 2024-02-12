package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers


// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class FlixsterFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flixster_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        // Using the client, perform the HTTP request
        client[
            "https://api.themoviedb.org/3/movie/now_playing?&api_key=$API_KEY",
            params,
            object : JsonHttpResponseHandler()
            { //connect these callbacks to your API call


                /*
                 * The onSuccess function gets called when
                 * HTTP response status is "200 OK"
                 */
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    // The wait for a response is over
                    progressBar.hide()

                    try {
                        // Check if the JSON object is null
                        if (json == null) {
                            Log.e("FlixsterFragment", "JSON response is null")
                            return
                        }

                        // Check if the "results" array exists in the JSON object
                        if (!json.jsonObject.has("results")) {
                            Log.e("FlixsterFragment", "No 'results' array found in JSON response")
                            return
                        }

                        // Retrieve the 'results' JSONArray
                        val resultsArray = json.jsonObject.getJSONArray("results")

                        // Convert the JSONArray to a string
                        val jsonString = resultsArray.toString()

                        // Parse JSON into Models
                        val gson = Gson()
                        val models: List<Flixster> = gson.fromJson(jsonString, object : TypeToken<List<Flixster>>() {}.type)
                        recyclerView.adapter = FlixsterAdapter(models, this@FlixsterFragment)

                        // Look for this in Logcat:
                        Log.d("FlixsterFragment", "Parsed JSON: $resultsArray")
                    } catch (e: Exception) {
                        Log.e("FlixsterFragment", "Error parsing JSON: ${e.message}")
                    }
                }



                /*
                 * The onFailure function gets called when
                 * HTTP response status is "4XX" (eg. 401, 403, 404)
                 */
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("FlixsterFragment", errorResponse)
                    }
                }
            }]


    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: Flixster) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}
