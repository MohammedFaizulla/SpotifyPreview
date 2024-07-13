package com.faizulla.music.remote

import com.faizulla.music.remote.dataModel.AlbumResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface SpotifyApi {
        @GET("albums/?ids=3IBcauSj5M2A6lTeffJzdv")
        suspend fun getSpotifyApi(
            @Header("x-rapidapi-key") key: String,
            @Header("x-rapidapi-host") host: String
        ): AlbumResponse
}