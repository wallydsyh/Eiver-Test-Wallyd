package com.example.eiver_test_wallyd.api

import com.example.eiver_test_wallyd.model.MovieDetails
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.model.VideosResponse
import io.reactivex.rxjava3.core.Single


class ApiServiceImpl(
    private val apiService: ApiService = ApiClient().getClient()
) : ApiService {

    override fun getMovies(apiKey: String, page: Int): Single<MoviesResponse> {
        return apiService.getMovies(apiKey, page)
    }

    override fun getMovieDetails(movieId: Long, apiKey: String): Single<MovieDetails> {
        return apiService.getMovieDetails(movieId, apiKey)
    }

    override fun getVideos(movieId: Long, apiKey: String): Single<VideosResponse> {
        return apiService.getVideos(movieId, apiKey)
    }

}