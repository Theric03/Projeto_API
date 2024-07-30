package com.example.api_project.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.api_project.R
import com.example.api_project.data.Hero
import com.example.api_project.network.OpenDotaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HeroesUiState{
    object Loading : HeroesUiState
    data class Success (val heroes: List<Hero>) : HeroesUiState
    object Error : HeroesUiState
}

class HeroesViewModel: ViewModel() {

    //Inalteravel fora da classe
    private var _heroesListUiState: MutableStateFlow<HeroesUiState> = MutableStateFlow(HeroesUiState.Loading)
    //Acessivel em outras classes
    val heroesListUiState: StateFlow<HeroesUiState> = _heroesListUiState.asStateFlow()

    private val _heroStatsUiState: MutableStateFlow<HeroStatsUiState> =
        MutableStateFlow(HeroStatsUiState())

    val heroStatsUiState: StateFlow<HeroStatsUiState> =
        _heroStatsUiState.asStateFlow()

    private val _mainScreenUiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState())
    val mainScreenUiState: StateFlow<MainScreenUiState> =
        _mainScreenUiState.asStateFlow()

    init {
        getHeroes()
    }

    private fun getHeroes(){
        viewModelScope.launch {
            try {
                _heroesListUiState.value = HeroesUiState.Success(OpenDotaApi.retrofitService.getHeroes())
            }catch (e: IOException){
                HeroesUiState.Error
            }catch (e: HttpException){
                HeroesUiState.Error
            }
        }
    }




    fun navigateBack(navController: NavController){
        _mainScreenUiState.update {
            MainScreenUiState()
        }
        navController.popBackStack()
    }

    fun selectHero(hero: Hero, navController: NavController){
        _heroStatsUiState.update { currentState ->
            currentState.copy(
                img = hero.img,
                name = hero.name,
                life = hero.life,
                mana = hero.mana,
                attackMin = hero.attackMin,
                attackMax = hero.attackMax,
                rangeAttack = hero.rangeAttack,
                armor = hero.armor
            )
        }

        _mainScreenUiState.update { currentState ->
            currentState.copy(
                title = R.string.hero_stats,
            )
        }
        navController.navigate(route = AppScreens.HeroStats.name)
    }

}

