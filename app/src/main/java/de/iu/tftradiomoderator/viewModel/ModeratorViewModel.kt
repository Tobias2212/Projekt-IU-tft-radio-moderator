package de.iu.tftradiomoderator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradiomoderator.data.service.ModeratorService
import de.iu.tftradiomoderator.ui.Rating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

internal class ModeratorViewModel : ViewModel() {
    private val moderatorService = ModeratorService()

    private val _moderatorName = MutableStateFlow("")
    val moderatorName: StateFlow<String> = _moderatorName

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating: StateFlow<Double> = _averageRating

    private val _ratings = MutableStateFlow<List<Rating>>(emptyList())
    val ratings: StateFlow<List<Rating>> = _ratings

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error

    init {
        loadModeratorInfo()
        viewModelScope.launch {
            startPollingForNewRatings()
        }
    }

    private fun loadModeratorInfo() {
        viewModelScope.launch {
            while (true) {
                try {
                    val moderator = moderatorService.getCurrentModerator()
                    _moderatorName.value = moderator.name
                    _averageRating.value = moderator.averageRating
                    _error.value = null
                } catch (e: Exception) {

                    _error.value = e
                }
                delay(5000L)
            }
        }
    }

    private suspend fun startPollingForNewRatings() {
        while (true) {
            try {
                _ratings.value = moderatorService.getRatings()
                _averageRating.value = moderatorService.calculateAverageRating()

            } catch (e: Exception) {
                _error.value = e
            }
            delay(5000L)
        }
    }

    // Funktion zum erneuten Laden der Daten
    fun retryLoading() {
        _error.value = null
        // Daten erneut laden
        loadModeratorInfo()
        viewModelScope.launch {
            startPollingForNewRatings()
        }
    }
}