package com.example.domain.repo

import Resource
import android.location.Location
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.entity.azan.Month
import com.example.domain.entity.azkar.AzkarRespons
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.entity.quran.QuranResponse
import com.example.domain.entity.quran_audio.QuranAudioResponse
import com.example.domain.entity.quran_en.QuranEnglishResponse
import com.google.android.gms.maps.model.LatLng
import java.time.Year

interface UserRepo {

    suspend fun getHadithFromRemote(result: (Resource<HadithResponse>) -> Unit)
    suspend fun getQuranFromRemote(result: (Resource<QuranAudioResponse>) -> Unit)
    suspend fun getAzkarFromLocal(result: (Resource<List<AzkarRespons>>) -> Unit)

    suspend fun getAzanFromRemote(month:Int, year:Int, location: LatLng, result: (Resource<AzanResponse>) -> Unit)
    suspend fun getUserCurrantLocationJustOnce(result: (Resource<Location>) -> Unit)
    suspend fun getAyaAudioLinkFromRemote(ayaNumber:Int,result: (Resource<String>) -> Unit)

    suspend fun getEnglishQuran(result: (Resource<QuranEnglishResponse>) -> Unit)
    suspend fun getAyaInEnglish(ayaNumber: Int,result: (Resource<String>) -> Unit)

}