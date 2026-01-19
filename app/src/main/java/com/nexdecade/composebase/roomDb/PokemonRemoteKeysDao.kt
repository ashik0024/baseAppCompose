package com.nexdecade.composebase.roomDb

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon

@Dao
interface PokemonRemoteKeysDao {
    
    
    @Query("SELECT * FROM pokemon_remote_keys WHERE name = :name")
    suspend fun remoteKeysByName(name: String): PokemonRemoteKeys?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<PokemonRemoteKeys>)
    
    @Query("DELETE FROM pokemon_remote_keys")
    suspend fun clearRemoteKeys()
}
