package com.nexdecade.composebase.homepagePaging.domain.repository

import androidx.paging.PagingData
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonPagingRepo {

     fun getAllPokemonPagingList(): Flow<PagingData<Pokemon>>
}