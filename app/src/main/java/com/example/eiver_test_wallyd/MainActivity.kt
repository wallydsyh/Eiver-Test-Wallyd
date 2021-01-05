package com.example.eiver_test_wallyd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.eiver_test_wallyd.api.ApiHelper
import com.example.eiver_test_wallyd.api.ApiServiceImpl
import com.example.eiver_test_wallyd.databinding.ActivityMainBinding
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import com.example.eiver_test_wallyd.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        moviesViewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(ApiServiceImpl()))).get(
            MoviesViewModel::class.java
        )
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view,MoviesFragment())
            }
        }


    }
}