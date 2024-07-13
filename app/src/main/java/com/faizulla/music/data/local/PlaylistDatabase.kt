package com.faizulla.music.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [MusicPlaylist::class], version = 1)
abstract class PlaylistDatabase :RoomDatabase(){
    abstract fun musicDao():MusicDao
}