package com.delarosa.detailsurprise.ui.video

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.delarosa.detailsurprise.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.fragment_video.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel


class VideoFragment : Fragment() {

    private val viewModel: VideoViewModel by currentScope.viewModel(this)
    private var player: SimpleExoPlayer? = null
    private var playbackPosition = 0L
    private var currentWindow = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()
                ?.let {
                    findNavController().navigate(R.id.action_video_to_info)
                }
        })

        viewModel.model.observe(this, Observer(::updateUi))


        next?.setOnClickListener {
            viewModel.buttonClicked()
        }

        replay?.setOnClickListener {
            viewModel.replayButtonClicked()
        }

        downloadButton?.setOnClickListener {
            viewModel.downloadButtonClicked()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun updateUi(model: VideoViewModel.UiModel) {
        when (model) {
            is VideoViewModel.UiModel.ShowVideo -> {
                showOptions(false)
                setVideo(model.video)
            }
            is VideoViewModel.UiModel.DownloadVideo -> {
                downloadVideo(model.video)
            }
        }
    }

    private fun setVideo(path: String) {
        val mediaSource: MediaSource =
            ExtractorMediaSource.Factory(
                DefaultHttpDataSourceFactory("exoplayer-codelab")
            ).createMediaSource(Uri.parse(path))
        player = context?.let { ExoPlayerFactory.newSimpleInstance(it) }
        player?.run {
            playerView?.player = player
            prepare(mediaSource, true, false)
            seekTo(currentWindow, playbackPosition)
            playWhenReady = true
            repeatMode = SimpleExoPlayer.REPEAT_MODE_OFF
        }

        player?.addListener(object : Player.DefaultEventListener() {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_BUFFERING -> {
                    }
                    Player.STATE_READY -> {
                    }
                    Player.STATE_ENDED -> {
                        showOptions(true)
                    }
                }
            }
        })
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = currentPosition
            currentWindow = currentWindowIndex
            playWhenReady = playWhenReady
            release()
            player = null
        }
    }

    private fun showOptions(show: Boolean) {
        if (show) {
            detailVideo?.visibility = View.VISIBLE
            next?.isEnabled = true
            replay?.isEnabled = true
            downloadButton?.isEnabled = true
        } else {
            detailVideo?.visibility = View.GONE
            next?.isEnabled = false
            replay?.isEnabled = false
            downloadButton?.isEnabled = false
        }
    }

    private fun downloadVideo(path: String) {
        /*       val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(path))
                   .setTitle("surprise")
                   .setDescription("Downloading")
                   .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                   .setDestinationUri(Uri.fromFile(file))
                   .setAllowedOverMetered(true)
                   .setAllowedOverRoaming(true)
                   .setDestinationInExternalPublicDir(
                       Environment.DIRECTORY_PICTURES,
                       File.separator + DIR_NAME + File.separator + "surprise");

               val downloadId: Long = mDownloadManager.enqueue(request)*/
    }

}
