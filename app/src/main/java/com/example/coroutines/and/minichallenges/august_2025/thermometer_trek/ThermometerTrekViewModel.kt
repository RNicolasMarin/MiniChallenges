package com.example.coroutines.and.minichallenges.august_2025.thermometer_trek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ThermometerTrekViewModel: ViewModel() {

    var state by mutableStateOf(ThermometerTrekState())
        private set

}