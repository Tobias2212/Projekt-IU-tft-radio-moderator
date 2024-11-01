package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.objects.SongRequest
import de.iu.tftradiomoderator.data.provider.RadioMemoryProvider
import de.iu.tftradiomoderator.data.provider.RadioNetworkProvider
import kotlinx.coroutines.delay

class SongRequestService {

    private val songCache = RadioMemoryProvider<List<SongRequest>>()
    private val radioNetworkProvider = RadioNetworkProvider()
    private val maxSongs = 20

    private var requestCounter = 0

    suspend fun getSongRequests(clearCache: Boolean = false): List<SongRequest> {
        if (clearCache) songCache.clean()


        return songCache.retrieve() ?: loadFromNetworkAndCache()
    }

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


    suspend fun getLatestSongRequest(): SongRequest {
        val newSong = SongRequest(
            title = "Song $requestCounter",
            interpret = "Neuer Interpret",
            album = "Neues Album",
            favoriteCount = (1..100).random()
        )
        requestCounter++
        delay(1000L)
        addSongRequest(newSong)
        return newSong
    }

    fun addTestDataToCache(testData: List<SongRequest>) {

        val limitedTestData = testData.take(maxSongs)
        songCache.cacheAndRetrieve(limitedTestData)

        println("Daten? ${songCache.retrieve()}")
    }


    suspend fun getInitialSongRequests(): List<SongRequest> {

        if (songCache.retrieve().isNullOrEmpty()) {
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

    private fun addSongRequest(newSong: SongRequest) {
        val updatedList = (songCache.retrieve() ?: emptyList()).toMutableList()
        updatedList.add(0, newSong)
        if (updatedList.size > maxSongs) {
            updatedList.removeLast()
        }
        songCache.cacheAndRetrieve(updatedList)
    }
}
