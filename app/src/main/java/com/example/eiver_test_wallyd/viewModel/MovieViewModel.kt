package com.example.eiver_test_wallyd.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eiver_test_wallyd.model.MoviesResponse
import com.example.eiver_test_wallyd.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

var movieList = MutableLiveData<MoviesResponse>()
    fun getMovies(apiKey: String, page: Int) {
         movieRepository.getMovie(apiKey, page).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                     movieList.postValue(it)

            },{

            })
    }
}