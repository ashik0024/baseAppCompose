package com.nexdecade.composebase.uiCompose.nonpaging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexdecade.composebase.Result
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
    private val getPokemonService: GetPokemonService
) : ViewModel() {
    
    // State Management
    private val _uiState = MutableStateFlow<PokemonUiState>(Loading)
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()
    
    // Action
    fun fetchPokemon() {
        viewModelScope.launch {
            // Set loading state
            _uiState.value = Loading
            
            // Call the service
            val result = getPokemonService.getPokemonData()
            
            // Handle result
            _uiState.value = when (result) {
                is Result.Success -> {
                    Log.d("fetchPokemonDAta", ": "+result.data)
                    Success(pokemonList = result.data)
                }
                is Result.Error -> {
                    Log.d("fetchPokemonDAta error",
                        (":" + result.exception.message) ?: "Unknown error"
                    )
                    Error(errorMessage = result.exception.message ?: "Unknown error")
                }
                
                Result.Loading -> TODO()
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