package com.nexdecade.composebase.network.services

import androidx.paging.PagingData
import com.nexdecade.composebase.BasePagingRepository
import com.nexdecade.composebase.network.responseClass.Pokemon
import com.nexdecade.composebase.network.retrofit.ApiInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPokemonPagingService @Inject constructor(
    private val api: ApiInterface
) : BasePagingRepository<Pokemon>() {
    
    fun getPokemonPaging(): Flow<PagingData<Pokemon>> {
        return getPager { limit, offset ->
            api.getPokemonPaging(limit, offset).results?: emptyList()
        }
    }
}
