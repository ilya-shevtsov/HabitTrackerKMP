package org.example.project.presentation.habitlist

sealed interface HabitListEvent {

    data class OpenHabitDetails(val id: String) : HabitListEvent

}