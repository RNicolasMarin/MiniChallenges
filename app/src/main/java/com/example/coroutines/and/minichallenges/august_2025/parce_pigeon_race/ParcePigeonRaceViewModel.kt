package com.example.coroutines.and.minichallenges.august_2025.parce_pigeon_race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ParcePigeonRaceViewModel: ViewModel() {

    var state by mutableStateOf(ParcePigeonRaceState())
        private set

}