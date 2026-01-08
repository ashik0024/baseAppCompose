package com.nexdecade.composebase.roomDb

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexdecade.composebase.network.responseClass.Pokemon

@Dao
interface PokemonDao {
    
    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemon(): List<Pokemon>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<Pokemon>)
    
    @Query("DELETE FROM pokemon_table")
    suspend fun clearPokemon()
    
    @Query("SELECT * FROM pokemon_table ORDER BY name ASC")
    fun getPokemonPaging(): PagingSource<Int, Pokemon>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<Pokemon>)
    
    @Query("DELETE FROM pokemon_table")
    suspend fun clearAll()

}
