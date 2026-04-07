package com.xihad.androidutils.utils

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import java.lang.ref.WeakReference

object MediaPlayerUtil {

    private var mPlayer: MediaPlayer? = null
    private var length: Int = 0

    /**
     * Starts playing an audio resource.
     * Uses applicationContext internally to avoid holding an Activity reference.
     *
     * @param context   Any context — applicationContext is used internally to prevent leaks.
     * @param file      Raw resource ID of the audio file.
     * @param isLooping Whether the audio should loop.
     */
    fun startMediaPlayer(context: Context, file: Int, isLooping: Boolean = false) {
        stopMediaPlayer()
        mPlayer = MediaPlayer.create(context.applicationContext, file)
        mPlayer?.apply {
            isLooping = isLooping
            if (!isLooping) {
                setOnCompletionListener { stopMediaPlayer() }
            }
            start()
        }
        length = 0
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
            try {
                if (it.isPlaying) it.stop()
                it.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            mPlayer = null
        }
    }

    fun isPlaying(): Boolean = mPlayer?.isPlaying == true

    fun playTapSound(context: Context) {
        val am = context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.playSoundEffect(AudioManager.FX_KEY_CLICK, 0.5f)
    }

    fun playClickSound(context: Context) {
        val am = context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, 0.5f)
    }
}
