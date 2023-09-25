package com.example.data.repo

import Resource
import android.content.Context
import android.location.Location
import android.util.Log
import com.example.data.data.ApiAzanService
import com.example.data.data.ApiHadithService
import com.example.data.data.ApiQuranService
import com.example.data.data.AssestClass
import com.example.data.data.GetCurrantLocationJustOnce
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.entity.azkar.AzkarRespons
import com.example.domain.entity.hadith.HadithResponse
import com.example.domain.entity.quran.QuranResponse
import com.example.domain.repo.UserRepo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.nio.charset.StandardCharsets

class userRepoImpl(
    private val apiHadithService: ApiHadithService,
    private val apiQuranService: ApiQuranService,
    private val assestClass: AssestClass,
    private val context:Context,
    private val azanService: ApiAzanService,
    private val getCurrantLocationJustOnce: GetCurrantLocationJustOnce
) : UserRepo {
    private val TAG = "userRepoImpl"
    override suspend fun getHadithFromRemote(result: (Resource<HadithResponse>) -> Unit) {
        val response = apiHadithService.getHadith()
        if (response.isSuccessful) {
            if (response.body() != null) {
                Log.d(TAG, "${response!!.body()!!.hadiths}")
                result.invoke(Resource.Success(response!!.body()!!))
            } else {
                Log.e(TAG, "data is null ")
                result.invoke(Resource.Failure("failed to get hadieth data is null "))
            }
        } else {
            Log.e(TAG, "failed to get hadieth ${response.body()}")
            result.invoke(Resource.Failure("failed to get hadieth ${response.body()}"))
        }

    }

    override suspend fun getAzanFromRemote(
        month: Int,
        year: Int,
        location: com.google.android.gms.maps.model.LatLng,
        result: (Resource<AzanResponse>) -> Unit
    ) {
        val response =azanService.getAzanTimes(year,month,location.latitude,location.longitude,2)
        if (response.isSuccessful) {
            if (response.body() != null) {
                result.invoke(Resource.Success(response!!.body()!!))
            } else {
                Log.e(TAG, "data is null ")
                result.invoke(Resource.Failure("failed to get azan -> data is null "))
            }
        } else {
            Log.e(TAG, "failed to get azan ${response.body()}")
            result.invoke(Resource.Failure("failed to get azan ${response.body()}"))
        }

    }

    override suspend fun getUserCurrantLocationJustOnce(result: (Resource<Location>) -> Unit) {
        getCurrantLocationJustOnce.startGettingLocation(result)
    }

    override suspend fun getQuranFromRemote(result: (Resource<QuranResponse>) -> Unit) {
        assestClass.loadDataFromJson(context,"quran.json",result)

    }

    fun getAllAzkar(): List<AzkarRespons> {
        val azkarFile: InputStream = context.assets.open("azkar.json")

        val size: Int = azkarFile.available()
        val bytes = ByteArray(size)

        azkarFile.read(bytes)
        azkarFile.close()

        val azkarString = String(bytes, StandardCharsets.UTF_8)
        val gson = Gson()

        return gson.fromJson(azkarString, object : TypeToken<List<AzkarRespons>>() {}.type)
    }

    override suspend fun getAyaAudioLinkFromRemote(
        ayaNumber: Int,
        result: (Resource<String>) -> Unit
    ) {
        assestClass.loadQuranAudionDataFromJson(context,"quraan_audio.json",ayaNumber,result)
    }

    override suspend fun getAzkarFromLocal(result: (Resource<List<AzkarRespons>>) -> Unit) {

        val response = getAllAzkar()
        if (response != null){
            result.invoke(Resource.Success(response))
        }else{
            result.invoke(Resource.Failure("failed to get Azkar Category"))
        }
    }

//    override suspend fun getAzkarFromLocal(result: (Resource<List<AzkarRespons>>) -> Unit, category:String) {
//        val response = getAllAzkar().filter {category == it.category }
//        if (response != null){
//            result.invoke(Resource.Success(response))
//        }else{
//            result.invoke(Resource.Failure("failed to get Azkar"))
//        }
//    }
}