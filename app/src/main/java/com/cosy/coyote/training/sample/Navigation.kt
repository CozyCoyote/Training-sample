package com.cosy.coyote.training.sample

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cosy.coyote.training.sample.stepper.Step1
import com.cosy.coyote.training.sample.stepper.Step2

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) = NavHost(navController = navController, startDestination = "main_screen") {
    composable("main_screen") {
        MainScreen(goToStepper = {
            navController.navigate("stepper")
        })
    }
    composable("stepper") {
        Step1()
    }
    composable("step2") {
        Step2()
    }
}
