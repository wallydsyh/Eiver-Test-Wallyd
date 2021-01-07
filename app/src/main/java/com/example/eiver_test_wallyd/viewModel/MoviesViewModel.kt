package com.example.eiver_test_wallyd.viewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.eiver_test_wallyd.Constant.ApiKey.apiKey
import com.example.eiver_test_wallyd.MoviesPagingSource
import com.example.eiver_test_wallyd.model.*
import com.example.eiver_test_wallyd.repository.MovieRepository
import com.example.eiver_test_wallyd.utils.Resource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var compositeDisposable = CompositeDisposable()
    var movieList = MutableLiveData<Resource<MoviesResponse>>()
    var movieDetails = MutableLiveData<Resource<MovieDetails>>()
    var movieVideos = MutableLiveData<Resource<VideosResponse>>()

    val getMovies = Pager(PagingConfig(pageSize = 10, enablePlaceholders = true, maxSize = 200)) {
        MoviesPagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)

    suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return movieRepository.getMovieDetails(movieId, apiKey)
    }


    suspend fun getVideos(movieId: Long): VideosResponse {
        return movieRepository.getVideos(movieId, apiKey)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}