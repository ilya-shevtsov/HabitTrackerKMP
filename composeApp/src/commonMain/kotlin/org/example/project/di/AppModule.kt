package org.example.project.di

import org.example.project.data.repository.InMemoryHabitRepository
import org.example.project.domain.repository.HabitRepository
import org.example.project.domain.usecase.AddHabitUseCase
import org.example.project.domain.usecase.GetHabitByIdUseCase
import org.example.project.domain.usecase.ObserveHabitListUseCase
import org.example.project.domain.usecase.ToggleHabitDoneUseCase
import org.example.project.presentation.habitlist.HabitListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    single<HabitRepository> { InMemoryHabitRepository() }

    factory { ObserveHabitListUseCase(get()) }
    factory { GetHabitByIdUseCase(get()) }
    factory { ToggleHabitDoneUseCase(get()) }
    factory { AddHabitUseCase(get()) }

    viewModelOf(::HabitListViewModel)
}