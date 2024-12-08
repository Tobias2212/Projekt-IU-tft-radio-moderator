package de.iu.tftradiomoderator.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
internal data class SongRequest(
    @JsonProperty("title") val title: String,
    @JsonProperty("album") val album: String,
    @JsonProperty("interpret") val interpret: String,
    @JsonProperty("favoriteCount") val favoriteCount: Int

)