package com.example.domain.usecase

import Resource
import com.example.domain.entity.azan.AzanResponse
import com.example.domain.repo.UserRepo
import com.google.android.gms.maps.model.LatLng

class GetAzanFromRemote(private val userRepo: UserRepo) {
    suspend operator fun invoke(month:Int, year:Int, location: LatLng, result: (Resource<AzanResponse>) -> Unit)=userRepo.getAzanFromRemote(month,year,location,result)
}