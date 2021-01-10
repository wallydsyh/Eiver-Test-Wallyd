package com.example.eiver_test_wallyd.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.adapter.MovieDetailsAdapter
import com.example.eiver_test_wallyd.databinding.FragmentMovieDetailBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.Dialog
import com.example.eiver_test_wallyd.utils.ImageUtils
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
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
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(ARG_MOVIE)
        }
        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        movieDetailsAdapter = MovieDetailsAdapter(viewLifecycleOwner.lifecycle)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.supportActionBar?.title = getString(R.string.title_movie_details)
        setupObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieDetailsAdapter
        }
    }

    private fun setupObserver() {
        mainActivity.updateLoadingIndicatorVisibility(true, binding.loadingIndicator)
        if (mainActivity.isNetworkConnected()) {
            viewLifecycleOwner.lifecycleScope.launch(Dialog().displayError(binding.root.context)) {
                movie?.id?.let {
                    moviesViewModel.getMovieDetails(it).run {
                        mainActivity.updateLoadingIndicatorVisibility(false,
                            binding.loadingIndicator)
                        binding.textViewTitle.text = this.title
                        binding.synopsis.text = this.overview
                        val imageMovie = this.posterPath?.let { it1 -> ImageMovie(it1).large }
                        ImageUtils().displayImageFromUrl(
                            binding.root.context,
                            imageMovie.toString(),
                            binding.imageViewPoster
                        )
                    }
                    moviesViewModel.getVideos(it).run {
                        movieDetailsAdapter.submitList(this.results)
                    }
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