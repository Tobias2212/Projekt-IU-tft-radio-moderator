package de.iu.tftradiomoderator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradiomoderator.data.service.SongRequestService
import de.iu.tftradiomoderator.data.objects.SongRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SongRequestViewModel : ViewModel() {
    private val songRequestService = SongRequestService()

    private val _songRequests = MutableStateFlow<List<SongRequest>>(emptyList())
    val songRequests: StateFlow<List<SongRequest>> = _songRequests

    init {

        startPollingForNewRequests()
    }

    fun loadSongRequests() {
        _songRequests.value = songRequestService.getSongRequests()
    }

    private fun startPollingForNewRequests() {
        viewModelScope.launch {
            while (true) {
                val newSong = songRequestService.getLatestSongRequest()

                songRequestService.addSongRequest(newSong)
                _songRequests.value = songRequestService.getSongRequests().toList()

                delay(5000L)
            }
        }
    }
}