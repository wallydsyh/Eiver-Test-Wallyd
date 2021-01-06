package com.example.eiver_test_wallyd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.databinding.MovieDetailsItemBinding
import com.example.eiver_test_wallyd.databinding.MovieItemBinding
import com.example.eiver_test_wallyd.model.ImageMovie
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.model.Videos
import com.example.eiver_test_wallyd.utils.ImageUtils

class MovieDetailsAdapter() :
    RecyclerView.Adapter<MovieDetailsAdapter.MoviesViewHolder>(

    ) {
    private lateinit var binding: MovieDetailsItemBinding
    var videosList = emptyList<Videos>()
    var onVideoClick: ((Videos) -> Unit)? = null

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_details_item, parent, false)
        return MoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = videosList[position]
        binding.textViewTrailerName.text = item.name

        holder.itemView.setOnClickListener{
            onVideoClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
