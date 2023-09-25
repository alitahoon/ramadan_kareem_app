package com.example.ramadan_kareem.ui

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.example.domain.entity.quran.Ayah
import com.example.domain.entity.quran.Surah
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.FragmentSurahAyasViewerBinding
import com.example.ramadan_kareem.databinding.FragmentSurahViewerBinding
import com.example.ramadan_kareem.util.AyaCustomAdapter
import com.example.ramadan_kareem.util.AyahItemListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Surah_ayas_viewer(private val surahFromQuran: Surah) : BottomSheetDialogFragment(),AyahItemListener {

    private var binding:FragmentSurahAyasViewerBinding?=null
    private val surahAyasViewerViewmodel:Surah_ayas_viewer_Viewmodel by activityViewModels()
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSurahAyasViewerBinding.inflate(inflater,container,false)
            .apply {
                this.surah=surahFromQuran
            }
        setAdapterItems()

        return binding!!.root
    }

    private fun setAdapterItems() {
        val adapter = AyaCustomAdapter(requireContext(),surahFromQuran,this)
        binding!!.surahAyaViewrRcv.adapter=adapter
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        val window: Window? = dialog.window
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
        return dialog
    }
    companion object {

    }

    override fun onBtnPlayAyaClicked(ayah: Ayah) {
        //first we will get the link for aya audio
        surahAyasViewerViewmodel.getAyaAudioLink(ayah.number)
        lifecycleScope.launch{
            surahAyasViewerViewmodel.ayaAudioLink.collect{
                // Replace with your audio URL
                val audioUrl = it
                // Start loading audio using a coroutine
                CoroutineScope(Dispatchers.IO).launch {
                    mediaPlayer = loadAudio(audioUrl)
                    playAudio(binding!!.root)
                }
            }
        }
    }
    private suspend fun loadAudio(audioUrl: String): MediaPlayer? {
        return try {
            MediaPlayer().apply {
                setDataSource(audioUrl)
                prepare()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun playAudio(view: View) {
        mediaPlayer?.start()
    }

    override fun onBtnCopyAyahClicked(ayah: Ayah) {
    }

    override fun onBtnShareAyaClicked(ayah: Ayah) {
    }

    override fun onBtnSaveAyahClicked(ayah: Ayah) {

    }
}