package com.cosy.coyote.training.sample.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cosy.coyote.training.sample.R
import com.cosy.coyote.training.sample.RepoApi
import com.cosy.coyote.training.sample.components.Toolbar
import com.cosy.coyote.training.sample.data.Repo
import com.cosy.coyote.training.sample.data.SessionHolder
import com.cosy.coyote.training.sample.ui.theme.TrainingSampleTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    goBack: () -> Unit,
    vm: SettingsViewModel = viewModel()
) {
    SettingsLayout(goBack)
}

@Composable
private fun SettingsLayout(homeClick: () -> Unit) = Scaffold(
    topBar = {
        Toolbar(title = "Settings", homeIcon = R.drawable.ic_back, homeClick = homeClick)
    }
) {
    Box(modifier = Modifier.padding(it)) {
        Text("my app settings")
    }
    val repo: RepoApi = SessionHolder.get()
    LaunchedEffect(key1 = "test", block = {
        repo.accounts()
        SessionHolder.clear()
    })
}

@Preview
@Composable
private fun preview() {
    TrainingSampleTheme {
        SettingsLayout(
            homeClick = {},
        )
    }
}
