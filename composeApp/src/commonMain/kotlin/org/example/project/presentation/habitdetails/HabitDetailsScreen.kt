package org.example.project.presentation.habitdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailsScreen(
    state: HabitDetailsViewState,
    onAction: (HabitDetailsAction) -> Unit,
    modifier: Modifier = Modifier
) {

    when(state){
        is HabitDetailsViewState.Error -> {

        }
        is HabitDetailsViewState.Loading -> {

        }
        is HabitDetailsViewState.Content -> {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text("Habit Details") },
                        navigationIcon = {
                            TextButton(onClick = { onAction(HabitDetailsAction.OnBackClick) }) {
                                Text("Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors()
                    )
                }
            ) { innerPadding ->
                val habit = state.habit

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = habit.title,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = habit.description,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text("Current streak: ${habit.streak}")
                    Text("Completed days: ${habit.completedDays}")
                    Text(if (habit.doneToday) "Done today" else "Not done today")

                    Checkbox(
                        checked = habit.doneToday,
                        onCheckedChange = {
                            onAction(HabitDetailsAction.OnToggleDone)
                        }
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onAction(HabitDetailsAction.OnToggleDone) }
                    ) {
                        Text(
                            if (habit.doneToday) "Mark as not done" else "Mark as done"
                        )
                    }
                }
            }
        }
    }

}