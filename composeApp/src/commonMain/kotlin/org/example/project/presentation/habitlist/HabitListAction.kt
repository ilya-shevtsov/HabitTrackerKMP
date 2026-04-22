package org.example.project.presentation.habitlist

sealed interface HabitListAction {

    data class OnHabitClick(val id: String) : HabitListAction

    data class OnToggleDone(val id: String) : HabitListAction

    data class OnFilterChanged(val filter: HabitFilter) : HabitListAction

    data object OnAddMockHabit : HabitListAction
}