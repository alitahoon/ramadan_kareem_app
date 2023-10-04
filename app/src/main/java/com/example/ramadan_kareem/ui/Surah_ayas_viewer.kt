package com.example.ramadan_kareem.ui

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.domain.entity.quran_audio.Ayah
import com.example.domain.entity.quran_audio.Surah
import com.example.domain.usecase.GetTafsirForAya
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class Surah_ayas_viewer(private val surahFromQuran: Surah) : BottomSheetDialogFragment(),
    AyahItemListener {
    private var audioJob: Job? = null
    private var binding: FragmentSurahAyasViewerBinding? = null
    private val surahAyasViewerViewmodel: Surah_ayas_viewer_Viewmodel by activityViewModels()
    var isFirstTime = true
    private var ayaDuraion: Int = 0
    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private var isExpanded = false
    private var observing = false
    private lateinit var handler: Handler
    private var isDefaultState = true  // Initial state
    private var isAutoPlayNext = false
    private var adapter: AyaCustomAdapter? = null

    @Inject
    lateinit var getTafsirForAya: GetTafsirForAya

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurahAyasViewerBinding.inflate(inflater, container, false)
            .apply {
                this.surah = surahFromQuran
            }
        handler = Handler(Looper.getMainLooper())

        sheetBehavior = BottomSheetBehavior.from(binding!!.playerBottomSheetLayout)


        binding!!.quranAyaAudionProgressBtnAutoPlayNext.setOnClickListener {

            if (isDefaultState) {
                isAutoPlayNext = true
                it.background = requireContext().getDrawable(R.drawable.circle_defult)
            } else {

                isAutoPlayNext = false
                it.background = requireContext().getDrawable(R.drawable.circle)
            }
            isDefaultState = !isDefaultState  // Toggle the state
        }

        binding!!.playerBtnScrollCard.setOnClickListener {
            if (isExpanded) {
                shrinkFab()
            } else if (mediaPlayer != null && !isExpanded) {
                expandFab()

            }
        }
        binding!!.quranAyaAudionProgressBtnNext.setOnClickListener {
            if (currantAya != null) {
                resetMediaPlayer()
                nextAya = surahFromQuran.ayahs[currantAya!!.numberInSurah]
                scrollToCenter(binding!!.surahAyaViewrRcv, nextAya!!.numberInSurah-1)
                binding!!.quranAyaAudionProgressTxtAyaNumber.setText(nextAya!!.numberInSurah.toString())
                lifecycleScope.launch(Dispatchers.IO) {
                    loadAudio(nextAya!!)
                }
            }
        }

        binding!!.quranAyaAudionProgressBtnPrevios.setOnClickListener {
            if (currantAya != null) {
                resetMediaPlayer()
                nextAya = surahFromQuran.ayahs[currantAya!!.numberInSurah-2]
                binding!!.quranAyaAudionProgressTxtAyaNumber.setText(nextAya!!.numberInSurah.toString())
                scrollToCenter(binding!!.surahAyaViewrRcv, nextAya!!.numberInSurah-1)

                lifecycleScope.launch(Dispatchers.IO) {
                    loadAudio(nextAya!!)
                }
            }
        }
        binding!!.quranAyaAudionProgressBtnStop.setOnClickListener {
            resetMediaPlayer()
            currantAya=null
            nextAya=null
            shrinkFab()
        }
        binding!!.root.postDelayed({
            setAdapterItems()
        }, 1000)





        return binding!!.root
    }

    private fun setAdapterItems() {
        adapter = AyaCustomAdapter(getTafsirForAya,requireContext(), surahFromQuran, this, childFragmentManager)
        binding!!.surahAyaViewrRcv.adapter = adapter
        resetRCVLayout()
    }

    private fun resetRCVLayout() {
        binding!!.surahAyaViewrRcv.visibility = View.VISIBLE
        binding!!.facbookAyaShimmer.visibility = View.GONE
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

    fun copyToClipboard(label: String, text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    private fun setObservers(ayah: Ayah) {


//        lifecycleScope.launch {
//            audioJob=surahAyasViewerViewmodel.ayaAudioLink.onEach {
//                Log.d("ggg","ttt")
//                // Replace with your audio URL
//                audioUrl = it
//                // Start loading audio using a coroutine
////                val context = coroutineContext
////                if (audioUrlf!=""){
////                    val bottomSheetFragment = QuranAyaAudioProgress(surahFromQuran.name,audioUrl,ayah.numberInSurah,context!!)
////                    bottomSheetFragment.show(requireActivity().supportFragmentManager, "QuranAyaAudioProgress")
////                }
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    if (audioUrl.isNotEmpty()){
//                        resetMediaPlayer()
//                        mediaPlayer = loadAudio(audioUrl)
//                        playAudio(binding!!.root)
//                    }
//
//                }
//            }.launchIn(this)
//
//        }
    }

    companion object {
        var audioUrl = ""
        var mediaPlayer: MediaPlayer? = null
        var currantAya: Ayah? = null
        var nextAya: Ayah? = null
    }

    override fun onDestroy() {
        super.onDestroy()
        audioJob?.cancel() // Cancel the audioJob when the view is destroyed
        resetMediaPlayer()
    }

    override fun onDetach() {
        super.onDetach()
        audioJob?.cancel() // Cancel the audioJob when the view is Detach
        resetMediaPlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        audioJob?.cancel() // Cancel the audioJob when the view is DestroyView
        resetMediaPlayer()
    }

    private fun expandFab() {
        sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding!!.playerBtnScrollCard.setCompoundDrawablesWithIntrinsicBounds(
            null,
            requireContext().getDrawable(R.drawable.baseline_arrow_drop_down_24),
            null,
            null
        )
        isExpanded = !isExpanded

    }

    private fun shrinkFab() {
        isExpanded = !isExpanded
        binding!!.playerBtnScrollCard.setCompoundDrawablesWithIntrinsicBounds(
            null,
            requireContext().getDrawable(R.drawable.baseline_arrow_drop_up_24),
            null,
            null
        )

        sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

    override fun onBtnPlayAyaClicked(ayah: Ayah) {
        expandFab()
        binding!!.ayeh = ayah.numberInSurah.toString()
        resetMediaPlayer()
//        first we will get the link for aya audio
//        val context: CoroutineContext? = null
//        audioUrl=""
//        surahAyasViewerViewmodel.getAyaAudioLink(ayah.number)
////        if (!observing){
////            setObservers()
////            observing=!observing
////        }
        lifecycleScope.launch(Dispatchers.IO) {
            resetMediaPlayer()
            loadAudio(ayah)
        }


    }

    private fun resetMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop();
            mediaPlayer!!.reset();
            mediaPlayer!!.release();
            mediaPlayer = null;
            // Remove the callback to prevent memory leaks
            handler.removeCallbacks(updateProgressBar)
        }
    }

    private suspend fun loadAudio(ayah: Ayah) {
        mediaPlayer = try {
            MediaPlayer().apply {
                setDataSource(ayah.audio)
                prepare()
                setOnCompletionListener {
                    if (isAutoPlayNext && ayah.numberInSurah <= surahFromQuran.ayahs.size) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            loadAudio(surahFromQuran.ayahs[ayah.numberInSurah])
                            lifecycleScope.launch(Dispatchers.Main) {
                                binding!!.quranAyaAudionProgressTxtAyaNumber.text =
                                    ayah.numberInSurah.toString()
//                                    binding!!.surahAyaViewrRcv.smoothScrollToPosition(ayah.numberInSurah)
                                scrollToCenter(binding!!.surahAyaViewrRcv, ayah.numberInSurah)
                            }
                        }
                    } else {
                        shrinkFab()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        binding!!.quranAyaAudionProgresslinearProgressIndicator.max = 0
        ayaDuraion = mediaPlayer!!.duration
        mediaPlayer!!.start()
        binding!!.quranAyaAudionProgresslinearProgressIndicator.max = mediaPlayer!!.duration
        handler.post(updateProgressBar)
        currantAya = ayah

    }

    fun scrollToCenter(recyclerView: RecyclerView, position: Int) {
        recyclerView.post {
            val smoothScroller = object : LinearSmoothScroller(recyclerView.context) {
                override fun getVerticalSnapPreference(): Int {
                    return LinearSmoothScroller.SNAP_TO_START
                }
            }
            smoothScroller.targetPosition = position
            recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        }
    }

    // Runnable to update the progress bar
    private val updateProgressBar = object : Runnable {
        override fun run() {
            // Update the progress bar with the current position
            binding!!.quranAyaAudionProgresslinearProgressIndicator.progress =
                mediaPlayer!!.currentPosition

            // Repeat the update every 100 milliseconds (adjust as needed)
            handler.postDelayed(this, 100)
        }
    }

    fun playAudio(view: View) {
        mediaPlayer?.start()
    }

    override fun onBtnCopyAyahClicked(ayah: Ayah) {
        copyToClipboard(ayah.number.toString(), "{${ayah.text}}")
        Toast.makeText(requireContext(), "تم نسخ الايه", Toast.LENGTH_SHORT).show()
    }

    override fun onBtnShareAyaClicked(ayah: Ayah) {
        shareContent("مشاركه ايه قرانيه", "{${ayah.text}}")
    }

    override fun onBtnSaveAyahClicked(ayah: Ayah) {

    }

    fun shareContent(subject: String, text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        requireContext().startActivity(
            Intent.createChooser(
                sendIntent,
                "Share using Ramadan Kareem Application"
            )
        )
    }
}