package com.faizulla.music.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.util.Log.d
import com.faizulla.music.utility.Constants.ACTION_MEDIA_SESSION
import com.faizulla.music.utility.Constants.MEDIA_SESSION_TOKEN
import com.faizulla.music.listener.MediaControllerListener

class MediaControllerReceiver : BroadcastReceiver() {
    private var mMediaControllerListener: MediaControllerListener? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_MEDIA_SESSION -> {
                val mediaSessionToken =
                    intent.getParcelableExtra<MediaSession.Token>(MEDIA_SESSION_TOKEN)
                d(TAG, "Media session token: $mediaSessionToken")
                if (mediaSessionToken != null) {
                    mMediaControllerListener?.mediaSessionToken(mediaSessionToken)
                }
            }
        }
    }

    fun setMediaSessionInterface(mediaControllerListener: MediaControllerListener) {
        mMediaControllerListener = mediaControllerListener
    }

    companion object {
        @Volatile
        private var mInstance: MediaControllerReceiver? = null
        private val TAG:String = MediaControllerListener::class.java.simpleName
        @JvmStatic
        fun getInstance(): MediaControllerReceiver = mInstance ?: synchronized(this) {
            mInstance ?: MediaControllerReceiver().also {
                mInstance = it
            }
        }
    }
}