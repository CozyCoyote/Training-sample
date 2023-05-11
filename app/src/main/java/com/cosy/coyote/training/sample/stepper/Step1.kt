package com.cosy.coyote.training.sample.stepper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cosy.coyote.training.sample.components.Toolbar

@Composable
fun Step1()= Scaffold(
    topBar = { Toolbar("Stepper") }
) {
    val nav = rememberNavController()
    Box(modifier = Modifier.padding(it)) {
        Text(text = "Step 1", Modifier.clickable {
            nav.navigate("step2")
        })
    }
}
