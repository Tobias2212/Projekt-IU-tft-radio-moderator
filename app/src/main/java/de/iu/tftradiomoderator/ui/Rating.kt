package de.iu.tftradiomoderator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.Alignment
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
data class Rating(
    @JsonProperty("stars") val rating: Int,
    @JsonProperty("comment")val comment: String
)

@Composable
fun RatingCard(rating: Rating) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                StarRating(rating = rating.rating.toDouble())
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${rating.rating} Sterne)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = rating.comment,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}

@Composable
fun StarRating(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        val maxStars = 5
        val fullStars = rating.toInt()
        val hasHalfStar = (rating - fullStars) >= 0.5
        val emptyStars = maxStars - fullStars - if (hasHalfStar) 1 else 0


        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = " Star",
                tint = Color.Yellow
            )
        }

        if (hasHalfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.StarHalf,
                contentDescription = "half Star",
                tint = Color.Yellow
            )
        }


        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Outlined.StarBorder,
                contentDescription = "empty Star",
                tint = Color.Gray
            )
        }
    }
}