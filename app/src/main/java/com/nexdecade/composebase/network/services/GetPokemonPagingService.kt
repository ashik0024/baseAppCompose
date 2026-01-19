package com.nexdecade.composebase.network.services

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nexdecade.composebase.BasePagingRepository
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import com.nexdecade.composebase.network.retrofit.ApiInterface
import com.nexdecade.composebase.roomDb.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPokemonPagingService @Inject constructor(
    private val db: AppDatabase,
    private val api: ApiInterface
) {
    
    @OptIn(ExperimentalPagingApi::class)
    fun getPokemonPaging(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = PokemonRemoteMediator(api, db),
            pagingSourceFactory = { db.pokemonDao().getPokemonPaging() }
        ).flow
    }

}

