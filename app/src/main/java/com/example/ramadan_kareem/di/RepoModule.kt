package com.example.trainlivelocation.di

import android.app.Application
import android.content.Context
import com.example.data.*
import com.example.data.data.ApiHadithService
import com.example.data.data.ApiQuranService
import com.example.data.repo.userRepoImpl
import com.example.domain.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun ProvideRepo(apiHadithService: ApiHadithService,apiQuranService: ApiQuranService,@ApplicationContext context: Context ): UserRepo {
        return userRepoImpl(apiHadithService,apiQuranService,context)
    }

}