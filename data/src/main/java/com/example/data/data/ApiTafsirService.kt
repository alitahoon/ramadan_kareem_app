package com.example.data.data

import android.location.Location
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.entity.tafsir.TafsirResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.Month
import java.time.Year


interface ApiTafsirService {

    @GET("/tafseer/{tafseer_id}/{sura_number}/{ayah_number}")
    suspend fun getAzanTimes(
        @Path("tafseer_id") tafseer_id:Int,
        @Path("sura_number") sura_number:Int,
        @Path("ayah_number") ayah_number:Int
    ): Response<TafsirResponse>
}