package com.example.trainlivelocation.di

import android.app.Application
import android.content.Context
import com.example.data.*
import com.example.data.data.ApiAzanService
import com.example.data.data.ApiHadithService
import com.example.data.data.ApiQuranService
import com.example.data.data.ApiTafsirService
import com.example.data.data.AssestClass
import com.example.data.data.GetCurrantLocationJustOnce
import com.example.data.repo.userRepoImpl
import com.example.domain.repo.UserRepo
import com.example.ramadan_kareem.util.BindingMethods
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun ProvideRepo(apiHadithService: ApiHadithService,apiQuranService: ApiQuranService,assestClass: AssestClass,@ApplicationContext context: Context,apiAzanService: ApiAzanService,getCurrantLocationJustOnce: GetCurrantLocationJustOnce ,apiTafsirService: ApiTafsirService): UserRepo {
        return userRepoImpl(apiHadithService,apiQuranService,assestClass,context,apiAzanService,getCurrantLocationJustOnce, apiTafsirService)
    }

    @Provides
    fun ProvideAssetsClass ():AssestClass{
        return AssestClass()
    }


    @Provides
    fun ProvideGetCurrantLocationJustOnce(context: Context):GetCurrantLocationJustOnce{
        return GetCurrantLocationJustOnce(context)
    }


}