package com.example.eiver_test_wallyd.api


class ApiHelper(private val apiService: ApiService) {
         suspend fun getMovies(apiKey: String, page: Int)= apiService.getMovies(apiKey, page)
         suspend fun getMovieDetails(movieId: Long, apiKey: String)= apiService.getMovieDetails(movieId,apiKey)
         suspend fun getVideos(movieId: Long, apiKey: String)= apiService.getVideos(movieId,apiKey)
}