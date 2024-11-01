package de.iu.tftradiomoderator.data.objects

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
data class SongRequest(
    @JsonProperty("title") val title: String,
    @JsonProperty("album") val album: String,
    @JsonProperty("interpret") val interpret: String,
    @JsonProperty("favoriteCount") val favoriteCount: Int

)