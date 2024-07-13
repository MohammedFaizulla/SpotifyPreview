package com.faizulla.music.presentation

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faizulla.music.remote.repo.ApiRepository
import com.faizulla.music.remote.dataModel.AlbumResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    private val _response  = MutableLiveData<AlbumResponse>()
    val response:LiveData<AlbumResponse>
        get() = _response
    fun getResponseSpotify() {
        viewModelScope.launch {
           val response = apiRepository.getSpotifyApi()
            if(response != null) {
                _response.value = apiRepository.getSpotifyApi()
                d("Api response", "value:${_response.value}")
            }
        }
    }
}