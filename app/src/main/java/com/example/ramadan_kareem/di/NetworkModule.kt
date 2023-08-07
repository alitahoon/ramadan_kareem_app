package com.example.trainlivelocation.di

import com.example.data.data.ApiHadithService
import com.example.data.data.ApiQuranService
import com.example.ramadan_kareem.util.constant.Companion.BASE_URL
import com.example.ramadan_kareem.util.constant.Companion.BASE_URL_QURAAN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApiServiceForHadith(retrofit: Retrofit): ApiHadithService {
        return retrofit.create(ApiHadithService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceForQuran(okHttpClient: OkHttpClient): ApiQuranService {
        val retrofitQuran=retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL_QURAAN)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitQuran.create(ApiQuranService::class.java)
    }

}