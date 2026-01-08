package com.nexdecade.composebase.roomDb

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
}
