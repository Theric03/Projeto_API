package com.example.api_project.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_project.data.Hero
import com.example.api_project.network.OpenDotaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HeroesViewModel: ViewModel() {

    private var _uiState: MutableStateFlow<HeroesUiState> = MutableStateFlow(HeroesUiState.Loading)
    val uiState: StateFlow<HeroesUiState> = _uiState.asStateFlow()

    init {
        getHeroes()
    }

    private fun getHeroes(){
        viewModelScope.launch {
            try {
                _uiState.value = HeroesUiState.Success(OpenDotaApi.retrofitService.getHeroes())
            }catch (e: IOException){
                HeroesUiState.Error
            }catch (e: HttpException){
                HeroesUiState.Error
            }
        }
    }

}

sealed interface HeroesUiState{
    object Loading : HeroesUiState
    data class Success (val heroes: List<Hero>) : HeroesUiState
    object Error : HeroesUiState
}