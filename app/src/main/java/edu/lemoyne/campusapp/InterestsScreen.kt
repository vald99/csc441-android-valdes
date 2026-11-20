package edu.lemoyne.campusapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InterestsScreen(
    uiState: ShowcaseUiState,
    onBack: () -> Unit,
    onRefresh: () -> Unit, // Lab 21: Task 1
    modifier: Modifier = Modifier,
) {
    // Lab 21: Task 1: load when the screen opens
    LaunchedEffect(Unit) {
        onRefresh
    }

    BackHandler { onBack() }
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("Back") }
        // Lab 21: Task 1: reload when tapped
        TextButton(onClick = onRefresh) {
            Text("Refresh items")
        }
        when (uiState) {
            ShowcaseUiState.Loading -> Text("Loading from Supabase...")
            is ShowcaseUiState.Error -> Column {
                Text("Could not load: " + uiState.message)
            }
            is ShowcaseUiState.Success -> LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.items) { item ->
                    Column(Modifier.padding(8.dp)) {
                        item.title?.let { Text(it) }
                        item.category?.let { Text(it) }
                        item.description?.let { Text(it) }
                    }
                }
            }
        }
    }
}