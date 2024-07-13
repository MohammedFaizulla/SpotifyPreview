package com.faizulla.music.listener

import android.media.session.MediaSession

interface MediaControllerListener {
    fun mediaSessionToken(token: MediaSession.Token)
}