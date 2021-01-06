package com.example.eiver_test_wallyd

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.adapter.MoviesAdapter
import com.example.eiver_test_wallyd.databinding.MoviesFragmentBinding
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel

class MoviesFragment : Fragment() {
    private val movieViewModel: MoviesViewModel by activityViewModels()
    private lateinit var binding: MoviesFragmentBinding
    lateinit var movieAdapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MoviesAdapter()
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
            movieAdapter.onMovieClick = {
                movieViewModel.getMovieDetail(it.id)
                movieViewModel.getMovieVideos(it.id)
                Log.d("MovieId", it.id.toString())
                displayMovieDetailFragment(it)
            }
        }
    }

    private fun displayMovieDetailFragment(movie: Movie) {
        activity?.supportFragmentManager?.commit {
            replace(R.id.fragment_container_view, MovieDetailFragment.newInstance(movie))
            addToBackStack(MovieDetailFragment.toString())
        }
    }

    private fun setUpObserver() {
        movieViewModel.movieList.observe(viewLifecycleOwner, Observer {
            movieAdapter.movieList = it.results
            movieAdapter.notifyDataSetChanged()
        })
    }


}