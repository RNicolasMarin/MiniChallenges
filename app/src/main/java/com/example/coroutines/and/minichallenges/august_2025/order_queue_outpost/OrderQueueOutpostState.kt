package com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost

import com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost.Status.*

data class OrderQueueOutpostState(
    val status: Status = CAN_START,
    val orderId: List<Int> = emptyList()
)

enum class Status {
    CAN_START,
    PAUSED,
    GOING
}