package com.example.ramadan_kareem.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ramadan_kareem.databinding.FragmentHomeBinding
import com.example.ramadan_kareem.util.getAzanData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Date
import org.threeten.bp.LocalTime
import org.threeten.bp.Duration
import java.util.Locale
import java.util.GregorianCalendar
import java.util.TimeZone
import kotlin.concurrent.timer

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private var localTimeNow: LocalTime? = null
    private val TAG = "FragmentHomeBinding"
    private var prayerTimings: List<Pair<String, String>> = emptyList()
    private var prayerDurations: List<Pair<String, Duration>> = emptyList()
    private val handler = Handler(Looper.getMainLooper())
    private val maxProgress = 100 // Max progress value
    private val updateInterval = 1000L // Update interval in milliseconds (1 second)
    private var isPrgressWork = false

    companion object {
        var hours = 0
        var minutes = 0
        var seconds = 0
        var theNextPrayer = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.hadithCard.setOnClickListener {
            val action = HomeDirections.actionHomeToHadith()
            mNavController.navigate(action)
        }
        binding.quran.setOnClickListener {
            val action = HomeDirections.actionHomeToAlQuran()
            mNavController.navigate(action)
        }
        binding.azkarCard.setOnClickListener {
            val action = HomeDirections.actionHomeToAzkar()
            mNavController.navigate(action)
        }
        binding.qiblaCard.setOnClickListener {
            val action = HomeDirections.actionHomeToQibla()
            mNavController.navigate(action)
        }
        binding.prayer.setOnClickListener {
            val action = HomeDirections.actionHomeToPrayerTime()
            mNavController.navigate(action)
        }

        binding.gregorianDate.setText(getCurrentDateInArabicFormat())
        binding.IslamicDate.setText(getCurrentIslamicDate())

        setobservers()
        homeViewModel.getAzan()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setobservers() {
        localTimeNow = LocalTime.now()
        hours = localTimeNow!!.hour
        minutes = localTimeNow!!.minute
        val azanData = getAzanData(requireContext())
        if ( azanData!= null){
            prayerTimings = listOf(
                Pair("الفجر", azanData.data.get(azanData.data.size - 1).timings.Fajr),
                Pair("الظهر", azanData.data.get(azanData.data.size - 1).timings.Dhuhr),
                Pair("العصر", azanData.data.get(azanData.data.size - 1).timings.Asr),
                Pair("المغرب", azanData.data.get(azanData.data.size - 1).timings.Maghrib),
                Pair("العشاء", azanData.data.get(azanData.data.size - 1).timings.Isha)
            )
            getNextPrayer(prayerTimings)
            setPrayerDurations()
            if (!isPrgressWork) {
                setProgressbar()
                isPrgressWork = !isPrgressWork
            }
        }else{
            homeViewModel.azanResponse.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    prayerTimings = listOf(
                        Pair("الفجر", it.data.get(it.data.size - 1).timings.Fajr),
                        Pair("الظهر", it.data.get(it.data.size - 1).timings.Dhuhr),
                        Pair("العصر", it.data.get(it.data.size - 1).timings.Asr),
                        Pair("المغرب", it.data.get(it.data.size - 1).timings.Maghrib),
                        Pair("العشاء", it.data.get(it.data.size - 1).timings.Isha)
                    )
                    getNextPrayer(prayerTimings)
                    setPrayerDurations()
                    if (!isPrgressWork) {
                        setProgressbar()
                        isPrgressWork = !isPrgressWork
                    }
                }

            })
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextPrayer(timings: List<Pair<String, String>>) {
        for ((prayer, timing) in timings) {
            // Extract the time part before any parentheses (if any)
            val timeString = timing.substringBefore("(", timing).trim()
            val prayerTime = try {
                LocalTime.parse(timeString)

            } catch (e: DateTimeParseException) {
                // Handle parsing errors if necessary
                null
            }

            Log.d(TAG, "prayer ${prayer}" + prayerTime + "------ time now ${localTimeNow}")
            if (prayerTime != null && localTimeNow!!.isBefore(prayerTime)) {
//                if (prayerTime.hour == 13) {
//                    hours = prayerTime.hour - 12
//                } else if (prayerTime.hour > 13 && prayerTime.hour < 14)
//                else {
//                    hours = 1
//                }
                val amPm: String = if (prayerTime.hour >= 12 ) {
                    "مساء"
                } else {
                    "صباحا"
                }
                theNextPrayer = prayer
                binding.prayerTimeAlongDay.setText(amPm)
                binding.prayerTimeWidgedPrayerName.setText("صلاة ${theNextPrayer}")
                binding.prayerName.setText(prayer)
                binding.homeCurrantTimeTxt.setText("${prayerTime.hour}:" + prayerTime.minute.toString())
                break
            }
        }
    }

    fun getCurrentDateInArabicFormat(): String {
        val currentDate = Date()
        val locale = Locale("ar", "SA") // Arabic language, Saudi Arabia region
        val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", locale)
        return dateFormat.format(currentDate)
    }

    fun getCurrentIslamicDate(): String {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hijriMonth = getHijriMonth(calendar.get(Calendar.MONTH))
        val year = calendar.get(Calendar.YEAR)

        return "$day $hijriMonth $year هـ"
    }

    fun getHijriMonth(month: Int): String {
        val hijriMonths = listOf(
            "محرم", "صفر", "ربيع الأول", "ربيع الآخر", "جمادى الأولى", "جمادى الآخرة",
            "رجب", "شعبان", "رمضان", "شوال", "ذو القعدة", "ذو الحجة"
        )
        return hijriMonths[month]
    }

    fun convertStringToTime(timeString: String, format: String): Date? {
        val dateFormat = SimpleDateFormat(format)
        return try {
            dateFormat.parse(timeString)
        } catch (e: Exception) {
            null // Handle parsing errors, if any
        }
    }


    fun setPrayerDurations() {
        prayerDurations = listOf(
            Pair(
                "الفجر",
                Duration.between(
                    LocalTime.parse(
                        prayerTimings.get(0).second.substringBefore("(").trim()
                    ), LocalTime.parse(prayerTimings.get(1).second.substringBefore("(").trim())
                )
            ),
            Pair(
                "الظهر",
                Duration.between(
                    LocalTime.parse(
                        prayerTimings.get(1).second.substringBefore("(").trim()
                    ), LocalTime.parse(prayerTimings.get(2).second.substringBefore("(").trim())
                )
            ),
            Pair(
                "العصر",
                Duration.between(
                    LocalTime.parse(
                        prayerTimings.get(2).second.substringBefore("(").trim()
                    ), LocalTime.parse(prayerTimings.get(3).second.substringBefore("(").trim())
                )
            ),
            Pair(
                "المغرب",
                Duration.between(
                    LocalTime.parse(
                        prayerTimings.get(3).second.substringBefore("(").trim()
                    ), LocalTime.parse(prayerTimings.get(4).second.substringBefore("(").trim())
                )
            ),
            Pair(
                "العشاء",
                Duration.between(
                    LocalTime.parse(
                        prayerTimings.get(4).second.substringBefore("(").trim()
                    ), LocalTime.parse(prayerTimings.get(0).second.substringBefore("(").trim())
                )
            )
        )
        Log.d(TAG, "${prayerDurations}")
    }

    fun setProgressbar() {
        //set the duration
        when (theNextPrayer) {
            prayerDurations.get(0).first -> {
                startProgressBar(prayerDurations.get(0).second,0)
            }

            prayerDurations.get(1).first -> {
                startProgressBar(prayerDurations.get(0).second,1)
            }

            prayerDurations.get(2).first -> {
                startProgressBar(prayerDurations.get(0).second,2)

            }

            prayerDurations.get(3).first -> {
                startProgressBar(prayerDurations.get(0).second,3)


            }

            prayerDurations.get(4).first -> {
                startProgressBar(prayerDurations.get(0).second,4)

            }
        }
    }

    private fun startProgressBar(duration: Duration, prayerIndex: Int) {
        val totalDurationSeconds = duration.seconds
        val progressIncrement = 1L

        var currentProgress =Duration.between(
             LocalTime.parse(
                prayerTimings.get(prayerIndex-1).second.substringBefore("(").trim()
            ),LocalTime.now()
        ).seconds
        Log.d(TAG,"currantProgress  ${currentProgress}, totalDurationSeconds ${totalDurationSeconds} ----> ${prayerIndex}")

        val timer = timer(period = updateInterval) {
            currentProgress += progressIncrement

            if (currentProgress <= totalDurationSeconds) {
                val remainingDuration = Duration.ofSeconds(totalDurationSeconds - currentProgress)
                val remainingMinutes = remainingDuration.toMinutes()
                val remainingHours = remainingDuration.minusMinutes(remainingMinutes).toHours()
                val remainingSeconds = remainingDuration.minusMinutes(remainingMinutes).seconds
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.prayerTimeEstimatWidged.setText("${remainingHours}:${remainingMinutes}:${remainingSeconds}")
                    binding.homePrayerprogressBar.progress =
                        (currentProgress * maxProgress / totalDurationSeconds).toInt()
                }

                Log.d(TAG, "Remaining: $remainingMinutes minutes $remainingSeconds seconds")
            } else {
                cancel() // Stop the timer when the duration is complete
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.homePrayerprogressBar.progress = 0
                    binding.prayerTimeEstimatWidged.setText("الان موعد الاذان")

                }
                Log.d(TAG, "Duration Complete")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}