package org.example.project.presentation.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.Habit
import org.example.project.domain.usecase.AddHabitUseCase
import org.example.project.domain.usecase.ObserveHabitListUseCase
import org.example.project.domain.usecase.ToggleHabitDoneUseCase
import org.example.project.presentation.habitlist.entities.HabitModel

class HabitListViewModel(
    private val observeHabitListUseCase: ObserveHabitListUseCase,
    private val toggleHabitDoneUseCase: ToggleHabitDoneUseCase,
    private val addHabitUseCase: AddHabitUseCase

) : ViewModel() {

    private val selectedFilter = MutableStateFlow(HabitFilter.ALL)

    private val _uiState = MutableStateFlow<HabitListViewState>(HabitListViewState.Loading)
    val uiState: StateFlow<HabitListViewState> = _uiState.asStateFlow()


    private val _uiEvents = MutableSharedFlow<HabitListEvent>()
    val uiEvents: SharedFlow<HabitListEvent> = _uiEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            combine(
                observeHabitListUseCase(),
                selectedFilter
            ) { habits, filter ->

                HabitListViewState.Content(
                    selectedFilter = filter,
                    habits = habits
                        .filterBy(filter)
                        .map { it.toModel() }
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun handleAction(action: HabitListAction) {
        when (action) {
            is HabitListAction.OnAddMockHabit -> handleOnAddMockHabit()
            is HabitListAction.OnFilterChanged -> handleOnFilterChanged(action)
            is HabitListAction.OnHabitClick -> handleOnHabitClick(action)
            is HabitListAction.OnToggleDone -> handleOnToggleDone(action)
        }
    }

    private fun handleOnToggleDone(action: HabitListAction.OnToggleDone) {
        viewModelScope.launch {
            toggleHabitDoneUseCase(action.id)
        }
    }

    private fun handleOnHabitClick(action: HabitListAction.OnHabitClick) {
        viewModelScope.launch {
            _uiEvents.emit(HabitListEvent.OpenHabitDetails(action.id))
        }
    }

    private fun handleOnFilterChanged(action: HabitListAction.OnFilterChanged) {
        selectedFilter.update { action.filter }
    }

    private fun handleOnAddMockHabit() {
        val actionTitle = "New Habit"
        val actionDesc = "New Habit created"
        viewModelScope.launch {
            addHabitUseCase(
                title = actionTitle,
                description = actionDesc
            )
        }
    }


    private fun Habit.toModel(): HabitModel {
        return HabitModel(
            id = id,
            title = title,
            description = description,
            streak = streak,
            doneToday = doneToday,
            completedDays = completedDays
        )
    }

    private fun List<Habit>.filterBy(filter: HabitFilter): List<Habit> {
        return when (filter) {
            HabitFilter.ALL -> this
            HabitFilter.ACTIVE -> filter { !it.doneToday }
            HabitFilter.COMPLETED -> filter { it.doneToday }
        }
    }

}