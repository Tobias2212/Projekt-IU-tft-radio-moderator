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
    val _songRequests = MutableStateFlow<List<SongRequest>>(emptyList())
    val songRequests: StateFlow<List<SongRequest>> = _songRequests

    init {
        startPollingForNewRequests()
    }


    private fun startPollingForNewRequests() {
        viewModelScope.launch {

            _songRequests.value = songRequestService.getInitialSongRequests()
            delay(100)

            while (true) {
                _songRequests.value = songRequestService.loadFromNetworkAndCache()
                delay(5000L)
            }
        }
    }
}

