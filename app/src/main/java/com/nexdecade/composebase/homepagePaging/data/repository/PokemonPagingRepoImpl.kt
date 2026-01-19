package com.nexdecade.composebase.homepagePaging.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nexdecade.composebase.homePage.data.repository.PokemonRepoImpl
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import com.nexdecade.composebase.homepagePaging.data.remote.PokemonPagingApi
import com.nexdecade.composebase.homepagePaging.domain.repository.PokemonPagingRepo

import com.nexdecade.composebase.homepagePaging.data.repository.PokemonRemoteMediator
import com.nexdecade.composebase.roomDb.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonPagingRepoImpl@Inject constructor(
    private val db: AppDatabase,
    private val api: PokemonPagingApi
): PokemonPagingRepo {
    
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllPokemonPagingList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PokemonRemoteMediator(api, db),
            pagingSourceFactory = { db.pokemonDao().getPokemonPaging() }
        ).flow
    }
}