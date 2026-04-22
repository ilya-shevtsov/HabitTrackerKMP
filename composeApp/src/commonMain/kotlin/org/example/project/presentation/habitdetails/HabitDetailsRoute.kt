package org.example.project.presentation.habitdetails


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun HabitDetailsRoute(
    habitId: String,
    onBack: () -> Unit
) {
    val viewModel: HabitDetailsViewModel = koinInject {
        parametersOf(habitId)
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                HabitDetailsEvent.NavigateBack -> onBack()
            }
        }
    }

    HabitDetailsScreen(
        state = state,
        onAction = viewModel::handleAction
    )
}