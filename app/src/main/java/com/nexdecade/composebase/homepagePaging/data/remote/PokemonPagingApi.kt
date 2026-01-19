package com.nexdecade.composebase.homepagePaging.data.remote

import com.nexdecade.composebase.homePage.domain.model.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonPagingApi {
    
    @GET("pokemon")
    suspend fun getPokemonPaging(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse
}