package org.example.project.presentation.habitdetails

sealed interface HabitDetailsAction {

    data object OnBackClick : HabitDetailsAction

    data object OnToggleDone : HabitDetailsAction
}