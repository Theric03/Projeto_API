package com.example.api_project.ui.views

import com.example.api_project.R
import com.squareup.moshi.Json

data class HeroStatsUiState(
    val name: String = "",
    val img: String = "",
    val life: Int = 0,
    val mana: Int = 0,
    val attackMin: Int = 0,
    val attackMax: Int = 0,
    val rangeAttack: Int = 0,
    val armor: Int = 0
)
