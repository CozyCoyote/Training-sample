package com.cosy.coyote.training.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    private val _viewAction = MutableSharedFlow<ViewAction>()
    val viewAction: SharedFlow<ViewAction> = _viewAction

    init {
        viewModelScope.launch {
            val data = getData()
            _viewState.emit(ViewState.Data(data))
        }
    }

    fun nextClicked() {
        viewModelScope.launch {
            _viewAction.emit(ViewAction.NavigateToStepper)
        }
    }

    private suspend fun getData() = withContext(Dispatchers.IO) {
        delay(2000L)
        "backend data"
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val body: String) : ViewState()
    }

    sealed class ViewAction {
        object None : ViewAction()
        object NavigateToStepper : ViewAction()
    }

}
