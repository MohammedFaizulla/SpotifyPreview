package com.faizulla.music.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylist(musicPlaylist: MusicPlaylist)

    @Delete
    fun deletePlaylist(musicPlaylist: MusicPlaylist)

    @Query("SELECT * FROM music_playlist")
    fun getAllPlaylist():LiveData<List<MusicPlaylist>>
}