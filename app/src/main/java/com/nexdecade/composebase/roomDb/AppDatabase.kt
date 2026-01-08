package com.nexdecade.composebase.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexdecade.composebase.network.responseClass.Pokemon

@Database(
    entities = [Pokemon::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun pokemonDao(): PokemonDao
}
