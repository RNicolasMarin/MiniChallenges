package com.example.coroutines.and.minichallenges.august_2025.heartbeat_sentinel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.and.minichallenges.august_2025.heartbeat_sentinel.StationStatus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HeartbeatSentinelViewModel: ViewModel() {

    var state by mutableStateOf(HeartbeatSentinelState())
        private set

    private val heartbeatDuration = 50L

    init {
        startStation(Station.A)
        startStation(Station.B)
        startStation(Station.C)

        viewModelScope.launch {
            while (true) {
                delay(500)
                checkOffline(Station.A)
                checkOffline(Station.B)
                checkOffline(Station.C)
            }
        }

        viewModelScope.launch {
            delay(4000)
            updateStation(Station.C, Showing(false))
            delay(4000)
            updateStation(Station.C, Showing(true))
        }
    }

    private fun checkOffline(station: Station) {
        when (station) {
            Station.A -> {
                if (state.stationA.status != Offline && System.currentTimeMillis() - state.stationA.lastOnLine < 2000) return
                state = state.copy(
                    stationA = state.stationA.copy(
                        status = Offline,
                    )
                )
            }
            Station.B -> {
                if (state.stationB.status != Offline && System.currentTimeMillis() - state.stationB.lastOnLine < 2000) return
                state = state.copy(
                    stationB = state.stationB.copy(
                        status = Offline,
                    )
                )
            }
            Station.C -> {
                if (state.stationC.status != Offline && System.currentTimeMillis() - state.stationC.lastOnLine < 2000) return
                //Log.d("TAGN", "checkOffline OFFLINE")
                state = state.copy(
                    stationC = state.stationC.copy(
                        status = Offline,
                    )
                )
            }
        }
    }

    private fun isOnline(station: Station): Boolean {
        return when (station) {
            Station.A -> state.stationA.status.isOnline
            Station.B -> state.stationB.status.isOnline
            Station.C -> state.stationC.status.isOnline
        }
    }

    private fun startStation(station: Station) {
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                updateStation(station, NotShowing(isOnline(station)), true)

                delay(heartbeatDuration)

                updateStation(station, Showing(isOnline(station)), true)

                delay(station.timeElapsed - heartbeatDuration)
            }
        }
    }

    private fun updateStation(
        station: Station,
        status: StationStatus,
        validateNotOffline: Boolean = false
    ) {
        when (station) {
            Station.A -> {
                if (validateNotOffline && state.stationA.status == Offline) return
                state = state.copy(
                    stationA = state.stationA.copy(
                        status = status,
                        lastOnLine = if (status.isOnline) System.currentTimeMillis() else state.stationA.lastOnLine
                    )
                )
            }
            Station.B -> {
                if (validateNotOffline && state.stationB.status == Offline) return
                state = state.copy(
                    stationB = state.stationB.copy(
                        status = status,
                        lastOnLine = if (status.isOnline) System.currentTimeMillis() else state.stationB.lastOnLine
                    )
                )
            }
            Station.C -> {
                if (validateNotOffline && state.stationC.status == Offline) return
                state = state.copy(
                    stationC = state.stationC.copy(
                        status = status,
                        lastOnLine = if (status.isOnline) System.currentTimeMillis() else state.stationC.lastOnLine
                    )
                )
            }
        }
    }
}