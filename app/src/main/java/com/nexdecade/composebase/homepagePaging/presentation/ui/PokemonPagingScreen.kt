package com.nexdecade.composebase.homepagePaging.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey

import com.nexdecade.composebase.homePage.presentation.ui.PokemonItem
import com.nexdecade.composebase.homepagePaging.presentation.viewmodel.PokemonPagingViewModel


@Composable

fun PokemonPagingScreen(viewModel: PokemonPagingViewModel = hiltViewModel()) {
    val lazyPagingItems = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()
    
    // Show loading / error state
    val isLoading = lazyPagingItems.loadState.refresh is LoadState.Loading
    val isError = lazyPagingItems.loadState.refresh is LoadState.Error
    
    if (isLoading) {
        // Show a loading UI while first page is fetched
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    
    if (isError) {
        // Show error UI
        val error = lazyPagingItems.loadState.refresh as LoadState.Error
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: ${error.error.localizedMessage}")
        }
        return
    }
    
    LazyColumn {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            lazyPagingItems[index]?.let { pokemon ->
                PokemonItem(pokemon)
            }
        }
    }
}


