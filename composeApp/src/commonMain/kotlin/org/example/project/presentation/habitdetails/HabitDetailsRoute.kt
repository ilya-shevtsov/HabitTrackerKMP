package org.example.project.presentation.habitdetails


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.platform.PlatformActions
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun HabitDetailsRoute(
    habitId: String,
    platformActions: PlatformActions,
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
        onAction = viewModel::handleAction,
        onOpenGitHub = {
            platformActions.openUrl("https://github.com/ilya-shevtsov")
        },
        onShareHabit = { habit ->
            val text = buildString {
                appendLine("Habit: ${habit.title}")
                appendLine("Description: ${habit.description}")
                appendLine("Streak: ${habit.streak}")
                appendLine("Completed days: ${habit.completedDays}")
                appendLine("Done today: ${habit.doneToday}")
            }
            platformActions.shareText(text)
        }
    )
}