package de.iu.tftradiomoderator.data.api

import de.iu.tftradiomoderator.data.objects.SongRequest
import de.iu.tftradiomoderator.data.objects.Rating
import de.iu.tftradiomoderator.data.objects.Moderator
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/songRequests")
    suspend fun getSongRequests(): Response<List<SongRequest>>

   // @POST("/songRequest")
    //suspend fun addSongRequest(@Body songRequest: SongRequest): Response<Unit>

    @GET("/moderator")
    suspend fun getModerator(): Response<Moderator>

    @GET("/ratings")
    suspend fun getRatings(): Response<List<Rating>>


}
