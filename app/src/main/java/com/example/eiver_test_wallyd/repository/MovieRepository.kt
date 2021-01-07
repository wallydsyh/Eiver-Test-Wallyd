package com.example.eiver_test_wallyd.repository

import com.example.eiver_test_wallyd.api.ApiHelper
import com.example.eiver_test_wallyd.model.MovieDetails
import com.example.eiver_test_wallyd.utils.Resource

class MovieRepository(private val apiHelper: ApiHelper) {
    suspend fun getMovie(apiKey: String, page: Int) = apiHelper.getMovies(apiKey, page)
    suspend fun getMovieDetails(movieId: Long, apiKey: String) = apiHelper.getMovieDetails(movieId, apiKey)
    suspend fun getVideos(movieId: Long, apiKey: String) = apiHelper.getVideos(movieId, apiKey)
}