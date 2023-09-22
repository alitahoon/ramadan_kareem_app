package com.example.trainlivelocation.di

import com.example.data.data.ApiAzanService
import com.example.data.data.ApiHadithService
import com.example.data.data.ApiQuranService
import com.example.ramadan_kareem.util.constant.Companion.BASE_URL_AZAN
import com.example.ramadan_kareem.util.constant.Companion.BASE_URL_HADITH
import com.example.ramadan_kareem.util.constant.Companion.BASE_URL_QURAAN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
    @Named("Hadith")
    fun provideRetrofitForHadith(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL_HADITH)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApiServiceForHadith(@Named("Hadith")retrofit: Retrofit): ApiHadithService {
        return retrofit.create(ApiHadithService::class.java)
    }

    @Provides
    @Singleton
    @Named("Quran")
    fun provideRetrofitForQuran(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL_QURAAN)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApiServiceForQuran(@Named("Quran") retrofit: Retrofit): ApiQuranService {
        return retrofit.create(ApiQuranService::class.java)
    }

    @Provides
    @Singleton
    @Named("Azan")
    fun provideRetrofitForAzan(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL_AZAN)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApiServiceForAzan(@Named("Azan") retrofit: Retrofit): ApiAzanService {
        return retrofit.create(ApiAzanService::class.java)
    }

}