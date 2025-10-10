package com.example.minichallenges.september_2025.accessible_audio_schedule

data class Performance(
    val artist: String,
    val time: String,
    val venue: Venue,
    val description: String,
    val genre: Genre
)
