package com.example.api_project.data

import com.example.api_project.R
import com.squareup.moshi.Json

data class Hero(
    val id: Int,
    val img: String,
    @Json(name = "localized_name") val name: String,
    @Json(name = "base_health") val life: Int,
    @Json(name = "base_mana") val mana: Int,
    @Json(name = "base_attack_min") val attackMin: Int,
    @Json(name = "base_attack_max") val attackMax: Int,
    @Json(name = "attack_range") val rangeAttack: Int,
    @Json(name = "base_armor") val armor: Int


)
