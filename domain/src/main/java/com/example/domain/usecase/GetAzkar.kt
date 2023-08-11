package com.example.domain.usecase

import Resource
import com.example.domain.entity.azkar.AzkarRespons
import com.example.domain.repo.UserRepo

class GetAzkar(private val userRepo: UserRepo) {

    suspend operator fun invoke(result: (Resource<List<AzkarRespons>>) -> Unit, category:String) = userRepo.getAzkarFromLocal(result,category)

}