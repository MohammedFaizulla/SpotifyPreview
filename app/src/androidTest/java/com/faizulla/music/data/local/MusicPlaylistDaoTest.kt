package com.faizulla.music.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.faizulla.music.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@SmallTest
class MusicPlaylistDaoTest {

    @get:Rule
    var executorRule = InstantTaskExecutorRule()

    private lateinit var db:PlaylistDatabase
    private lateinit var musicDao: MusicDao


    @Before
    fun setUp(){
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            PlaylistDatabase::class.java).allowMainThreadQueries().build()

        musicDao = db.musicDao()
    }

    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun insertPlaylist() = runTest {
        val playlist = MusicPlaylist("Google","Anirudh")
        musicDao.insertPlaylist(playlist)
        val getAllPlaylist = musicDao.getAllPlaylist().getOrAwaitValue ()
        assertThat(getAllPlaylist.contains(playlist))
    }

}