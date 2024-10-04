package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.objects.SongRequest
import kotlinx.coroutines.delay

class SongRequestService {

    private val songRequestList = mutableListOf<SongRequest>()
    private val maxSongs = 20 //length of the list
    private var i = 0

    suspend fun getLatestSongRequest(): SongRequest {
        // Simulation

        val newSong = SongRequest(
            title = "Song $i",
            interpret = "Neuer Interpret",
            album = "Neues Album",
            favoriteCount = (1..100).random()
        )
        i++
        delay(1000L)
        addSongRequest(newSong)
        return newSong


    }
suspend fun getInitialSongRequestes(): List<SongRequest> {
    var newSong = SongRequest(
        title = "Song A",
        interpret = "Interpret A",
        album = "Album A",
        favoriteCount = 100
    )
    addSongRequest(newSong)
     newSong = SongRequest(
        title = "Song B",
        interpret = "Interpret C",
        album = "Album D",
        favoriteCount = 50
    )
    addSongRequest(newSong)

    return songRequestList
}



    private fun addSongRequest(newSong: SongRequest) {
        songRequestList.add(0, newSong)

       if (songRequestList.size > maxSongs) {
            songRequestList.removeLast()
        }
    }

    fun getSongRequests(): List<SongRequest> {

        return songRequestList
    }
}