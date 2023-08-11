package com.example.data.data

import Resource
import android.content.Context
import android.util.Log
import com.example.domain.entity.quran.QuranResponse
import com.google.gson.Gson
import java.io.IOException

class AssestClass {
    private val TAG="AssestClass"
    fun loadDataFromJson(
        context: Context,
        fileName: String,
        result: (Resource<QuranResponse>) -> Unit
    ){
        val json: String?
        Log.e(TAG,"from loadDataFromJson")

        try {
            Log.e(TAG,"from loadDataFromJson")
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
            if (json != null) {
                val gson = Gson()
                val response = gson.fromJson(json, QuranResponse::class.java)
                result.invoke(Resource.Success(response))
            } else {
                Log.e(TAG,"file $fileName is not containing any data")
                result.invoke(Resource.Failure("file $fileName is not containing any data"))
            }

        } catch (ex: IOException) {
            Log.e(TAG,"${ex.printStackTrace()}")
            ex.printStackTrace()
            result.invoke(Resource.Failure("${ex.printStackTrace()}"))
        }
    }
}
