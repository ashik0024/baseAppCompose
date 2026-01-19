package com.nexdecade.composebase.homePage.data.remote

import com.nexdecade.composebase.homePage.domain.model.response.PokemonListResponse
import retrofit2.http.GET

interface PokemonApi {
    
    @GET("pokemon")
    suspend fun getPokemon(): PokemonListResponse
}