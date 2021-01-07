package com.example.eiver_test_wallyd.api

import androidx.lifecycle.MutableLiveData
import com.example.eiver_test_wallyd.model.MovieDetails
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.model.VideosResponse
import com.example.eiver_test_wallyd.utils.Resource


class ApiServiceImpl(
    private val apiService: ApiService = ApiClient().getClient()
) : ApiService {

    override suspend fun getMovies(apiKey: String, page: Int): MoviesResponse {
        return apiService.getMovies(apiKey, page)
    }

    override suspend fun getMovieDetails(movieId: Long, apiKey: String): MovieDetails {
        return apiService.getMovieDetails(movieId, apiKey)
    }

    override suspend fun getVideos(movieId: Long, apiKey: String): VideosResponse {
        return apiService.getVideos(movieId, apiKey)
    }

}