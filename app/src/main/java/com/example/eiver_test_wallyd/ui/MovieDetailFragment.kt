package com.example.eiver_test_wallyd.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.Constant.ApiKey
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.adapter.MovieDetailsAdapter
import com.example.eiver_test_wallyd.databinding.FragmentMovieDetailBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.Dialog
import com.example.eiver_test_wallyd.utils.ImageUtils
import com.example.eiver_test_wallyd.utils.Resource
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import com.example.eiver_test_wallyd.utils.Status
import kotlinx.coroutines.*

private const val ARG_MOVIE = "arg_movie"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : Fragment() {
    private var movie: Movie? = null
    private lateinit var binding: FragmentMovieDetailBinding
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private lateinit var movieDetailsAdapter: MovieDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(ARG_MOVIE)
        }
        movieDetailsAdapter = MovieDetailsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieDetailsAdapter
        }
        movieDetailsAdapter.binding?.youtubePlayerView?.let {
            viewLifecycleOwner.lifecycle.addObserver(it)
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            movie?.id?.let {
                moviesViewModel.getMovieDetails(it).run {
                    binding.textViewTitle.text = this.title
                    binding.textViewTitle.text = this.title
                    binding.synopsis.text = this.overview
                    val imageMovie = this.posterPath?.let { it1 -> ImageMovie(it1).large }
                    ImageUtils().displayImageFromUrl(
                        binding.root.context,
                        imageMovie.toString(),
                        binding.imageViewPoster,
                        null
                    )
                }
                moviesViewModel.getVideos(it).run {
                    movieDetailsAdapter.videosList = this.results
                    movieDetailsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun updateLoadingIndicatorVisibility(enable: Boolean) {
        when {
            enable -> binding.loadingIndicator.visibility = View.VISIBLE
            else -> binding.loadingIndicator.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailsAdapter.binding?.youtubePlayerView?.removeYouTubePlayerListener(
            movieDetailsAdapter.callback
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
    }


}