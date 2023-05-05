package com.example.aliensmasher.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.example.aliensmasher.R

class MediaPlayerClass(private val context: Context) {
    private var currentIndex = 0
    private var mediaPlayer: MediaPlayer? = null
    private val audioFiles: List<Uri> = listOf(Uri.parse("android.resource://"+R.raw.sound_killer))

    fun play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }

        mediaPlayer?.apply {
            try {
                setDataSource(context, audioFiles[currentIndex])
                prepare()
                start()
            }catch (e: Exception){
                MediaPlayer.create(context, R.raw.sound_killer)
                start()
            }

        }

        mediaPlayer?.setOnCompletionListener {
            playNext()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun playNext() {
        currentIndex = (currentIndex + 1) % audioFiles.size
        stop()
        play()
    }
}