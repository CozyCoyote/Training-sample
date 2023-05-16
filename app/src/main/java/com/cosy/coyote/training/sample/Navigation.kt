package com.cosy.coyote.training.sample

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cosy.coyote.training.sample.settings.SettingsScreen
import com.cosy.coyote.training.sample.stepper.includeStepperGraph

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) = NavHost(navController = navController, startDestination = "main_screen") {
    composable("main_screen") {
        MainScreen(
            goToStepper = { navController.navigate("stepper") },
            goToSettings = { navController.navigate("settings") }
        )
    }
    composable(
        "settings",
        deepLinks = listOf(createIntentFilter("android.intent.action.APPLICATION_PREFERENCES"))
    ) {
        SettingsScreen(goBack = {
            navController.navigateBack()
        })
    }
    includeStepperGraph(navController)
}

private fun createIntentFilter(action: String) = NavDeepLink.Builder().setAction(action).build()

fun NavController.navigateBack() {
    if (backQueue.size > 2) {
        popBackStack()
    }
}
