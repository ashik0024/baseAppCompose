package com.nexdecade.composebase.homePage.domain.model.response

import android.os.Parcelable
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PokemonListResponse (
    @SerialName("count")
    val count: Int? = 0,
    @SerialName("next")
    val next: String? = "",
    @SerialName("previous")
    val previous: String? = "",
    @SerialName("results")
    val results : List<Pokemon>? = null,
): Parcelable