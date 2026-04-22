package org.example.project.presentation.habitlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HabitListRoute(
    onOpenDetails: (String) -> Unit,
    viewModel: HabitListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is HabitListEvent.OpenHabitDetails -> onOpenDetails(event.id)
            }
        }
    }

   HabitListScreen(
       state = state,
       onAction = viewModel::handleAction
   )
}