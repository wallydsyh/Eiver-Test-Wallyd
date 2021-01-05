package com.example.eiver_test_wallyd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.adapter.MoviesAdapter
import com.example.eiver_test_wallyd.databinding.MoviesFragmentBinding
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel

class MoviesFragment : Fragment() {
    private  val movieViewModel: MoviesViewModel by activityViewModels()
    private lateinit var binding: MoviesFragmentBinding
    lateinit var movieAdapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MoviesAdapter()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater ,R.layout.movies_fragment, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
            movieAdapter.onMovieClick = {
                activity?.supportFragmentManager?.commit {
                    replace(R.id.fragment_container_view,MovieDetailFragment.newInstance(it))
                }
            }
        }

        movieViewModel.getMovies( 1)
        movieViewModel.movieList.observe(viewLifecycleOwner, Observer {
            movieAdapter.movieList = it.results
            movieAdapter.notifyDataSetChanged()
        })

    }


}