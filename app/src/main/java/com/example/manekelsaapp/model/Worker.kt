package com.example.manekelsaapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Worker(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val age: String = "",
    val experience: String = "",
    val dailyRate: String = "",
    val skill: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val available: Boolean = false,
    val availability: String = "",
    val rating: Double = 0.0,
    val ratingCount: Int = 0,
    val location: String = "", // Fixed: Now stores the user's choice
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val distance: Double = 0.0
)