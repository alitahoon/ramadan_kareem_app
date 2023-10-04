package com.example.domain.usecase

import Resource
import com.example.domain.entity.tafsir.TafsirResponse
import com.example.domain.repo.UserRepo

class GetTafsirForAya(private val userRepo: UserRepo) {
    suspend operator fun invoke(
        tafseer_id: Int,
        sura_number: Int,
        ayah_number: Int,
        result: (Resource<TafsirResponse>) -> Unit
    ) = userRepo.getTafsirForAya(tafseer_id, sura_number, ayah_number, result)
}