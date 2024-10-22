package de.iu.tftradiomoderator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import de.iu.tftradiomoderator.data.service.ModeratorService

import de.iu.tftradiomoderator.data.objects.Rating
import kotlinx.coroutines.delay

class ModeratorViewModel : ViewModel() {
    private val moderatorService = ModeratorService()

    private val _moderatorName = MutableStateFlow("Max Mustermann")
    val moderatorName: StateFlow<String> = _moderatorName

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating: StateFlow<Double> = _averageRating

    private val _ratings = MutableStateFlow<List<Rating>>(emptyList())
    val ratings: StateFlow<List<Rating>> = _ratings

    init {
        loadModeratorInfo()

        viewModelScope.launch {
            loadInitialRatings()
            startPollingForNewRatings()
        }
    }

    private fun loadModeratorInfo() {
        viewModelScope.launch {
            val moderator = moderatorService.getCurrentModerator()
            _moderatorName.value = moderator.name
            _averageRating.value = moderator.averageRating
        }
    }

    private suspend fun loadInitialRatings() {

        _ratings.value = moderatorService.getInitialRatings().toList()
        updateAverageRating()

    }

    private suspend fun startPollingForNewRatings() {


        while (true) {
            moderatorService.getLatestRating()
            _ratings.value = moderatorService.getRatings().toList()
            updateAverageRating()
            delay(5000L)
        }
    }

    private fun updateAverageRating() {
        _averageRating.value = moderatorService.calculateAverageRating()
    }
}