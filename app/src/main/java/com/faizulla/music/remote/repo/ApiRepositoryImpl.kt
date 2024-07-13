package com.faizulla.music.remote.repo

import android.util.Log.d
import com.faizulla.music.remote.SpotifyApi
import com.faizulla.music.remote.dataModel.AlbumResponse
import com.faizulla.music.remote.repo.ApiRepository

class ApiRepositoryImpl(private val api: SpotifyApi) : ApiRepository {
    override suspend fun getSpotifyApi(): AlbumResponse? {
        return try{
            api.getSpotifyApi(
                "4dd7c02246msh78b9002059c6478p11c501jsn0ddc90a4554f",
                "spotify23.p.rapidapi.com"
            )
        }catch (e:Exception){
            d("Api"," exception: ${e.message}")
           null
        }

    }
}