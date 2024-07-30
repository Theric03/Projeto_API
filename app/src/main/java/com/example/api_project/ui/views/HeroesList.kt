package com.example.api_project.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.api_project.R
import com.example.api_project.data.Hero
import kotlinx.coroutines.selects.select

@Composable
fun HeroesListScreen(
    heroesViewModel: HeroesViewModel,
    navControler : NavController//= viewModel()
) {
    val uiState by heroesViewModel.heroesListUiState.collectAsState()
    when(uiState){
        is HeroesUiState.Loading -> LoadingScreen()
        is HeroesUiState.Success -> HeroList(heroes = (uiState as HeroesUiState.Success).heroes, heroesViewModel, navControler)
        is HeroesUiState.Error -> ErrorScreen()
    }

}

@Composable
fun LoadingScreen() {
//IMPLEMENTAR
}

@Composable
fun ErrorScreen() {
//IMPLEMENTAR
}


@Composable
fun HeroList(
    heroes: List<Hero>,
    viewModel: HeroesViewModel,
    navControler : NavController
) {
    LazyVerticalGrid(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {
        items(heroes) { hero ->
            HeroEntry(hero = hero,
                selectHero = {
                    viewModel.selectHero(
                        hero = hero,
                        navController = navControler
                    )
                })
        }
    }
}
@Composable
fun HeroEntry(
    hero: Hero,
    selectHero: (Hero) -> Unit,
) {
    val IMG_URL = "https://cdn.cloudflare.steamstatic.com/"

    val density = LocalDensity.current.density
    val width = remember {mutableStateOf(0F)}
    val height = remember {mutableStateOf(0F)}

    Card(
        modifier = Modifier
            .padding(6.dp)
            .clickable {
                selectHero(hero)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(IMG_URL+hero.img)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.dota_placeholder),
                contentDescription = hero.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    }
            )
            Box(modifier = Modifier
                .size(width = width.value.dp, height = height.value.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black),
                        100F,
                        500F,
                    )
                )
            )
            Text(
                text = hero.name,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White, fontWeight = FontWeight.Bold
                )
            )
        }
    }


}