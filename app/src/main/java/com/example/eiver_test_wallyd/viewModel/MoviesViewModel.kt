package com.example.eiver_test_wallyd.viewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.eiver_test_wallyd.Constant.ApiKey.API_KEY
import com.example.eiver_test_wallyd.MoviesPagingSource
import com.example.eiver_test_wallyd.model.*
import com.example.eiver_test_wallyd.repository.MovieRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var compositeDisposable = CompositeDisposable()

    val getMovies = Pager(PagingConfig(pageSize = 10, enablePlaceholders = true, maxSize = 200)) {
        MoviesPagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)

    suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return movieRepository.getMovieDetails(movieId, API_KEY)
    }

    suspend fun getVideos(movieId: Long): VideosResponse {
        return movieRepository.getVideos(movieId, API_KEY)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}