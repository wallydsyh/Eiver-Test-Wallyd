package com.example.eiver_test_wallyd.api


class ApiHelper(private val apiService: ApiService) {
         fun getMovies(apiKey: String, page: Int)= apiService.getMovies(apiKey, page)
         fun getMovieDetails(movieId: Long, apiKey: String)= apiService.getMovieDetails(movieId,apiKey)
         fun getMovieVideos(movieId: Long, apiKey: String)= apiService.getMovieVideos(movieId,apiKey)
}