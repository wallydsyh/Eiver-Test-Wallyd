package com.example.eiver_test_wallyd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.databinding.MovieItemBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.ImageUtils

class MoviesAdapter :
    PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(diffCallback) {
    private lateinit var binding: MovieItemBinding
    var movieList = emptyList<Movie>()
    var onMovieClick: ((Movie) -> Unit)? = null

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_item, parent, false)
        return MoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.itemView.context
        binding.title.text = item?.title
        (context.getString(R.string.release_date) + item?.releaseDate).also {
            binding.date.text = it
        }
        binding.synopsis.text = item?.overview
        val url = item?.posterPath?.let {
            ImageMovie(it).medium
        }
        ImageUtils().displayImageFromUrl(
            context,
            url.toString(),
            binding.poster,
            null
        )
        holder.itemView.setOnClickListener {
            item?.let { it1 -> onMovieClick?.invoke(it1) }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean =
                oldItem.equals(newItem)
        }
    }
}
