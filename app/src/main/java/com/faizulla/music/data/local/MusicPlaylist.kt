package com.faizulla.music.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_playlist")
data class MusicPlaylist(
    val title:String,
    val artist:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null
)
