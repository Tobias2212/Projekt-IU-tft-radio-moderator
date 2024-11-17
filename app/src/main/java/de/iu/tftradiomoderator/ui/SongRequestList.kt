package de.iu.tftradiomoderator.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.iu.tftradiomoderator.data.objects.SongRequest
import de.iu.tftradiomoderator.viewModel.SongRequestViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
internal fun SongRequestList(
    viewModel: SongRequestViewModel,
    modifier: Modifier = Modifier
) {
    var prevFirstSong by remember { mutableStateOf<SongRequest?>(null) }
    var highlightedSong by remember { mutableStateOf<SongRequest?>(null) }
    val songRequestList by viewModel.songRequests.collectAsState()
    LaunchedEffect(songRequestList) {
        if (songRequestList.isNotEmpty()) {
            val firstSong = songRequestList.first()
            if (firstSong != prevFirstSong) {
                prevFirstSong = firstSong
                highlightedSong = firstSong

                launch {
                    delay(1000L)
                    highlightedSong = null
                }
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Songwünsche",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 8.dp
            )
        )
        LazyColumn {
            items(songRequestList) { song ->
                val highlightColor = if (song == highlightedSong) Color.Red else Color.Transparent
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    SongRequestCard(
                        title = song.title,
                        interpret = song.interpret,
                        album = song.album,
                        favoriteCount = song.favoriteCount,
                        highlightColor = highlightColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 0.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SongRequestListPreview() {
    val mockViewModel = SongRequestViewModel().apply {
        _songRequests.value = listOf(
            SongRequest("Song A", "Interpret A", "Album A", 5),
            SongRequest("Song B", "Interpret B", "Album B", 8),
            SongRequest("Song C", "Interpret C", "Album C", 12)
        )
    }
    SongRequestList(viewModel = mockViewModel)
}

