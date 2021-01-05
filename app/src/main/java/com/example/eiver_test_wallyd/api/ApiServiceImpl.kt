package com.example.eiver_test_wallyd.api

import com.example.eiver_test_wallyd.model.MoviesResponse
import io.reactivex.rxjava3.core.Single


class ApiServiceImpl(
    private val apiService: ApiService = ApiClient().getClient()
) : ApiService {

    override  fun getMovies(apiKey: String, page: Int): Single<MoviesResponse> {
         return  apiService.getMovies(apiKey, page)
    }

}