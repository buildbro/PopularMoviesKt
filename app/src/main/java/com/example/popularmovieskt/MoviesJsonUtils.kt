package com.example.popularmovieskt

import android.util.Log
import com.example.popularmovieskt.model.Movie
import org.json.JSONException
import org.json.JSONObject


object MoviesJsonUtils {
    val RESULT_FIELD = "results"
    val TITLE_FIELD = "original_title"
    val ID_FIELD = "id"
    val OVERVIEW_FIELD = "overview"
    val IMAGE_FIELD = "poster_path"
    val VOTE_AVERAGE_FIELD = "vote_average"
    val RELEASE_DATE = "release_date"

    fun parseMoviesJson(json: String?): ArrayList<Movie>? {
        if (json == null) {
            return null
        }

        try {
            val jsonObject = JSONObject(json)

            val moviesJsonArray = jsonObject.optJSONArray(RESULT_FIELD)

            val moviesArray = ArrayList<Movie>()
            for (i in 0 until moviesJsonArray.length()) {
                val originalTitle = moviesJsonArray.optJSONObject(i).optString(TITLE_FIELD)
                val id = moviesJsonArray.optJSONObject(i).optString(ID_FIELD)
                val overview = moviesJsonArray.optJSONObject(i).optString(OVERVIEW_FIELD)
                val image = "https://image.tmdb.org/t/p/w185//" + moviesJsonArray.optJSONObject(i).optString(IMAGE_FIELD)
                val voteAverage = moviesJsonArray.optJSONObject(i).optString(VOTE_AVERAGE_FIELD)
                val releaseDate = moviesJsonArray.optJSONObject(i).optString(RELEASE_DATE)

                moviesArray.add(Movie(originalTitle, id, overview, image, voteAverage, releaseDate))
            }

            return moviesArray

        } catch (e: JSONException) {
            Log.e("MoviesJSONUtils", "JSON error: $e")
            return null
        }

    }
}