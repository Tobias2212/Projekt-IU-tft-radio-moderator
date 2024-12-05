package de.iu.tftradiomoderator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradiomoderator.data.service.SongRequestService
import de.iu.tftradiomoderator.data.model.SongRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class SongRequestViewModel : ViewModel() {
    private val songRequestService = SongRequestService()
    val _songRequests = MutableStateFlow<List<SongRequest>>(emptyList())
    val songRequests: StateFlow<List<SongRequest>> = _songRequests
    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error
    init {
        startPollingForNewRequests()
    }

    private fun startPollingForNewRequests() {
        viewModelScope.launch {
            while (true) {
                try {
                    _songRequests.value = songRequestService.loadSongRequests()
                    _error.value = null // Fehlerzustand zurücksetzen
                } catch (e: Exception) {
                    _error.value = e // Fehlerzustand setzen
                }
                delay(5000L)
            }
        }
    }

    // Retry-Funktion
    fun retryLoadingSongRequests() {
        _error.value = null
        startPollingForNewRequests()
    }
}
