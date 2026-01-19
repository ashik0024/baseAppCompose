package com.nexdecade.composebase.homePage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexdecade.composebase.utils.Result
import com.nexdecade.composebase.homePage.data.repository.PokemonRepoImpl
import com.nexdecade.composebase.homePage.presentation.viewmodel.PokemonUiState.*
import com.nexdecade.composebase.homePage.domain.model.response.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepoImpl
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<PokemonUiState>(Loading)
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private var fullPokemonList: List<Pokemon> = emptyList() // cached list
    
    init {
        fetchPokemon()
    }
    
    fun fetchPokemon() {
        viewModelScope.launch {
            _uiState.value = Loading
            
            when (val result = repository.getAllPokemonList()) {
                is Result.Success -> {
                    fullPokemonList = result.data
                    _uiState.value = Success(result.data)
                }
                is Result.Error -> _uiState.value =
                    Error(result.exception.message ?: "Unknown error")
                Result.Loading -> _uiState.value = Loading
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterPokemon(query)
    }
    
    private fun filterPokemon(query: String) {
        val filtered = if (query.isBlank()) fullPokemonList
        else fullPokemonList.filter { it.name?.contains(query, ignoreCase = true) == true }
        
        _uiState.value = Success(filtered)
    }
}

// UI State sealed class for better state management
sealed class PokemonUiState {
    data object Loading : PokemonUiState()
    data class Success(val pokemonList: List<Pokemon>) : PokemonUiState()
    data class Error(val errorMessage: String) : PokemonUiState()
}