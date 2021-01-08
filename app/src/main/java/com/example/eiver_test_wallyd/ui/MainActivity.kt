package com.example.eiver_test_wallyd.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.api.ApiHelper
import com.example.eiver_test_wallyd.api.ApiServiceImpl
import com.example.eiver_test_wallyd.databinding.ActivityMainBinding
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import com.example.eiver_test_wallyd.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpViewModel()
        if (savedInstanceState == null) {
            displaySplashScreen()
        }
    }

    private fun setUpViewModel() {
        moviesViewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(ApiServiceImpl()))).get(
                MoviesViewModel::class.java
            )
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun updateLoadingIndicatorVisibility(enable: Boolean, loadingIndicator: ProgressBar) {
        when {
            enable -> loadingIndicator.visibility = View.VISIBLE
            else -> loadingIndicator.visibility = View.GONE
        }
    }

    fun displayMovieDetailFragment(movie: Movie) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, MovieDetailFragment.newInstance(movie))
            addToBackStack(MovieDetailFragment.toString())
        }
    }

    private fun displaySplashScreen() {
        supportFragmentManager.commit {
            add(R.id.fragment_container_view, SplashScreenFragment())
        }
    }
}