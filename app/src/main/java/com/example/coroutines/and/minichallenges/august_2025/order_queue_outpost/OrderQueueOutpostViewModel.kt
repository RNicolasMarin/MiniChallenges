package com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost.Status.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class OrderQueueOutpostViewModel: ViewModel() {
    var state by mutableStateOf(OrderQueueOutpostState())
        private set

    val orderChanel = Channel<Int>(capacity = 25)
    val eventsFlow: Flow<Int> = orderChanel.receiveAsFlow()

    fun startProducer() {
        state = state.copy(
            status = GOING,
            orderId = emptyList()
        )
        viewModelScope.launch {
            for (order in orderChanel) {
                state = state.copy(
                    orderId = state.orderId.drop(1)
                )
                println("Order removed: $order, ${state.orderId}")
                delay(Random.nextLong(100, 250))
            }
        }

        viewModelScope.launch {
            val orderIds = (1..50).toList()
            orderIds.forEach {
                delay(250)
                state = state.copy(
                    orderId = state.orderId + it
                )
                println("Order send: $it, ${state.orderId}")
                orderChanel.send(it)
            }
        }
    }
}
