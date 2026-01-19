package com.nexdecade.composebase.network.services

import com.nexdecade.composebase.network.repository.safeApiCall
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import com.nexdecade.composebase.network.retrofit.RetrofitClient
import com.nexdecade.composebase.Result


class GetPokemonService {
    
    suspend fun getPokemonData(): Result<List<Pokemon>> {
        return safeApiCall {
            RetrofitClient.apiService.getPokemon().results
                ?: throw Exception("No Pokémon found")
        }
    }
}
