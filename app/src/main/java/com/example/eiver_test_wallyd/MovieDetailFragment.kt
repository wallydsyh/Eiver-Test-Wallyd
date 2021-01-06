package com.example.eiver_test_wallyd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eiver_test_wallyd.adapter.MovieDetailsAdapter
import com.example.eiver_test_wallyd.databinding.FragmentMovieDetailBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.ImageUtils
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

// TODO: Rename parameter arguments, choose names that match
private const val ARG_MOVIE = "arg_movie"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    private fun setUpListener() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayout.HORIZONTAL, false)
            adapter = movieDetailsAdapter
            movieDetailsAdapter.onVideoClick = {
                (activity as MainActivity).initializePlayer(it)
            }

        }

    }

    private fun setupObserver() {
        moviesViewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            binding.textViewTitle.text = it.title
            binding.synopsis.text = it.overview
            binding.textViewDate.text = it.releaseDate
            val imageMovie = it.posterPath?.let { it1 -> ImageMovie(it1).large }
            ImageUtils().displayImageFromUrl(
                binding.root.context,
                imageMovie.toString(),
                binding.imageViewPoster,
                null
            )
        })

        moviesViewModel.movieVideos.observe(viewLifecycleOwner, Observer {
            movieDetailsAdapter.videosList = it.results
            movieDetailsAdapter.notifyDataSetChanged()

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailsAdapter.binding?.youtubePlayerView?.removeYouTubePlayerListener(
            movieDetailsAdapter.callback
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(movie: Movie) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
    }


}