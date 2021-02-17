package com.example.eiver_test_wallyd.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.adapter.MovieDetailsAdapter
import com.example.eiver_test_wallyd.databinding.FragmentMovieDetailBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.ImageUtils
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel

private const val ARG_MOVIE = "arg_movie"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private lateinit var movieDetailsAdapter: MovieDetailsAdapter
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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

        moviesViewModel.videos.observe(viewLifecycleOwner, {
            movieDetailsAdapter.submitList(it.results)
        })
        moviesViewModel.videos.observe(viewLifecycleOwner, {
            movieDetailsAdapter.submitList(it.results)

        })
        moviesViewModel.movieDetails.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                mainActivity.updateLoadingIndicatorVisibility(false,
                    binding.loadingIndicator)
                binding.textViewTitle.text = it.body()?.title
                binding.synopsis.text = it.body()?.overview
                val imageMovie = it.body()?.posterPath?.let { it1 -> ImageMovie(it1).large }
                ImageUtils().displayImageFromUrl(
                    binding.root.context,
                    imageMovie.toString(),
                    binding.imageViewPoster
                )
            } else {
                Toast.makeText(context, "error ${it.errorBody()}", Toast.LENGTH_LONG).show()
            }
        })

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