package com.example.ramadan_kareem.di

import com.example.domain.repo.UserRepo
import com.example.domain.usecase.GetAzanFromRemote
import com.example.domain.usecase.GetAzkar
import com.example.domain.usecase.GetAzkarCategory
import com.example.domain.usecase.GetHadith
import com.example.domain.usecase.GetQuranFromRemote
import com.example.domain.usecase.GetUserCurrantLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetHadithFromRemote(userRepo: UserRepo):GetHadith{
        return GetHadith(userRepo)
    }

    @Provides
    fun provideGetQuranFromRemote(userRepo: UserRepo):GetQuranFromRemote{
        return GetQuranFromRemote(userRepo)
    }

    @Provides
    fun provideGetAzkarCategory(userRepo: UserRepo):GetAzkarCategory{
        return GetAzkarCategory(userRepo)
    }

    @Provides
    fun provideGetAzkar(userRepo: UserRepo):GetAzkar{
        return GetAzkar(userRepo)
    }
    @Provides
    fun provideGetAzanFromRemote(userRepo: UserRepo):GetAzanFromRemote{
        return GetAzanFromRemote(userRepo)
    }
    @Provides
    fun provideGetUserCurrantLocation(userRepo: UserRepo):GetUserCurrantLocation{
        return GetUserCurrantLocation(userRepo)
    }


}