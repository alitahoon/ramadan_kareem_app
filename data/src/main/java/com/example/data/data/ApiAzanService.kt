package com.example.data.data

import android.location.Location
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.entity.hadith.HadithResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.Month
import java.time.Year


interface ApiAzanService {

    @GET("calendar/{year}/{month}")
    suspend fun getAzanTimes(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int
    ): Response<AzanResponse>
}