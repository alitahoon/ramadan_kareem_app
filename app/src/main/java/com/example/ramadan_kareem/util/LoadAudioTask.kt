package com.example.ramadan_kareem.util
import android.media.MediaPlayer
import android.os.AsyncTask
import android.util.Log
import java.io.IOException
class LoadAudioTask(private val audioUrl: String) : AsyncTask<Void, Void, MediaPlayer>() {

    override fun doInBackground(vararg params: Void): MediaPlayer? {
        val mediaPlayer = MediaPlayer()

        try {
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.prepare()
        } catch (e: IOException) {
            Log.e("LoadAudioTask", "Error loading audio: ${e.message}")
            mediaPlayer.release()
            return null
        }

        return mediaPlayer
    }
}