package de.iu.tftradiomoderator.data.api

import de.iu.tftradiomoderator.data.model.SongRequest
import de.iu.tftradiomoderator.ui.Rating
import de.iu.tftradiomoderator.data.model.Moderator
import retrofit2.Response
import retrofit2.http.GET

internal interface ApiService {

    @GET("/songRequests")
    suspend fun getSongRequests(): Response<List<SongRequest>>

    @GET("/moderator")
    suspend fun getModerator(): Response<Moderator>

    @GET("/ratings")
    suspend fun getRatings(): Response<List<Rating>>
}
