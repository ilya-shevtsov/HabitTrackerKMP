package org.example.project.presentation.habitlist

import org.example.project.presentation.habitlist.entities.HabitModel

sealed class HabitListViewState {
    data object Loading : HabitListViewState()

    data object Error : HabitListViewState()

    data class Content(
        val habits: List<HabitModel>,
        val selectedFilter: HabitFilter
    ) : HabitListViewState()
}


