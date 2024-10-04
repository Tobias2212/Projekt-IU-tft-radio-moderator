package de.iu.tftradiomoderator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SongRequestCard(
    title: String,
    interpret: String,
    album: String,
    favoriteCount: Int,
    highlightColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background (highlightColor)
            .height(160.dp),
        shape = MaterialTheme.shapes.medium,

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background (highlightColor)
                .padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                Album(text = album, modifier = Modifier.padding(vertical = 8.dp))
                Title(text = title, modifier = Modifier.padding(vertical = 8.dp))
                Interpret(text = interpret, modifier = Modifier.padding(vertical = 8.dp))
            }
            Favorite(favoriteCount = favoriteCount)
        }
    }
}

@Composable
fun Favorite(favoriteCount: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(40.dp)
        )
        Text(text = favoriteCount.toString(), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun Album(text: String, modifier: Modifier) {
    Text(text = text, style = MaterialTheme.typography.bodyLarge, modifier = modifier)
}

@Composable
fun Title(text: String, modifier: Modifier) {
    Text(text = text, style = MaterialTheme.typography.headlineMedium, modifier = modifier)
}

@Composable
fun Interpret(text: String, modifier: Modifier) {
    Text(text = text, style = MaterialTheme.typography.bodyLarge, modifier = modifier)
}

@Preview
@Composable
fun SongRequestCardPreview() {
    SongRequestCard(
        title = "Beispiel Song",
        interpret = "Beispiel Interpret",
        album = "Beispiel Album",
        favoriteCount = 15,
        highlightColor = Color.Yellow,
    )
}