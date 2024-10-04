package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.objects.SongRequest
import kotlinx.coroutines.delay

class SongRequestService {

    private val songRequestList = mutableListOf<SongRequest>()
    private val maxSongs = 20 //length of the list
    private var i = 0

    suspend fun getLatestSongRequest(): SongRequest {
        // Simulation
        i++
        delay(1000L)
        return SongRequest(
            title = "Song $i",
            interpret = "Neuer Interpret",
            album = "Neues Album",
            favoriteCount = (1..100).random()

        )

    }

    fun addSongRequest(newSong: SongRequest) {
        songRequestList.add(0, newSong)

       if (songRequestList.size > maxSongs) {
            songRequestList.removeLast()
        }
    }

    fun getSongRequests(): List<SongRequest> {

        return songRequestList
    }
}