package com.example.eiver_test_wallyd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.adapter.MoviesRxAdapter
import com.example.eiver_test_wallyd.api.ApiHelper
import com.example.eiver_test_wallyd.api.ApiServiceImpl
import com.example.eiver_test_wallyd.databinding.ActivityMainBinding
import com.example.eiver_test_wallyd.viewModel.MovieViewModel
import com.example.eiver_test_wallyd.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAdapter: MoviesRxAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        movieViewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(ApiServiceImpl()))).get(
            MovieViewModel::class.java
        )
        mAdapter = MoviesRxAdapter()

        mBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.recyclerView.adapter = mAdapter

        movieViewModel.getMovies("d36edbc03f7f0cab8cab72580e6d4f79", 1)
        movieViewModel.movieList.observe(this, Observer {
            mAdapter.movieList = it.results
            mAdapter.notifyDataSetChanged()
        })


    }
}