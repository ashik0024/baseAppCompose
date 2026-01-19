package com.nexdecade.composebase.homePage.domain.repository

import com.nexdecade.composebase.utils.Result
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon

interface PokemonRepo {

    suspend fun getAllPokemonList(): Result<List<Pokemon>>
}