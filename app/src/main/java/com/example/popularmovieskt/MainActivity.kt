package com.example.popularmovieskt

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popularmovieskt.model.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val API_KEY = "" //TODO Enter your theMovieDb API Key here
    val API_URL = "https://api.themoviedb.org/3/movie/popular"

    lateinit var movieAdapter: MovieAdapter
    var mMoviesList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieAdapter = MovieAdapter(mMoviesList, this@MainActivity)
        movies_recycler_view.layoutManager = GridLayoutManager(this, 2)
        movies_recycler_view.adapter = movieAdapter

        requestMovies(API_URL)
    }

    private fun requestMovies(url: String) {
        val client = AsyncHttpClient()
        val params = RequestParams().apply{
            put("api_key", API_KEY)
        }



        client.get(url, params, object : TextHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, res: String) {
                mMoviesList.clear()
                mMoviesList.addAll(MoviesJsonUtils.parseMoviesJson(res)!!)
                movieAdapter.notifyDataSetChanged()

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, res: String, t: Throwable) {
                Toast.makeText(applicationContext, "Failed!" + res, Toast.LENGTH_LONG).show()
            }
        })
    }

}
