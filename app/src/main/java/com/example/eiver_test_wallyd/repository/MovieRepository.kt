package com.example.eiver_test_wallyd.repository

import com.example.eiver_test_wallyd.api.ApiHelper
import com.example.eiver_test_wallyd.model.MovieDetails
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.model.VideosResponse
import com.example.eiver_test_wallyd.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepository(private val apiHelper: ApiHelper) {
    suspend fun getMovie(apiKey: String, page: Int): MoviesResponse {
        return withContext(Dispatchers.IO) {
            apiHelper.getMovies(apiKey, page)
        }

    }

    suspend fun getMovieDetails(movieId: Long, apiKey: String): Response<MovieDetails> {
        return withContext(Dispatchers.IO) {
            apiHelper.getMovieDetails(movieId, apiKey)
        }
    }


    suspend fun getVideos(movieId: Long, apiKey: String): VideosResponse {
        return withContext(Dispatchers.IO) {
            apiHelper.getVideos(movieId, apiKey)
        }
    }

    suspend fun searchMovie(query: String, page: Int, apiKey: String): MoviesResponse {
        return withContext(Dispatchers.IO) {
            apiHelper.searchMovie(query, page, apiKey)
        }
    }

}