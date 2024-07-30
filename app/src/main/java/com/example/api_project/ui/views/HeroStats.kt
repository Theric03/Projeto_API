package com.example.api_project.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.api_project.R
import com.example.api_project.data.Hero

@Composable
fun HeroStats(
    viewModel: HeroesViewModel,
    navController: NavController,
){
    BackHandler {
        viewModel.navigateBack(navController)
    }
    val uiState by viewModel.heroStatsUiState.collectAsState()
    HeroStats(uiState = uiState)
}

@Composable
fun HeroStats(modifier: Modifier = Modifier, uiState: HeroStatsUiState){
    Column (modifier = modifier.fillMaxSize()){

        Box(modifier = modifier.fillMaxWidth()){
            val IMG_URL = "https://cdn.cloudflare.steamstatic.com/"
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(IMG_URL+uiState.img)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.dota_placeholder),
                contentDescription = uiState.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp)
                    .clip(RectangleShape)
            )
        }
        Text(text = "Name: ${uiState.name}")
        Text(text = "Health: ${uiState.life}")
        Text(text = "Mana: ${uiState.mana}")
        Text(text = "Minimum attack damage: ${uiState.attackMin}")
        Text(text = "Maximum  attack damage: ${uiState.attackMax}")
        Text(text = "Attack range: ${uiState.rangeAttack}")
        Text(text = "Armor: ${uiState.armor}")

    }
}