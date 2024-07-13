package com.faizulla.music.remote.repo

import com.faizulla.music.remote.dataModel.AlbumResponse

interface ApiRepository {
  suspend fun getSpotifyApi(): AlbumResponse?
}