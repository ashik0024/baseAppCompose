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
    private val getPokemonPagingService: GetPokemonPagingService
) : ViewModel() {
    
    // Expose the paging data as Flow<PagingData<Pokemon>>
    val pokemonPagingFlow: Flow<PagingData<Pokemon>> =
        getPokemonPagingService.getPokemonPaging()
            .cachedIn(viewModelScope)
}
