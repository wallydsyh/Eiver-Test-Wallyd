package com.example.eiver_test_wallyd.api

import com.example.eiver_test_wallyd.model.MovieDetailResponse
import com.example.eiver_test_wallyd.model.MoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Single<MovieDetailResponse>


}