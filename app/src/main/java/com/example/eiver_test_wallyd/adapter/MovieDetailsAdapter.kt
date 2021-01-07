package com.example.eiver_test_wallyd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eiver_test_wallyd.R
import com.example.eiver_test_wallyd.databinding.MovieDetailsItemBinding
import com.example.eiver_test_wallyd.model.Videos
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieDetailsAdapter :
    RecyclerView.Adapter<MovieDetailsAdapter.MoviesViewHolder>(

    ) {
    private var videoId: String = ""
    var binding: MovieDetailsItemBinding? = null
    var videosList = emptyList<Videos>()
    var onVideoClick: ((Videos) -> Unit)? = null
    var callback = object :
        AbstractYouTubePlayerListener() {
        override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
            youTubePlayer.cueVideo(videoId, 0f)
        }
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_details_item, parent, false)
        return MoviesViewHolder(binding?.root!!)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = videosList[position]
        holder.itemView.setOnClickListener {
            onVideoClick?.invoke(item)
        }
        videoId = item.key
        binding?.youtubePlayerView?.addYouTubePlayerListener(callback)
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
