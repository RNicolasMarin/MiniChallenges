package com.example.coroutines.and.minichallenges.august_2025.heartbeat_sentinel

data class HeartbeatSentinelState(
    val stationA: HeartbeatSentinelStateStation = HeartbeatSentinelStateStation(Station.A),
    val stationB: HeartbeatSentinelStateStation = HeartbeatSentinelStateStation(Station.B),
    val stationC: HeartbeatSentinelStateStation = HeartbeatSentinelStateStation(Station.C),
)

fun HeartbeatSentinelState.getFirstOnline(): HeartbeatSentinelStateStation? {
    var station: HeartbeatSentinelStateStation? = null
    if (stationA.status == StationStatus.Offline) {
        station = stationA.copy()
    }
    if (stationB.status == StationStatus.Offline) {
        when {
            station == null -> {
                station = stationB.copy()
            }
            stationB.lastOnLine < station.lastOnLine -> {
                station = stationB.copy()
            }
        }
    }
    if (stationC.status == StationStatus.Offline) {
        when {
            station == null -> {
                station = stationC.copy()
            }
            stationC.lastOnLine < station.lastOnLine -> {
                station = stationC.copy()
            }
        }
    }
    return station
}

data class HeartbeatSentinelStateStation(
    val station: Station,
    val status: StationStatus = StationStatus.Showing(true),
    val lastOnLine: Long = 0
)

sealed class StationStatus(open val isOnline: Boolean) {
    data class Showing(override val isOnline: Boolean): StationStatus(isOnline)
    data class NotShowing(override val isOnline: Boolean): StationStatus(isOnline)
    data object Offline: StationStatus(false)
}
