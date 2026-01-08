package com.nexdecade.composebase.uiCompose.paging



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nexdecade.composebase.network.responseClass.Pokemon
import com.nexdecade.composebase.network.services.GetPokemonPagingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonPagingViewModel @Inject constructor(
    getPokemonPagingService: GetPokemonPagingService
) : ViewModel() {
    
    val pokemonPagingFlow =
        getPokemonPagingService.getPokemonPaging()
            .cachedIn(viewModelScope)
}


