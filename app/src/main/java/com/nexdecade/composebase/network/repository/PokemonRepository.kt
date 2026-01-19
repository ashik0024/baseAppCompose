package com.nexdecade.composebase.network.repository

import android.util.Log
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import com.nexdecade.composebase.network.services.GetPokemonService
import com.nexdecade.composebase.roomDb.PokemonDao
import com.nexdecade.composebase.Result

import javax.inject.Inject

open class PokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val getPokemonService: GetPokemonService
) {
    
    suspend fun getPokemon(): Result<List<Pokemon>> {
        
        // 1️⃣ Check Room first
        val localData = pokemonDao.getAllPokemon()
        if (localData.isNotEmpty()) {
            Log.d("dataLoaded1", "loaded from db: ")
            return Result.Success(localData)
        }
        
        // 2️⃣ Fetch from network
        return when (val result = getPokemonService.getPokemonData()) {
            is Result.Success -> {
                Log.d("dataLoaded2", "saving in db: ")
                // Save to Room
                pokemonDao.insertPokemon(result.data)
                Result.Success(result.data)
            }
            is Result.Error -> result
            Result.Loading -> Result.Loading
        }
    }
}
