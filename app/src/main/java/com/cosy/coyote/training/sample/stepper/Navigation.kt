package com.cosy.coyote.training.sample.stepper

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cosy.coyote.training.sample.navigateBack

fun NavGraphBuilder.includeStepperGraph(navController: NavController) {
    navigation(startDestination = "step1", route = "stepper") {
        composable("step1") {
            Step1(goToStep2 = {
                navController.navigate("step2")
            }, goBack = {
                navController.navigateBack()
            })
        }
        composable("step2") {
            Step2(goBack = {
                navController.navigateBack()
            })
        }
    }
}
