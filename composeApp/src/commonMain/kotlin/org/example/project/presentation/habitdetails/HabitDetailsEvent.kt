package org.example.project.presentation.habitdetails

sealed interface HabitDetailsEvent {
    data object NavigateBack : HabitDetailsEvent
}