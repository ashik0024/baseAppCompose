package com.nexdecade.composebase.uiCompose.paging

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

import com.nexdecade.composebase.uiCompose.nonpaging.PokemonItem


@Composable
fun PokemonPagingScreen(viewModel: PokemonPagingViewModel = hiltViewModel()) {
    val lazyPagingItems = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()
    
    LazyColumn {
  
        items(
            count = lazyPagingItems.itemCount,

        ) { index ->
            val pokemon = lazyPagingItems[index]
            pokemon?.let {
                PokemonItem(pokemon = it)
            }
        }
    }
}


