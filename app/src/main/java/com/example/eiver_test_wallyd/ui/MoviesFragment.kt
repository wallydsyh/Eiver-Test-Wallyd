package com.example.eiver_test_wallyd.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.adapter.MoviesAdapter
import com.example.eiver_test_wallyd.databinding.MoviesFragmentBinding
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.Dialog
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import com.example.eiver_test_wallyd.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {
    private val movieViewModel: MoviesViewModel by activityViewModels()
    private lateinit var binding: MoviesFragmentBinding
    lateinit var movieAdapter: MoviesAdapter
    private lateinit var mainActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MoviesAdapter()
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        setUpObserver()
    }


    private fun setUpListener() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
            movieAdapter.onMovieClick = { mainActivity.displayMovieDetailFragment(it) }
        }
    }


    private fun setUpObserver() {
        mainActivity.updateLoadingIndicatorVisibility(true, binding.loadingIndicator)
        if (mainActivity.isNetworkConnected()) {
            viewLifecycleOwner.lifecycleScope.launch(Dialog().displayError(binding.root.context)) {
                movieViewModel.getMovies.collectLatest {
                    mainActivity.updateLoadingIndicatorVisibility(false, binding.loadingIndicator)
                    movieAdapter.submitData(it)
                }
            }
        } else {
            mainActivity.updateLoadingIndicatorVisibility(false, binding.loadingIndicator)
            context?.let {
                Dialog().displayDialog(it,
                    context?.getString(R.string.error_title),
                    context?.getString(R.string.error_message)).show()
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        movieAdapter.onMovieClick = null
    }
}