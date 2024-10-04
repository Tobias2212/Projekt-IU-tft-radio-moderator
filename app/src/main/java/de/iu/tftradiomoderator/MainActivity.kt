package de.iu.tftradiomoderator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.iu.tftradiomoderator.ui.SongRequestList
import de.iu.tftradiomoderator.ui.theme.TftradiomoderatorTheme
import de.iu.tftradiomoderator.viewModel.SongRequestViewModel

class MainActivity : ComponentActivity() {
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
                    SongRequestView()
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



@Preview(showBackground = true)
@Composable
fun Preview() {
    SongRequestView()
}


