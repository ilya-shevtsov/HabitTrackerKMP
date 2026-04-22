package org.example.project.domain.usecase

import org.example.project.domain.repository.HabitRepository

class ToggleHabitDoneUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(id: String) = repository.toggleDoneToday(id)
}