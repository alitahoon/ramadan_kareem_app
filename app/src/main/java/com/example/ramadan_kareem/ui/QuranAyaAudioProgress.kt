package com.example.ramadan_kareem.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentQuranAyaAudioProgressBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class QuranAyaAudioProgress(private val surehName:String, private val ayehLink:String, private val ayehNumber:Int,
                            override val coroutineContext: CoroutineContext
) : BottomSheetDialogFragment() ,
    CoroutineScope {
    private var binding:FragmentQuranAyaAudioProgressBinding?=null
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
     var duration = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentQuranAyaAudioProgressBinding.inflate(inflater,container,false)
            .apply {
                this.sureh=surehName
                this.ayeh=ayehNumber.toString()
            }
        CoroutineScope(Dispatchers.IO).launch {
            mediaPlayer = loadAudio(ayehLink)
            mediaPlayer?.setOnCompletionListener {
                // Reset the play button and progress bar when audio completes
                isPlaying = false
                duration = 0
                binding!!.quranAyaAudionProgresslinearProgressIndicator.progress = 0
            }
        }

        return binding!!.root
    }
    private suspend fun loadAudio(audioUrl: String): MediaPlayer? {
        return try {
            MediaPlayer().apply {
                setDataSource(audioUrl)
                setOnPreparedListener {
                    // Media player is prepared, start playing audio
                    it.start()
                    startUpdatingProgressBar()
                }
                setOnErrorListener { _, what, extra ->
                    // Print the error code and extra info
                    println("MediaPlayer error: what=$what, extra=$extra")
                    true
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun playAudio(view: View) {
        mediaPlayer?.let { player ->
            if (isPlaying) {
                player.pause()
            } else {
                player.start()
                startUpdatingProgressBar()
            }
            isPlaying = !isPlaying
        }
    }

    private fun startUpdatingProgressBar() {
        launch(Dispatchers.Main) {
            while (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                binding!!.quranAyaAudionProgresslinearProgressIndicator.progress = mediaPlayer!!.currentPosition
                delay(1000)
            }
        }
    }

    companion object {

    }
}