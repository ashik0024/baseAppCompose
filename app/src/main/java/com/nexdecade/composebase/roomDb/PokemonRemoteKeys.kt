package com.nexdecade.composebase.roomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_remote_keys")
data class PokemonRemoteKeys(
    @PrimaryKey val name: String,
    val prevKey: Int?,
    val nextKey: Int?
)
