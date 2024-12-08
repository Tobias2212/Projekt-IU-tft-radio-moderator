package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.model.SongRequest
import de.iu.tftradiomoderator.data.provider.RadioMemoryProvider
import de.iu.tftradiomoderator.data.provider.RadioNetworkProvider

internal class SongRequestService {

    private val songCache = RadioMemoryProvider<List<SongRequest>>()
    private val radioNetworkProvider = RadioNetworkProvider()

    suspend fun loadSongRequests(): List<SongRequest> {
        return try {
            val updatedList = radioNetworkProvider.getSongRequests()
            songCache.cacheAndRetrieve(updatedList)
        } catch (e: Exception) {
            val cachedList = songCache.retrieve()
            if (cachedList != null && cachedList.isNotEmpty()) {
                cachedList
            } else {
                throw e
            }
        }
    }
}

