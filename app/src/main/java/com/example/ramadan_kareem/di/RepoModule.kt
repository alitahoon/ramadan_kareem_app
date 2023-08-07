package com.example.trainlivelocation.di

import com.example.data.*
import com.example.data.data.ApiHadithService
import com.example.data.data.ApiQuranService
import com.example.data.repo.userRepoImpl
import com.example.domain.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun ProvideRepo(apiHadithService: ApiHadithService,apiQuranService: ApiQuranService ): UserRepo {
        return userRepoImpl(apiHadithService,apiQuranService)
    }

}