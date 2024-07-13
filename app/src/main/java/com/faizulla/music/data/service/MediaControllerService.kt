package com.faizulla.music.data.service

import android.content.Intent
import android.media.session.MediaSession
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log.d
import com.faizulla.music.utility.Constants.ACTION_MEDIA_SESSION
import com.faizulla.music.utility.Constants.MEDIA_SESSION_TOKEN

class MediaControllerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        val packageName = sbn.packageName   
        val bundle = sbn.tag
        d("Music Listener", "packageName ----- $packageName")
        if (sbn.notification.extras != null && sbn.notification.extras.containsKey("android.mediaSession")) {
            d("Music Listener", "notification channel ${sbn.notification}")
            handleMediaPlaybackState(sbn)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
    }

    private fun handleMediaPlaybackState(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras ?: return
        val mediaSessionToken = extras.getParcelable<MediaSession.Token>("android.mediaSession")
        if (mediaSessionToken != null) {
            val intent = Intent(ACTION_MEDIA_SESSION)
            intent.putExtra(MEDIA_SESSION_TOKEN, mediaSessionToken)
            sendBroadcast(intent)
        }
    }

}