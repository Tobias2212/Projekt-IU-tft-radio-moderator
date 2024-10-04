package de.iu.tftradiomoderator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.iu.tftradiomoderator.data.service.SongRequestService
import de.iu.tftradiomoderator.data.objects.SongRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class SongRequestViewModel : ViewModel() {
    private val songRequestService = SongRequestService()

    internal val _songRequests = MutableStateFlow<List<SongRequest>>(emptyList())
    var songRequests: StateFlow<List<SongRequest>> = _songRequests

    fun getRequestList()
    {
        loadSongRequests()
        _songRequests.value = songRequestService.getSongRequests().toList()


    }
init {
    startPollingForNewRequests()
}

    private fun loadSongRequests() {
        viewModelScope.launch {

           songRequestService.getInitialSongRequestes()

        }
    }

    private fun startPollingForNewRequests() {
        viewModelScope.launch {
            while (true) {
                songRequestService.getLatestSongRequest()
                _songRequests.value = songRequestService.getSongRequests().toList()


                delay(5000L)
            }
        }
    }
}