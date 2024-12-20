package de.iu.tftradiomoderator.data.model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
internal data class Moderator(
    @JsonProperty("name") val name: String,
    @JsonProperty("averageRating")val averageRating: Double
)