package de.iu.tftradiomoderator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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

import kotlinx.coroutines.launch
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
                    MainScreen()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
    songRequestViewModel: SongRequestViewModel = viewModel(),
    moderatorViewModel: ModeratorViewModel  = viewModel()
) {var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ModeratorInfoSection(viewModel = moderatorViewModel)

        Spacer(modifier = Modifier.height(16.dp))


        SongRequestList(
            viewModel = songRequestViewModel,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button for Bottom Sheet
        Button(
            onClick = { showBottomSheet = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Bewertungen anzeigen")
        }

        //  Bottom Sheet
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                RatingsSection(viewModel = moderatorViewModel)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable

fun Preview() {

    MainScreen()
}



