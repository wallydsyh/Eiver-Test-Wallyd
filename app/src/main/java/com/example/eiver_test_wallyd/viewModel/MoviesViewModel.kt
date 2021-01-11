package com.example.eiver_test_wallyd.viewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.eiver_test_wallyd.Constant.ApiKey.API_KEY
import com.example.eiver_test_wallyd.MoviesPagingSource
import com.example.eiver_test_wallyd.SearchMoviesPagingSource
import com.example.eiver_test_wallyd.model.*
import com.example.eiver_test_wallyd.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 100)) {
            MoviesPagingSource(movieRepository)
        }.flow.cachedIn(viewModelScope)
    }

    fun searchMovie(query: String): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 100, enablePlaceholders = true)) {
            SearchMoviesPagingSource(movieRepository, query)
        }.flow.cachedIn(viewModelScope)
    }

    suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return movieRepository.getMovieDetails(movieId, API_KEY)
    }

    suspend fun getVideos(movieId: Long): VideosResponse {
        return movieRepository.getVideos(movieId, API_KEY)
    }
}