package com.example.minichallenges.august_2025.parce_pigeon_race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

class ParcePigeonRaceViewModel constructor(
    private val repository: ParcePigeonRaceRepository
): ViewModel() {

    var state by mutableStateOf(ParcePigeonRaceState())
        private set

    var jobs: MutableList<Job> = (0..5).map { Job() }.toMutableList()

    private val mutex = Mutex()

    fun runOrRunAgain(cacheDir: File) {
        state = state.copy(
            status = Status.RUNNING,
            images = (0..5).map { PigeonImage(it) }.toMutableList()
        )
        viewModelScope.launch {
            state.images.forEachIndexed { index, item ->
                jobs[index] = launch {
                    val result = repository.getPigeonImage(item, cacheDir)
                    if (result != null) {
                        mutex.withLock {
                            val newImages = state.images.map { oldItem ->
                                if (oldItem.position == result.position) {
                                    PigeonImage(
                                        position = oldItem.position,
                                        file = result.file,
                                        size = result.size,
                                        durationInMilSeconds = result.durationInMilSeconds,
                                        isLoading = result.isLoading
                                    )
                                } else {
                                    PigeonImage(
                                        position = oldItem.position,
                                        file = oldItem.file,
                                        size = oldItem.size,
                                        durationInMilSeconds = oldItem.durationInMilSeconds,
                                        isLoading = oldItem.isLoading
                                    )
                                }
                            }
                            state = state.copy(images = newImages.toMutableList())
                        }
                    }
                }
            }
            for (job in jobs) {
                job.join()
            }
            state = state.copy(
                status = Status.CAN_RUN_AGAIN
            )
        }
    }

}