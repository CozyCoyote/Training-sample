package com.cosy.coyote.training.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() = Scaffold() {
    Box(modifier = Modifier.padding(it)) {
        Text(text = "Tut body")
    }
}
