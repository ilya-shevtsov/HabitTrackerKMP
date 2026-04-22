package org.example.project.presentation.habitlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.habitlist.entities.HabitModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitListScreen(
    state: HabitListViewState,
    onAction: (HabitListAction) -> Unit,
    modifier: Modifier = Modifier
) {

    when(state){
        is HabitListViewState.Error -> {
            //TODO handle error
        }
        is HabitListViewState.Loading -> {
            //TODO handle loading

        }
        is HabitListViewState.Content -> {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text("Habit Tracker") },
                        colors = TopAppBarDefaults.topAppBarColors()
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { onAction(HabitListAction.OnAddMockHabit) }
                    ) {
                        Text("+")
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    FilterRow(
                        selectedFilter = state.selectedFilter,
                        onFilterSelected = {
                            onAction(HabitListAction.OnFilterChanged(it))
                        }
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.habits, key = { it.id }) { habit ->
                            HabitCard(
                                habit = habit,
                                onClick = {
                                    onAction(HabitListAction.OnHabitClick(habit.id))
                                },
                                onToggleDone = {
                                    onAction(HabitListAction.OnToggleDone(habit.id))
                                }
                            )
                        }
                    }
                }
            }
        }
        }
    }



@Composable
private fun FilterRow(
    selectedFilter: HabitFilter,
    onFilterSelected: (HabitFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HabitFilter.entries.forEach { filter ->
            AssistChip(
                onClick = { onFilterSelected(filter) },
                label = { Text(filter.name) },
                leadingIcon = {
                    if (selectedFilter == filter) {
                        Text("✓")
                    }
                }
            )
        }
    }
}

@Composable
private fun HabitCard(
    habit: HabitModel,
    onClick: () -> Unit,
    onToggleDone: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = habit.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = habit.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Checkbox(
                    checked = habit.doneToday,
                    onCheckedChange = { onToggleDone() }
                )
            }

            Text("Streak: ${habit.streak}")
            Text("Completed days: ${habit.completedDays}")
            Text(
                if (habit.doneToday) "Done today" else "Not done today",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}