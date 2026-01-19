package com.nexdecade.composebase.network.services

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import com.nexdecade.composebase.network.retrofit.ApiInterface
import com.nexdecade.composebase.roomDb.AppDatabase
import com.nexdecade.composebase.roomDb.PokemonRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val api: ApiInterface,
    private val db: AppDatabase
) : RemoteMediator<Int, Pokemon>() {
    
    companion object {
        private const val TAG = "PokemonRemoteMediator"
    }
    
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        
        Log.d(TAG, "➡️ LoadType = $loadType")
        val offset = when (loadType) {
            LoadType.REFRESH -> {
                Log.d(TAG, "🔄 REFRESH → offset = 0")
                0
            }
            LoadType.PREPEND -> {
                Log.d(TAG, "⛔ PREPEND not supported")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                Log.d(TAG, "⬇️ APPEND lastItem = ${lastItem.name}")
                db.remoteKeysDao().remoteKeysByName(lastItem.name)?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        
//        val offset = when (loadType) {
//
//            LoadType.REFRESH -> {
//                Log.d(TAG, "🔄 REFRESH → offset = 0")
//                0
//            }
//
//            LoadType.PREPEND -> {
//                Log.d(TAG, "⛔ PREPEND not supported")
//                return MediatorResult.Success(endOfPaginationReached = true)
//            }
//
//            LoadType.APPEND -> {
//                val lastItem = state.lastItemOrNull()
//                Log.d(TAG, "⬇️ APPEND lastItem = ${lastItem?.name}")
//
//                val nextKey = lastItem?.let {
//                    db.remoteKeysDao()
//                        .remoteKeysByName(it.name)
//                        ?.nextKey
//                }
//
//                Log.d(TAG, "➡️ APPEND nextKey = $nextKey")
//                nextKey ?: return MediatorResult.Success(
//                    endOfPaginationReached = true
//                )
//            }
//        }
        
        return try {
            Log.d(
                TAG,
                "🌐 API call → limit=${state.config.pageSize}, offset=$offset"
            )
            
            val response = api.getPokemonPaging(
                limit = state.config.pageSize,
                offset = offset
            )
            
            val pokemon = response.results.orEmpty()
            Log.d(TAG, "✅ API success → received ${pokemon.size} items")
            
            db.withTransaction {
                
                if (loadType == LoadType.REFRESH) {
                    Log.d(TAG, "🧹 Clearing DB (refresh)")
                    db.remoteKeysDao().clearRemoteKeys()
                    db.pokemonDao().clearAll()
                }
                
                val keys = pokemon.map {
                    PokemonRemoteKeys(
                        name = it.name,
                        prevKey = if (offset == 0) null else offset - state.config.pageSize,
                        nextKey = offset + state.config.pageSize
                    )
                }
                
                Log.d(TAG, "💾 Inserting ${keys.size} remote keys")
                db.remoteKeysDao().insertAll(keys)
                
                Log.d(TAG, "💾 Inserting ${pokemon.size} Pokémon")
                db.pokemonDao().insertAll(pokemon)
            }
            
            Log.d(
                TAG,
                "🏁 Load finished → endOfPaginationReached = ${pokemon.isEmpty()}"
            )
            
            MediatorResult.Success(
                endOfPaginationReached = pokemon.isEmpty()
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "❌ Load error", e)
            MediatorResult.Error(e)
        }
    }
}
