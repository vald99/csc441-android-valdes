package edu.lemoyne.campusapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ShowcaseUiState {
    data object Loading: ShowcaseUiState()
    data class Success(val items: List<ShowcaseItem>): ShowcaseUiState()
    data class Error(val message: String): ShowcaseUiState()
}

class ShowcaseViewModel: ViewModel() {
    private val repository = ShowcaseRepository()

    private val _state = MutableStateFlow<ShowcaseUiState>(ShowcaseUiState.Loading)
//    val uiState: StateFlow<ShowcaseUiState> = _uiState
    val state = _state.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _state.value = ShowcaseUiState.Loading

            try {
                val items = repository.loadItems()
                _state.value = ShowcaseUiState.Success(items)
            } catch (error: Exception) {
                _state.value = ShowcaseUiState.Error(error.message ?: "Network error")
            }
        }
    }
}