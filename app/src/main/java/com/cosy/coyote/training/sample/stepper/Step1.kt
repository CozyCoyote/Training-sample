package com.cosy.coyote.training.sample.stepper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cosy.coyote.training.sample.R
import com.cosy.coyote.training.sample.components.Toolbar

@Composable
fun Step1(
    goToStep2: () -> Unit,
    goBack: () -> Unit,
) = Scaffold(
    topBar = { Toolbar("Stepper", R.drawable.ic_back, goBack) }
) {
    Box(modifier = Modifier.padding(it)) {
        Text(text = "Step 1", Modifier.clickable {
            goToStep2()
        })
    }
}
