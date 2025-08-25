package com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost.Status.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class OrderQueueOutpostViewModel: ViewModel() {
    var state by mutableStateOf(OrderQueueOutpostState())
        private set

    val orderChanel = Channel<Int>(capacity = 30)

    fun startProducer() {
        state = state.copy(
            status = GOING,
            orderAmount = 0
        )
        startProducing()
        startConsuming()
    }

    private fun startProducing() {
        viewModelScope.launch {
            val orderIds = (1..50).toList()
            orderIds.forEach {
                orderChanel.send(it)
                if (state.orderAmount < 30) {
                    state = state.copy(
                        orderAmount = state.orderAmount + 1
                    )
                    println("Order send: $it, ${state.orderAmount}")
                }
                delay(250)
            }
        }
    }

    private fun startConsuming() {
        viewModelScope.launch {
            for (order in orderChanel) {
                delay(Random.nextLong(100, 250))
                if (state.status == PAUSED) break
                if (state.orderAmount > 1) {
                    state = state.copy(
                        orderAmount = state.orderAmount - 1
                    )
                }
                println("Order removed: $order, ${state.orderAmount}")
            }
        }
    }

    fun stopOrRestartConsuming() {
        when (state.status) {
            CAN_START -> Unit
            PAUSED -> {
                state = state.copy(
                    status = GOING,
                )
                startConsuming()
            }
            GOING -> {
                state = state.copy(
                    status = PAUSED
                )
            }
        }
    }
}
