package com.xihad.androidutils.utils

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer

object MediaPlayerUtil {

    private var mPlayer: MediaPlayer? = null

    private var length: Int = 0


    fun startMediaPlayer(activity: Activity, file: Int, isLooping: Boolean = false) {
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(activity, file)
        }
        mPlayer?.start()
        length = 0
        if (isLooping) {
            mPlayer?.isLooping = true
        } else {
            mPlayer?.setOnCompletionListener {
                stopMediaPlayer()
            }
        }
    }


    fun pauseMediaPlayer() {
        mPlayer?.let {
            it.pause()
            length = it.currentPosition
        }
    }

    fun resumeMediaPlayer() {
        mPlayer?.let {
            it.seekTo(length)
            it.start()
        }
    }


    fun stopMediaPlayer() {
        mPlayer?.let {
            it.release()
            mPlayer = null
        }
    }


    fun playTapSound(activity: Activity) {
        val am = activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val vol = 0.5f
        am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol)
    }


    fun playClickSound(activity: Activity) {
        val am = activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val vol = 0.5f
        am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, vol)
    }


}