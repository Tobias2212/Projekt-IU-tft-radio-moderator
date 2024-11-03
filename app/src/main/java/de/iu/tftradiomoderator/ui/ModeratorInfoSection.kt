package de.iu.tftradiomoderator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.iu.tftradiomoderator.R
import de.iu.tftradiomoderator.viewModel.ModeratorViewModel

@Composable
fun ModeratorInfoSection(viewModel: ModeratorViewModel) {
    val moderatorName by viewModel.moderatorName.collectAsState()
    val averageRating by viewModel.averageRating.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.current_moderator),
            style = MaterialTheme.typography.titleMedium,
           )


        Text(
            text = moderatorName,
            style = MaterialTheme.typography.headlineMedium
        )
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {

            StarRating(rating = averageRating)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "(${String.format("%.1f", averageRating)} Sterne)",
                style = MaterialTheme.typography.bodyLarge
            )

        }

    }
}
@Preview(showBackground = true)
@Composable
private fun Preview() {
    ModeratorInfoSection(viewModel = viewModel())
}

