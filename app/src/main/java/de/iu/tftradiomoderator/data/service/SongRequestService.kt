package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.objects.SongRequest
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

//    suspend fun getInitialSongRequests(): List<SongRequest> {
//
//        if (songCache.retrieve().isNullOrEmpty()) {
//            println("Versuche Daten zu laden")
//            val initialSongs = try {
//                val networkSongs = radioNetworkProvider.getSongRequests()
//                songCache.cacheAndRetrieve(networkSongs)
//
//            } catch (e: Exception) {
//                //Testdaten
//                val fallbackSongs = listOf(
//                    SongRequest("Song A", "Interpret A", "Album A", 100),
//                    SongRequest("Song B", "Interpret B", "Album B", 50),
//                    SongRequest("Song C", "Interpret C", "Album C", 75)
//                )
//                songCache.cacheAndRetrieve(fallbackSongs)
//            }
//            return initialSongs
//        }
//
//        return songCache.retrieve() ?: emptyList()
//    }

