package com.nexdecade.composebase.homePage.data.repository

import android.util.Log
import com.nexdecade.composebase.Result
import com.nexdecade.composebase.homePage.data.remote.PokemonApi
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import com.nexdecade.composebase.homePage.domain.repository.PokemonRepo
import com.nexdecade.composebase.network.repository.PokemonRepository
import com.nexdecade.composebase.network.retrofit.RetrofitClient.apiService
import com.nexdecade.composebase.network.services.GetPokemonService
import com.nexdecade.composebase.roomDb.PokemonDao
import javax.inject.Inject

class PokemonRepoImpl @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonApi: PokemonApi
) : PokemonRepo {
    
    override suspend fun getAllPokemonList(): Result<List<Pokemon>> {
        return try {
            val localData = pokemonDao.getAllPokemon()
            if (localData.isNotEmpty()) {
                Log.d("dataLoaded", "Loaded from DB")
                return Result.Success(localData)
            }
            
            val response = pokemonApi.getPokemon()
            val remoteList = response.results ?: emptyList()
            
            if (remoteList.isNotEmpty()) {
                pokemonDao.insertPokemon(remoteList)
                Log.d("dataLoaded", "Fetched from Network & Saving to DB")
                Result.Success(remoteList)
            } else {
                Result.Error(Exception("No Pokémon found"))
            }
            
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}
