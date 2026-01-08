package com.nexdecade.composebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.nexdecade.composebase.ui.theme.ComposeBaseTheme
import com.nexdecade.composebase.uiCompose.nonpaging.PokemonScreen
import com.nexdecade.composebase.uiCompose.paging.PokemonPagingScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeBaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                    .statusBarsPadding()        // only status bar
//                    .navigationBarsPadding()
                ) { innerPadding ->
                    PokemonScreen()
//                    PokemonPagingScreen()
                }
            }
        }
    }
}

