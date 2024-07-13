package com.faizulla.music.presentation

import android.content.IntentFilter
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.faizulla.music.utility.Constants
import com.faizulla.music.listener.MediaControllerListener
import com.faizulla.music.broadcastReceiver.MediaControllerReceiver
import com.faizulla.music.R
import com.faizulla.music.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationFragment : Fragment(R.layout.fragment_notification) {
    private var _mBinding :FragmentNotificationBinding?= null
    private val mBinding
        get() = _mBinding!!

    private var mPlaybackJob: Job? = null
    private lateinit var mMediaController: MediaController
    private var mPlaybackState: PlaybackState? = null
    private var mIsSeeking = false
    private var mIsPlayBack = false
    private val mViewModel : MusicViewModel by viewModels()
    private var mMediaControllerReceiver = MediaControllerReceiver.getInstance()


    private var mMediaControllerListener = object : MediaControllerListener {
        override fun mediaSessionToken(token: MediaSession.Token) {
            mMediaController = MediaController(requireContext(), token)
            val metadata = mMediaController.metadata
            if (metadata != null) {
                val title = metadata.getString(MediaMetadata.METADATA_KEY_TITLE)
                val album = metadata.getString(MediaMetadata.METADATA_KEY_ALBUM)
                val duration = metadata.getLong(MediaMetadata.METADATA_KEY_DURATION) ?: 0
                d(TAG, "Title: $title, Album: $album  ,Duration : $duration")
                mBinding.tvMusicTitle.text = title
                mBinding.tvArtist.text = album
                mPlaybackJob?.cancel()
                mPlaybackJob = CoroutineScope(Dispatchers.IO).launch {
                    while (isActive) {
                        delay(500)
                        activity?.runOnUiThread {
                            mPlaybackState = mMediaController.playbackState
                            val playbackPosition = mPlaybackState?.position ?: 0
                            d(TAG, "Playback Position: $playbackPosition")
                            val progress = (playbackPosition * 100 / duration).toInt()
                            mBinding.seekBar.progress = progress
                            d(
                                TAG,
                                "playback position: $playbackPosition, Duration: $duration"
                            )
                            updatePlaybackTime(playbackPosition, duration)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _mBinding = FragmentNotificationBinding.bind(view)


        mBinding.btnPlayPause.setOnClickListener {
            if (mPlaybackState?.state == PlaybackState.STATE_PLAYING) {
                mMediaController.transportControls.pause()
                mBinding.btnPlayPause.setImageResource(R.drawable.ic_play)
            } else if (mPlaybackState?.state == PlaybackState.STATE_PAUSED) {
                mBinding.btnPlayPause.setImageResource(R.drawable.ic_pause)
                mMediaController.transportControls.play()
            }
        }

        mBinding.btnFwd.setOnClickListener {
            if (this::mMediaController.isInitialized) {
                mMediaController.transportControls.skipToNext()
            }else{
                Toast.makeText(requireContext(), "Please play the music", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnBwd.setOnClickListener {
            if (this::mMediaController.isInitialized) {
                mMediaController.transportControls.skipToPrevious()
            }else {
                Toast.makeText(requireContext(), "Please play the music", Toast.LENGTH_SHORT).show()
            }
            }

        mBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    d(TAG, "$mIsSeeking")
                    mIsSeeking = true
                    val duration =
                        mMediaController.metadata?.getLong(MediaMetadata.METADATA_KEY_DURATION) ?: 0
                    d(TAG, "duration: $duration")
                    val newPosition = duration * progress / 100
                    mMediaController.transportControls.seekTo(newPosition)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mIsSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mIsSeeking = false
            }
        })
        registerReceiver()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsPlayBack = false
        _mBinding = null

    }

    private fun updatePlaybackTime(position: Long, duration: Long) {
        val startTime = formatTime(position)
        val endTime = formatTime(duration)
        d(TAG, "start time : $startTime")
        mBinding.tvStartTime.text = startTime
        d(TAG, "end time : $endTime")
        mBinding.tvEndTime.text = endTime
    }

    private fun formatTime(timeMillis: Long): String {
        val seconds = (timeMillis / 1000) % 60
        val minutes = ((timeMillis / (1000 * 60)) % 60)
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun registerReceiver() {
        val intent = IntentFilter(Constants.ACTION_MEDIA_SESSION)
        mMediaControllerReceiver.setMediaSessionInterface(mMediaControllerListener)
       activity?. registerReceiver(mMediaControllerReceiver, intent)
    }

    companion object {
     private val TAG:String = NotificationFragment::class.java.simpleName
    }
}