package com.nexdecade.composebase.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon

@Database(
    entities = [Pokemon::class, PokemonRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): PokemonRemoteKeysDao
}
