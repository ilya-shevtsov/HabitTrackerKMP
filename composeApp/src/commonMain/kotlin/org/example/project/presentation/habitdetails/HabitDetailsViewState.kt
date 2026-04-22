package org.example.project.presentation.habitdetails

import org.example.project.presentation.habitdetails.entities.HabitDetailsModel

sealed interface HabitDetailsViewState {
    data object Loading : HabitDetailsViewState
    data object Error : HabitDetailsViewState
    data class Content(
        val habit: HabitDetailsModel
    ) : HabitDetailsViewState
}

