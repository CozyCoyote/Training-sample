package com.cosy.coyote.training.sample.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.cosy.coyote.training.sample.R
import com.cosy.coyote.training.sample.ui.theme.TrainingSampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String
) = TopAppBar(
    title = { Text(title) },
    navigationIcon = {
//        val navController = rememberNavController()
        IconButton(onClick = {
//            navController.navigateUp()
        }) {
            Icon(painter = painterResource(R.drawable.forest),
                contentDescription = "none",
                tint = Color(0xFF008800)
            )
        }
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSecondary
    ),
)

@Preview
@Composable
private fun preview() {
    TrainingSampleTheme {
        Toolbar(title = "Demo title")
    }
}
