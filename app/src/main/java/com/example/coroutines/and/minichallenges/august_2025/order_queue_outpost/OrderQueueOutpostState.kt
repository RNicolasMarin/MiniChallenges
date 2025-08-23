package com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost

import com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost.Status.*

data class OrderQueueOutpostState(
    val status: Status = CAN_START,
    val orderAmount: Int = 0
)

enum class Status {
    CAN_START,
    PAUSED,
    GOING
}