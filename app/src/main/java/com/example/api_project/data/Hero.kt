package com.example.api_project.data

import com.squareup.moshi.Json

data class Hero(
    val id: Int,
    val img: String,
    @Json(name = "localized_name") val name: String
)
