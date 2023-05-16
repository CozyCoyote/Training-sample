package com.cosy.coyote.training.sample

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cosy.coyote.training.sample.components.Toolbar
import com.cosy.coyote.training.sample.components.collectAsEffect
import com.cosy.coyote.training.sample.ui.theme.TrainingSampleTheme

@Composable
fun MainScreen(
    goToStepper: () -> Unit,
    goToSettings: () -> Unit,
    vm: MainViewModel = viewModel(),
) {

    val context = LocalContext.current
    vm.viewAction.collectAsEffect(block = {
        when (it) {
            is MainViewModel.ViewAction.DataNotLoaded -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.data_loading),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is MainViewModel.ViewAction.NavigateToStepper -> goToStepper()
            is MainViewModel.ViewAction.NavigateToSettings -> goToSettings()
        }
    })

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
        settingsClicked = {
            vm.settingsClicked()
        }
    )
}

@Composable
private fun MainScreenLayout(
    status: @Composable () -> String,
    acknowledge: String,
    nextClicked: () -> Unit,
    settingsClicked: () -> Unit,
) = Scaffold(
    topBar = { Toolbar(
        "Main screen",
        actions = listOf(R.drawable.ic_settings to {
            settingsClicked()
        })
    ) }
) { actionbarPadding ->
    Column(
        modifier = Modifier
            .padding(actionbarPadding)
            .fillMaxSize()
    ) {
        Text(text = status(), Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
        Row(Modifier.padding(horizontal = 16.dp)) {
            Text(text = acknowledge,
                Modifier
                    .weight(weight = 1F)
                    .align(CenterVertically)
            )
            var checked by remember { mutableStateOf(false) }
            Switch(checked = checked, onCheckedChange = {
                checked = it
            })
        }
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
private fun preview() {
    TrainingSampleTheme {
        MainScreenLayout(
            status = { "Data is loading" },
            acknowledge = "Tutorial body",
            nextClicked = {},
            settingsClicked = {},
        )
    }
}
