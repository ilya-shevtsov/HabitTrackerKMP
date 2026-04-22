package org.example.project.domain.usecase

import org.example.project.domain.repository.HabitRepository

class AddHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(title: String, description: String) {
        repository.addHabit(title, description)
    }
}