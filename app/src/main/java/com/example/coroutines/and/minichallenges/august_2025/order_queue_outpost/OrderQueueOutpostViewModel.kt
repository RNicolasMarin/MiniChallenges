package com.example.coroutines.and.minichallenges.august_2025.order_queue_outpost

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OrderQueueOutpostViewModel: ViewModel() {

    var state by mutableStateOf(OrderQueueOutpostState())
        private set


}