package com.nexdecade.composebase.homepagePaging.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nexdecade.composebase.homepagePaging.data.repository.PokemonPagingRepoImpl

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonPagingViewModel @Inject constructor(
    getPokemonPagingService: PokemonPagingRepoImpl
) : ViewModel() {
    
    val pokemonPagingFlow =
        getPokemonPagingService.getAllPokemonPagingList()
            .cachedIn(viewModelScope)
}