package com.example.eiver_test_wallyd.api

import com.example.eiver_test_wallyd.model.MovieDetails
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.model.VideosResponse
import retrofit2.Response
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
    ): Response<MovieDetails>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): VideosResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MoviesResponse


}