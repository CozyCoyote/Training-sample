package com.cosy.coyote.training.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cosy.coyote.training.sample.components.Toolbar
import com.cosy.coyote.training.sample.ui.theme.TrainingSampleTheme
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MainScreen(
    goToStepper: () -> Unit,
    vm: MainViewModel = viewModel(),
) {

    println("redraw all")


    MainScreenLayout(
        status = {
            val viewState by vm.viewState.collectAsStateWithLifecycle()
            when (viewState) {
                is MainViewModel.ViewState.Loading -> "Data is loading"
                is MainViewModel.ViewState.Data -> (viewState as MainViewModel.ViewState.Data).body
            }
        },
        acknowledge = "Tutorial body",
        nextClicked = {
            vm.nextClicked()
        },
        viewState = {
            val viewAction by vm.viewAction.consumeAsFlow().collectAsStateWithLifecycle(
                initialValue = MainViewModel.ViewAction.None
            )
            when (viewAction) {
                is MainViewModel.ViewAction.NavigateToStepper -> goToStepper()
                is MainViewModel.ViewAction.None -> { /* nothing to do here */ }
            }
        }
    )
}

@Composable
private fun MainScreenLayout(
    status: @Composable () -> String,
    acknowledge: String,
    nextClicked: () -> Unit,
    viewState: @Composable () -> Unit,
) = Scaffold(
    topBar = { Toolbar("Main screen") }
) { actionbarPadding ->
    Column(
        modifier = Modifier
            .padding(actionbarPadding)
            .fillMaxSize()
    ) {
        Text(text = status(), Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
        Text(text = acknowledge, Modifier.padding(horizontal = 16.dp))
        Button(
            onClick = { nextClicked() },
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = "Next page")
            viewState()
        }
    }
}

@Preview
@Composable
private fun preview() {
    TrainingSampleTheme {
        MainScreenLayout(
            status = { "Data is loading" },
            acknowledge = "Tutorial body",
            nextClicked = {},
            viewState = {},
        )
    }
}
