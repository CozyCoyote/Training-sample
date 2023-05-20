package com.cosy.coyote.training.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.internal.lib.RequiresAttention
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
    @RequiresAttention("view actions should not be observed as states")
    val viewAction: SharedFlow<ViewAction> = _viewAction

    init {
        viewModelScope.launch {
            val data = getData()
            _viewState.emit(ViewState.Data(data))
        }
    }

    fun nextClicked() {
        viewModelScope.launch {
            _viewAction.emit(
                if (_viewState.value == ViewState.Loading) {
                    ViewAction.DataNotLoaded
                } else {
                    ViewAction.NavigateToStepper
                }
            )
        }
    }

    fun settingsClicked() {
        viewModelScope.launch {
            _viewAction.emit(ViewAction.NavigateToSettings)
        }
    }

    private suspend fun getData() = withContext(Dispatchers.IO) {
        delay(5000L)
        "backend data"
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val body: String) : ViewState()
    }

    sealed class ViewAction {
        object DataNotLoaded : ViewAction()
        object NavigateToStepper : ViewAction()
        object NavigateToSettings : ViewAction()
    }

}
