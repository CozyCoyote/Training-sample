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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cosy.coyote.training.sample.components.Toolbar

@Composable
fun MainScreen(
    navHostController: NavHostController,
    vm: MainViewModel = viewModel(),
) {

    println("redraw -- main screen")

    MainScreenLayout(
        status = {
            println("redraw -- status")
            val viewState by vm.viewState.collectAsStateWithLifecycle()
            when (viewState) {
                is MainViewModel.ViewState.Loading -> "Data is loading"
                is MainViewModel.ViewState.Data -> (viewState as MainViewModel.ViewState.Data).body
            }
        },
        acknowledge = "Tutorial body",
        nextClicked = {
            navHostController.navigate("stepper")
        }
    )
}

@Composable
private fun MainScreenLayout(
    status: @Composable () -> String,
    acknowledge: String,
    nextClicked: () -> Unit,
) = Scaffold(
    topBar = {
        println("redraw -- toolbar")
        Toolbar("Main screen") }
) { actionbarPadding ->
    Column(
        modifier = Modifier
            .padding(actionbarPadding)
            .fillMaxSize()
    ) {
        println("redraw -- column")
        Text(text = status(), Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
        Text(text = acknowledge, Modifier.padding(horizontal = 16.dp))
        Button(
            onClick = { nextClicked() },
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = "Next page")
        }
    }
}

@Preview
@Composable
fun preview() {
    MainScreen(rememberNavController())
}
