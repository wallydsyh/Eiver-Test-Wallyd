package com.example.eiver_test_wallyd.api

import androidx.lifecycle.MutableLiveData
import com.example.eiver_test_wallyd.model.MovieDetails
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.model.VideosResponse
import com.example.eiver_test_wallyd.utils.Resource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): MovieDetails

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): VideosResponse


}