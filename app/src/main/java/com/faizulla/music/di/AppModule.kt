package com.faizulla.music.di

import android.content.Context
import androidx.room.Room
import com.faizulla.music.remote.repo.ApiRepository
import com.faizulla.music.remote.repo.ApiRepositoryImpl
import com.faizulla.music.utility.Constants.BASE_URL
import com.faizulla.music.utility.Constants.DATABASE_NAME
import com.faizulla.music.data.local.MusicDao
import com.faizulla.music.data.local.PlaylistDatabase
import com.faizulla.music.remote.SpotifyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context):PlaylistDatabase = Room.databaseBuilder(
        context,PlaylistDatabase::class.java,DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesDAo(database: PlaylistDatabase):MusicDao{
        return  database.musicDao()
    }

    @Provides
    @Singleton
    fun provideSpotifyApi():SpotifyApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .build().create(SpotifyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiRepo(api: SpotifyApi): ApiRepository {
        return ApiRepositoryImpl(api)
    }

}