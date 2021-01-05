package com.example.eiver_test_wallyd.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eiver_test_wallyd.Constant.ApiKey.apiKey
import com.example.eiver_test_wallyd.model.MovieDetailResponse
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var compositeDisposable = CompositeDisposable()
    var movieList = MutableLiveData<MoviesResponse>()
    var movieDetails = MutableLiveData<MovieDetailResponse>()

    fun getMovies( page: Int) {
        compositeDisposable.add(
            movieRepository.getMovie(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieList.postValue(it)
                }, {

                })
        )

    }

    fun getMovieDetail(movieId: Long) { //TODO remove key and put in Constant
        compositeDisposable.add(
            movieRepository.getMovieDetails(movieId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieDetails.postValue(it)

                }, {
                    Log.d("wallydSyh", it.localizedMessage)
                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}