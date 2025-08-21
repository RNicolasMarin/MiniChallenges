package com.example.coroutines.and.minichallenges.august_2025.thermometer_trek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.and.minichallenges.august_2025.thermometer_trek.Status.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class ThermometerTrekViewModel(
    private val repository: ThermometerTrekRepository
): ViewModel() {

    var state by mutableStateOf(ThermometerTrekState())
        private set

    fun startRestartThermometer() {
        state = state.copy(
            status = TRACKING,
            temperatures = emptyList()
        )

        viewModelScope.launch {
            repository.observeTemperatures()
                .onEach {
                    println("onEach; $it")
                    delay(250)
                }
                .filter {
                    it >= -50
                }
                .map {
                    (it * 9 / 5) + 32
                }
                .take(20)
                .collect {
                    state = state.copy(
                        status = if (state.temperatures.size == 19) CAN_RESET else TRACKING,
                        temperatures = state.temperatures + it
                    )
                    println("collecting: $state")
                }
        }
    }

}