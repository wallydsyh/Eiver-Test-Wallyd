package com.example.eiver_test_wallyd

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.eiver_test_wallyd.api.ApiHelper
import com.example.eiver_test_wallyd.api.ApiServiceImpl
import com.example.eiver_test_wallyd.databinding.ActivityMainBinding
import com.example.eiver_test_wallyd.model.Videos
import com.example.eiver_test_wallyd.viewModel.MoviesViewModel
import com.example.eiver_test_wallyd.viewModel.ViewModelFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class MainActivity : AppCompatActivity() {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding
     lateinit var videoBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var videoBottomSheetLayout: ConstraintLayout
    lateinit var playerView: PlayerView
    private var player: SimpleExoPlayer? = null
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0
    private var playWhenReady: Boolean = true
    private lateinit var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback
   lateinit var youTubePlayer : YouTubePlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        videoBottomSheetLayout = findViewById(R.id.constraint_layout_exoplayer)
        videoBottomSheetBehavior =
            BottomSheetBehavior.from<ConstraintLayout>(videoBottomSheetLayout)
        videoBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        addBottomSheetCallback()


        moviesViewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(ApiServiceImpl()))).get(
                MoviesViewModel::class.java
            )
        moviesViewModel.getMovies(1)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container_view, SplashScreenFragment())
            }
        }
    }


    private fun addBottomSheetCallback() {
        bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Do something for new state
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        releasePlayer()
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Do something for slide offset
            }
        }
        videoBottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    fun initializePlayer(videos: Videos) {
        youTubePlayer.loadVideo(videos.key, 0f)
        videoBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


    internal fun releasePlayer() {
        player?.let {
            playWhenReady = it.playWhenReady
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            it.release()
        }
        videoBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onDestroy() {
        super.onDestroy()
        videoBottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallback)

    }



}