package com.nexdecade.composebase.uiCompose.nonpaging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexdecade.composebase.Result
import com.nexdecade.composebase.network.repository.PokemonRepository
import com.nexdecade.composebase.uiCompose.nonpaging.PokemonUiState.*
import com.nexdecade.composebase.network.responseClass.Pokemon
import com.nexdecade.composebase.network.services.GetPokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<PokemonUiState>(Loading)
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()
    
    fun fetchPokemon() {
        viewModelScope.launch {
            _uiState.value = Loading
            
            _uiState.value = when (val result = repository.getPokemon()) {
                is Result.Success -> Success(result.data)
                is Result.Error -> Error(result.exception.message ?: "Unknown error")
                Result.Loading -> Loading
            }
        }
    }
}

// UI State sealed class for better state management
sealed class PokemonUiState {
    data object Loading : PokemonUiState()
    data class Success(val pokemonList: List<Pokemon>) : PokemonUiState()
    data class Error(val errorMessage: String) : PokemonUiState()
}