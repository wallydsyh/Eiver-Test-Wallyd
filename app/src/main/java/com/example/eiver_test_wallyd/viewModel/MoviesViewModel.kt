package com.example.eiver_test_wallyd.viewModel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.eiver_test_wallyd.Constant.ApiKey.API_KEY
import com.example.eiver_test_wallyd.viewModel.pagingSource.MoviesPagingSource
import com.example.eiver_test_wallyd.viewModel.pagingSource.SearchMoviesPagingSource
import com.example.eiver_test_wallyd.model.*
import com.example.eiver_test_wallyd.repository.MovieRepository
import com.example.eiver_test_wallyd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    var movieDetails = MutableLiveData<Response<MovieDetails>>()
    val videos = MutableLiveData<VideosResponse>()


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


    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId, API_KEY).run {
                movieDetails.postValue(this)
            }
        }
    }

    fun getVideos(movieId: Long) {
        viewModelScope.launch {
            movieRepository.getVideos(movieId, API_KEY).run {
                videos.postValue(this)
            }
        }
    }
}