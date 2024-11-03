package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.objects.SongRequest
import de.iu.tftradiomoderator.data.provider.RadioMemoryProvider
import de.iu.tftradiomoderator.data.provider.RadioNetworkProvider


class SongRequestService {

    private val songCache = RadioMemoryProvider<List<SongRequest>>()
    private val radioNetworkProvider = RadioNetworkProvider()
    private val maxSongs = 20


    suspend fun loadFromNetworkAndCache(): List<SongRequest> {
        return try {

            val networkSongs = radioNetworkProvider.getSongRequests()
            val cachedSongs = songCache.retrieve() ?: emptyList()


            val newSongs = networkSongs.filterNot { cachedSongs.contains(it) }


            val updatedList = (newSongs + cachedSongs).take(maxSongs)

            songCache.cacheAndRetrieve(updatedList)
        } catch (e: Exception) {

            songCache.retrieve() ?: emptyList()
        }
    }


    suspend fun getInitialSongRequests(): List<SongRequest> {

        if (songCache.retrieve().isNullOrEmpty()) {
            println("Versuche Daten zu laden")
            val initialSongs = try {
                val networkSongs = radioNetworkProvider.getSongRequests()
                songCache.cacheAndRetrieve(networkSongs.take(maxSongs))

            } catch (e: Exception) {
                //Testdaten
                val fallbackSongs = listOf(
                    SongRequest("Song A", "Interpret A", "Album A", 100),
                    SongRequest("Song B", "Interpret B", "Album B", 50),
                    SongRequest("Song C", "Interpret C", "Album C", 75)
                )
                songCache.cacheAndRetrieve(fallbackSongs)
            }
            return initialSongs
        }

        return songCache.retrieve() ?: emptyList()
    }
}
