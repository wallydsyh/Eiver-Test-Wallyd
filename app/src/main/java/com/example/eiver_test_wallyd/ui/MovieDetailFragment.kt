package com.example.eiver_test_wallyd.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.adapter.MovieDetailsAdapter
import com.example.eiver_test_wallyd.databinding.FragmentMovieDetailBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.Dialog
import com.example.eiver_test_wallyd.utils.ImageUtils
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import com.example.eiver_test_wallyd.utils.Status

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
        };
    }

    private fun setupObserver() {
        moviesViewModel.movieDetails.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.Loading -> updateLoadingIndicatorVisibility(true)
                Status.Success -> {
                    updateLoadingIndicatorVisibility(false)
                    binding.textViewTitle.text = resource.data?.title
                    binding.synopsis.text = resource.data?.overview
                    val imageMovie = resource.data?.posterPath?.let { it1 -> ImageMovie(it1).large }
                    ImageUtils().displayImageFromUrl(
                        binding.root.context,
                        imageMovie.toString(),
                        binding.imageViewPoster,
                        null
                    )
                }
                Status.Error -> {
                    updateLoadingIndicatorVisibility(false)
                    Dialog().displayDialog(binding.root.context,
                        getString(R.string.error_title),
                        getString(
                            R.string.error_message)).show()
                }
            }

        })

        moviesViewModel.movieVideos.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.Success -> {
                    updateLoadingIndicatorVisibility(false)
                    it.data?.let { videosResponse ->
                        movieDetailsAdapter.videosList = videosResponse.results
                        movieDetailsAdapter.notifyDataSetChanged()
                    }
                }
                Status.Error -> {
                    updateLoadingIndicatorVisibility(false)
                }
            }

        })
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