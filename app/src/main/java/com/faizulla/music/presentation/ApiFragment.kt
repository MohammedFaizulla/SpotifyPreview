package com.faizulla.music.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faizulla.music.R
import com.faizulla.music.databinding.FragmentApiBinding
import com.faizulla.music.remote.dataModel.Item
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApiFragment : Fragment(R.layout.fragment_api) {
    private var _mBinding: FragmentApiBinding? = null
    private val mBinding
        get() = _mBinding!!
    private val mViewModel: MusicViewModel by viewModels()
    private lateinit var mTracks:List<Item>
    private lateinit var mMediaPlayer :MediaPlayer
    private var mCurrentTrackIndex: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _mBinding = FragmentApiBinding.bind(view)
        mMediaPlayer = MediaPlayer()
        mViewModel.getResponseSpotify()

        mViewModel.response.observe(viewLifecycleOwner) {response->
            mTracks = response.albums.first().tracks.items
            val isPlayable = response.albums.last().is_playable
            val artist = response.albums.first().artists.first().name
            val imageUri = response.albums.first().images.first().url
            if(isPlayable &&mTracks.isNotEmpty()){
                d(TAG,"Size of the track:${mTracks.size}")
                mCurrentTrackIndex = 0
                playTrack(mCurrentTrackIndex)
            }

            Glide.with(requireContext())
                .load(imageUri)
                .into(mBinding.ivMusicImage)

            mBinding.btnFwd.setOnClickListener {
                playNextTrack()
            }
            mBinding.btnBwd.setOnClickListener {
                playPreviousTrack()
            }

            d(TAG, "Api data $response")
        }
    }
    private fun playTrack(index:Int){
        if(index >= 0 && index < mTracks.size){
            val previewUri = mTracks[index].preview_url
            val artist = mTracks[index].artists.first().name
            mBinding.tvArtist.text = artist
            mMediaPlayer.reset()
            mMediaPlayer.setAudioAttributes(AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
            try {
                mMediaPlayer.setDataSource(previewUri)
                mMediaPlayer.prepare()
                mMediaPlayer.start()
            }catch (e:Exception){
                d(TAG,"Exception:${e.message}")
            }
        }
    }

    private fun playNextTrack(){
        if(mCurrentTrackIndex < mTracks.size - 1){
            mCurrentTrackIndex ++
            playTrack(mCurrentTrackIndex)
        }else{
            Toast.makeText(requireContext(),"No more tracks",Toast.LENGTH_SHORT).show()
            d(TAG,"No tracks available")
        }
    }

    private fun playPreviousTrack(){
        if(mCurrentTrackIndex <mTracks.size -1){
            mCurrentTrackIndex --
            playTrack(mCurrentTrackIndex)
        }else{
            Toast.makeText(requireContext(),"No more tracks",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMediaPlayer.stop()
        mMediaPlayer.reset()
        mMediaPlayer.release()

    }

    companion object {
        private val TAG: String = ApiFragment::class.java.simpleName
    }
}