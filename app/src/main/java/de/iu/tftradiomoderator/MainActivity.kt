package de.iu.tftradiomoderator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.iu.tftradiomoderator.ui.ModeratorInfoSection
import de.iu.tftradiomoderator.ui.RatingsSection
import de.iu.tftradiomoderator.ui.SongRequestList
import de.iu.tftradiomoderator.ui.theme.TftradiomoderatorTheme
import de.iu.tftradiomoderator.viewModel.ModeratorViewModel
import de.iu.tftradiomoderator.viewModel.SongRequestViewModel

class MainActivity : ComponentActivity() {
    private val songRequestViewModel: SongRequestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TftradiomoderatorTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen(songRequestViewModel = songRequestViewModel)
                }
            }
        }
    }
}


@Composable
fun SongRequestView() {
    val songRequestViewModel: SongRequestViewModel = viewModel()
    SongRequestList(viewModel = songRequestViewModel)
}

@Composable
private fun MainScreen(
    songRequestViewModel: SongRequestViewModel,
    moderatorViewModel: ModeratorViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Moderator-Informationen oben
        ModeratorInfoSection(viewModel = moderatorViewModel)

        Spacer(modifier = Modifier.height(0.dp))

        // Liste der Wunschsongs mit Gewichtung
        SongRequestList(
            viewModel = songRequestViewModel,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bewertungen unten
        RatingsSection(viewModel = moderatorViewModel)
    }
}


@Preview(showBackground = true)
@Composable

fun Preview() {
    val songRequestViewModel: SongRequestViewModel = viewModel()
    MainScreen(songRequestViewModel = songRequestViewModel)
}



