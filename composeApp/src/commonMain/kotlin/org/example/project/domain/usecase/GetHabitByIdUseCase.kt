package org.example.project.domain.usecase

import org.example.project.domain.model.Habit
import org.example.project.domain.repository.HabitRepository

class GetHabitByIdUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(id: String): Habit? {
        return repository.getHabitById(id)
    }
}