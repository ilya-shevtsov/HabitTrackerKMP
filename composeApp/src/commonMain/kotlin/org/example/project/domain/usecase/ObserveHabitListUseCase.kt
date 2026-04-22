package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.Habit
import org.example.project.domain.repository.HabitRepository

class ObserveHabitListUseCase(
    private val repository: HabitRepository
) {
    operator fun invoke(): Flow<List<Habit>> {
        return repository.observerHabits()
    }
}