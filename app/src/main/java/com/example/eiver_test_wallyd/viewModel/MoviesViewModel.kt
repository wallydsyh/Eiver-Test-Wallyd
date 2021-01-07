package com.example.eiver_test_wallyd.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eiver_test_wallyd.Constant.ApiKey.apiKey
import com.example.eiver_test_wallyd.model.MovieDetails
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.model.VideosResponse
import com.example.eiver_test_wallyd.repository.MovieRepository
import com.example.eiver_test_wallyd.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var compositeDisposable = CompositeDisposable()
    var movieList = MutableLiveData<Resource<MoviesResponse>>()
    var movieDetails = MutableLiveData<Resource<MovieDetails>>()
    var movieVideos = MutableLiveData<Resource<VideosResponse>>()

    fun getMovies(page: Int) {
        compositeDisposable.add(
            movieRepository.getMovie(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieList.postValue(Resource.success(it))
                }, {
                    movieList.postValue(Resource.error(it.localizedMessage, null))
                })
        )

    }

    fun getMovieDetails(movieId: Long) {
        compositeDisposable.add(
            movieRepository.getMovieDetails(movieId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieDetails.postValue(Resource.success(it))

                }, {
                    movieDetails.postValue(Resource.error(it.localizedMessage, null))

                })
        )

    }

    fun getVideos(movieId: Long) {
        compositeDisposable.add(
            movieRepository.getVideos(movieId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieVideos.postValue(Resource.success(it))
                }, {
                    movieVideos.postValue(Resource.error(it.localizedMessage, null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}