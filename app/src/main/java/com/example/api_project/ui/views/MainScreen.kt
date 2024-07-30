package com.example.api_project.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier
) {
    val viewModel: HeroesViewModel = viewModel()
    val navController = rememberNavController()

    val uiState by viewModel.mainScreenUiState.collectAsState()

    //Andaime para construir as telas
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = uiState.title))
            })
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = AppScreens.HeroesList.name,
            modifier = modifier.padding(it)
        ) {
            composable(route = AppScreens.HeroesList.name){
                HeroesListScreen(viewModel, navController)
            }
            composable(route = AppScreens.HeroStats.name){
                HeroStats(viewModel = viewModel, navController = navController)
            }
        }
    }
}

enum class AppScreens{
    HeroesList,
    HeroStats,
}