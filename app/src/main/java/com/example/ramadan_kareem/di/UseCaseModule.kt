package com.example.ramadan_kareem.di

import com.example.domain.repo.UserRepo
import com.example.domain.usecase.GetHadith
import com.example.domain.usecase.GetQuranFromRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(userRepo: UserRepo):GetHadith{
        return GetHadith(userRepo)
    }

    @Provides
    fun provideGetQuranFromRemote(userRepo: UserRepo):GetQuranFromRemote{
        return GetQuranFromRemote(userRepo)
    }

}