package com.example.eiver_test_wallyd.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.adapter.MoviesAdapter
import com.example.eiver_test_wallyd.adapter.MoviesLoadStateAdapter
import com.example.eiver_test_wallyd.databinding.MoviesFragmentBinding
import com.example.eiver_test_wallyd.utils.Dialog
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {
    private val movieViewModel: MoviesViewModel by activityViewModels()
    private lateinit var binding: MoviesFragmentBinding
    lateinit var movieAdapter: MoviesAdapter
    private lateinit var mainActivity: MainActivity
    private var searchCallback = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            binding.recyclerView.recycledViewPool.clear()
            lifecycleScope.launch {
                when {
                    newText.toString().isBlank() -> {
                        getMovies()
                    }
                    else -> {
                        movieViewModel.searchMovie(newText.toString()).collectLatest {
                            movieAdapter.submitData(it)
                        }
                    }
                }
            }
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MoviesAdapter()
        mainActivity = activity as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_fragment, container, false)
        mainActivity.supportActionBar?.title = getString(R.string.title_movies)
        setHasOptionsMenu(true)
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
            adapter = movieAdapter.withLoadStateFooter(MoviesLoadStateAdapter {
                movieAdapter.retry()
            })
            movieAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            movieAdapter.onMovieClick = { mainActivity.displayMovieDetailFragment(it) }
        }
    }

    private suspend fun getMovies() {
        movieViewModel.getMovies().collectLatest {
            mainActivity.updateLoadingIndicatorVisibility(false, binding.loadingIndicator)
            movieAdapter.submitData(it)
        }
    }

    private fun setUpObserver() {
        mainActivity.updateLoadingIndicatorVisibility(true, binding.loadingIndicator)
        if (mainActivity.isNetworkConnected()) {
            viewLifecycleOwner.lifecycleScope.launch(Dialog().displayError(binding.root.context)) {
                getMovies()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = mainActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(mainActivity.componentName))
            setIconifiedByDefault(false)
            setOnQueryTextListener(searchCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieAdapter.onMovieClick = null
    }
}