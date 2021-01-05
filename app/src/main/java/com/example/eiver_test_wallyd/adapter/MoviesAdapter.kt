package com.example.eiver_test_wallyd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.databinding.MovieItemBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.utils.ImageUtils

class MoviesAdapter() :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>(

    ) {
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
        val item = movieList[position]
        binding.title.text = item.title
        binding.date.text = item.releaseDate
        binding.synopsis.text = item.overview
        val url = item.posterPath?.let { ImageMovie(it).small }
        ImageUtils().displayImageFromUrl(
            holder.itemView.context,
            url.toString(),
            binding.poster,
            null
        )
        holder.itemView.setOnClickListener{
            onMovieClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}