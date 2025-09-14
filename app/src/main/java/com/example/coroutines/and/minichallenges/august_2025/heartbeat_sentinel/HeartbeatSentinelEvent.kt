package com.example.coroutines.and.minichallenges.august_2025.heartbeat_sentinel

sealed class HeartbeatSentinelEvent {

    data class ShowSnackBar(
        val station: Station,
        val isOnline: Boolean
    ) : HeartbeatSentinelEvent()
}