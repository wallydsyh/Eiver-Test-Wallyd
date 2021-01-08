package com.example.eiver_test_wallyd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.databinding.MovieDetailsItemBinding
import com.example.eiver_test_wallyd.model.Movie
import com.example.eiver_test_wallyd.model.Videos
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MovieDetailsAdapter(private val lifecycle: Lifecycle) :
    ListAdapter<Videos, MovieDetailsAdapter.MoviesViewHolder>(diffCallback) {
    lateinit var binding: MovieDetailsItemBinding
    var onVideoClick: ((Videos) -> Unit)? = null

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_details_item, parent, false)
        lifecycle.addObserver(binding.youtubePlayerView)
        return MoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onVideoClick?.invoke(item)
        }
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(item.key, 0f)
            }
        })
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Videos>() {
            override fun areItemsTheSame(
                oldItem: Videos,
                newItem: Videos
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Videos,
                newItem: Videos
            ): Boolean =
                oldItem.equals(newItem)
        }
    }

}
