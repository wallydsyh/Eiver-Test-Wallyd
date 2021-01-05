package com.example.eiver_test_wallyd.repository

import com.example.eiver_test_wallyd.api.ApiHelper

class MovieRepository(private val apiHelper: ApiHelper) {
     fun getMovie(apiKey: String, page: Int) = apiHelper.getMovies(apiKey, page)
}