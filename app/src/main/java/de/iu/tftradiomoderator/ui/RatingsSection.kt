package de.iu.tftradiomoderator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.iu.tftradiomoderator.R
import de.iu.tftradiomoderator.data.objects.RatingCard
import de.iu.tftradiomoderator.viewModel.ModeratorViewModel

@Composable
fun RatingsSection(viewModel: ModeratorViewModel,  modifier: Modifier = Modifier) {
    val ratings by viewModel.ratings.collectAsState()




    LazyColumn(
        modifier = modifier
            .fillMaxWidth()

    ) {
        item {
            Text(
                text = stringResource(R.string.evaluation),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))


            ratings.forEach { rating ->
                RatingCard(rating = rating)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RatingsSection(viewModel = viewModel())
}
